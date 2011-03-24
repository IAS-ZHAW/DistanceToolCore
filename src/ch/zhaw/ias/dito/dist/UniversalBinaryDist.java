package ch.zhaw.ias.dito.dist;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.jcip.annotations.ThreadSafe;

/**
 * Since engine.getParamter("THREADING") returns MULTITHREADED it should be safe to use this implementation
 * in a multithreaded environment.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 *
 */
@ThreadSafe
public class UniversalBinaryDist extends AbstractBinaryDist {
  private ScriptEngine engine;
  private Invocable invocableEngine;
  private String exp;
  
  public UniversalBinaryDist() {
    ScriptEngineManager factory = new ScriptEngineManager();
    engine = factory.getEngineByName("JavaScript");
    invocableEngine = (Invocable) engine; 
  }
  
  public synchronized void setExpression(String exp) {
    this.exp = exp;
    try {
      engine.eval("function distance(a, b, c, d) {" +
          "  return " + exp + ";" + 
          "}");
    } catch (ScriptException e) {
      throw new IllegalStateException("error during script evaluation: " + exp, e);
    }
  }
  
  @Override
  public synchronized double distance(double a, double b, double c, double d) {
    try {
      return (Double) invocableEngine.invokeFunction("distance", a, b, c, d);
    } catch (ScriptException e) {
      throw new IllegalStateException("error during script evaluation: " + exp, e);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException("error during script evaluation: " + exp, e);
    }
  }
}
