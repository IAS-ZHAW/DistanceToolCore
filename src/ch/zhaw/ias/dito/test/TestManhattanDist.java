package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.ManhattanDist;

public class TestManhattanDist extends TestCase {
	private ManhattanDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new ManhattanDist();
	}
	
	public void testSingle() {
		assertEquals(dist.distance(5, 3), 2.0);
		assertEquals(dist.distance(10, 3), 7.0);
	}
	
	/*public void testArrayException() {
		try {
			dist.distance(new double[] {3, 5}, new double[] {1,2,4});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}*/
	
	public void testDVector() {
		DVector v1 = new DVector(1,7);
		DVector v2 = new DVector(4,11);
		assertEquals(dist.distance(v1, v2), 7.0);
	}
}
