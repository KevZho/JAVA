package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja čvor koji sadrži program(niz naredbi koje se izvršavaju sljedno).
 * @author Igor Smolkovič
 *
 */
public class ProgramNode extends Node {
	/**
	 * Lista naredbi.
	 */
	private List<Node> statements;

	/**
	 * Konstruktor.
	 */
	public ProgramNode() {
		statements = new ArrayList<>();
	}

	/**
	 * Metoda koja dodaje naredbu u listu naredbi. 
	 * @param node referenca na novu naredbu.
	 * @throws IllegalArgumentException ako je predana null referenca.
	 */
	public void addStatement(Node node) {
		if(node == null) {
			throw new IllegalArgumentException("Null reference.");
		}
		statements.add(node);
	}

	/**
	 * Metoda koja dohvaća listu naredbi.
	 * @return lista naredbi.
	 */
	public List<Node> getStatements() {
		return new ArrayList<>(statements);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
