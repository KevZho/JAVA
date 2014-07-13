package hr.fer.zemris.bool.opimpl;

import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

/**
 * Razred <code>BooleanOperatorAND</code> nasljeđuje razred
 * <code>BooleanOperator</code>. Omogućava korištenje AND
 * operatora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanOperatorAND extends BooleanOperator {

	/**
	 * Konstruktor razreda <code>BooleanOperatorAND</code> prima
	 * listu izvora.
	 * 
	 * @param sources Lista izvora.
	 */
	public BooleanOperatorAND(List<BooleanSource> sources) {
		super(sources);
	}

	
	/**
	 * Overridana metoda getValue() koja dohvaća vrijednost koju daje 
	 * rezultat primjene operatora.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {
		List<BooleanSource> sources = getSources();
		BooleanValue result = sources.get(0).getValue();
		
		if(sources.size() == 1) {
			return result;
		}
		
		for(int i = 1, end = sources.size(); i < end; i++) {
			BooleanValue value = sources.get(i).getValue();
			result = ThreeValuedLogic.OPERATION.and(result, value);
		}
		
		return result;
	}
	
}
