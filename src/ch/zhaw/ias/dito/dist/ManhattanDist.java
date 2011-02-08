package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.ManhattanOp;

public class ManhattanDist extends AbstractDist {

	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new ManhattanOp());
		return dist.sum();
	}
}
