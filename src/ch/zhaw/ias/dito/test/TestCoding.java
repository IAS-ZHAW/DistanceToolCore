package ch.zhaw.ias.dito.test;

import java.util.Arrays;

import ch.zhaw.ias.dito.Coding;
import junit.framework.TestCase;

public class TestCoding extends TestCase {
	public void testGetBinaryCoding() {
		double[] values = Coding.getBinaryCoding(3, 5);
		assertTrue(Arrays.equals(values, new double[] {0, 0, 0, 1, 0}));
		
		try {
		  Coding.getBinaryCoding(5, 5);
		} catch (IllegalArgumentException e) {
		  assertTrue(true);
		}
	}
	
  public void testNanBinaryCoding() {
    double[] values = Coding.getBinaryCoding(-1, 5);
    assertTrue(Arrays.equals(values, new double[] {Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN}));
  }
}
