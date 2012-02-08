package ch.zhaw.ias.dito.util;

import java.util.Random;

public class NoiseGenerator {
  
  public static double[][] generateSymmetricNoiseMatrix(int dim, double maxAmp) {
    return generateSymmetricNoiseMatrix(dim, 0.0, maxAmp);
  }
  
  /**
   * Creates a square, symmetric random noise matrix of dimension n with 0 on the diagonal. 
   * The noise has uniform distribution ranging from minAmp to maxAmp.
   * @param len
   * @param minAmp minimum amplitude
   * @param maxAmp maximum amplitude
   * @return
   */
  public static double[][] generateSymmetricNoiseMatrix(int dim, double minAmp, double maxAmp) {
    Random r = new Random();
    maxAmp = maxAmp - minAmp;
    double[][] values = new double[dim][dim];
    for (int i = 0; i < dim; i++) {
      for (int j = i + 1; j < dim; j++) {
        double value = r.nextDouble() * maxAmp + minAmp;
        values[i][j] = value;
        values[j][i] = value;
      }
    }
    return values;
  }
  
}
