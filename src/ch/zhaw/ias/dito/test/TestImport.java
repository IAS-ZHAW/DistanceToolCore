package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.config.Input;

import junit.framework.TestCase;

public class TestImport extends TestCase {
  public void testComplicatedImport() {
    try {
      Matrix m = Matrix.readFromFile(new File("testdata/complicatedImport.csv"), ';', true);
      assertEquals(m.getRowCount(), 3);
      assertEquals(m.getColCount(), 9);
      //normal line
      assertEquals(m.col(0), new DVector(3.0, 5.0, 10.0));
      //including whitespaces and colons
      assertEquals(m.col(1), new DVector(3.0, 5.0, 10.0));
      //exponential format
      assertEquals(m.col(2), new DVector(3.0, 0.50, 100.0));
      //no end semicolon
      assertEquals(m.col(3), new DVector(3.0, 5.0, 10.0));
      // don't remove " and '
      assertEquals(m.col(4), new DVector(Double.NaN, 1.654, Double.NaN));
      // text to NaN
      assertEquals(m.col(5), new DVector(Double.NaN, 10, Double.NaN));
      // negative and no leading zero
      assertEquals(m.col(6), new DVector(-4.15, -0.1487, 0.1457));
      // explicit plus
      assertEquals(m.col(7), new DVector(4.15, 0.1487, 0.1457));      
      // empty line
      assertEquals(m.col(8), new DVector(Double.NaN, Double.NaN, Double.NaN));      
    } catch (IOException e) {
      fail();
    }
  }
  
  public void testExcludeQuestionsImport() throws IOException {
    Input i = new Input(new File("testdata/complicatedImport.csv"), ';', true, false, 1, 3, true, -1, -2);    
    Matrix m = Matrix.readFromFile(i);
    assertEquals(m.getRowCount(), 3);
    i.setStartQuestion(2);
    m = Matrix.readFromFile(i);
    assertEquals(m.getRowCount(), 2);
    assertEquals(m.col(0), new DVector(5.0, 10.0));
    assertEquals(m.col(2), new DVector(0.50, 100.0));
    i.setEndQuestion(2);
    m = Matrix.readFromFile(i);    
    assertEquals(m.getRowCount(), 1);    
    i.setAllQuestions(true);
    m = Matrix.readFromFile(i);
    assertEquals(m.getRowCount(), 3);    
  }
  
  public void testExcludeSurveysImport() throws IOException {
    Input i = new Input(new File("testdata/complicatedImport.csv"), ';', true, true, -1, -2, false, 1, 5);
    Matrix m = Matrix.readFromFile(i);
    assertEquals(m.getColCount(), 5);     
    i.setStartSurvey(2);
    m = Matrix.readFromFile(i);
    assertEquals(m.getColCount(), 4);
    assertEquals(m.col(1), new DVector(3.0, 0.5, 100.0));
    assertEquals(m.col(3), new DVector(Double.NaN, 1.654, Double.NaN));
    i.setQuestionTitles(false);
    m = Matrix.readFromFile(i);
    assertEquals(m.getColCount(), 4);
    assertEquals(m.col(2), new DVector(3.0, 0.5, 100.0));
    i.setAllSurveys(true);
    m = Matrix.readFromFile(i);
    assertEquals(m.getColCount(), 10);
    i.setQuestionTitles(true);
    m = Matrix.readFromFile(i);
    assertEquals(m.getColCount(), 9);
  }
  
  public void testWhitespaceImport() {
    try {
      Matrix m = Matrix.readFromFile(new File("testdata/whitespaceImport.csv"), ' ', false);
      assertEquals(m.getRowCount(), 3);
      assertEquals(m.getColCount(), 2);
      //normal line
      assertEquals(m.col(0), new DVector(4.0, 3.1, 4.143));
      //including whitespaces and colons
      assertEquals(m.col(1), new DVector(1.24, 5.97, .965));    
    } catch (IOException e) {
      fail();
    }
  }
}
