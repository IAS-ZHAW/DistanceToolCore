package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import ch.zhaw.ias.dito.dist.WaveHedgesDist;
import junit.framework.TestCase;

public class TestMatrix extends TestCase {
	private DVector a;
	private DVector b;
	private Matrix m;
	private Matrix m23;
	private Matrix m33;
	
	public void setUp() {
		a = new DVector(1,2);
		b = new DVector(3,4);
		m = new Matrix(a, b);
		m23 = new Matrix(new double[][] {{1,2,3}, {4,5,6}});
		m33 = new Matrix(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}});
	}
	
	public void testConstructor() {
		try {
			new Matrix(new DVector(3,4), new DVector(3,4), new DVector(5,6,7));
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testArrayConstructor() {
		Matrix m2 = new Matrix(new double[][] {{1,2}, {3,4}});
		assertEquals(m2.col(0), a);
		assertEquals(m2.col(1), b);
	}	
	
	public void testCheckLengths() {
		assertEquals(Matrix.checkLengths(), true);
		assertEquals(Matrix.checkLengths(new DVector(3,4)), true);
		assertEquals(Matrix.checkLengths(new DVector(3,4), new DVector(5,6)), true);
		assertEquals(Matrix.checkLengths(new DVector(3,4), new DVector(3,4), new DVector(5,6,7)), false);
	}
	
	public void testCol() {
		assertEquals(m.col(0), a);
		assertEquals(m.col(1), b);
		try {
			m.col(2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}
	
	public void testRow() {
		assertEquals(m.row(0), new DVector(1,3));
		assertEquals(m.row(1), new DVector(2,4));
		try {
			m.row(2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}		
	}
	
	public void testGetRowCount() {
		assertEquals(m.getRowCount(), 2);
		assertEquals(m23.getRowCount(), 3);
	}
	
	public void testGetColCount() {
		assertEquals(m23.getColCount(), 2);
		assertEquals(m33.getColCount(), 3);
	}	
	
	public void testEquals() {
		assertEquals(m.equals(null), false);
		assertEquals(m.equals("asfd"), false);
		assertEquals(m.equals(new Matrix(new double[][]{{1,2}, {3,4}})), true);
		assertEquals(m.equals(new Matrix(new double[][]{{1,2}, {3,5}})), false);
		assertEquals(m.equals(new Matrix(new double[][]{{1,2}})), false);
		assertEquals(m.equals(m), true);
	}
	
	public void testTranspose() {
		assertEquals(m23.transpose(), new Matrix(new double[][] {{1,4}, {2,5}, {3,6}}));
		assertEquals(m23.transpose().transpose(), m23);
	}
	
	public void testCalculateDistance() {
		m = new Matrix(new double[][]{{1,4},{8,4}});
		Matrix distance = m.calculateDistance(new EuklidianDist());
		assertEquals(distance, new Matrix(new double[][] {{0,5}, {5,0}}));
	}
	
	public void testToString() {
		assertEquals(m.toString(), "((1.0,2.0),(3.0,4.0))");
	}
	
	public void testIsSquare() {
		assertEquals(m33.isSquare(), true);
		assertEquals(m23.isSquare(), false);
	}
	
	public void testNaN() {
		Matrix nanM = new Matrix(new double[][] {{Double.NaN,4}, {Double.NaN,5}, {3,6}});
		assertEquals(nanM.transpose(), new Matrix(new double[][] {{Double.NaN, Double.NaN, 3}, {4.0, 5, 6}}));
		Matrix dist = nanM.calculateDistance(new EuklidianDist());
		assertEquals(dist, new Matrix(new double[][] {{0.0, 3.0}, {3.0, 0.0}}));
	}
	
	public void testRoundedEquals() {
    Matrix floating = new Matrix(new double[][] {{1.1254,2,3.754}, {4,5,6}});
    assertEquals(m23.equalsRounded(floating, 0), false);
    floating = new Matrix(new double[][] {{1.1254,2,3.454}, {4,5,6}});
    assertEquals(m23.equalsRounded(floating, 0), true);
    assertEquals(m23.equalsRounded(floating, 1), false);
	}
	
	public void testEqualDimensions() {
	  assertEquals(m33.equalDimensions(m33), true);
	  assertEquals(m33.equalDimensions(new Matrix(new double[][]{{0,0,0}, {0,0,0}, {0,0,0}})), true);
	  assertEquals(m33.equalDimensions(m23), false);
	  assertEquals(m33.equalDimensions(m), false);
	}
	
	public void testRoundingProblem() {
	  DVector col2 = new DVector(4,8,16,32,64,128,256,512,1024,2048,4096);
	  DVector col64 = new DVector(128,256,512,1024,2048,4096,8192,16384,32768,65536,131072);
	  Matrix m = new Matrix(col2, col64);
	  Matrix dist = m.transpose().calculateDistance(new WaveHedgesDist());
	  assertEquals(dist, new Matrix(new double[][]{{0,10.65625}, {10.65625, 0}}));
	}
}
