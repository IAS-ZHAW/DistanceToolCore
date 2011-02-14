package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.CanberraDist;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import ch.zhaw.ias.dito.dist.ManhattanDist;
import ch.zhaw.ias.dito.dist.WaveHedgesDist;

public class TestComplexMatrix extends TestCase {
	public void testReadFromFile() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m3x3.csv"), ';');
		assertEquals(m, new Matrix(new double[][] {{4.0, 5.0, 667}, {4.5, 30.25, 3}, {1.2, Double.NaN, 100.01}}));
	}
	
	public void testLargeManhattan() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m10x10mul.csv"), ';');
		assertEquals(m.transpose().calculateDistance(new ManhattanDist()), Matrix.readFromFile(new File("./testdata/m10x10mul-manhattan.csv"), ';'));
		
		m = Matrix.readFromFile(new File("./testdata/m10x10add.csv"), ';');
		assertEquals(m.transpose().calculateDistance(new ManhattanDist()), Matrix.readFromFile(new File("./testdata/m10x10add-manhattan.csv"), ';'));
	}	
	
	public void testLargeCanberra() throws IOException {
	  Matrix m = Matrix.readFromFile(new File("./testdata/m10x10add.csv"), ';');
    Matrix dist = m.transpose().calculateDistance(new CanberraDist());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m10x10add-canberra.csv"), ','), 3), true);
	}
	
	public void testLargeWaveHeges() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m100x10mul.csv"), ',');
    Matrix dist = m.transpose().calculateDistance(new WaveHedgesDist());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m100x10mul-wavehedges.csv"), ','), 4), true);
	}
	
  public void testLargeEuklid() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m200x130rand.csv"), ',');
    Matrix dist = m.transpose().calculateDistance(new EuklidianDist());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m200x130rand-euklid.csv"), ','), 4), true);
  }	
  
  public void testLargeManhattanNaN() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m10x10addNaN.csv"), ';');
    Matrix dist = m.transpose().calculateDistance(new ManhattanDist());
    assertEquals(dist, Matrix.readFromFile(new File("./testdata/m10x10addNaN-Manhattan.csv"), ';'));
  } 

  /*public void testSmartvoteEuklid() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m1271x70smartvote.csv"), ',');
    Matrix dist = m.transpose().calculateDistance(new EuklidianDist());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m1271x70smartvote-euklid.csv"), ','), 4), true);
  } */
  
  
}
