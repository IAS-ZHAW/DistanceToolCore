/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.BhattacharyyaDist;

public class TestBhattacharyyaDist extends TestCase {
	private BhattacharyyaDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new BhattacharyyaDist();
	}
		
	public void testDVector() {
		DVector v1 = new DVector(49,9);
		DVector v2 = new DVector(9,36);
		assertEquals(dist.distance(v1, v2), 5.0);
	}
}
