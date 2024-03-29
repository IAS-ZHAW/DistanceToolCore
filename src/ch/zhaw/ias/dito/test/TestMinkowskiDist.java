/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.dist.MinkowskiDist;

public class TestMinkowskiDist extends TestCase {
	private MinkowskiDist dist;
	
	@Override
	protected void setUp() throws Exception {
		dist = new MinkowskiDist();
	}
		
	public void testDVector() {
		DVector v1 = new DVector(1,9);
		DVector v2 = new DVector(4,11);
		assertEquals(dist.distance(v1, v2), 0.185);
		
    v1 = new DVector(1,Double.NaN, 9, 11);
    v2 = new DVector(4, 7, 11, Double.NaN);
    assertEquals(dist.distance(v1, v2), 0.185);		
	}
}
