package ch.zhaw.ias.dito.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

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
    
    assertEquals(config.getQuestion(3), config.createDefaultQuestion(3));
  }
  
  public void testQuestionEquals() {
    Question q = new Question(3, "test", 2.0, 3.0, 4.0);
    assertEquals(q.equals(null), false);
    assertEquals(q.equals("asfd"), false);
    assertEquals(q, q);
    assertEquals(q, new Question(3, "test", 2.0, 3.0, 4.0));
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
    Input i = new Input("c:/asdf", "a");
    Output o = new Output("c:/bsdf", "b", 10);
    Method m = new Method(DistanceMethodEnum.get("Canberra"), false, 20);
    QuestionConfig qc = new QuestionConfig(false, false, true, false);
    List<Question> qs = new ArrayList<Question>();
    qs.add(new Question(100, "name", 100.0, 50.0, 30.0));
    
    DitoConfiguration config = new DitoConfiguration(i, o, m, qc, qs);
    DitoConfiguration.saveToFile("./testdata/simple-copy.dito", config);
    DitoConfiguration reloadedConfig = DitoConfiguration.loadFromFile("./testdata/simple-copy.dito");
    assertEquals(reloadedConfig.getInput(), i);
    assertEquals(reloadedConfig.getMethod(), m);
    assertEquals(reloadedConfig.getOutput(), o);    
    assertEquals(config, reloadedConfig);
    reloadedConfig.getQuestions().clear();
    assertEquals(config.equals(reloadedConfig), false);
    reloadedConfig.getQuestions().add(new Question(100, "name", 100.0, 50.0, 30.0));
    assertEquals(config.equals(reloadedConfig), true);
    reloadedConfig.getQuestions().clear();
    reloadedConfig.getQuestions().add(new Question(100, "name2", 100.0, 50.0, 30.0));
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
    Input i = new Input("filename", "|");
    assertEquals(i.equals(null), false);
    assertEquals(i.equals("something"), false);
    assertEquals(i.equals(i), true);
    assertEquals(i.equals(new Input("filename", ";")), false);
    assertEquals(i.equals(new Input("filename", "|")), true);
  }
  
  public void testQuestionConfigEquals() {
    QuestionConfig qc = new QuestionConfig(true, false, true, false);
    assertEquals(qc.equals(null), false);
    assertEquals(qc.equals("something"), false);
    assertEquals(qc.equals(qc), true);
    assertEquals(qc.equals(new QuestionConfig(true, true, true, false)), false);
    assertEquals(qc.equals(new QuestionConfig(true, false, true, false)), true);
  }  
}
