package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.DivergenceOp2;

public class DivergenceDist extends AbstractDist {
  public double distance(DVector a, DVector b) {
    DVector dist = a.zipWith(b, new DivergenceOp2());
    return dist.sum()/dist.filteredLength();
  } 
}
