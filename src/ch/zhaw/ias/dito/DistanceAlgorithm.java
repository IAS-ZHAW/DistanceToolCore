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
  private Matrix inputM;
  private Matrix dist;
  
  public DistanceAlgorithm(Matrix inputM, DitoConfiguration config) {
    this.config = config;
    this.inputM = inputM;
  }
    
  public Matrix getRescaled() {
    DVector[] rescaled = new DVector[inputM.getColCount()];
    for (int i = 0; i < inputM.getColCount(); i++) {
      DVector v = inputM.col(i);
      Question q = config.getQuestion(i+1);
      double multiplier = q.getQuestionWeight()/q.getScaling();
      rescaled[i] = v.rescale(multiplier, 0);
    }
    return new Matrix(rescaled);
  }
  
  public void doIt() throws IOException {
    System.out.println("calculating " + config.getMethod().getName() + " distances");
    long start = System.currentTimeMillis();
    Matrix scaled = getRescaled();
    System.out.println("rescaling finished after " + (System.currentTimeMillis() - start) + " ms");
    start = System.currentTimeMillis();
    //m = m.transpose();
    System.out.println("transposing finished after " + (System.currentTimeMillis() - start) + " ms");
    start = System.currentTimeMillis();         
    DistanceSpec spec = DistanceMethodEnum.get(config.getMethod().getName()).getSpec();
    dist = scaled.calculateDistance(spec, false);
    System.out.println("calculations finished after " + (System.currentTimeMillis() - start) + " ms");
  }
  
  public void writeToFile() throws IOException {
    String outputFilename = config.getOutput().getFilename().replace("$$METHOD$$", config.getMethod().getName());
    System.out.println("writing results to output-file: " + outputFilename);
    long start = System.currentTimeMillis();
    FileWriter fw = new FileWriter(outputFilename);
    //BufferedWriter bw = new BufferedWriter(fw, 8192);
    CSVWriter writer = new CSVWriter(fw, config.getOutput().getSeparator(), CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.NO_QUOTE_CHARACTER);
    
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(config.getOutput().getPrecision());
    nf.setGroupingUsed(false);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    for (int i = 0; i < dist.getColCount(); i++) {
      DVector col = dist.col(i);
      //StringBuffer sb = new StringBuffer(col.length()*10);
      String[] line = new String[col.length()];
      for (int j = 0; j < col.length(); j++) {
        line[j] = nf.format(col.component(j));
        //sb.append(nf.format(col.component(j)));
        //nf.format();
        //bw.write(Double.toString(col.component(j)));
      }
      //sb.append("\r\n");
      //bw.write("\r\n");
      writer.writeNext(line);
    }
    System.out.println("writing output finished after " + (System.currentTimeMillis() - start) + " ms");
    //bw.close();
    fw.close();
  }
}
