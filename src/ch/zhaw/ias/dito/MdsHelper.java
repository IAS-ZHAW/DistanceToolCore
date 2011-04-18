package ch.zhaw.ias.dito;

import java.util.Arrays;

import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

import Jama.EigenvalueDecomposition;

public class MdsHelper {

  private MdsHelper() {
    
  }
  
  public static double[][] getMds(Matrix m) {
    Logger.INSTANCE.log("starting mds", LogLevel.INFORMATION);
    long start = System.currentTimeMillis();
    final int NUMBER_OF_DIMENSIONS = 2;
    Jama.Matrix jamaM = m.toJama();
    
    //square matrix
    jamaM.arrayTimesEquals(jamaM);
    
    //get J Matrix
    Jama.Matrix j = MdsHelper.getJMatrix(m.getColCount());
    //calculate B Matrix
    Jama.Matrix b = j.times(jamaM).times(j).times(-0.5);
    
    EigenvalueDecomposition decomp = b.eig();
    double[] ev = decomp.getRealEigenvalues();
    int[] indizes = MdsHelper.getIndizesOfLargestEv(NUMBER_OF_DIMENSIONS, ev);
    Jama.Matrix eigenVectors = decomp.getV();

    Jama.Matrix valueMatrix = new Jama.Matrix(NUMBER_OF_DIMENSIONS, NUMBER_OF_DIMENSIONS, 0);
    for (int i = 0; i < NUMBER_OF_DIMENSIONS; i++) {
      valueMatrix.set(i, i, Math.sqrt(Math.abs(ev[indizes[i]])));
    }
    Jama.Matrix relevant = eigenVectors.getMatrix(0, eigenVectors.getRowDimension() - 1, indizes);
    relevant = relevant.times(valueMatrix);
    Logger.INSTANCE.log("finished mds after: " + (System.currentTimeMillis() - start), LogLevel.INFORMATION);
    return relevant.getArray();
  }
  
  public static Jama.Matrix getJMatrix(int size) {
    Jama.Matrix identity = Jama.Matrix.identity(size, size);
    Jama.Matrix tempM = new Jama.Matrix(size, size, -1.0/size);
    return identity.plus(tempM);
  }
  
  public static int[] getIndizesOfLargestEv(int count, double[] ev) {
    int[] indizes = new int[count];
    double[] sorted = Arrays.copyOf(ev, ev.length);
    for (int i = 0; i < sorted.length; i++) {
      sorted[i] = Math.abs(sorted[i]);
    }
    Arrays.sort(sorted);
    for (int i = 0; i < count; i++) {
      double value = sorted[sorted.length - 1 - i];
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
