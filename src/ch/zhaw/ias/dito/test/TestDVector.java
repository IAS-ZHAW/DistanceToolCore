/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import ch.zhaw.ias.dito.*;
import ch.zhaw.ias.dito.ops.AddOp2;
import ch.zhaw.ias.dito.ops.EuklidOp2;
import ch.zhaw.ias.dito.ops.ManhattanOp2;
import ch.zhaw.ias.dito.ops.MultiplyOp1;
import ch.zhaw.ias.dito.ops.Operation1;

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
	
	public void testListConstructor() {
	  List<Double> vals = new ArrayList<Double>();
	  vals.add(-3.0);
	  vals.add(3.41);
	  vals.add(Double.NaN);
	  
	  DVector vec = new DVector(vals);
	  assertEquals(vec, new DVector(-3.0, 3.41, Double.NaN));
	}
	
	public void testSum() {
		assertEquals(v1.sum(), 6.0);
		assertEquals(v2.sum(), 15.0);
	}
	
  public void testMean() {
    assertEquals(v1.mean(), 2.0);
    assertEquals(v2.mean(), 5.0);
    assertEquals(new DVector(-2, 2).mean(), 0.0);
    assertEquals(new DVector(2, Double.NaN).mean(), 2.0);
  }
  
  public void testVariance() {
    assertEquals(v1.unscaledVariance(), 2.0);
    assertEquals(new DVector(-1, 0, 1, 3, 4,4, 3).unscaledVariance(), 24.0);
  }  
  
  public void testScalarProduct() {
    assertEquals(v1.scalarProduct(v2), 32.0);
    assertEquals(v1.scalarProduct(new DVector(1.0, -3.0, Double.NaN)), -5.0);
  }  
  
  public void testRemoveMean() {
    assertEquals(v1.removeMean(), new DVector(-1.0, 0.0, 1.0));
    assertEquals(v2.removeMean(), new DVector(-1.0, 0.0, 1.0));
    assertEquals(new DVector(2, Double.NaN).removeMean(), new DVector(0.0, Double.NaN));
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
		DVector sum = v1.zipWith(v2, new AddOp2());
		assertEquals(sum, new DVector(5,7,9));
		try {
			v1.zipWith(v3, new AddOp2());	
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testFold() {
		assertEquals(v1.foldl1(new ManhattanOp2()), 2.0);
		assertEquals(new DVector(1,2,3,4).foldl1(new EuklidOp2()), 0.0);
	}
	
	public void testToString() {
		assertEquals(new DVector(new double[0]).toString(), "()");
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
		assertEquals(new DVector(3, Double.NaN, 5, Double.NaN, 10).filteredLength(), 3);
		
		DVector sum = nanV.zipWith(new DVector(Double.NaN, 5, 3), new AddOp2());
		assertEquals(sum, new DVector(Double.NaN, Double.NaN, 8));
		
		assertEquals(nanV.max(), 5.0);
		assertEquals(nanV.min(), 3.0);
	}
	
	public void testMap() {
	  DVector twice = v1.map(new Operation1() {
      
      @Override
      public double execute(double a) {
        return 2*a;
      }
    });
	  assertEquals(twice, new DVector(2, 4, 6));
	}
	
	public void testRescale() {
	  DVector scaled = v1.rescale(2.0, 0);
	  assertEquals(scaled, new DVector(2,4,6));
	  scaled = v1.rescale(0.5, 1);
	  assertEquals(scaled, new DVector(0.0,0.5,1));
	}
	
  public void testAutoRescale() {
    DVector v1 = new DVector(0, 10, 5, 2, 4);
    DVector scaled = v1.autoRescale();
    assertTrue(scaled.equals(new DVector(0, 1, 0.5, 0.2, 0.4)));
    v1 = new DVector(1, 11, 5, 2, 5);
    scaled = v1.autoRescale();
    assertEquals(scaled, new DVector(0, 1, 0.4, 0.1, 0.4));
  }
  
  public void testToBinary() {
    v1 = new DVector(0, 1, 0, 0, 1);
    assertEquals(v1.toBinary(), new DVector(0, 1, 0, 0, 1));
    
    v1 = new DVector(0, 1, 2, 0, 3, Double.NaN, -1, -2.514);
    assertEquals(v1.toBinary(), new DVector(0, 1, 1, 0, 1, Double.NaN, Double.NaN, Double.NaN));    
    assertEquals(new DVector(0.0, -0.0).toBinary(), new DVector(0, 0));
  }
  
  public void testImmutableArray() {
    double[] values = new double[] {0, 1, 0, 0, 1};
    v1 = new DVector(values);
    values[0] = 100;
    assertEquals(v1, new DVector(0, 1, 0, 0, 1));
  }
  
  public void testMultiplyOp() {
    DVector v = new DVector(-2, 0, Double.NaN, 2).map(new MultiplyOp1(2.0));
    assertEquals(v, new DVector(-4, 0, Double.NaN, 4));
  }
  
  public void testExclude() {
    DVector v = new DVector(-1, 0, 3, 10, -1, 5);
    DVector filtered = v.exclude(-1);
    assertEquals(filtered, new DVector(Double.NaN, 0, 3, 10, Double.NaN, 5));
    filtered = v.exclude(-1, 0, 5);
    assertEquals(filtered, new DVector(Double.NaN, Double.NaN, 3, 10, Double.NaN, Double.NaN));    
  }
  
  public void testIsConstant() {
    assertEquals(v3.isConstant(), false);
    assertEquals(new DVector(1).isConstant(), true);
    assertEquals(new DVector(1, 1).isConstant(), true);
    assertEquals(new DVector(1, 1, 1).isConstant(), true);
    assertEquals(new DVector(1, 1, 2).isConstant(), false);
  }
  
  public void testContainsSpecial() {
    assertEquals(new DVector(1, 1, 2).containsSpecial(), false);
    assertEquals(new DVector(0.0/0.0, 1, 2).containsSpecial(), true);
    assertEquals(new DVector(1, 1.0/0.0, 1, 2).containsSpecial(), true);
    assertEquals(new DVector(1, Double.NaN, 2).containsSpecial(), true);
    assertEquals(new DVector(1, Double.POSITIVE_INFINITY, 2).containsSpecial(), true);
    assertEquals(new DVector(1, 3, 4, Double.POSITIVE_INFINITY).containsSpecial(), true);
  }

  public void testRandomNoiseVector() {
    DVector random = DVector.generateRandomVector(10, 0.5);
    assertEquals(random.length(), 10);
    assertTrue(random.max() <= 0.5);
    assertTrue(random.min() >= 0.0);
    DVector random2 = DVector.generateRandomVector(10, 0.5);
    assertFalse(random.equals(random2)); //could fail in some cases but with very low probability
  }
}
