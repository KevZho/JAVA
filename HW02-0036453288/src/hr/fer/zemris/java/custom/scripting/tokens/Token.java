package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Bazni razred Token za hijerahiju razreda koji modeliraju
 * izraze.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Token {
	
	/**
	 * Metoda koju sve izvedene metode overridaju.
	 * 
	 * @return String reprezentacija onoga što razred pohranjuje; bazni
	 * 			vraća null.
	 */
	public String asText() {
		return null;
	}
}
