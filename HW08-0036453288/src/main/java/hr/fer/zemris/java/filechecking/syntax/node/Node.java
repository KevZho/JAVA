package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji predstavlja osnovni čvor sitanksnog stabla.
 * @author Igor Smolkovič
 *
 */
public abstract class Node {

	/**
	 * Konstruktor.
	 */
	public Node() {
	}

	/**
	 * Prihvat posjetitelja.
	 * @param visitor posjetitelj
	 */
	public abstract void accept(FCNodeVisitor visitor);
}
