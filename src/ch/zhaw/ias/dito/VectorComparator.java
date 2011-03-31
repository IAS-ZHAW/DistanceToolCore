package ch.zhaw.ias.dito;

import java.util.Comparator;

public class VectorComparator implements Comparator<DVector>{
  private int column;
  
  public VectorComparator(int column) {
    this.column = column;
  }
  
  @Override
  public int compare(DVector o1, DVector o2) {
    return (int) Math.signum(o1.component(column) - o2.component(column));
  }
}
