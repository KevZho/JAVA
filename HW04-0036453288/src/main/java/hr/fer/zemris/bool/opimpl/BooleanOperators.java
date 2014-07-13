package hr.fer.zemris.bool.opimpl;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;

/**
 * Razred <code>BooleanOperators</code> sadrži metode 
 * tvornice koje instanciraju operatore.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanOperators {

	/**
	 * Privatni konstruktor razreda <code>BooleanOperators</code>.
	 * 
	 */
	private BooleanOperators() {
	}
	
	
	/**
	 * Statička metoda koja instancira and operator.
	 * 
	 * @param sources Lista izvora dobivena kao više argumenata.
	 * @return Instanca razreda BooleanOperator.
	 */
	public static BooleanOperator and(BooleanSource ... sources) {
		return new BooleanOperatorAND(createList(sources));
	}

	
	/**
	 * Statička metoda koja instancira or operator.
	 * 
	 * @param sources Lista izvora dobivena kao više argumenata.
	 * @return Instanca razreda BooleanOperator.
	 */
	public static BooleanOperator or(BooleanSource ... sources) {
		return new BooleanOperatorOR(createList(sources));
	}
	
	
	/**
	 * Statička metoda koja instancira not operator.
	 * 
	 * @param source Izvor.
	 * @return Instanca razreda BooleanOperator.
	 */
	public static BooleanOperator not(BooleanSource source) {
		return new BooleanOperatorNOT(source);
	}
	
	
	/**
	 * Statička metoda koja generira listu izvora.
	 * 
	 * @param sources Lista izvora dobivena kao više argumenata.
	 * @return Lista izvora.
	 */
	private static List<BooleanSource> createList(BooleanSource... sources) {
		return Arrays.asList(sources);
	}
	
}
