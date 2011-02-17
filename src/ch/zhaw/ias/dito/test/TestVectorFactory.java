package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.VectorFactory;
import junit.framework.TestCase;

public class TestVectorFactory extends TestCase {
  
  public void testIsBinaryData() {
    assertEquals(VectorFactory.isBinaryData(Arrays.asList(true, false, true, true)), true);
    assertEquals(VectorFactory.isBinaryData(Arrays.asList(true, false, true, 3.0)), false);
    assertEquals(VectorFactory.isBinaryData(Arrays.asList(2, 3)), false);
    assertEquals(VectorFactory.isBinaryData(Arrays.asList(true, true, true)), true);
    assertEquals(VectorFactory.isBinaryData(Arrays.asList(false, false, false)), true);
  } 
}
