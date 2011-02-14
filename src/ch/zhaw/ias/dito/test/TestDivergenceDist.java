package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.DivergenceDist;

public class TestDivergenceDist extends TestCase {
	private DivergenceDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new DivergenceDist();
	}
		
	public void testDVector() {
		DVector v1 = new DVector(1,9);
		DVector v2 = new DVector(4,11);
		assertEquals(dist.distance(v1, v2), 0.185);
		
    v1 = new DVector(1,Double.NaN, 9, 11);
    v2 = new DVector(4, 7, 11, Double.NaN);
    assertEquals(dist.distance(v1, v2), 0.185);		
	}
}
