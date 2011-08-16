package ch.zhaw.ias.dito;

import java.util.Arrays;

import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

/**
 * Currently it supports 2 and 3 dimensionsal multidimensional scaling.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 *
 */
public class MdsDecomposition implements EigenvalueDecomposition {
  private double[] sortedEigenvalues;
  private Jama.Matrix relevantEigenValues;
  private Jama.Matrix relevantEigenVectors;
  
  public MdsDecomposition(Matrix m) {
    if (m.containsSpecial()) {
      throw new DecompositionException("decomp.ex.specialValue");
    }
    Logger.INSTANCE.log("starting mds", LogLevel.INFORMATION);
    long start = System.currentTimeMillis();
    final int NUMBER_OF_DIMENSIONS = Math.min(m.getColCount(), 3);
    Jama.Matrix jamaM = m.toJama();
    
    //square matrix
    jamaM.arrayTimesEquals(jamaM);
    
    //get J Matrix
    Jama.Matrix j = getJMatrix(m.getColCount());
    //calculate B Matrix
    Jama.Matrix b = j.times(jamaM).times(j).times(-0.5);
    
    //here the decomposition cold be stored. Then the number of dimensions could be switched very fast
    //the tradeoff would be that the decomposition doubles the amount of needed memory.
    Jama.EigenvalueDecomposition decomp = b.eig();
    double[] ev = decomp.getRealEigenvalues();
    
    //this part changes depending on the number of dimensions
    int[] indizes = getIndizesOfLargestEv(NUMBER_OF_DIMENSIONS, ev);
    Jama.Matrix eigenVectors = decomp.getV();

    relevantEigenValues = new Jama.Matrix(NUMBER_OF_DIMENSIONS, NUMBER_OF_DIMENSIONS, 0);
    for (int i = 0; i < NUMBER_OF_DIMENSIONS; i++) {
      relevantEigenValues.set(i, i, Math.sqrt(Math.abs(ev[indizes[i]])));
    }
    //valueMatrix.
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
    Jama.Matrix relevant = relevantEigenVectors.getMatrix(0, relevantEigenVectors.getRowDimension()-1, 0, dimensions-1).times(relevantEigenValues.getMatrix(0, dimensions-1, 0, dimensions-1));    
    return relevant.getArray();    
  }
  
  public double[] getSortedEigenvalues() {
    return sortedEigenvalues;
  }  
  
  public Jama.Matrix getJMatrix(int size) {
    Jama.Matrix identity = Jama.Matrix.identity(size, size);
    Jama.Matrix tempM = new Jama.Matrix(size, size, -1.0/size);
    return identity.plus(tempM);
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
