package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred TextNode nasljeđuje razred Node. Omogućava spremanje
 * tekstualnih dijelova.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Konstruktor koji postavlja vrijednost propertya text.
	 * 
	 * @param text Vrijednost koju poprima property text.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	
	/**
	 * Get metoda koja vraća vrijednost propertya.
	 * 
	 * @return Vrijednost propertya 
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Overidana asText metoda vraća tekst koji čuva
	 * razred TextNode.
	 * 
	 */
	@Override
	public String asText() {
		String ret = this.text.replaceAll("\\{", "\\\\{");
		return ret;
	}
}
