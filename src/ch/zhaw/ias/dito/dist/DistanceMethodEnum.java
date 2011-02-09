package ch.zhaw.ias.dito.dist;

import java.util.ArrayList;
import java.util.List;

public class DistanceMethodEnum {

	private static List<DistanceMethodEnum> METHODS = new ArrayList<DistanceMethodEnum>();
	static {
		METHODS.add(new DistanceMethodEnum("Euklid", new EuklidianDist()));
		METHODS.add(new DistanceMethodEnum("Manhattan", new ManhattanDist()));
		METHODS.add(new DistanceMethodEnum("Canberra", new CanberraDist()));
		METHODS.add(new DistanceMethodEnum("Bhattacharyya", new BhattacharyyaDist()));
		METHODS.add(new DistanceMethodEnum("WaveHedges", new WaveHedgesDist()));
	}
	
	public static DistanceSpec get(String name) {
		for (DistanceMethodEnum method : METHODS) {
			if (method.getName().equals(name)) {
				return method.getSpec();
			}
		}
		return null;
	}
	
	private String name;
	private DistanceSpec spec;
	
	private DistanceMethodEnum(String name, DistanceSpec spec) {
		this.name = name;
		this.spec = spec;
	}
	
	public String getName() {
		return name;
	}
	
	public DistanceSpec getSpec() {
		return spec;
	}
}
