package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Razred ForLoopNode nasljeđuje razred Node. Omogućava 
 * korištenje for pelji.
 * 
 * @author Igor Smolkovič 
 * @version 1.0
 *
 */
public class ForLoopNode extends Node {
	
	private TokenVariable variable;
	private Token startExpression;
	private Token endExpression;
	private Token stepExpression;
	
	/**
	 * Konstruktor koji postavlja propertya variable, startExpression,
	 * endExpression i stepExpression.
	 * 
	 * @param variable Vrijednost koju poprima property variable.
	 * @param startExpression Vrijednost koju poprima property startExpression.
	 * @param endExpression Vrijednost koju poprima property endExpression.
	 * @param stepExpression Vrijednost koju poprima property stepExpression.
	 * @throws IllegalArgumentException ako variable, startExpression 
	 * 			ili endExpression null.
	 */
	public ForLoopNode(TokenVariable variable,
						Token startExpression,
						Token endExpression,
						Token stepExpression
	) throws IllegalArgumentException {
		if(variable == null || startExpression == null || endExpression == null) {
			throw new IllegalArgumentException();
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	
	/**
	 * Get metoda koja dohvaća vrijednost propertya
	 * variable.
	 * 
	 * @return Vrijednost propertya variable.
	 */
	public TokenVariable getVariable() {
		return this.variable;
	}

	
	/**
	 * Get metoda koja dohvaćavrijednost propertya
	 * startExpression.
	 * 
	 * @return Vrijednost propertya startExpression.
	 */
	public Token getStartExpression() {
		return this.startExpression;
	}

	
	/**
	 * Get metoda koja dohvaća vrijednost propertya
	 * endExpression.
	 * 
	 * @return Vrijednost propertya endExpression.
	 */
	public Token getEndExpression() {
		return this.endExpression;
	}

	
	/**
	 * Get metoda koja dohvaća vrijednost propertya
	 * stepExpression.
	 * 
	 * @return Vrijednost propertya stepExpression.
	 */
	public Token getStepExpression() {
		return this.stepExpression;
	}	
	
	/**
	 * Overridana asText metoda vraća string reprezentaciju
	 * onoga što čuva razred FoorLoopNode.
	 */
	@Override
	public String asText() {
		String ret = this.variable.asText() + " " + 
				this.startExpression.asText() + " " +
				this.endExpression.asText();
		
		if(this.stepExpression != null) {
			ret += " " + this.stepExpression.asText();
		}
		
		return ret;
	}
}
