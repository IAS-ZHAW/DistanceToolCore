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
		Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',');
		m = m.toBinary();
		DistanceMethodEnum method = DistanceMethodEnum.get("Simple");
		assertEquals(m.calculateDistance(method.getSpec()), Matrix.readFromFile(new File("./testdata/m20x20randBin-simple.csv"), ','));
	}
	
  public void testLargeJaccard() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m20x20randBin.csv"), ',');
    m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Jaccard");
    Matrix dist = m.calculateDistance(method.getSpec());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m20x20randBin-jaccard.csv"), ','), 7), true);
  }
  
  public void testConfig() {
    DitoConfiguration config = new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), null);
    //config.setData(data);
  }
  
  public void testLargeSimple2() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ');
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Simple");
    Matrix dist = m.calculateDistance(method.getSpec());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-simple.csv"), ';'), 4), true);
  }

  public void testLargeCzekanowski() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ');
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Czekanowski");
    Matrix dist = m.calculateDistance(method.getSpec());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-Czekanowski.csv"), ';'), 4), true);
  }
  
  public void testLargeOchiai() throws IOException {
    Matrix m = Matrix.readFromFile(new File("./testdata/m153x166chemical.csv"), ' ');
    //m = m.toBinary();
    DistanceMethodEnum method = DistanceMethodEnum.get("Ochiai");
    Matrix dist = m.calculateDistance(method.getSpec());
    assertEquals(dist.equalsRounded(Matrix.readFromFile(new File("./testdata/m153x153chemical-Ochiai.csv"), ';'), 4), true);
  }  
}
