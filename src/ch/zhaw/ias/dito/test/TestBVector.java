/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.util.BitSet;

import junit.framework.TestCase;

public class TestBVector extends TestCase {
  /*public void testA() {
    assertEquals(new BVector(true, false, true).a(new BVector(true, true, false)), 1);
    assertEquals(new BVector(true, false, true).a(new BVector(true, true, true)), 2);
  }
  
  public void testD() {
    assertEquals(new BVector(true, false, true).d(new BVector(true, true, false)), 0);
    assertEquals(new BVector(true, false, true).d(new BVector(true, false, true)), 1);
    assertEquals(new BVector(false, false, false).d(new BVector(true, false, false)), 2);
  }
  
  public void testB() {
    assertEquals(new BVector(true, false, true).b(new BVector(true, true, false)), 1);
    assertEquals(new BVector(true, false, true).b(new BVector(true, false, true)), 0);
    assertEquals(new BVector(true, true, true).b(new BVector(true, false, false)), 2);    
  }
  
  public void testC() {
    assertEquals(new BVector(true, false, true).c(new BVector(true, true, false)), 1);
    assertEquals(new BVector(true, false, true).c(new BVector(true, false, true)), 0);
    assertEquals(new BVector(true, true, true).c(new BVector(true, false, false)), 0);    
  }  
  
  public void testToBitSet() {
    BitSet set = new BitSet(3);
    set.set(0);
    set.set(2);
    assertEquals(BVector.toBitSet(new Boolean[] {true, false, true}), set); 
  }
  
  public void testEquals() {
    BVector v = new BVector(true, false, false, true);
    assertEquals(v.equals(null), false);
    assertEquals(v.equals("bla"), false);
    assertEquals(v.equals(v), true);
    assertEquals(v.equals(new BVector(true, false, false, true)), true);
    assertEquals(v.equals(new BVector(true, false, false, true, false)), true);
    assertEquals(v.equals(new BVector(true, false, false, false)), false);
  }
  
  public void testLength() {
    assertEquals(new BVector(true, false, false, true).length(), 4);
    assertEquals(new BVector(true, false, false, true, false).length(), 5);
  }*/
}
