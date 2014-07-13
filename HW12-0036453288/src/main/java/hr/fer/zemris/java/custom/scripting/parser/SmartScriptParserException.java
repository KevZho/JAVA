package hr.fer.zemris.java.custom.scripting.parser;


/**
 * Razred SmartScripParserExpception implementira iznimku koja se
 * baca ukoliko dođe do greške u parsiranju.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = -7146405271722626823L;

	/**
	 * Konstruktor razreda SmartScripParserException. 
	 * 
	 * @param msg Poruka koja se šalje.
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}
}
