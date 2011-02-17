package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.dist.CanberraDist;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import junit.framework.TestCase;

public class TestDistanceMethodEnum extends TestCase {
	public void testGet() {
		DistanceSpec spec = DistanceMethodEnum.get("Euklid").getSpec();
		assertTrue(spec.getClass() == EuklidianDist.class);
		spec = DistanceMethodEnum.get("Canberra").getSpec();
		assertTrue(spec.getClass() == CanberraDist.class);
		try {
		  DistanceMethodEnum.get("invalid");
		  fail();
		} catch (IllegalArgumentException e) {
		  assertTrue(true);
		}		
	}
}
