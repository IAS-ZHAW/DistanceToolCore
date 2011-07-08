package ch.zhaw.ias.dito.ops;

/**
 * A simple interface for all operations with 2 operands (binary operations)
 * All implementing classes must be threadsafe.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
public interface Operation2 {
	public double execute(double a, double b);
}
