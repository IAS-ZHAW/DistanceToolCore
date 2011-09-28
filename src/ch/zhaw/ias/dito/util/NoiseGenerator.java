package ch.zhaw.ias.dito.util;

import java.util.Random;

public class NoiseGenerator {
  
  /**
   * Creates a square, symmetric random noise matrix of dimension n with 0 on the diagonal. 
   * The noise has uniform distribution ranging from 0 to maxNoise.
   * @param len
   * @param maxNoise
   * @return
   */
  public static double[][] generateSymmetricNoiseMatrix(int dim, double maxAmp) {
    Random r = new Random();
    double[][] values = new double[dim][dim];
    for (int i = 0; i < dim; i++) {
      for (int j = i + 1; j < dim; j++) {
        double value = r.nextDouble() * maxAmp;
        values[i][j] = value;
        values[j][i] = value;
      }
    }
    return values;
  }
  
}
