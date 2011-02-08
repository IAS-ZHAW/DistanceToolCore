package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.ops.AddOp;
import ch.zhaw.ias.dito.ops.CanberraOp;
import ch.zhaw.ias.dito.ops.EuklidOp;
import ch.zhaw.ias.dito.ops.ManhattanOp;
import junit.framework.TestCase;

public class TestOps extends TestCase {

	public void testAddOp() {
		assertEquals(new AddOp().execute(5, 10), 15.0);
	}
	
	public void testEuklidOp() {
		assertEquals(new EuklidOp().execute(5, 10), 25.0);
	}
	
	public void testCanberraOp() {
		assertEquals(new CanberraOp().execute(4, 6), 0.2);
	}
	
	public void testManhattanOp() {
		assertEquals(new ManhattanOp().execute(4, 6), 2.0);
	}	
}
