/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import java.util.HashMap;

import ch.zhaw.ias.dito.io.CommandlineParser;
import junit.framework.TestCase;

public class TestIo extends TestCase {
	public void testCommandline() {
		HashMap<String, String> params = CommandlineParser.parse("-o", "oVal", "-i", "-q", "qVal", "ignore");
		assertEquals(params.containsKey("-o"), true);
		assertEquals(params.get("-o"), "oVal");
		assertEquals(params.containsKey("-i"), true);
		assertEquals(params.get("-i"), null);
		assertEquals(params.containsKey("-q"), true);
		assertEquals(params.get("-q"), "qVal");
		assertEquals(params.containsKey("ignore"), false);
	}
}
