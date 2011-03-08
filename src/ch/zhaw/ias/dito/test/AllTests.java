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
		suite.addTestSuite(TestVectorFactory.class);
		suite.addTestSuite(TestDitoConfiguration.class);
		suite.addTestSuite(TestBinaryDistance.class);
		suite.addTestSuite(TestBrayCurtisDist.class);
		suite.addTestSuite(TestSoergelDist.class);
		//$JUnit-END$
		return suite;
	}
}
