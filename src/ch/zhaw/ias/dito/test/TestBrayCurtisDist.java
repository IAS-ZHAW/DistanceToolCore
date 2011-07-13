package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.BrayCurtisDist;
import junit.framework.TestCase;

public class TestBrayCurtisDist extends TestCase {
  private BrayCurtisDist dist;
  
  @Override
  protected void setUp() throws Exception {
    dist = new BrayCurtisDist();
  }
    
  public void testDVector() {
    DVector v1 = new DVector(1,9);
    DVector v2 = new DVector(4,11);
    assertEquals(dist.distance(v1, v2), 0.1);
    
    v1 = new DVector(1,Double.NaN, 9, 11);
    v2 = new DVector(4, 7, 11, Double.NaN);
    assertEquals(dist.distance(v1, v2), 0.05);
    
    v1 = new DVector(-5, 10);
    v2 = new DVector(10, 5);
    assertEquals(dist.distance(v1, v2), 0.5);    
  }
  
  public void testMatrix() {
    DVector v1 = new DVector(1,Double.NaN, 9, 11);
    DVector v2 = new DVector(4, 7, 11, Double.NaN);
    
    Matrix m = new Matrix(v1, v2);
    Matrix distM = DistanceTestCaseHelper.compareNormalParallel(dist, m);
    assertEquals(distM, Matrix.createDoubleMatrix(new double[][] {{0.0, 0.05}, {0.05, 0.0}}));
  }
  
  public void testZeroValues() {
    DVector v1 = new DVector(0,Double.NaN, 9, 11);
    DVector v2 = new DVector(0, 7, 11, Double.NaN);
    
    Matrix m = new Matrix(v1, v2);
    Matrix distM = DistanceTestCaseHelper.compareNormalParallel(dist, m);
    assertEquals(distM, Matrix.createDoubleMatrix(new double[][] {{0.0, 0.025}, {0.025, 0.0}}));    
  }
}
