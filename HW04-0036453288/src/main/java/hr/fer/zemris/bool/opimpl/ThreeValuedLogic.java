package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanValue;

/**
 * Razred <code>ThreeValuedLogic</code> definira rezultate 
 * operacija u logici koja sadrži vrijednosti TRUE, FALSE i
 * DON'T CARE.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ThreeValuedLogic {

	public final static ThreeValuedLogic OPERATION = new
			ThreeValuedLogic();
	
	/**
	 * Privatni konstruktor razreda <code>ThreeValuedLogic</code>.
	 * 
	 */
	private ThreeValuedLogic() {
	}
	
	
	/**
	 *Metoda koja provodi operaciju AND.
	 * 
	 * @param first Prvi operand.
	 * @param second Drugi operand.
	 * @return Rezultat operacije AND.
	 */
	public  BooleanValue and(BooleanValue first, BooleanValue second) {
		if(first == BooleanValue.DONT_CARE 
				&& second != BooleanValue.FALSE) {
			return BooleanValue.DONT_CARE;
		}
		else if(second == BooleanValue.DONT_CARE 
				&& first != BooleanValue.FALSE) {
			return BooleanValue.DONT_CARE;
		}
		else if(first == BooleanValue.FALSE ) {
			return BooleanValue.FALSE;
		}
		else if(second == BooleanValue.FALSE) {
			return BooleanValue.FALSE;
		}
			
		return BooleanValue.TRUE;
	}
	
	
	/**
	 * Metoda koja provodi operaciju OR.
	 * 
	 * @param first Prvi operand.
	 * @param second Drugi operand.
	 * @return Rezultat operacije OR.
	 */
	public BooleanValue or(BooleanValue first, BooleanValue second) {		
		if(first == BooleanValue.TRUE ) {
			return BooleanValue.TRUE;
		}
		else if(second == BooleanValue.TRUE ) {
			return BooleanValue.TRUE;
		}
		else if(first == BooleanValue.DONT_CARE) {
			return BooleanValue.DONT_CARE;
		}
		
		else if(second == BooleanValue.DONT_CARE) {
			return BooleanValue.DONT_CARE;
		}
		
		return BooleanValue.FALSE;
	}
	
	
	/**
	 * Metoda koja provodi operaciju NOT.
	 * 
	 * @param value Vrijednost nad kojom se provodi operacija.
	 * @return Rezultat operacije NOT.
	 */
	public BooleanValue not(BooleanValue value) {
		if(value == BooleanValue.FALSE) {
			return BooleanValue.TRUE;
		}
		else if(value == BooleanValue.TRUE) {
			return BooleanValue.FALSE;
		}
		
		return BooleanValue.DONT_CARE;
	}
	
}
