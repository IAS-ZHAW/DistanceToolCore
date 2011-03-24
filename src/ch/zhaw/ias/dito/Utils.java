package ch.zhaw.ias.dito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Utils {
  private Utils() {
    
  }
  
  public static String toCommaSeparatedString(Iterator<? extends Object> i) {
    StringBuilder sb = new StringBuilder();
    for (; i.hasNext(); ) {
      sb.append(i.next().toString());
      if (i.hasNext() == true) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }
  
  public static String toCommaSeparatedString(double[] list) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.length; i++) {
      sb.append(Double.toString(list[i]));
      if ((i + 1) < list.length) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  public static double[] splitToDouble(String input) {
    Scanner s = new Scanner(input);
    s.useDelimiter("(,|;)\\s*");
    List<Double> values = new ArrayList<Double>();
    while (s.hasNext()) {
      if (s.hasNextDouble()) {
        values.add(s.nextDouble());
      } else {
        //ignore
        s.next();
      }   
    }
    double[] converted = new double[values.size()];
    for (int i = 0; i < converted.length; i++) {
      converted[i] = values.get(i);
    }
    return converted;
  }  
}
