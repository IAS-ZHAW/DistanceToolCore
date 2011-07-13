package ch.zhaw.ias.dito;

public interface EigenvalueDecomposition {
  public double[] getSortedEigenvalues();
  public double[][] getReducedDimensions();
  public double[][] getReducedDimensions(int dimensions);
}
