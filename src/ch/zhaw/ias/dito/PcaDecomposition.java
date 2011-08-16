package ch.zhaw.ias.dito;

import java.util.Arrays;

import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

/**
 * The Decomposition will not scale the data. It will calculate the covariances directly.
 * Currently it supports 2 and 3 dimensionsal principal component analysis.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 *
 */
public class PcaDecomposition implements EigenvalueDecomposition {
  private Matrix dataMatrix;
  private double[] sortedEigenvalues;
  private Jama.Matrix relevantEigenVectors;
  
  public PcaDecomposition(Matrix m) {
    if (m.containsSpecial()) {
      throw new DecompositionException("decomp.ex.specialValue");
    }
    this.dataMatrix = m;
    Logger.INSTANCE.log("starting pca", LogLevel.INFORMATION);
    long start = System.currentTimeMillis();
    final int NUMBER_OF_DIMENSIONS = Math.min(m.getColCount(), 3);

    //calculate covariance
    m = m.covariance();
    Jama.Matrix jamaM = m.toJama();
    
    //decomposition
    Jama.EigenvalueDecomposition decomp = jamaM.eig();
    double[] ev = decomp.getRealEigenvalues();
    
    //this part changes depending on the number of dimensions
    int[] indizes = getIndizesOfLargestEv(NUMBER_OF_DIMENSIONS, ev);
    Jama.Matrix eigenVectors = decomp.getV();

    relevantEigenVectors = eigenVectors.getMatrix(0, eigenVectors.getRowDimension() - 1, indizes);
    Logger.INSTANCE.log("finished mds after: " + (System.currentTimeMillis() - start), LogLevel.INFORMATION);
  }
  
  public double[][] getReducedDimensions() {
    return getReducedDimensions(2);
  }
  
  public double[][] getReducedDimensions(int dimensions) {
    if (dimensions != 2 && dimensions != 3) {
      throw new IllegalArgumentException("currently only 2 or 3 dimensions are supported");
    }
    Matrix noMeans = dataMatrix.removeMeans();
    Jama.Matrix relevant = relevantEigenVectors.getMatrix(0, relevantEigenVectors.getRowDimension()-1, 0, dimensions-1).transpose().times(noMeans.toJama());    
    return relevant.transpose().getArray();    
  }
  
  public double[][] getQuestions(int dimensions) {
    if (dimensions != 2 && dimensions != 3) {
      throw new IllegalArgumentException("currently only 2 or 3 dimensions are supported");
    }
    Jama.Matrix relevant = relevantEigenVectors.getMatrix(0, relevantEigenVectors.getRowDimension()-1, 0, dimensions-1).transpose();    
    return relevant.transpose().getArray();    
  }
  
  public double[] getSortedEigenvalues() {
    return sortedEigenvalues;
  }  
  
  public int[] getIndizesOfLargestEv(int count, double[] ev) {
    if (count > ev.length) {
      throw new IllegalArgumentException("argument count too big: " + count + ">" + ev.length);
    }
    int[] indizes = new int[count];
    sortedEigenvalues = Arrays.copyOf(ev, ev.length);
    // calculate the absolute values of all eigenvalues. not 100% sure whether this is correct/does make sense
    for (int i = 0; i < sortedEigenvalues.length; i++) {
      sortedEigenvalues[i] = Math.abs(sortedEigenvalues[i]);
    }
    Arrays.sort(sortedEigenvalues);
    for (int i = 0; i < count; i++) {
      double value = sortedEigenvalues[sortedEigenvalues.length - 1 - i];
      for (int k = 0; k < ev.length; k++) {
        if (Math.abs(ev[k]) == value) {
          indizes[i] = k;
          break;
        }
      }
    }
    return indizes;
  }
}
