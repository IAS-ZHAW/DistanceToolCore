package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.util.NoiseGenerator;

import junit.framework.TestCase;

public class TestNoiseGenerator extends TestCase {
  public void testGenerateSymmetricNoiseMatrix () {
    int size = 10;
    double[][] noise = NoiseGenerator.generateSymmetricNoiseMatrix(size, 0.5);
    Matrix m = Matrix.createDoubleMatrix(noise);
    assertEquals(m.getColCount(), size);
    assertEquals(m.isSquare(), true);
    assertEquals(m.transpose(), m);
    assertTrue(m.extremum(true) <= 0.5);
    assertTrue(m.extremum(false) >= 0.0);
    //ensure diagnoal elements are 0
    for (int i = 0; i < size; i++) {
      assertEquals(m.col(i).component(i), 0.0);
    }
  }
}
