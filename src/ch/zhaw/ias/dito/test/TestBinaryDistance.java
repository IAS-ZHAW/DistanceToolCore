/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.AbstractBinaryDist;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import junit.framework.TestCase;

public class TestBinaryDistance extends TestCase {
  public void testBinary() {
    DVector b1 = new DVector(0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, Double.NaN, Double.NaN);
    DVector b2 = new DVector(1, 0, 1, 1, 1, 0, 0, 0, 1, 0, Double.NaN, 0, Double.NaN);
    //a=2, b=1, c=3, d=4
    DistanceSpec spec = DistanceMethodEnum.get("Jaccard").getSpec();
    assertEquals(spec.distance(b1, b2), 1/3.0);
    spec = DistanceMethodEnum.get("Kulzynski").getSpec();
    assertEquals(spec.distance(b1, b2), 0.5);
    spec = DistanceMethodEnum.get("Simple").getSpec();
    assertEquals(spec.distance(b1, b2), 0.6);    
    spec = DistanceMethodEnum.get("Braun, Blanque").getSpec();
    assertEquals(spec.distance(b1, b2), 2/5.0);
    spec = DistanceMethodEnum.get("Czekanowski").getSpec();
    assertEquals(spec.distance(b1, b2), 0.5);    
    spec = DistanceMethodEnum.get("Hamman").getSpec();
    assertEquals(spec.distance(b1, b2), 0.2);
    spec = DistanceMethodEnum.get("Michael").getSpec();
    assertEquals(spec.distance(b1, b2), 5/13.0);    
  }
  
  public void testIllegalVector() {
    try {
      DVector b1 = new DVector(1, 0, 0, 1, 1);
      DVector b2 = new DVector(1, 1, 0, 3, 1);
      DistanceSpec spec = DistanceMethodEnum.get("Jaccard").getSpec();
      assertEquals(spec.distance(b1, b2), 0.5);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }
  
  public void testAbcd() {
    DVector b1 = new DVector(0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, Double.NaN, Double.NaN);
    DVector b2 = new DVector(1, 0, 1, 1, 1, 0, 0, 0, 1, 0, Double.NaN, 0, Double.NaN);
    AbcdCounter spec = new AbcdCounter();
    spec.distance(b1, b2);
    assertEquals(spec.a, 2.0);
    assertEquals(spec.b, 1.0);
    assertEquals(spec.c, 3.0);
    assertEquals(spec.d, 4.0);
  }
  
  private class AbcdCounter extends AbstractBinaryDist {
    double a;
    double b;
    double c;
    double d;
    
    @Override
    public double distance(double a, double b, double c, double d) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
      return 0;
    }
  }
}
