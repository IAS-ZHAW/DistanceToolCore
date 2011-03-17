package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import junit.framework.TestCase;

public class TestBinaryDistance extends TestCase {
  public void testBinary() {
    DVector b1 = new DVector(1, 0, 0, 1, 1);
    DVector b2 = new DVector(1, 1, 0, 0, 1);
    DistanceSpec spec = DistanceMethodEnum.get("Jaccard").getSpec();
    assertEquals(spec.distance(b1, b2), 0.5);
    spec = DistanceMethodEnum.get("Kulzynski").getSpec();
    assertEquals(spec.distance(b1, b2), 1.);
    spec = DistanceMethodEnum.get("Simple").getSpec();
    assertEquals(spec.distance(b1, b2), 0.6);    
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
}
