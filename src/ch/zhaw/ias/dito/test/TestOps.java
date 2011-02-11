package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.ops.AddOp2;
import ch.zhaw.ias.dito.ops.CanberraOp2;
import ch.zhaw.ias.dito.ops.EuklidOp2;
import ch.zhaw.ias.dito.ops.ManhattanOp2;
import junit.framework.TestCase;

public class TestOps extends TestCase {

	public void testAddOp() {
		assertEquals(new AddOp2().execute(5, 10), 15.0);
	}
	
	public void testEuklidOp() {
		assertEquals(new EuklidOp2().execute(5, 10), 25.0);
	}
	
	public void testCanberraOp() {
		assertEquals(new CanberraOp2().execute(4, 6), 0.2);
	}
	
	public void testManhattanOp() {
		assertEquals(new ManhattanOp2().execute(4, 6), 2.0);
	}	
}
