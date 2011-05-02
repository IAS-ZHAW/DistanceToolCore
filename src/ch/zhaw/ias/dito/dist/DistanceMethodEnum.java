package ch.zhaw.ias.dito.dist;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ias.dito.Coding;

public class DistanceMethodEnum {

	private static List<DistanceMethodEnum> METHODS = new ArrayList<DistanceMethodEnum>();
	static {
    METHODS.add(new DistanceMethodEnum("Bhattacharyya", new BhattacharyyaDist(), "\\sqrt {\\sum_{i}(\\sqrt {x_{ri}}- \\sqrt {x_{si}})^2}"));
    METHODS.add(new DistanceMethodEnum("Bray-Curtis", new BrayCurtisDist(), "{1 \\over p} {\\sum_i |x_{ri}-x_{si}| \\over \\sum_i (x_{ri}+x_{si})}"));
	  METHODS.add(new DistanceMethodEnum("Canberra", new CanberraDist(), "\\sum_{i} {|x_{ri}-x_{si}| \\over x_{ri}+x_{si}}"));
	  METHODS.add(new DistanceMethodEnum("Divergence", new DivergenceDist(), "{1 \\over p} \\sum_i {(x_{ri}-x_{si})^2 \\over (x_{ri}+x_{si})^2}"));
	  METHODS.add(new DistanceMethodEnum("Euklid", new EuklidianDist(), "\\sqrt{ \\sum_{i}(x_{ri}-x_{si})^{2}}"));
		METHODS.add(new DistanceMethodEnum("Manhattan", new ManhattanDist(), "\\sum_{i} |x_{ri}-x_{si}|"));
		METHODS.add(new DistanceMethodEnum("Soergel", new SoergelDist(), "{1 \\over p} {\\sum_i |x_{ri}-x_{si}| \\over \\sum_i max(x_{ri}, x_{si})}"));
		METHODS.add(new DistanceMethodEnum("WaveHedges", new WaveHedgesDist(), "\\sum_i(1-{min(x_{ri},x_{si}) \\over max(x_{ri},x_{si})})"));
		METHODS.add(new DistanceMethodEnum("Ott-Niederberger", new OttNiederbergerDist(), "100(1-{\\sum_i min(x_{ri}, x_{si}) \\over \\sum_i (x_{ri}+x_{si}) - \\sum_i min(x_{ri}-x_{si})})"));
		METHODS.add(new DistanceMethodEnum("Niederberger", new NiederbergerDist(), ""));
		METHODS.add(new DistanceMethodEnum("Niederberger Cyklisch", new NiederbergerCyclicDist(), ""));
		
		METHODS.add(new DistanceMethodEnum("Universal", new UniversalBinaryDist(), Coding.BINARY, ""));
		METHODS.add(new DistanceMethodEnum("Braun, Blanque", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/Math.max(a+b, a+c);
      }
    }, Coding.BINARY, "a \\over max\\{(a+b),(a+c)\\}"));
    METHODS.add(new DistanceMethodEnum("Czekanowski", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return 2*a/(2*a + b + c);
      }
    }, Coding.BINARY, "2a \\over 2a+b+c"));
    METHODS.add(new DistanceMethodEnum("Hamman", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return (a - (b + c) + d)/(a + b + c + d);
      }
    }, Coding.BINARY, "{a-(b+c)+d \\over a+b+c+d}"));
    METHODS.add(new DistanceMethodEnum("Jaccard", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(a+b+c);
      }
    }, Coding.BINARY, "{a \\over a+b+c}"));    
    METHODS.add(new DistanceMethodEnum("Kulzynski", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(b+c);
      }
    }, Coding.BINARY, "{a \\over b+c}"));    
    METHODS.add(new DistanceMethodEnum("Michael", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        double n = 4*(a*d - b*c);
        double m = (a + d)*(a + d) + (b + c)*(b + c);
        return n/m;
      }
    }, Coding.BINARY, "4(ad-bc) \\over (a+d)^2 + (b+c)^2"));    
    METHODS.add(new DistanceMethodEnum("Ochiai", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/Math.sqrt((a+b)*(a+c));
      }
    }, Coding.BINARY, "a \\over \\sqrt{(a+b)(a+c)}"));
    METHODS.add(new DistanceMethodEnum("Simple", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return (a+d)/(a+b+c+d);
      }
    }, Coding.BINARY, "{a+d \\over a+b+c+d}"));
	}

	public static DistanceMethodEnum get(String name) {
		for (DistanceMethodEnum method : METHODS) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException("no such distance-method: " + name);
	}
	
	public static List<DistanceMethodEnum> get(Coding c) {
    List<DistanceMethodEnum> methods = new ArrayList<DistanceMethodEnum>();
	  for (DistanceMethodEnum method : METHODS) {
      if (method.getCoding() == c) {
        methods.add(method);
      }
    }
    return methods;
  }  
	
	private final String name;
	private final DistanceSpec spec;
	private final Coding coding;
	private final String formula; 

  private DistanceMethodEnum(String name, DistanceSpec spec, String formula) {
   this(name, spec, Coding.REAL, formula);
  }
	
	private DistanceMethodEnum(String name, DistanceSpec spec, Coding coding, String formula) {
		this.name = name;
		this.spec = spec;
		this.coding = coding;
		this.formula = formula;
	}
	
	public String getName() {
		return name;
	}
	
	public DistanceSpec getSpec() {
		return spec;
	}
	
	public Coding getCoding() {
	  return coding;
	}
	
	@Override
	public String toString() {
	  return name;
	}

  public String getFormula() {
    return formula;
  }
}
