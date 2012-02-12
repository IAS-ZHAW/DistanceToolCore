/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
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
import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

public class DistanceAlgorithm {
  private DitoConfiguration config;
  private Matrix dist;
  
  public DistanceAlgorithm(DitoConfiguration config, boolean random) {
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
          double offset = q.getOffset();
          rescaled[i] = v.rescale(multiplier, offset);
        }
      } else {
        rescaled[i] = v;
      }
      if (qc.isEnableQuestionWeight() && m.getMethod().getCoding() != Coding.BINARY) {
        rescaled[i] = rescaled[i].map(new MultiplyOp1(q.getQuestionWeight()));
      }
    }
    return new Matrix(rescaled);
  }
  
  /**
   * Only if randomMode is set to true AND the UseRandomSample in the configuration is set to true a random sample will be taken.
   * @param randomMode
   * @return
   */
  public Matrix doIt(boolean randomMode) {
    Logger.INSTANCE.log("calculating " + config.getMethod().getName() + " distances", LogLevel.INFORMATION);
    long start = System.currentTimeMillis();
    
    Matrix m = getRescaledOfFiltered();
    m = getRecoded(m);

    Logger.INSTANCE.log("rescaling finished after " + (System.currentTimeMillis() - start) + " ms", LogLevel.INFORMATION);
    start = System.currentTimeMillis();
    m = m.transpose();
    Logger.INSTANCE.log("transposing finished after " + (System.currentTimeMillis() - start) + " ms", LogLevel.INFORMATION);      

    if (randomMode && config.getMethod().isUseRandomSample()) {
      m = m.getRandomSample(config.getMethod().getSampleSize());
    }
    
    start = System.currentTimeMillis();         
    DistanceSpec spec = DistanceMethodEnum.get(config.getMethod().getName()).getSpec();
    if (config.getMethod().isParallel()) {
      dist = m.calculateDistanceParallel(spec, config.getMethod().getNumberOfThreads());
    } else {
      dist = m.calculateDistance(spec);
    }
    Logger.INSTANCE.log("calculations finished after " + (System.currentTimeMillis() - start) + " ms", LogLevel.INFORMATION);
    return dist;
  }

  /**
   * recode vectors and put them into one list (two dimensions)
   * @param m
   * @param coding
   * @return
   */
  public Matrix getRecoded(Matrix m) {
    Coding coding = config.getMethod().getMethod().getCoding();
    ArrayList<DVector> vecs = new ArrayList<DVector>();
    for (int i = 0; i < m.getColCount(); i++) {
      Question q = config.getQuestions().get(i);
      DVector[] v = m.col(i).recode(coding, q.getQuestionType());
      vecs.addAll(Arrays.asList(v));
    }
    return new Matrix(vecs);
  }
}
