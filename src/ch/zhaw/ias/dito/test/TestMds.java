/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.MdsDecomposition;
import junit.framework.TestCase;

public class TestMds extends TestCase {
  public void testSmallMatrix() {
    MdsDecomposition decomp = new MdsDecomposition(Matrix.createDoubleMatrix(new double[][]{{0.0}}));
    assertEquals(Matrix.createDoubleMatrix(decomp.getReducedDimensions()), Matrix.createDoubleMatrix(new double[][] {{0.0}}));
  }
  
  public void testGetJ() throws IOException {
    MdsDecomposition decomp = new MdsDecomposition(Matrix.createDoubleMatrix(new double[][]{{1,2}, {2, 3}}));
    Jama.Matrix j = decomp.getJMatrix(4);
    assertEquals(Matrix.createDoubleMatrix(j.getArray()), Matrix.createDoubleMatrix(new double[][]{{0.75, -0.25, -0.25, -0.25}, {-0.25, 0.75, -0.25, -0.25}, {-0.25, -0.25, 0.75, -0.25}, {-0.25, -0.25, -0.25, 0.75}}));    
  }
  
  public void testGetIndizesOfLargestEv() {
    double[] ev = new double[] {0, 5.31, 2.43, 3.41};
    MdsDecomposition decomp = new MdsDecomposition(Matrix.createDoubleMatrix(new double[][]{{1,2}, {2, 3}}));
    int[] indizes = decomp.getIndizesOfLargestEv(2, ev);
    assertEquals(indizes[0], 1);
    assertEquals(indizes[1], 3);
  } 
  
  public void testNegativeEv() {
    assertTrue(false);
  }
  
  public void testCitiesCase() throws IOException {
    Matrix m = Matrix.readFromFile(new File("testdata/m119x119cities-euklid.csv"), ';').getMatrix();
    MdsDecomposition decomp = new MdsDecomposition(m);
    double mds[][] = decomp.getReducedDimensions();
    Matrix mdsMatrix = Matrix.createDoubleMatrix(mds);
    Matrix exp = Matrix.readFromFile(new File("testdata/m119x2cities-mds.csv"), ';').getMatrix();
    assertTrue(mdsMatrix.equalsRounded(exp, 3));
  }
  
  public void testCitiesCase3dim() throws IOException {
    Matrix m = Matrix.readFromFile(new File("testdata/m119x119cities-euklid.csv"), ';').getMatrix();
    MdsDecomposition decomp = new MdsDecomposition(m);
    double mds[][] = decomp.getReducedDimensions(3);
    Matrix mdsMatrix = Matrix.createDoubleMatrix(mds);
    Matrix exp = Matrix.readFromFile(new File("testdata/m119x2cities-mds-3dim.csv"), ' ').getMatrix();
    assertTrue(mdsMatrix.equalsRounded(exp, 3));
  }      
}
