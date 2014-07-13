package hr.fer.zemris.java.filechecking.syntax.node;

import java.io.File;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Apstraktan razred koji omogućava provjeru da li je datoteka predviđenog formata.
 * Konkretni formati implementacije su razreda koji nasljeđuju ovaj razred.
 *
 * @author Igor Smolkovič
 *
 */
public abstract class FormatNode extends TestNode {

	/**
	 * Konstruktor.
	 * @param errorMsg poruka greške ako datoteka nije predviđenog formata. Argument je null ako ne postoji.
	 * @param program niz naredbi koje se izvršavaju ako je test bio uspješan. Arguemnt je null ako naredbe ne postoje.
	 * @param invert invertira li se rezultat testa.
	 */
	public FormatNode(String errorMsg, ProgramNode program, boolean invert) {
		super(errorMsg, program, invert);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void accept(FCNodeVisitor visitor);

	/**
	 * Metoda koja provjerava da li je dobivena datoteka predviđenog formata.
	 * @param f datoteka koja se provjerava.
	 * @return true datoteka je predviđenog formata, inače false.
	 */
	public abstract boolean acceptType(File f);

	/**
	 * Metoda koja vraća sadržaj koji pamti dobivena datoteka.
	 * @param f datoteka čiji se sadržaj vadi.
	 * @return lista sadržaj koji pamti datoteka.
	 */
	public abstract List<String> getFileContent(File f);
}
