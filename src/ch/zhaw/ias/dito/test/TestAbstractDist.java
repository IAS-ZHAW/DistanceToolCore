package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.dist.EuklidianDist;
import junit.framework.TestCase;

public class TestAbstractDist extends TestCase {
	private EuklidianDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new EuklidianDist();
	}
	
	public void testSingle() {
		assertEquals(dist.distance(5, 3), 2.0);
	}
	
	public void testArrayException() {
		try {
			dist.distance(new double[] {3, 5}, new double[] {1,2,4});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
