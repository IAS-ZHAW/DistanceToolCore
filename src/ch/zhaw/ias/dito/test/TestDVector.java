package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;
import ch.zhaw.ias.dito.*;
import ch.zhaw.ias.dito.ops.AddOp;
import ch.zhaw.ias.dito.ops.EuklidOp;
import ch.zhaw.ias.dito.ops.ManhattanOp;

public class TestDVector extends TestCase {
	private DVector v1;
	private DVector v1Copy;
	private DVector v2;
	private DVector v3;
	
	@Override
	protected void setUp() throws Exception {
		v1 = new DVector(1,2,3);
		v1Copy = new DVector(1,2,3);
		v2 = new DVector(4,5,6);
		v3 = new DVector(4,5,6,9,10);
		super.setUp();
	}
	
	public void testSum() {
		assertEquals(v1.sum(), 6.0);
		assertEquals(v2.sum(), 15.0);
	}
	
	public void testLength() {
		assertEquals(v1.length(), 3);
		assertEquals(v3.length(), 5);
	}
	
	public void testEquals() {
		assertEquals(v1.equals(null), false);
		assertEquals(v1.equals("bla"), false);
		assertEquals(v1.equals(v2), false);
		assertEquals(v1.equals(v3), false);
		assertEquals(v1.equals(v1Copy), true);
		assertEquals(v1.equals(v1), true);
	}

	public void testAdd() {
		assertEquals(v1.add(v2), new DVector(5,7,9));
		try {
			v1.add(v3);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testComponent() {
		assertEquals(v1.component(0), 1.0);
		assertEquals(v2.component(2), 6.0);
		assertEquals(v3.component(4), 10.0);
		try {
			v3.component(5);	
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}
	
	public void testZipWith() {
		DVector sum = v1.zipWith(v2, new AddOp());
		assertEquals(sum, new DVector(5,7,9));
		try {
			v1.zipWith(v3, new AddOp());	
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testFold() {
		assertEquals(v1.foldl1(new ManhattanOp()), 2.0);
		assertEquals(new DVector(1,2,3,4).foldl1(new EuklidOp()), 0.0);
	}
	
	public void testToString() {
		assertEquals(new DVector().toString(), "()");
		assertEquals(v3.toString(), "(4.0,5.0,6.0,9.0,10.0)");
	}
	
	public void testMin() {
		assertEquals(v1.min(), 1.0);
		assertEquals(v3.min(), 4.0);
		assertEquals(new DVector(-1,-3, -2).min(), -3.0);
	}

	public void testMax() {
		assertEquals(v1.max(), 3.0);
		assertEquals(v3.max(), 10.0);
		assertEquals(new DVector(-3, -1, -2).max(), -1.0);
	}
	
	public void testNan() {
		DVector nanV = new DVector(3, Double.NaN, 5);
		assertEquals(nanV.sum(), 8.0);
		assertEquals(new DVector(Double.NaN, 5, 3).sum(), 8.0);
		
		assertEquals(nanV, new DVector(3, Double.NaN, 5));
		assertEquals(nanV.equals(new DVector(-1, 5, 3)), false);
		
		assertEquals(nanV.length(), 3);
		assertEquals(new DVector(3, Double.NaN, 5).filteredLength(), 2);
		
		DVector sum = nanV.zipWith(new DVector(Double.NaN, 5, 3), new AddOp());
		assertEquals(sum, new DVector(Double.NaN, Double.NaN, 8));
		
		assertEquals(nanV.max(), 5.0);
		assertEquals(nanV.min(), 3.0);
	} 
}
