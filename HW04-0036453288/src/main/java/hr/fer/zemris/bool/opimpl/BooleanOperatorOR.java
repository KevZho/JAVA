package hr.fer.zemris.bool.opimpl;

import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

/**
 * Razred <code>BooleanOperatorOR</code> nasljeđuje razred
 * <code>BooleanOperator</code>. Omogućava korištenje OR
 * operatora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanOperatorOR extends BooleanOperator {

	/**
	 * Konstruktor razreda <code>BooleanOperatorOR</code> prima
	 * listu izvora.
	 * 
	 * @param sources Lista izvora.
	 */
	public BooleanOperatorOR(List<BooleanSource> sources) {
		super(sources);
	}

	
	/**
	 * Overridana metoda getValue() vraća rezultat dobiven
	 * primjenom OR operatora.
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
			result = ThreeValuedLogic.OPERATION.or(result, value);
		}
		
		return result;
	}
	
}
