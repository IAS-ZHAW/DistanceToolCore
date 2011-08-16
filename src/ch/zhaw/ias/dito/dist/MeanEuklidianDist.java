package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.EuklidOp2;

public class MeanEuklidianDist extends AbstractDist {
	
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new EuklidOp2());
		return Math.sqrt(dist.sum())/dist.filteredLength();
	}	
}
