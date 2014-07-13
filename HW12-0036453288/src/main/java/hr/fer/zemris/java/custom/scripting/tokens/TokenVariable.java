package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Izvedeni razred TokenVariable pamti nazive
 * varijabli.
 * 
 * @author Igor Smolkovič
 *
 */
public class TokenVariable extends Token {

	private String name;
	
	/**
	 * Konstruktor koji postavlja vrijednost propertya
	 * name.
	 * 
	 * @param name Vrijednost koju poprima property name.
	 */
	public TokenVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Overridana metoda koja vraća string reprezentaciju
	 * propertya name.
	 * 
	 */
	@Override
	public String asText() {
		return this.name;
	}

	
	/**
	 * Get metoda koja vraća pravu vrijednost propertya name.
	 * 
	 * @return Prava vrijednost propertya name.
	 */
	public String getName() {
		return this.name;
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ITokenVisitor visitor) {
		visitor.visitTokenVariable(this);
	}
}
