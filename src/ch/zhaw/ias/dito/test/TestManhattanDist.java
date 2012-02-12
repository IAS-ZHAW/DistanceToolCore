/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.ManhattanDist;

public class TestManhattanDist extends TestCase {
	private ManhattanDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new ManhattanDist();
	}
	
	public void testDVector() {
		DVector v1 = new DVector(1,7);
		DVector v2 = new DVector(4,11);
		assertEquals(dist.distance(v1, v2), 7.0);
	}
}
