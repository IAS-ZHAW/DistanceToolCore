package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import ch.zhaw.ias.dito.DistanceAlgorithm;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Output;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.config.QuestionConfig;

public class TestDistanceAlgorithm extends TestCase {
  private DitoConfiguration config;
  private Matrix m;
  private DistanceAlgorithm algo;
  
  @Override
  protected void setUp() throws Exception {
    Question q1 = new Question(1, "1", 2.0, 1.0, 1.0);
    Question q2 = new Question(2, "2", 1.0, 2.0, 1.0);
    List<Question> questions = new ArrayList<Question>();
    questions.add(q1);
    questions.add(q2);
    config = new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), questions);
  }
  
  public void testRescale() {
    Matrix m = Matrix.createDoubleMatrix(new double[][] {{1, 2}, {3, 4}});
    config.setData(m);
    algo = new DistanceAlgorithm(config);
    Matrix scaled = algo.getRescaled();
    assertEquals(scaled, Matrix.createDoubleMatrix(new double[][] {{0.5, 1}, {6, 8}}));
  }
  
  public void testRescaleMissing() {
    //config = new DitoConfiguration(new Input(), new Output(), new Method(), questions);     
  }
}
