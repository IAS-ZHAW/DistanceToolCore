package ch.zhaw.ias.dito.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Output;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.io.CommandlineParser;
import junit.framework.TestCase;

public class TestDitoConfiguration extends TestCase {
  private DitoConfiguration config;
  
  @Override
  protected void setUp() throws Exception {
    Question q1 = new Question(1, "1", 2.0, 1.0, 1.0);
    Question q2 = new Question(2, "2", 1.0, 2.0, 1.0);
    List<Question> questions = new ArrayList<Question>();
    questions.add(q1);
    questions.add(q2);
    config = new DitoConfiguration(new Input(), new Output(), new Method(), questions);  
  }
  
  public void testRescale() {
    Matrix m = new Matrix(new double[][] {{1, 2}, {3, 4}});
    Matrix scaled = config.rescale(m);
    assertEquals(scaled, new Matrix(new double[][] {{0.5, 1}, {6, 8}}));
  }
}
