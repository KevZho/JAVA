package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji implementira def naredbu.
 * @author Igor Smolkovič
 *
 */
public class DefStatement extends Node {
	/**
	 * Naziv varijable.
	 */
	private String variable;
	/**
	 * Putanja koju čuva varijabla.
	 */
	private FCString path;

	/**
	 * Konstruktor.
	 * @param variable ime varijable.
	 * @param path putanja koju varijabla pamti.
	 */
	public DefStatement(String variable, FCString path) {
		super();
		this.variable = variable;
		this.path = path;
	}

	/**
	 * Metoda koja dohvaća ime varijable.
	 * @return ime varijable.
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * Metoda koja dohvaća putanju koju varijabla pamti.
	 * @return putanja koju varijabla pamti.
	 */
	public FCString getPath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
