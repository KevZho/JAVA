package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenConstantInteger pamti integer
 * vrijednosti.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 */
public class TokenConstantInteger extends Token {

	private int value;
	
	/**
	 * Konstruktor koji postavlja vrijednost propertya
	 * value.
	 * 
	 * @param value Vrijednost koju poprima property value.
	 */
	public TokenConstantInteger(int value) {
		this.value = value;
	}
	
	
	/**
	 * Overridana metoda asText vraća string reprezentaciju
	 * propertya value.
	 * 
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}

	
	/**
	 * Get metoda za dohvaćanje prave vrijednosti, int 
	 * vrijednosti propertya value. 
	 * 
	 * @return Prava vrijednost propertya value.
	 */
	public int getValue() {
		return this.value;
	}
}
