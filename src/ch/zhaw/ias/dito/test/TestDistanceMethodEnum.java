package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.dist.CanberraDist;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import ch.zhaw.ias.dito.dist.EuklidianDist;
import junit.framework.TestCase;

public class TestDistanceMethodEnum extends TestCase {
	public void testGet() {
		DistanceSpec spec = DistanceMethodEnum.get("Euklid");
		assertTrue(spec.getClass() == EuklidianDist.class);
		spec = DistanceMethodEnum.get("Canberra");
		assertTrue(spec.getClass() == CanberraDist.class);
		spec = DistanceMethodEnum.get("invalid");
		assertTrue(spec == null);		
	}
}
