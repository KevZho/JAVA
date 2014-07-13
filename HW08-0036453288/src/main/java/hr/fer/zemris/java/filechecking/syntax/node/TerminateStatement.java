package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji implementira naredbu terminate.
 * @author Igor Smolkovič
 *
 */
public class TerminateStatement extends Node {

	/**
	 * Konstruktor.
	 */
	public TerminateStatement() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
