package hr.fer.zemris.bool.opimpl;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

/**
 * Razred <code>BooleanOperatorNOT</code> nasljeđuje razred
 * <code>BooleanOperator</code>. Omogućava korištenje NOT
 * operatora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanOperatorNOT extends BooleanOperator {
		
	/**
	 * Konstruktor razreda <code>BooleanOperatorNOT</code> prima
	 * izvor.
	 * 
	 * @param source Izvor.
	 */
	public BooleanOperatorNOT(BooleanSource source) {
		super(Arrays.asList(source));
	}

	
	/**
	 * Overridana metoda getValue koja vraća rezultat dobiven
	 * provođenjem operacije NOT.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {
		List<BooleanSource> sources = getSources();
		BooleanValue value = sources.get(0).getValue();
		return ThreeValuedLogic.OPERATION.not(value);
	}
}
