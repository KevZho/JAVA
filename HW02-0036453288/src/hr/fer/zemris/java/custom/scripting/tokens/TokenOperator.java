package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenOperator pamti simbol
 * operatora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TokenOperator extends Token {
	
	private String symbol;
	
	/**
	 * Konstruktor koji postavlja vrijednost propertya 
	 * symbol.
	 * 
	 * @param symbol Vrijednost koju poprima property symbol.
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}
	
	
	/**
	 * Overridana metoda asText koja vraća string reprezentaciju
	 * propertya symbol.
	 * 
	 */
	@Override
	public String asText() {
		return this.symbol;
	}

	
	/**
	 * Get metoda koja vraća prava vrijednost propertya
	 * symbol.
	 * 
	 * @return Prava vrijednost propertya symbol.
	 */
	public String getSymbol() {
		return this.symbol;
	}
}
