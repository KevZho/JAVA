package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenFunction pamti nazive
 * funkcija.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TokenFunction extends Token {

	private String name;
	
	/**
	 * Kontruktor koji postavlja vrijednost propertya
	 * name.
	 * 
	 * @param name Vrijednost koju poprima property name.
	 */
	public TokenFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Overridana metoda asText koja vraća string reprezentaciju
	 * propertya name.
	 * 
	 */
	@Override
	public String asText() {
		return "@" + this.name;
	}

	
	/**
	 * Get metoda koja dohvaća ispravnu vrijednost
	 * propertya name.
	 * 
	 * @return Prava vrijednost properya name.
	 */
	public String getName() {
		return this.name;
	}
}
