package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.SoergelDist;
import junit.framework.TestCase;

public class TestSoergelDist extends TestCase {
  private SoergelDist dist;
  
  @Override
  protected void setUp() throws Exception {
    dist = new SoergelDist();
  }
    
  public void testDVector() {
    DVector v1 = new DVector(3,9);
    DVector v2 = new DVector(4,11);
    assertEquals(dist.distance(v1, v2), 0.1);
    
    v1 = new DVector(3,Double.NaN, 9, 11);
    v2 = new DVector(4, 7, 11, Double.NaN);
    assertEquals(dist.distance(v1, v2), 0.1);
    
    /*v1 = new DVector(-5, 10);
    v2 = new DVector(10, 5);
    assertEquals(dist.distance(v1, v2), 0.5); */   
  }
  
  public void testMatrix() {
    DVector v1 = new DVector(3,Double.NaN, 9, 11);
    DVector v2 = new DVector(4, 7, 11, Double.NaN);
    
    Matrix m = new Matrix(v1, v2);
    Matrix distM = m.calculateDistance(dist, true);
    assertEquals(distM, Matrix.createDoubleMatrix(new double[][] {{0.0, 0.1}, {0.1, 0.0}}));
  }
}
