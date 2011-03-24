package ch.zhaw.ias.dito.ops;

/**
 * A simple interface for all operations with 1 operands (unary operations)
 * All implementing classes must be threadsafe.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
public interface Operation1 {
	public double execute(double a);
}
