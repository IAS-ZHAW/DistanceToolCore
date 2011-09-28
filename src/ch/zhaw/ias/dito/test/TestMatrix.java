package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.DivergenceDist;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import ch.zhaw.ias.dito.dist.WaveHedgesDist;

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
		m23 = Matrix.createDoubleMatrix(new double[][] {{1,2,3}, {4,5,6}});
		m33 = Matrix.createDoubleMatrix(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}});
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
	  double[][] values = new double[][] {{1,2}, {3,4}};
		Matrix m2 = Matrix.createDoubleMatrix(values);
		assertEquals(m2.col(0), a);
		assertEquals(m2.col(1), b);
	}	
	
	public void testArrayListConstructor() {
	  ArrayList<DVector> vecs = new ArrayList<DVector>();
	  vecs.add(new DVector(1,3,10));
	  Matrix m = new Matrix(vecs);
	  vecs.add(new DVector(1,3,10));
	  assertEquals(m.getColCount(), 1);
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
		assertEquals(m.equals(Matrix.createDoubleMatrix(new double[][]{{1,2}, {3,4}})), true);
		assertEquals(m.equals(Matrix.createDoubleMatrix(new double[][]{{1,2}, {3,5}})), false);
		assertEquals(m.equals(Matrix.createDoubleMatrix(new double[][]{{1,2}})), false);
		assertEquals(m.equals(m), true);
	}
	
	public void testTranspose() {
		assertEquals(m23.transpose(), Matrix.createDoubleMatrix(new double[][] {{1,4}, {2,5}, {3,6}}));
		assertEquals(m23.transpose().transpose(), m23);
		Matrix binaryM = Matrix.createDoubleMatrix(new double[][]{{0,1},{1,0}});
		assertEquals(binaryM.transpose().transpose(), binaryM);
	}
	
	public void testCalculateDistance() {
		m = Matrix.createDoubleMatrix(new double[][]{{1,4},{8,4}});
		Matrix distance = DistanceTestCaseHelper.compareNormalParallel(new EuklidianDist(), m.transpose()); 
		assertEquals(distance, Matrix.createDoubleMatrix(new double[][] {{0,5}, {5,0}}));
	}
	
	public void testToString() {
		assertEquals(m.toString(), "((1.0,2.0),(3.0,4.0))");
	}
	
	public void testIsSquare() {
		assertEquals(m33.isSquare(), true);
		assertEquals(m23.isSquare(), false);
	}
	
	public void testNaN() {
		Matrix nanM = Matrix.createDoubleMatrix(new double[][] {{Double.NaN,4}, {Double.NaN,5}, {3,6}});
		assertEquals(nanM.transpose(), Matrix.createDoubleMatrix(new double[][] {{Double.NaN, Double.NaN, 3}, {4.0, 5, 6}}));
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new EuklidianDist(), nanM.transpose()); 
		assertEquals(dist, Matrix.createDoubleMatrix(new double[][] {{0.0, 3.0}, {3.0, 0.0}}));
	}
	
	public void testRoundedEquals() {
    Matrix floating = Matrix.createDoubleMatrix(new double[][] {{1.1254,2,3.754}, {4,5,6}});
    assertEquals(m23.equalsRounded(floating, 0), false);
    floating = Matrix.createDoubleMatrix(new double[][] {{1.1254,2,3.454}, {4,5,6}});
    assertEquals(m23.equalsRounded(floating, 0), true);
    assertEquals(m23.equalsRounded(floating, 1), false);
	}
	
  public void testRemoveMean() {
    assertEquals(m33.removeMeans(), Matrix.createDoubleMatrix(new double[][] {{-1,0,1}, {-1,0,1}, {-1,0,1}}));
  }
  
  public void testCovariance() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1, 20, 3}, {4, -4, 6}, {7, -7, -9}});
    assertEquals(m.covariance(), Matrix.createDoubleMatrix(new double[][] {{109,-53,-44}, {-53,28,10}, {-44,10,76}}));
  }

  public void testCovarianceBig() {
    try {
      Matrix m = Matrix.readFromFile(new File("testdata/m150x4irisFlower.csv"), ';').getMatrix();
      Matrix reference = Matrix.readFromFile(new File("testdata/m4x4-iris-covariances.csv"), ',').getMatrix();
      assertTrue(m.transpose().covariance().equalsRounded(reference, 3));
    } catch (IOException e) {
      fail();
    }
  }
  
  public void testCorrelationCoeffsBig() {
    try {
      Matrix m = Matrix.readFromFile(new File("testdata/m150x4irisFlower.csv"), ';').getMatrix();
      Matrix reference = Matrix.readFromFile(new File("testdata/m4x4-iris-corrcoef.csv"), ',').getMatrix();
      assertTrue(m.transpose().correlationCoeffs().equalsRounded(reference, 3));
    } catch (IOException e) {
      fail();
    }
  }
  
  public void testCorrelationCoeffs() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1, 20, 3}, {4, 5, 6}, {7, 8, 9}});
    assertTrue(m.correlationCoeffs().equalsRounded(Matrix.createDoubleMatrix(new double[][] {{1.0,0.0958,0.0958}, {0.0958,1.0,1.0}, {0.0958,1.0,1.0}}), 3));
  }  
	
	public void testEqualDimensions() {
	  assertEquals(m33.equalDimensions(m33), true);
	  assertEquals(m33.equalDimensions(Matrix.createDoubleMatrix(new double[][]{{0,0,0}, {0,0,0}, {0,0,0}})), true);
	  assertEquals(m33.equalDimensions(m23), false);
	  assertEquals(m33.equalDimensions(m), false);
	}
	
	public void testRoundingProblem() {
	  DVector col2 = new DVector(4,8,16,32,64,128,256,512,1024,2048,4096);
	  DVector col64 = new DVector(128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072);
	  Matrix m = new Matrix(col2, col64);
	  Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new WaveHedgesDist(), m); 
	  assertEquals(dist, Matrix.createDoubleMatrix(new double[][]{{0,10.65625}, {10.65625, 0}}));
	}
	
  public void testAutoRescale() {
    m = Matrix.createDoubleMatrix(new double[][] {{0, 1, 2}, {0, 3, 4}});
    assertEquals(m.autoRescale(), Matrix.createDoubleMatrix(new double[][] {{0, 0.5, 1.0}, {0, 0.75, 1.0}}));
  }
  
  public void testToBinary() {
    m = Matrix.createDoubleMatrix(new double[][] {{0, 1, Double.NaN}, {0.5, 3, -1}});
    assertEquals(m.toBinary(), Matrix.createDoubleMatrix(new double[][] {{0, 1, Double.NaN}, {1, 1, Double.NaN}}));
  }
  
  public void testExtremum() {
    m = Matrix.createDoubleMatrix(new double[][] {{0, 1, -2}, {0, Double.NaN, 4}});
    assertEquals(m.extremum(true), 4.0);
    assertEquals(m.extremum(false), -2.0);
  }
  
  public void testCompareZeroVectors() {
    m = Matrix.createDoubleMatrix(new double[][] {{0, 0}, {Double.NaN, 4}});
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new DivergenceDist(), m); 
    assertEquals(dist, Matrix.createDoubleMatrix(new double[][] {{0, 0.5}, {0.5, 0}}));
  }
  
  public void testToJama() throws IOException {
    Matrix m = Matrix.readFromFile(new File("testdata/m4x4citiesDenmark-euklid.csv"), ' ').getMatrix();
    Jama.Matrix jamaM = m.toJama();
    Matrix clone = Matrix.createDoubleMatrix(jamaM.getArray());
    assertEquals(m, clone);
  }
  
  public void testRandomSample() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m200x130rand.csv"), ',').getMatrix();
    Matrix sample = m.getRandomSample(10);
    assertEquals(sample.getColCount(), 10);
    assertEquals(sample.getRowCount(), 130);
    sample = m.getRandomSample(100);
    assertEquals(sample.getColCount(), 100);
    assertEquals(sample.getRowCount(), 130);
    sample = m.getRandomSample(200);
    assertEquals(sample.getColCount(), 200);
    assertTrue(sample == m);
    sample = m.getRandomSample(201);
    assertEquals(sample.getColCount(), 200);
    assertTrue(sample == m);
  }
  
  public void testDuplicateRandomSample() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1.0,2.0}, {1.0, 2.0}, {1.0, 2.0}});
    Matrix sample = m.getRandomSample(2);
    assertEquals(sample.getColCount(), 2);
  }
  
  public void testContainsSpecial() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1.0,2.0}, {1.0, 2.0}, {1.0, 2.0}});
    assertEquals(m.containsSpecial(), false);
    m = Matrix.createDoubleMatrix(new double[][] {{1.0,2.0}, {1.0, 2.0}, {Double.NaN, 2.0}});
    assertEquals(m.containsSpecial(), true);
    m = Matrix.createDoubleMatrix(new double[][] {{1.0,2.0}, {Double.NEGATIVE_INFINITY, 2.0}, {1.0, 2.0}});
    assertEquals(m.containsSpecial(), true);
  }
  
  public void testGetMinExcludingZero() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{0.0, 1.0, 0.0}, {3.0, 0.0, 0.0}, {0.0, 0.0, 4.0}});
    assertEquals(m.getMinExcludingZero(), 1.0);
  }
  
  
  public void testAddSymmetricRandomNoise() throws IOException {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1.0, 2.0, 0.0}, {2.0, 3.0, 1.0}, {0.0, 1.0, 4.0}});
    Matrix noised = m.addSymmetricRandomNoise();
    assertTrue(noised.col(2).component(0) > 0.0);
    
    m = Matrix.readFromFile(new File("testdata/m119x119cities-euklid.csv"), ';').getMatrix();
    noised = m.addSymmetricRandomNoise();
    double min = m.getMinExcludingZero();
    assertEquals(m.getColCount(), 119);
    assertEquals(m.getRowCount(), 119);
    assertTrue(noised.extremum(false) - m.extremum(false) < min / 10.0);
    assertTrue(noised.extremum(true) - m.extremum(true) < min / 10.0);
    assertEquals(m.transpose(), m);
    m = Matrix.readFromFile(new File("testdata/m150x4irisFlower.csv"), ',').getMatrix();
    try {
      m.addSymmetricRandomNoise();
      fail();
    } catch (IllegalStateException e) {
      assertTrue(true);
    }
  }
}
