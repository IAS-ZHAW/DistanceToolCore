package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.DivergenceDist;

public class TestDivergenceDist extends TestCase {
	private DivergenceDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new DivergenceDist();
	}
		
	public void testDVector() {
		DVector v1 = new DVector(1,9);
		DVector v2 = new DVector(4,11);
		assertEquals(dist.distance(v1, v2), 0.185);
		
    v1 = new DVector(1,Double.NaN, 9, 11);
    v2 = new DVector(4, 7, 11, Double.NaN);
    assertEquals(dist.distance(v1, v2), 0.0925);		
	}
	
  
  public void testZeroValues() {
    DVector v1 = new DVector(0,Double.NaN, 9, 11);
    DVector v2 = new DVector(0, 7, 11, Double.NaN);
    
    Matrix m = new Matrix(v1, v2);
    Matrix distM = m.calculateDistance(dist, true);
    assertEquals(distM, Matrix.createDoubleMatrix(new double[][] {{0.0, 0.0025}, {0.0025, 0.0}}));    
  }
}
