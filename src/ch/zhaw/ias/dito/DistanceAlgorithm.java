package ch.zhaw.ias.dito;

import java.util.ArrayList;
import java.util.Arrays;

import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Method;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.config.QuestionConfig;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import ch.zhaw.ias.dito.ops.MultiplyOp1;

public class DistanceAlgorithm {
  private DitoConfiguration config;
  private Matrix dist;
  
  public DistanceAlgorithm(DitoConfiguration config) {
    this.config = config;
  }
  
  /**
   * TODO Refactor this method. Quite complicated.
   * @return
   */
  public Matrix getRescaledOfFiltered() {
    Method m = config.getMethod();
    QuestionConfig qc = config.getQuestionConfig();
    Matrix inputM = config.getData();

    DVector[] rescaled = new DVector[inputM.getColCount()];
    for (int i = 0; i < inputM.getColCount(); i++) {
      Question q = config.getQuestion(i+1);
      DVector v = q.getExcludedVector();
      if (qc.isEnableScale() && m.getMethod().getCoding() != Coding.BINARY) {
        if (qc.isEnableAutoScale()) {
          rescaled[i] = v.autoRescale();
        } else {
          double multiplier = 1/q.getScaling();        
          rescaled[i] = v.rescale(multiplier, 0);
        }
      } else {
        rescaled[i] = v;
      }
      if (qc.isEnableQuestionWeight()) {
        rescaled[i] = rescaled[i].map(new MultiplyOp1(q.getQuestionWeight()));
      }
    }
    return new Matrix(rescaled);
  }
  
  public Matrix doIt(boolean randomMode) {
    System.out.println("calculating " + config.getMethod().getName() + " distances");
    long start = System.currentTimeMillis();
    Matrix m;
    Coding coding = config.getMethod().getMethod().getCoding();
    if (coding == Coding.REAL) {
      m = getRescaledOfFiltered();
    } else {
      m = config.getData();
    }
    ArrayList<DVector> vecs = new ArrayList<DVector>();
    for (int i = 0; i < m.getColCount(); i++) {
      Question q = config.getQuestions().get(i);
      DVector[] v = m.col(i).recode(coding, q.getQuestionType());
      vecs.addAll(Arrays.asList(v));
    }
    m = new Matrix(vecs);
    
    System.out.println("rescaling finished after " + (System.currentTimeMillis() - start) + " ms");
    start = System.currentTimeMillis();
    m = m.transpose();
    System.out.println("transposing finished after " + (System.currentTimeMillis() - start) + " ms");      
    
    start = System.currentTimeMillis();         
    DistanceSpec spec = DistanceMethodEnum.get(config.getMethod().getName()).getSpec();
    dist = m.calculateDistance(spec, false);
    System.out.println("calculations finished after " + (System.currentTimeMillis() - start) + " ms");
    return dist;
  }
}
