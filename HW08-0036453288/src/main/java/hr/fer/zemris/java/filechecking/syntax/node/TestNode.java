package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Vršni razred koji predstavlja bilo kakav test.
 * @author Igor Smolkovič
 *
 */
public abstract class TestNode extends Node {
	/**
	 * Poruka pogreške.
	 */
	private String errorMsg;
	/**
	 * Program koji se izvršava kao je test uspješan. 
	 */
	private ProgramNode program;
	/**
	 * Zastavica koja označava invertira li se rezultat testa.
	 */
	private boolean invert;

	/**
	 * Konstruktor.
	 * @param errorMsg poruka o grešci ako test nije uspješan. Argument je null ako ne postoji.
	 * @param program niz naredbi koje se izvršavaju ako je test bio uspješan. Argument je null
	 * 			ako naredbe ne postoje.
	 * @param invert zastavica koja označava invertira li se rezultat testa.
	 */
	public TestNode(String errorMsg, ProgramNode program, boolean invert) {
		super();
		this.errorMsg = errorMsg;
		this.program = program;
		this.invert = invert;
	}

	/**
	 * Metoda koja dohvaća poruku pogreške.
	 * @return poruka pogreške ako postoji, inače null.
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Metoda koja dohvaća niz naredbi koje se izvršavaju ako je test uspješan.
	 * @return niz naredbi.
	 */
	public ProgramNode getProgram() {
		return program;
	}

	/**
	 * Metoda koja ispituje invertira li se rezultat testa.
	 * @return zastavica invert.
	 */
	public boolean isInvert() {
		return invert;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void accept(FCNodeVisitor visitor);
}
