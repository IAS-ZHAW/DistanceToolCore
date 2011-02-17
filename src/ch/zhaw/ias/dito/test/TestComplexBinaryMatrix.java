package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

public class TestComplexBinaryMatrix extends TestCase {
	
	public void testLargeSimple() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',');
		m = m.toBinary();
		DistanceMethodEnum method = DistanceMethodEnum.get("Simple");
		assertEquals(m.calculateDistance(method.getSpec(), true), Matrix.readFromFile(new File("./testdata/m20x20randBin-simple.csv"), ','));
	}
	
  public void testLargeJaccard() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',');
    m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Jaccard");
    Matrix dist = m.calculateDistance(method.getSpec(), true);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m20x20randBin-jaccard.csv"), ','), 7), true);
  }	
}
