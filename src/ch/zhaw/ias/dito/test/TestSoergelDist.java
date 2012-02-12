/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
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
    assertEquals(dist.distance(v1, v2), 0.05);
  }
  
  public void testMatrix() {
    DVector v1 = new DVector(3,Double.NaN, 9, 11);
    DVector v2 = new DVector(4, 7, 11, Double.NaN);
    
    Matrix m = new Matrix(v1, v2);
    Matrix distM = DistanceTestCaseHelper.compareNormalParallel(dist, m);
    assertEquals(distM, Matrix.createDoubleMatrix(new double[][] {{0.0, 0.05}, {0.05, 0.0}}));
  }
}
