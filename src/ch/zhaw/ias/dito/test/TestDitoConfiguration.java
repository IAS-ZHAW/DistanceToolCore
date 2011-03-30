package ch.zhaw.ias.dito.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.QuestionType;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Output;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.config.QuestionConfig;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import junit.framework.TestCase;

public class TestDitoConfiguration extends TestCase {

  public void testQuestionConfig() throws FileNotFoundException, JAXBException {
    DitoConfiguration config = DitoConfiguration.loadFromFile("./testdata/simple.dito");
    assertTrue(config.getQuestionConfig() != null);
    assertEquals(config.getQuestionConfig().isEnableAutoScale(), true);
    assertEquals(config.getQuestions().size(), 2);
    
    assertEquals(config.getQuestion(3), null);
  }
  
  public void testSaveToFile() throws FileNotFoundException, JAXBException {
    DitoConfiguration config = DitoConfiguration.loadFromFile("./testdata/simple.dito");
    DitoConfiguration.saveToFile("./testdata/simple-copy.dito", config);
    DitoConfiguration reloadedConfig = DitoConfiguration.loadFromFile("./testdata/simple-copy.dito");
    assertEquals(config, reloadedConfig);
    reloadedConfig.getQuestions().clear();
    assertEquals(config.equals(reloadedConfig), false);
  }
  
  
  public void testCompleteSave() throws FileNotFoundException, JAXBException {
    Input i = new Input("c:/asdf", 'a', false, true, 0, 10, true, 0, 10);
    Output o = new Output("c:/bsdf", "b", 10);
    Method m = new Method(DistanceMethodEnum.get("Canberra"), false, 20);
    QuestionConfig qc = new QuestionConfig(false, false, true, false);
    List<Question> qs = new ArrayList<Question>();
    qs.add(new Question(100, "name", QuestionType.ORDINAL, 100.0, 50.0, 30.0, new double[] {3.31, 2.20, 1.0}));
    
    DitoConfiguration config = new DitoConfiguration(i, o, m, qc, qs);
    DitoConfiguration.saveToFile("./testdata/simple-copy.dito", config);
    DitoConfiguration reloadedConfig = DitoConfiguration.loadFromFile("./testdata/simple-copy.dito");
    assertEquals(reloadedConfig.getInput(), i);
    assertEquals(reloadedConfig.getMethod(), m);
    assertEquals(reloadedConfig.getOutput(), o);    
    assertEquals(config, reloadedConfig);
    reloadedConfig.getQuestions().clear();
    assertEquals(config.equals(reloadedConfig), false);
    reloadedConfig.getQuestions().add(new Question(100, "name", QuestionType.ORDINAL, 100.0, 50.0, 30.0, new double[] {3.31, 2.20, 1.0}));
    assertEquals(config.equals(reloadedConfig), true);
    reloadedConfig.getQuestions().clear();
    reloadedConfig.getQuestions().add(new Question(100, "name2", QuestionType.ORDINAL, 100.0, 50.0, 30.0, new double[0]));
    assertEquals(config.equals(reloadedConfig), false);
  }
  
  public void testMethodEquals() {
    Method m = new Method(DistanceMethodEnum.get("Euklid"));
    assertEquals(m.equals(null), false);
    assertEquals(m.equals("something"), false);
    assertEquals(m.equals(m), true);
    assertEquals(m.equals(new Method(DistanceMethodEnum.get("Canberra"))), false);
    assertEquals(m.equals(new Method(DistanceMethodEnum.get("Euklid"))), true);
    assertEquals(m.equals(new Method(DistanceMethodEnum.get("Euklid"), true, 10)), false);
    assertEquals(m.equals(new Method(DistanceMethodEnum.get("Euklid"), true, 5)), true);
  }

  public void testOutputEquals() {
    Output o = new Output("filename", "|", 5);
    assertEquals(o.equals(null), false);
    assertEquals(o.equals("something"), false);
    assertEquals(o.equals(o), true);
    assertEquals(o.equals(new Output("filename", "|", 6)), false);
    assertEquals(o.equals(new Output("filename", "|", 5)), true);
  }
  
  public void testInputEquals() {
    Input i = new Input("filename", '|', false, true, 0, 10, true, 0, 10);
    assertEquals(i.equals(null), false);
    assertEquals(i.equals("something"), false);
    assertEquals(i.equals(i), true);
    assertEquals(i.equals(new Input("filename", ';', false, true, 0, 10, true, 0, 10)), false);
    assertEquals(i.equals(new Input("filename", '|', false, true, 0, 10, true, 0, 10)), true);
  }
  
  public void testQuestionConfigEquals() {
    QuestionConfig qc = new QuestionConfig(true, false, true, false);
    assertEquals(qc.equals(null), false);
    assertEquals(qc.equals("something"), false);
    assertEquals(qc.equals(qc), true);
    assertEquals(qc.equals(new QuestionConfig(true, true, true, false)), false);
    assertEquals(qc.equals(new QuestionConfig(true, false, true, false)), true);
  }
  
  public void testCreateDefaultQuestion() throws FileNotFoundException, JAXBException {
    DitoConfiguration config = DitoConfiguration.loadFromFile("./testdata/simple.dito");
    Question q = config.createDefaultQuestion(3, new DVector(3, 3, 1));
    assertEquals(q.getQuestionType(), QuestionType.ORDINAL);
    
    q = config.createDefaultQuestion(3, new DVector(-3.1, 3, 1));
    assertEquals(q.getQuestionType(), QuestionType.METRIC);
    
    q = config.createDefaultQuestion(3, new DVector(1, 0, 0, 1));
    assertEquals(q.getQuestionType(), QuestionType.BINARY);
    
    q = config.createDefaultQuestion(3, new DVector(1, 0, 0, 1, 1.1));
    assertEquals(q.getQuestionType(), QuestionType.METRIC);
    
    q = config.createDefaultQuestion(3, new DVector(1, 0, 0, 1, 3));
    assertEquals(q.getQuestionType(), QuestionType.ORDINAL);
    
    q = config.createDefaultQuestion(3, new DVector(1, 0, 0, 1, Double.NaN));
    assertEquals(q.getQuestionType(), QuestionType.BINARY);
    assertTrue(q.getData() != null);
  }
  
  public void testIndexError() {
    DitoConfiguration config = new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), new ArrayList<Question>());
    config.getQuestions().add(config.createDefaultQuestion(1, new DVector()));
    config.setData(Matrix.createDoubleMatrix(new double[][] {{1,2}, {3,4}}));
    Question q = config.getQuestion(2);
    assertTrue(q != null);
  }
}
