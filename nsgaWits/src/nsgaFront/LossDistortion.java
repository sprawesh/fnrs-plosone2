package nsgaFront;

import java.util.ArrayList;














import pareto.front.ReadFiles;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.ArrayIntSolutionType;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.BinarySolutionType;
import jmetal.encodings.solutionType.IntRealSolutionType;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

public class LossDistortion extends Problem {
	
	private ArrayList<ArrayList<String>> accValues;
	private ArrayList<ArrayList<String>> disValues;

	public LossDistortion() {
		// TODO Auto-generated constructor stub
	}

	public LossDistortion(String solutionType) {
		//super(solutionType);
		
		numberOfVariables_   = 2;
		numberOfObjectives_  = 2;
		numberOfConstraints_ = 0;
		problemName_         = "LossDistortion";
		
		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_[0] = 3.9;
		lowerLimit_[1] = 20000;
		upperLimit_[0] = 10.1;
		upperLimit_[1] = 50000;
		
		if(solutionType.compareToIgnoreCase("BinaryReal") == 0) {
			solutionType_ = new BinaryRealSolutionType(this);
		} else if(solutionType.compareToIgnoreCase("Real") == 0) {
			solutionType_ = new RealSolutionType(this);
		} else if(solutionType.compareToIgnoreCase("Binary") == 0) {
			solutionType_ = new BinarySolutionType(this);
		} else if(solutionType.compareToIgnoreCase("Integer") == 0) {
			solutionType_ = new IntSolutionType(this);
		} else if(solutionType.compareToIgnoreCase("ArrayInt") == 0) {
			solutionType_ = new ArrayIntSolutionType(this);
		} else if(solutionType.compareToIgnoreCase("IntReal") == 0) {
			solutionType_ = new IntRealSolutionType(this, 2, 0); 
		} else {
			System.out.println("Error: solution type " + solutionType + " invalid") ;
			System.exit(-1) ;
		}
		
		lengthTest();
		readObjs();
	}

	private void readObjs() {
		String path = "F:\\manSc\\WITS15\\simulation data\\avg forNSGA\\";
		String accObj = path + "accLoss8nsga.csv";
		String disObj = path + "JSD8nsga.csv";
		ReadFiles rf = new ReadFiles(); 
		accValues = rf.readFile(accObj);
		disValues = rf.readFile(disObj);
		
	}

	private void lengthTest() {
		System.out.println("lengthTest in TradeOffMetric, default precision " + getLength(0)); 
		System.out.println("total no. of bits " + getNumberOfBits());		
	}

	@Override
	public void evaluate(Solution solution) throws JMException {
		
		Variable[] variable  = solution.getDecisionVariables();
		double [] f = new double[numberOfObjectives_];
		
		int exponent = (int)variable[0].getValue();
		int iteration = (int)variable[1].getValue();
		f[0] = Double.parseDouble(accValues.get(iteration-20000).get(exponent-3));
		f[1] = Double.parseDouble(disValues.get(iteration-20000).get(exponent-3));
		
		solution.setObjective(0,f[0]);
		solution.setObjective(1,f[1]);	 
	}

}
