package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Razred EchoNode nasljeđuje razred Node. Predstavlja
 * naredbu koja dinamički generira neki tekst.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class EchoNode extends Node {
	
	private Token[] tokens;
	
	/**
	 * Kontruktor koji postavlja property tokens.
	 * 
	 * @param tokens Vrijednost koju poprima property tokens.
	 */
	public EchoNode(Token[] tokens) {
		this.tokens = tokens;
	}
	
	
	/**
	 * Get metoda koja dohvaća vrijednost propertya tokens.
	 * 
	 * @return Vrijednost propertya tokens.
	 */
	public Token[] getTokens() {
		return tokens;
	}
	
	/**
	 * Overridana metoda koja vraća sadržaj EchoNoda u string
	 * formatu.
	 */
	@Override
	public String asText() {
		String ret = "";
		
		for(int i = 0; i < tokens.length; i++) {
			ret += tokens[ i ].asText() + " ";
		}
		
		return ret;
	}
}
