package ch.zhaw.ias.dito.test;

import ch.zhaw.ias.dito.Coding;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.QuestionType;
import ch.zhaw.ias.dito.config.Question;
import junit.framework.TestCase;

public class TestQuestion extends TestCase {
	public void testRecodeBinaryQuestion() {
	  Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0);
	  q.setData(new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	  
	  assertEquals(q.recode(Coding.BINARY)[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	  assertEquals(q.recode(Coding.REAL)[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
		/*Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0);
		q.setData(new DVector(3, 5, 10, 1, 0, -1, Double.NaN));

		assertEquals(q.recode(Coding.REAL)[0], new DVector(3, 5, 10, 1, 0, -1, Double.NaN));
		q.setQuestionType(QuestionType.BINARY);
		assertEquals(q.recode(Coding.REAL)[0], new DVector(1, 1, 1, 1, 0, Double.NaN, Double.NaN));
		assertEquals(q.recode(Coding.BINARY)[0], new DVector(1, 1, 1, 1, 0, Double.NaN, Double.NaN));*/
	}
	
	public void testRecodeNominal() {
	  Question q = new Question(1, "test", QuestionType.NOMINAL, 1.0, 1.0, 1.0);
    q.setData(new DVector(5, 1, 2, 4, 1, 5, Double.NaN));
    assertEquals(q.getQuestionType(), QuestionType.NOMINAL);
    DVector[] values = q.recode(Coding.BINARY);
    assertEquals(values.length, 5);
    assertEquals(values[0], new DVector(0, 1, 0, 0, 1, 0, Double.NaN));
    assertEquals(values[1], new DVector(0, 0, 1, 0, 0, 0, Double.NaN));
    assertEquals(values[2], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
    assertEquals(values[3], new DVector(0, 0, 0, 1, 0, 0, Double.NaN));
    assertEquals(values[4], new DVector(1, 0, 0, 0, 0, 1, Double.NaN));
    //assertEquals(q.recode(Coding.BINARY)[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
    //assertEquals(q.recode(Coding.REAL)[0], new DVector(1, 0, 0, 1, 0, 0, Double.NaN));
	}
	
	public void testRecodeOrdinal() {
	  Question q = new Question(1, "test", QuestionType.ORDINAL, 1.0, 1.0, 1.0);
	  q.setData(new DVector(5, 1, 2, 4, 1, -1, Double.NaN));
	  assertEquals(q.recode(Coding.REAL)[0], new DVector(5, 1, 2, 4, 1, -1, Double.NaN));
    DVector[] values = q.recode(Coding.BINARY);
    assertEquals(q.getQuestionType(), QuestionType.ORDINAL);
    assertEquals(values.length, 7);
    assertEquals(values[0], new DVector(0, 0, 0, 0, 0, 1, Double.NaN));
    assertEquals(values[1], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
    assertEquals(values[2], new DVector(0, 1, 0, 0, 1, 0, Double.NaN));
    assertEquals(values[3], new DVector(0, 0, 1, 0, 0, 0, Double.NaN));
    assertEquals(values[4], new DVector(0, 0, 0, 0, 0, 0, Double.NaN));
    assertEquals(values[5], new DVector(0, 0, 0, 1, 0, 0, Double.NaN));
    assertEquals(values[6], new DVector(1, 0, 0, 0, 0, 0, Double.NaN));
	}
  
  public void testQuestionEquals() {
    Question q = new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0);
    assertEquals(q.equals(null), false);
    assertEquals(q.equals("asfd"), false);
    assertEquals(q, q);
    assertEquals(q, new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0));
  }
  
  public void testTypeChange() {
    Question q = new Question(3, "test", QuestionType.ORDINAL, 2.0, 3.0, 4.0);
    q.setData(new DVector(1, 0, 1));
    assertEquals(q.getQuestionType(), QuestionType.BINARY);
  }
}
