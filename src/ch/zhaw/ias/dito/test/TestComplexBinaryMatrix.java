package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Output;
import ch.zhaw.ias.dito.config.QuestionConfig;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

public class TestComplexBinaryMatrix extends TestCase {
	
	public void testLargeSimple() throws IOException {
		Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',').getMatrix();
		m = m.toBinary();
		DistanceMethodEnum method = DistanceMethodEnum.get("Simple");
		Matrix dist = DistanceTestCaseHelper.compareNormalParallel(method.getSpec(), m); 
		assertEquals(dist, Matrix.readFromFile(new File("./testdata/m20x20randBin-simple.csv"), ',').getMatrix());
	}
	
  public void testLargeJaccard() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',').getMatrix();
    m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Jaccard");
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(method.getSpec(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m20x20randBin-jaccard.csv"), ',').getMatrix(), 7), true);
  }
  
  public void testConfig() {
    DitoConfiguration config = new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), null);
    assertTrue(false);
    //config.setData(data);
  }
  
  public void testLargeSimple2() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ').getMatrix();
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Simple");
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(method.getSpec(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-simple.csv"), ';').getMatrix(), 4), true);
  }

  public void testLargeCzekanowski() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ').getMatrix();
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Czekanowski");
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(method.getSpec(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-Czekanowski.csv"), ';').getMatrix(), 4), true);
  }
  
  public void testLargeOchiai() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ').getMatrix();
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Ochiai");
    Matrix dist = DistanceTestCaseHelper.compareNormalParallel(method.getSpec(), m);
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-Ochiai.csv"), ';').getMatrix(), 4), true);
  }  
}
