package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.ManhattanOp2;

public class MeanManhattanDist extends AbstractDist {

	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new ManhattanOp2());
		return dist.sum()/dist.filteredLength();
	}
}
