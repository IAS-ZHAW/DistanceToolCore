package ch.zhaw.ias.dito;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ias.dito.dist.CanberraDist;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import ch.zhaw.ias.dito.dist.ManhattanDist;

import au.com.bytecode.opencsv.CSVReader;

public class MainApp {
	public static void main(String... args) {		
	    CSVReader reader;
		try {
			reader = new CSVReader(new FileReader("./Daten/Smartvote/rohdaten2003-numeric.csv"), ';');
			String [] nextLine;
			List<DVector> vectors = new ArrayList<DVector>();
		    while ((nextLine = reader.readNext()) != null) {
		    	double[] values = new double[nextLine.length];
		        for (int i = 0; i < values.length; i++) {
		        	if (nextLine[i].length() == 0 || nextLine[i].isEmpty() == true || " ".equals(nextLine[i])) {
		        		values[i] = 0.0;
		        	} else {
		        		values[i] = Double.parseDouble(nextLine[i]);
		        	}
		        }
		        vectors.add(new DVector(values));
		    }
		    
		    Matrix m = new Matrix(vectors.toArray(new DVector[0]));
		    m = m.transpose();		
		    long start2 = System.currentTimeMillis();	        
		    EuklidianDist ed = new EuklidianDist();
		    Matrix dist = m.calculateDistance(ed);
		    System.out.println(System.currentTimeMillis() - start2);
		    
		    /*CSVWriter writer = new CSVWriter(new FileWriter("./Daten/Smartvote/rohdaten2003-numeric.csv"), ';');
		    Arrays.
		    writer.writeNext(arg0)*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
