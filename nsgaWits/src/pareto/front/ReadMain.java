package pareto.front;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import jmetal.core.SolutionSet;
import jmetal.util.Configuration;
import jmetal.util.ExtractParetoFront;

public class ReadMain extends ExtractParetoFront {

	private static String path = "F:\\manSc\\WITS15\\simulation data\\avg forNSGA\\";
	public static Logger      logger_ ;
	private static ArrayList<ArrayList<String>> accValues;
	private static ArrayList<ArrayList<String>> disValues;

	public ReadMain(String name, int dimensions) {
		super(name, dimensions);
		// TODO Auto-generated constructor stub
	}

	public static void preparingData() {

		String accObj = path + "accLoss8nsga.csv";
		String disObj = path + "JSD8nsga.csv";

		ReadFiles rf = new ReadFiles(); 
		accValues = rf.readFile(accObj); 
		disValues = rf.readFile(disObj);
		//rf.conversionForParetoFront(accValues, disValues, path);
	}

	public static void timeCalculation() {		
		logger_      = Configuration.logger_ ;
		long initTime = System.currentTimeMillis();
		ReadMain rm = new ReadMain(path+"pairSolutions.txt", 2);		
		rm.writeParetoFront();
		long estimatedTime = System.currentTimeMillis() - initTime;
		logger_.info("Total execution time in milliseconds: "+estimatedTime + " ms");
		logger_.info("Total execution time in seconds: "+ estimatedTime/1000 + " s");
	}

	public static void main(String[] args) {

		preparingData();
		String paretoFronts = path + "actualPareto.csv";

		try {
			File writeVals = new File(path + "paretoSolwithObs.csv"); 
			FileWriter fw = new FileWriter(writeVals);
			BufferedWriter bw = new BufferedWriter(fw);

			ReadFiles rf = new ReadFiles(); 
			ArrayList<ArrayList<String>> paretoFront = rf.readFile(paretoFronts);
			System.out.println("accValues size : " + accValues.get(0).size()); 
			System.out.println("paretoFront size : " + disValues.get(0).size()); 
			bw.write("accLoss"+","+"distortion"+","+"exponent"+","+"iteration"); 
			bw.newLine();

			double exponent;
			double iteration;

			for (int i = 0; i < paretoFront.size(); i++) {

				ArrayList<String> pareto = paretoFront.get(i);			
				String obj1 = pareto.get(0);
				String obj2 = pareto.get(1);				

				for(int j = 0; j < accValues.size(); j++) {
					ArrayList<String> accValue = accValues.get(j);
					ArrayList<String> disValue = disValues.get(j);

					if(accValue.get(1).equalsIgnoreCase(obj1) && disValue.get(1).equalsIgnoreCase(obj2)) {
						exponent = 0 + 4;
						iteration = j + 1;
						bw.write(accValue.get(1)+","+disValue.get(1)+","+exponent+","+iteration); 
						bw.newLine();
						break;
					} else if(accValue.get(2).equalsIgnoreCase(obj1) && disValue.get(2).equalsIgnoreCase(obj2)) {
						exponent = 1 + 4;
						iteration = j + 1;
						bw.write(accValue.get(2)+","+disValue.get(2)+","+exponent+","+iteration);
						bw.newLine();
						break;
					} else if(accValue.get(3).equalsIgnoreCase(obj1) && disValue.get(3).equalsIgnoreCase(obj2)) {
						exponent = 2 + 4;
						iteration = j + 1;
						bw.write(accValue.get(3)+","+disValue.get(3)+","+exponent+","+iteration);
						bw.newLine();
						break;
					} else if(accValue.get(4).equalsIgnoreCase(obj1) && disValue.get(4).equalsIgnoreCase(obj2)) {
						exponent = 3 + 4;
						iteration = j + 1;
						bw.write(accValue.get(4)+","+disValue.get(4)+","+exponent+","+iteration);
						bw.newLine();
						break;
					} else if(accValue.get(5).equalsIgnoreCase(obj1) && disValue.get(5).equalsIgnoreCase(obj2)) {
						exponent = 4 + 4;
						iteration = j + 1;
						bw.write(accValue.get(5)+","+disValue.get(5)+","+exponent+","+iteration); 
						bw.newLine();
						break;
					} else if(accValue.get(6).equalsIgnoreCase(obj1) && disValue.get(6).equalsIgnoreCase(obj2)) {
						exponent = 5 + 4;
						iteration = j + 1;
						bw.write(accValue.get(6)+","+disValue.get(6)+","+exponent+","+iteration);
						bw.newLine();
						break;
					} else if(accValue.get(7).equalsIgnoreCase(obj1) && disValue.get(7).equalsIgnoreCase(obj2)) {
						exponent = 6 + 4;
						iteration = j + 1;
						bw.write(accValue.get(7)+","+disValue.get(7)+","+exponent+","+iteration);
						bw.newLine();
						break;
					}
					else
						continue;					
				}
			}
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
