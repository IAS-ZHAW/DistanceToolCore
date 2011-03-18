package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.List;

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

public class TestDistanceAlgorithm extends TestCase {
  private DitoConfiguration config;
  private Matrix m;
  private DistanceAlgorithm algo;
  
  @Override
  protected void setUp() throws Exception {
    Question q1 = new Question(1, "1", QuestionType.ORDINAL, 2.0, 1.0, 1.0);
    Question q2 = new Question(2, "2", QuestionType.ORDINAL, 1.0, 3.0, 1.0);
    List<Question> questions = new ArrayList<Question>();
    questions.add(q1);
    questions.add(q2);
    config = new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), questions);
    
    m = Matrix.createDoubleMatrix(new double[][] {{-1, 3, 0, Double.NaN, 1}, {-2, 0, Double.NaN, 2, 1}});
    config.setData(m);
    algo = new DistanceAlgorithm(config);
    config.getQuestionConfig().setEnableScale(false);
    config.getQuestionConfig().setEnableAutoScale(false);
    config.getQuestionConfig().setEnableQuestionWeight(false);
  }

  public void testNoWeightNoScaling() {
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, m);

    config.getQuestionConfig().setEnableAutoScale(true);
    scaled = algo.getRescaled();
    assertEquals(scaled, m);
  }
  
  public void testNoWeightScaling() {
    config.getQuestionConfig().setEnableScale(true);
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{-0.5, 1.5, 0, Double.NaN, 0.5}, {-2, 0, Double.NaN, 2, 1}}));
  }
  
  public void testWeightScaling() {
    config.getQuestionConfig().setEnableScale(true);
    config.getQuestionConfig().setEnableQuestionWeight(true);
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{-0.5, 1.5, 0, Double.NaN, 0.5}, {-6, 0, Double.NaN, 6, 3}}));    
  }
  
  public void testNoWeightAutoScaling() {
    config.getQuestionConfig().setEnableScale(true);
    config.getQuestionConfig().setEnableAutoScale(true);
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{0, 1, 0.25, Double.NaN, 0.5}, {0, 0.5, Double.NaN, 1.0, 0.75}}));    
  }
  
  public void testWeightAutoScaling() {
    config.getQuestionConfig().setEnableScale(true);
    config.getQuestionConfig().setEnableAutoScale(true);
    config.getQuestionConfig().setEnableQuestionWeight(true);
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{0, 1, 0.25, Double.NaN, 0.5}, {0, 1.5, Double.NaN, 3.0, 2.25}}));    
  }
}
