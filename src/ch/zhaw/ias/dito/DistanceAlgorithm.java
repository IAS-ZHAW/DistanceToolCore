package ch.zhaw.ias.dito;

import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

public class DistanceAlgorithm {
  private DitoConfiguration config;
  private Matrix m;
  
  public DistanceAlgorithm(Matrix m, DitoConfiguration config) {
    this.config = config;
    this.m = m;
  }
  
  public void doIt(Matrix m, DistanceMethodEnum method) {
    if (method.isBinary()) {
      
    }
  }
  
  public Matrix rescale() {
    DVector[] rescaled = new DVector[m.getColCount()];
    for (int i = 0; i < m.getColCount(); i++) {
      DVector v = m.col(i);
      Question q = config.getQuestion(i+1);
      double multiplier = q.getQuestionWeight()/q.getScaling();
      rescaled[i] = v.rescale(multiplier, 0);
    }
    return new Matrix(rescaled);
  }
}
