package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.WaveHedgesDist;
import junit.framework.TestCase;

public class TestWaveHedgesDist extends TestCase {
	private WaveHedgesDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new WaveHedgesDist();
	}
		
	public void testDVector() {
		DVector v1 = new DVector(18,10);
		DVector v2 = new DVector(9,20);
		assertEquals(dist.distance(v1, v2), 1.0);
	}
}
