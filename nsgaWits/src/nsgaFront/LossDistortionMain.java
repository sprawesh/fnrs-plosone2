package nsgaFront;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;

public class LossDistortionMain {
	
	private static Logger logger_;
	private static FileHandler fileHandler_;
	
	private static int poulationSize_ = 40;
	private static int maxEvaluations_ = 10000;
	private static double mutationProbability_ = 0.1;
	private static double crossoverProbability_ = 0.9;
	

	
	public static void main(String[] args) throws Exception {
		
		Problem problem;
		Algorithm algorithm;
		Operator crossover;
		Operator mutation;
		Operator selection;
		
		HashMap<String, Double> parameters;
		QualityIndicator qi;
		
		logger_ = Configuration.logger_;
		fileHandler_ = new FileHandler("LossDistortion_main.log");
		logger_.addHandler(fileHandler_);
		
		problem = new LossDistortion("Integer");
		algorithm = new NSGAII(problem);
		
		algorithm.setInputParameter("populationSize", poulationSize_);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
		
		parameters = new HashMap<String, Double>();
		parameters.put("probability", mutationProbability_);
		crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters);
		
		parameters = new HashMap<String, Double>();
		parameters.put("probability", crossoverProbability_);
		mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters);
		
		parameters = null;
		selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);
		
		//add operators to algorithm
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);		
		
		//execute the algorithm
		long initTime = System.currentTimeMillis();
		SolutionSet population = algorithm.execute();
		long estimatedTime = System.currentTimeMillis() - initTime;
		
		 logger_.info("Total execution time: "+estimatedTime + "ms");
		 logger_.info("Variables values have been writen to file VAR");
		 population.printVariablesToFile("VAR");    
		 logger_.info("Objectives values have been writen to file FUN");
		 population.printObjectivesToFile("FUN");
		 
		 qi = new QualityIndicator(problem, "FUN"); 
		 algorithm.setInputParameter("indicators", qi);
		 System.out.println("final no. of bits : " + problem.getNumberOfBits()); 
		 
		 if (qi != null) {
		      logger_.info("Quality indicators") ;
		      logger_.info("Hypervolume: " + qi.getHypervolume(population)) ;
		      logger_.info("GD         : " + qi.getGD(population)) ;
		      logger_.info("IGD        : " + qi.getIGD(population)) ;
		      logger_.info("Spread     : " + qi.getSpread(population)) ;
		      logger_.info("Epsilon    : " + qi.getEpsilon(population)) ;  
		      
		      int evaluations = ((Integer)algorithm.getOutputParameter("evaluations")).intValue();
		      logger_.info("Speed      : " + evaluations + " evaluations") ;  
		 }
		      

	}

}
