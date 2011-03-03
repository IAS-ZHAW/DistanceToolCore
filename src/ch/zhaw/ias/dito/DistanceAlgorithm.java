package ch.zhaw.ias.dito;

import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

import au.com.bytecode.opencsv.CSVWriter;
import ch.zhaw.ias.dito.config.DitoConfiguration;
import ch.zhaw.ias.dito.config.Question;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;
import ch.zhaw.ias.dito.dist.DistanceSpec;

public class DistanceAlgorithm {
  private DitoConfiguration config;
  private Matrix dist;
  
  public DistanceAlgorithm(DitoConfiguration config) {
    this.config = config;
  }
    
  public Matrix getRescaled() {
    Matrix inputM = config.getData();
    DVector[] rescaled = new DVector[inputM.getColCount()];
    for (int i = 0; i < inputM.getColCount(); i++) {
      DVector v = inputM.col(i);
      Question q = config.getQuestion(i+1);
      //double multiplier = q.getQuestionWeight()/q.getScaling();
      //rescaled[i] = v.rescale(multiplier, 0);
      
      //TODO use configured scaling
      rescaled[i] = v.autoRescale();
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
