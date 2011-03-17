package ch.zhaw.ias.dito;

import java.util.Arrays;

public enum Coding {
  BINARY,
  REAL;
  
  /**
   * 
   * @param index
   * @param len
   * @return
   */
  public static double[] getBinaryCoding(int index, int len) {
    if (index >= len) {
      throw new IllegalArgumentException("index " + index + " too big for length " + len);
    }
    double[] values = new double[len];
    if (index < 0) {
      Arrays.fill(values, Double.NaN);
      return values;
    }
    Arrays.fill(values, 0.0);
    values[index] = 1;
    return values;
  }
}
