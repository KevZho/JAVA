package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenConstantDouble pamti double
 * vrijednosti.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 */
public class TokenConstantDouble extends Token {
	
	private double value;
	
	/**
	 * Konstruktor koji postavlja property value.
	 * 
	 * @param value Vrijednost koju poprima property value.
	 */
	public TokenConstantDouble(double value) {
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
	 * Get metoda za dohvaćanje prave, double vrijednosti 
	 * propertya value.
	 * 
	 * @return Prava vrijednost propertya value.
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ITokenVisitor visitor) {
		visitor.visitTokenConstantDouble(this);
	}
}
