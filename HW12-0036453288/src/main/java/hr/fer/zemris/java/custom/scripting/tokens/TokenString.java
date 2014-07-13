package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenString modelira pamti
 * string vrijednosti.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TokenString extends Token {
	
	private String value;
	
	/**
	 * Konstruktor koji postavlja vrijednost propertya
	 * value.
	 * 
	 * @param value Vrijednost koju poprima property value.
	 */
	public TokenString(String value) {
		this.value = value;
	}
	
	
	/**
	 * Overridana metoda asText vraća string reprezentaciju
	 * propertya value.
	 * 
	 */
	@Override
	public String asText() {
		return "\"" + this.value + "\"";
	}

	
	/**
	 * Get metoda koja vraća pravu vrijednost propertya
	 * value.
	 * 
	 * @return Prava vrijednost propertya value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ITokenVisitor visitor) {
		visitor.visitTokenString(this);
	}
}
