package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.AddOp2;
import ch.zhaw.ias.dito.ops.MinOp2;

public class OttNiederbergerDist extends AbstractDist {
  @Override
  public double distance(DVector a, DVector b) {
    DVector sum = a.zipWith(b, new AddOp2());
    DVector min = a.zipWith(b, new MinOp2());
    
    if (sum.filteredLength() != min.filteredLength()) {
      throw new IllegalStateException("filtered length of both vectors should be the same but isnt. Values " + min.filteredLength() + " !=" + sum.filteredLength());
    }
    double minSum = min.sum();
    double relation = minSum/(sum.sum() - minSum);
    return 100*(1-relation);
  }
}
