package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.*;
import ch.zhaw.ias.dito.ops.*;

public class EuklidianDist extends AbstractDist {
	
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new EuklidOp());
		return Math.sqrt(dist.sum());
	}	
}
