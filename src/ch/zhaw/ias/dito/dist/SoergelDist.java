package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.ManhattanOp2;
import ch.zhaw.ias.dito.ops.MaxOp2;

public class SoergelDist extends AbstractDist {
  @Override
  public double distance(DVector a, DVector b) {
    DVector n = a.zipWith(b, new ManhattanOp2());
    DVector m = a.zipWith(b, new MaxOp2());
    
    if (n.filteredLength() != m.filteredLength()) {
      throw new IllegalStateException("filtered length of both vectors should be the same but isnt. Values " + n.filteredLength() + " !=" + m.filteredLength());
    }
    double relation = n.sum()/m.sum();
    return relation/n.length();
  }
}
