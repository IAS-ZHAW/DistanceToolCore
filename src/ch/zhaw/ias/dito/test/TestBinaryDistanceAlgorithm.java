package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DistanceAlgorithm;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.QuestionType;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Output;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.config.QuestionConfig;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

public class TestBinaryDistanceAlgorithm extends TestCase {
  private DitoConfiguration config;
  private Matrix m;
  private DistanceAlgorithm algo;
  
  @Override
  protected void setUp() throws Exception {
    Question q1 = new Question(1, "1", QuestionType.ORDINAL, 2.0, 1.0, 1.0, new double[0]);
    Question q2 = new Question(2, "2", QuestionType.ORDINAL, 1.0, 3.0, 1.0, new double[0]);
    List<Question> questions = new ArrayList<Question>();
    questions.add(q1);
    questions.add(q2);
    config = new DitoConfiguration(new Input(), new Output(), new Method(DistanceMethodEnum.get("Czekanowski")), new QuestionConfig(), questions);
    
    m = Matrix.createDoubleMatrix(new double[][] {{-1, 3, 0, Double.NaN, 1}, {-2, 0, Double.NaN, 2, 1}});
    config.setData(new ArrayList<String>(), m);
    algo = new DistanceAlgorithm(config, false);
    config.getQuestionConfig().setEnableScale(false);
    config.getQuestionConfig().setEnableAutoScale(false);
    config.getQuestionConfig().setEnableQuestionWeight(false);
  }

  public void testExcluded() {
    Question q1 = new Question(1, "1", QuestionType.BINARY, 2.0, 1.0, 1.0, new double[] {});
    Question q2 = new Question(2, "2", QuestionType.BINARY, 1.0, 3.0, 10.0, new double[] {3});
    Question q3 = new Question(3, "3", QuestionType.BINARY, 10.0, 3.0, 1.0, new double[] {});
    Question q4 = new Question(4, "4", QuestionType.BINARY, 2.0, 3.0, 5.0, new double[] {});
    Question q5 = new Question(5, "5", QuestionType.BINARY, 1.0, 3.0, 1.0, new double[] {4});
    Question q6 = new Question(6, "6", QuestionType.BINARY, 4.0, 3.0, 3.0, new double[] {});
    List<Question> questions = new ArrayList<Question>();
    questions.add(q1);
    questions.add(q2);
    questions.add(q3);
    questions.add(q4);
    questions.add(q5);
    questions.add(q6);
    config = new DitoConfiguration(new Input(), new Output(), new Method(DistanceMethodEnum.get("BinaryTest")), new QuestionConfig(), questions);
    m = Matrix.createDoubleMatrix(new double[][] {{-1, 3, 1, 0, 4, 1}, {-2, 0, 3, 2, 1, -1}});
    config.setData(new ArrayList<String>(), m.transpose());
    config.getQuestionConfig().setEnableScale(false);
    config.getQuestionConfig().setEnableAutoScale(false);
    config.getQuestionConfig().setEnableQuestionWeight(false);
    algo = new DistanceAlgorithm(config, false);
    Matrix scaled = algo.doIt(false);
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{2001, 1010}, {1010, 3001}}));
  }
  
  public void testRecode() {
    try {
      DitoConfiguration config = DitoConfiguration.loadFromFile("./testdata/m10x4mixedData-binary.dito");
      config.loadMatrix();
      algo = new DistanceAlgorithm(config, false);
      Matrix m = algo.getRescaledOfFiltered();
      m = algo.getRecoded(m);
      
      Matrix ref = Matrix.readFromFile(new File("./testdata/m10x4mixedData-recoded.csv"), ' ').getMatrix();
      assertEquals(m.transpose(), ref);
    } catch (Exception e) {
      fail();
    } 
  }
}
