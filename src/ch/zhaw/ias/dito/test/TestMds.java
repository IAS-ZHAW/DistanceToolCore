package ch.zhaw.ias.dito.test;

import java.io.File;
import java.io.IOException;

import Jama.EigenvalueDecomposition;

import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.MdsHelper;
import junit.framework.TestCase;

public class TestMds extends TestCase {
  public void testGetJ() throws IOException {
    Jama.Matrix j = MdsHelper.getJMatrix(4);
    assertEquals(Matrix.createDoubleMatrix(j.getArray()), Matrix.createDoubleMatrix(new double[][]{{0.75, -0.25, -0.25, -0.25}, {-0.25, 0.75, -0.25, -0.25}, {-0.25, -0.25, 0.75, -0.25}, {-0.25, -0.25, -0.25, 0.75}}));    
  }
  
  public void testGetIndizesOfLargestEv() {
    double[] ev = new double[] {0, 5.31, 2.43, 3.41};
    int[] indizes = MdsHelper.getIndizesOfLargestEv(2, ev);
    assertEquals(indizes[0], 1);
    assertEquals(indizes[1], 3);
//    Matrix m = Matrix.readFromFile(new File("testdata/m4x4citiesDenmark-euklid.csv"), ' ');
  } 
  
  public void testNegativeEv() {
    assertTrue(false);
  }
  
  public void testCitiesCase() throws IOException {
    Matrix m = Matrix.readFromFile(new File("testdata/m119x119cities-euklid.csv"), ';');
    double mds[][] = MdsHelper.getMds(m);
    Matrix mdsMatrix = Matrix.createDoubleMatrix(mds);
    Matrix exp = Matrix.readFromFile(new File("testdata/m119x2cities-mds.csv"), ';');
    assertTrue(mdsMatrix.equalsRounded(exp, 3));
  }
    /*Jama.Matrix b = j.times(jamaM).times(j).times(-0.5);
    assertTrue(Matrix.createDoubleMatrix(b.getArray()).equalsRounded(Matrix.createDoubleMatrix(new double[][]{{5035.0625, -1553.0625, 258.9375, -3740.9375}, {-1553.0625, 507.8125, 5.3125, 1039.9375}, {258.9375, 5.3125, 2206.8125, -2471.0625}, {-3740.9375, 1039.9375, -2471.0625, 5172.0625}}), 4));
    
    EigenvalueDecomposition decomp = b.eig();
    double[] ev = decomp.getRealEigenvalues();
    indizes = MdsHelper.getIndizesOfLargestEv(NUMBER_OF_DIMENSIONS, ev);
    Jama.Matrix eigenVectors = decomp.getV();
    
    System.out.println(ev);
    Jama.Matrix valueMatrix = new Jama.Matrix(NUMBER_OF_DIMENSIONS, NUMBER_OF_DIMENSIONS, 0);
    for (int i = 0; i < NUMBER_OF_DIMENSIONS; i++) {
      valueMatrix.set(i, i, Math.sqrt(ev[indizes[i]]));
    }
    eigenVectors.print(10, 3);
    Jama.Matrix relevant = eigenVectors.getMatrix(0, eigenVectors.getRowDimension() - 1, indizes);
    relevant.times(valueMatrix).print(10, 3);
    
  }*/
    
}
