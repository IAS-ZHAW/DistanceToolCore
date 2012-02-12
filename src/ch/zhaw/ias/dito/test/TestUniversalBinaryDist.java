/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.UniversalBinaryDist;
import junit.framework.TestCase;

public class TestUniversalBinaryDist extends TestCase {

  public void testSimple() {
    DVector b1 = new DVector(0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, Double.NaN, Double.NaN);
    DVector b2 = new DVector(1, 0, 1, 1, 1, 0, 0, 0, 1, 0, Double.NaN, 0, Double.NaN);
    //a=2, b=1, c=3, d=4
    
    UniversalBinaryDist dist = new UniversalBinaryDist();
    dist.setExpression("a + b + c + d");
    double d = dist.distance(b1, b2);
    assertEquals(d, 10.0);
    
    dist = new UniversalBinaryDist();
    dist.setExpression("1000*a + 100*b + 10*c + d");
    d = dist.distance(b1, b2);
    assertEquals(d, 2134.0);
  }
}
