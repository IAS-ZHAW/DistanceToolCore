package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import junit.framework.TestCase;

public class DistanceTestCaseHelper {
  private DistanceTestCaseHelper() {
    
  }
  
  public static Matrix compareNormalParallel(DistanceSpec dist, Matrix m) {
    Matrix normal = m.calculateDistance(dist);
    Matrix parallel = m.calculateDistanceParallel(dist, 5);
    TestCase.assertEquals(normal, parallel);
    return normal;
  }
}
