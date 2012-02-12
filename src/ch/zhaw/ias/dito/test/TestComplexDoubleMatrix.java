/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.CanberraDist;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import ch.zhaw.ias.dito.dist.ManhattanDist;
import ch.zhaw.ias.dito.dist.WaveHedgesDist;

public class TestComplexDoubleMatrix extends TestCase {
	public void testReadFromFile() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m3x3.csv"), ';').getMatrix();
		assertEquals(m, Matrix.createDoubleMatrix(new double[][] {{4.0, 5.0, 667}, {4.5, 30.25, 3}, {1.2, Double.NaN, 100.01}}));
	}
	
	public void testLargeManhattan() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m10x10mul.csv"), ';').getMatrix();
		Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new ManhattanDist(), m);
		assertEquals(dist, Matrix.readFromFile(new File("./testdata/m10x10mul-manhattan.csv"), ';').getMatrix());
		
		m = Matrix.readFromFile(new File("./testdata/m10x10add.csv"), ';').getMatrix();
		dist = DistanceTestCaseHelper.compareNormalParallel(new ManhattanDist(), m);
		assertEquals(dist, Matrix.readFromFile(new File("./testdata/m10x10add-manhattan.csv"), ';').getMatrix());
	}	
	
	public void testLargeCanberra() throws IOException {
	  Matrix m = Matrix.readFromFile(new File("./testdata/m10x10add.csv"), ';').getMatrix();
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new CanberraDist(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m10x10add-canberra.csv"), ',').getMatrix(), 3), true);
	}
	
	public void testLargeWaveHeges() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m100x10mul.csv"), ',').getMatrix();
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new WaveHedgesDist(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m100x10mul-wavehedges.csv"), ',').getMatrix(), 4), true);
	}
	
  public void testLargeEuklid() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m200x130rand.csv"), ',').getMatrix();
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new EuklidianDist(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m200x130rand-euklid.csv"), ',').getMatrix(), 4), true);
  }	
  
  public void testLargeManhattanNaN() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m10x10addNaN.csv"), ';').getMatrix();
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(new ManhattanDist(), m);
    assertEquals(dist, Matrix.readFromFile(new File("./testdata/m10x10addNaN-Manhattan.csv"), ';').getMatrix());
  } 

  /*public void testSmartvoteEuklid() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m1271x70smartvote.csv"), ',');
    Matrix dist = m.transpose().calculateDistance(new EuklidianDist());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m1271x70smartvote-euklid.csv"), ','), 4), true);
  } */  
}
