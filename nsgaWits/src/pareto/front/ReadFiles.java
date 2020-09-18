package pareto.front;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jmetal.util.ExtractParetoFront;

public class ReadFiles {

	public ReadFiles() {
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public ArrayList<ArrayList<String>> readFile(String path) {
		/*
		 * exponent values are: 4, 5, 6, 7, 8, 9, 10
		 */
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		
		File readVals = new File(path);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(readVals);
			br = new BufferedReader(fr);			
			
			String line = null;
			int lineCount = 0;
			line = br.readLine();
			line = br.readLine();
			
			while(line != null) {
				String[] obValue = line.split(",");
				ArrayList<String> value = new ArrayList<String>();
				for(int i = 0; i < obValue.length; i++) {
					value.add(obValue[i]);
				}
				
				values.add(value);
				line = br.readLine();
				lineCount++;
			}
			
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values; 		
	}
	
	public void conversionForParetoFront(ArrayList<ArrayList<String>> accValues, ArrayList<ArrayList<String>> disValues, String forPareto) {
		File writeVals = new File(forPareto + "pairSolutions.txt"); 
		try {
			FileWriter fw = new FileWriter(writeVals);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < accValues.size(); i++) {
				
				ArrayList<String> obj1 = accValues.get(i); //obj1 is acc-loss
				ArrayList<String> obj2 = disValues.get(i); //obj2 is distortion
				//get both values
				for(int j = 1; j < obj1.size(); j++) { // because first entry is iternation no.
					String s1 = obj1.get(j);
					String s2 = obj2.get(j);
					bw.write(s1 + "\t" + s2);
					if((i == accValues.size()-1) && (j == obj1.size()-1)) {
						System.out.println("this program will end");
					} else {
						bw.newLine();
					}					
				}				
			}
			bw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
