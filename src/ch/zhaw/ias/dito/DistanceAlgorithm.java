package ch.zhaw.ias.dito;

import java.util.List;

import ch.zhaw.ias.dito.config.DitoConfiguration;
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
    DistanceMethodEnum method = config.getMethod().getMethod();
    if (isCompatible(config.getQuestions(), method) == false) {
      throw new IllegalArgumentException("questions and method don't match");
    }
  }
    
  public static boolean isCompatible(List<Question> questions, DistanceMethodEnum method) {
    if (method.getCoding() == Coding.REAL) {
      return true;
    }
    for (Question q : questions) {
      if (q.getQuestionType() == QuestionType.METRIC || q. getQuestionType() == QuestionType.ORDINAL) {
        return false;
      }
    }
    return true;
  }
  
  public Matrix getRescaled() {
    QuestionConfig qc = config.getQuestionConfig();
    Matrix inputM = config.getData();

    DVector[] rescaled = new DVector[inputM.getColCount()];
    for (int i = 0; i < inputM.getColCount(); i++) {
      DVector v = inputM.col(i);
      Question q = config.getQuestion(i+1);
      if (qc.isEnableScale()) {
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
  
  public Matrix doIt() {
    System.out.println("calculating " + config.getMethod().getName() + " distances");
    long start = System.currentTimeMillis();
    Matrix scaled = getRescaled();
    System.out.println("rescaling finished after " + (System.currentTimeMillis() - start) + " ms");
    start = System.currentTimeMillis();
    scaled = scaled.transpose();
    System.out.println("transposing finished after " + (System.currentTimeMillis() - start) + " ms");
    start = System.currentTimeMillis();         
    DistanceSpec spec = DistanceMethodEnum.get(config.getMethod().getName()).getSpec();
    dist = scaled.calculateDistance(spec, false);
    System.out.println("calculations finished after " + (System.currentTimeMillis() - start) + " ms");
    
    return dist;
  }
}
