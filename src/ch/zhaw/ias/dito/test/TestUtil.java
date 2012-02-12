/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.Arrays;

import ch.zhaw.ias.dito.Utils;
import junit.framework.TestCase;

public class TestUtil extends TestCase {
  public void testToCommaSeparatedString() {
    String s = Utils.toCommaSeparatedString(new double[] {-3.12, 3.0, 1.0, 5});
    assertEquals(s, "-3.12, 3.0, 1.0, 5.0");
    ArrayList<String> list = new ArrayList<String>();
    list.add("bla");
    list.add("test");
    list.add("hallo");
    s = Utils.toCommaSeparatedString(list.iterator());
    assertEquals(s, "bla, test, hallo");    
  }
  
  public void testSplitToDouble() {
    String s = "3.12, -4, 0.0, 100.523, bla;52.12,      3";
    double[] values = Utils.splitToDouble(s);
    assertTrue(Arrays.equals(values, new double[] {3.12, -4.0, 0.0, 100.523, 52.12, 3.0}));
  }
}
