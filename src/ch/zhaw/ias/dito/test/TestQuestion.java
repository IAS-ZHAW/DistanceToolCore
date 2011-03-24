package ch.zhaw.ias.dito.test;

import java.util.EnumSet;

import ch.zhaw.ias.dito.Coding;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.QuestionType;
import ch.zhaw.ias.dito.config.Question;
import junit.framework.TestCase;

public class TestQuestion extends TestCase {
	public void testRecodeBinaryQuestion() {
	  Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0, new double[0]);
	  q.setData(new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	  
	  DVector[] values = q.recode(Coding.BINARY);
	  assertEquals(values.length, 1);
	  assertEquals(values[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	  values = q.recode(Coding.REAL);
	  assertEquals(values.length, 1);
	  assertEquals(values[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	}
	
	public void testRecodeNominal() {
	  Question q = new Question(1, "test", QuestionType.NOMINAL, 1.0, 1.0, 1.0, new double[0]);
    q.setData(new DVector(5, 1, 2, 4, 1, 5, Double.NaN));
    assertEquals(q.getQuestionType(), QuestionType.NOMINAL);
    EnumSet<Coding> set = EnumSet.of(Coding.BINARY, Coding.REAL);
    for (Coding c : set) {
      DVector[] values = q.recode(c);
      assertEquals(values.length, 5);
      assertEquals(values[0], new DVector(0, 1, 0, 0, 1, 0, Double.NaN));
      assertEquals(values[1], new DVector(0, 0, 1, 0, 0, 0, Double.NaN));
      assertEquals(values[2], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
      assertEquals(values[3], new DVector(0, 0, 0, 1, 0, 0, Double.NaN));
      assertEquals(values[4], new DVector(1, 0, 0, 0, 0, 1, Double.NaN));  
    }
	}
	
	public void testRecodeOrdinal() {
	  Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0, new double[0]);
	  q.setData(new DVector(5, 1, 2, 4, 1, -1, Double.NaN));
    assertEquals(q.getQuestionType(), QuestionType.ORDINAL);
    
    DVector[] values = q.recode(Coding.REAL);
    assertEquals(values.length, 1);
    assertEquals(values[0], new DVector(5, 1, 2, 4, 1, -1, Double.NaN));
    values = q.recode(Coding.BINARY);
    assertEquals(values.length, 7);
    assertEquals(values[0], new DVector(0, 0, 0, 0, 0, 1, Double.NaN));
    assertEquals(values[1], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
    assertEquals(values[2], new DVector(0, 1, 0, 0, 1, 0, Double.NaN));
    assertEquals(values[3], new DVector(0, 0, 1, 0, 0, 0, Double.NaN));
    assertEquals(values[4], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
    assertEquals(values[5], new DVector(0, 0, 0, 1, 0, 0, Double.NaN));
    assertEquals(values[6], new DVector(1, 0, 0, 0, 0, 0, Double.NaN));
	}
  
	public void testRecodeMetric() {
    Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0, new double[0]);
    q.setData(new DVector(5, 1, 2.4, 4.2, 1.25, 0, Double.NaN, 1.001));
    assertEquals(q.getQuestionType(), QuestionType.METRIC);
    DVector[] values = q.recode(Coding.REAL);
    assertEquals(values.length, 1);
    assertEquals(values[0], new DVector(5, 1, 2.4, 4.2, 1.25, 0, Double.NaN, 1.001));
    
    values = q.recode(Coding.BINARY);
    assertEquals(values.length, 10);
    assertEquals(values[0], new DVector(0, 0, 0, 0, 0, 1, Double.NaN, 0));
    assertEquals(values[1], new DVector(0, 1, 0, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[2], new DVector(0, 0, 0, 0, 1, 0, Double.NaN, 1));
    assertEquals(values[3], new DVector(0, 0, 0, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[4], new DVector(0, 0, 1, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[5], new DVector(0, 0, 0, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[6], new DVector(0, 0, 0, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[7], new DVector(0, 0, 0, 0, 0, 0, Double.NaN, 0));
    assertEquals(values[8], new DVector(0, 0, 0, 1, 0, 0, Double.NaN, 0));
    assertEquals(values[9], new DVector(1, 0, 0, 0, 0, 0, Double.NaN, 0));
	}
	
  public void testQuestionEquals() {
    Question q = new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0, new double[0]);
    assertEquals(q.equals(null), false);
    assertEquals(q.equals("asfd"), false);
    assertEquals(q, q);
    assertEquals(q, new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0, new double[0]));
  }
  
  public void testTypeChange() {
    Question q = new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0, new double[0]);
    q.setData(new DVector(1, 0, 1));
    assertEquals(q.getQuestionType(), QuestionType.BINARY);
  }
  
  public void testGetExcludedVector() {
    Question q = new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0, new double[] {1, 0});
    q.setData(new DVector(1, 0, 1));
    assertEquals(q.getExcludedVector(), new DVector(Double.NaN, Double.NaN, Double.NaN));
  }
}
