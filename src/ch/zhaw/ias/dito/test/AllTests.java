/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestEuklidianDist.class);
		suite.addTestSuite(TestManhattanDist.class);
		suite.addTestSuite(TestDVector.class);
		suite.addTestSuite(TestMatrix.class);
		suite.addTestSuite(TestOps.class);
		suite.addTestSuite(TestIo.class);
		suite.addTestSuite(TestDistanceMethodEnum.class);
		suite.addTestSuite(TestBhattacharyyaDist.class);
		suite.addTestSuite(TestWaveHedgesDist.class);
		suite.addTestSuite(TestComplexDoubleMatrix.class);
		suite.addTestSuite(TestDivergenceDist.class);
		suite.addTestSuite(TestComplexBinaryMatrix.class);
		suite.addTestSuite(TestDistanceAlgorithm.class);
		suite.addTestSuite(TestBinaryDistanceAlgorithm.class);
		suite.addTestSuite(TestDitoConfiguration.class);
		suite.addTestSuite(TestQuestion.class);
		suite.addTestSuite(TestBinaryDistance.class);
		suite.addTestSuite(TestBrayCurtisDist.class);
		suite.addTestSuite(TestSoergelDist.class);
		suite.addTestSuite(TestUniversalBinaryDist.class);
		suite.addTestSuite(TestUtil.class);
		suite.addTestSuite(TestImport.class);
		suite.addTestSuite(TestMds.class);
		suite.addTestSuite(TestPca.class);
		suite.addTestSuite(TestNoiseGenerator.class);
		
		//$JUnit-END$
		return suite;
	}
}
