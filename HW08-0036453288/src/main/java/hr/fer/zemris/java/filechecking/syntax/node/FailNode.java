package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji implementira naredbu fail. Ovaj test je po definiciji neuspješan
 * te se ispisuje poruka o grešci ako postoji. Također ako je definiran niz 
 * u vitičastim zagradama one se izvršavaju.
 * @author Igor Smolkovič
 *
 */
public class FailNode extends TestNode {

	/**
	 * Konstruktor.
	 * @param errorMsg poruka o grešći koja se ispisuje nakon što se naredba izvrši.
	 * @param program niz naredbi koje se izvršavaju nakon što se test pokrene. Argument je null
	 * 			ako nema definirah naredbi.
	 * @param invert zastavica koja označava da li je test invertiran. 
	 */
	public FailNode(String errorMsg, ProgramNode program, boolean invert) {
		super(errorMsg, program, invert);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
