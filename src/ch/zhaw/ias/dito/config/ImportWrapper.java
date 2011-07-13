package ch.zhaw.ias.dito.config;

import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.NotThreadSafe;

import ch.zhaw.ias.dito.Matrix;

@NotThreadSafe
public class ImportWrapper {
  private List<String> columnNames = new ArrayList<String>();
  private Matrix matrix = new Matrix();
  
  public ImportWrapper(List<String> columnNames, Matrix matrix) {
    this.columnNames = columnNames; 
    this.matrix = matrix;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public Matrix getMatrix() {
    return matrix;
  }
}
