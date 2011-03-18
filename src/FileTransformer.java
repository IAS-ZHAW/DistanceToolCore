import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import au.com.bytecode.opencsv.CSVReader;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;


public class FileTransformer {
  public static void main(String... args) {
        
    CSVReader reader;
    try {
      reader = new CSVReader(new FileReader("c:/atizo_k-kiosk-21_02_2011-518.csv"), ',');
      String [] nextLine;
      List<DVector> vectors = new ArrayList<DVector>();
        while ((nextLine = reader.readNext()) != null) {
          double[] values = new double[nextLine.length];
            for (int i = 0; i < values.length; i++) {
              System.out.print(nextLine[i] + "\t");
              /*if (nextLine[i].length() == 0 || nextLine[i].isEmpty() == true || " ".equals(nextLine[i])) {
                values[i] = Float.NaN;
              } else {
                values[i] = Double.parseDouble(nextLine[i]);
              }*/
            }
            System.out.println();
        }      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
