/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import ch.zhaw.ias.dito.DecompositionException;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.MdsDecomposition;
import ch.zhaw.ias.dito.PcaDecomposition;
import junit.framework.TestCase;

public class TestPca extends TestCase {

  
  public void testCitiesCase3dim() throws IOException {
    Matrix m = Matrix.readFromFile(new File("testdata/m150x4irisFlower.csv"), ';').getMatrix();
    PcaDecomposition decomp = new PcaDecomposition(m.transpose());
    double mds[][] = decomp.getReducedDimensions(2);
    Matrix pcaMatrix = Matrix.createDoubleMatrix(mds);
    Matrix exp = Matrix.readFromFile(new File("testdata/m150x2irisFlower-pca.csv"), ',').getMatrix();
    assertTrue(pcaMatrix.equalsRounded(exp.transpose(), 3));
  }      
  
  public void testSpecialException() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1.0,2.0}, {1.0, 2.0}, {Double.NaN, 2.0}});
    try {
      new PcaDecomposition(m);
      fail();
    } catch (DecompositionException e) {
      assertEquals(e.getMessage(), "decomp.ex.specialValue");
    }
  }
}
