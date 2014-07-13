package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji implementira naredbu filename.
 * @author Igor Smolkovič
 *
 */
public class FileNameNode extends TestNode {
	/**
	 * Objekt koji pamti ime datoteke s kojim se uspoređuje.
	 */
	private FCString name;

	/**
	 * Konstruktor.
	 * @param errorMsg poruka o grešci ako test nije uspješan. Argument je null ako ne postoji.
	 * @param program niz naredbi koji se izvšava ako je test uspješan. Argument je null ako ne postoji.
	 * @param invert invertira li se rezultat izvršavanja naredbe.
	 * @param name ime s kojim se uspoređuje ime dobiveno od korisnika. 
	 */
	public FileNameNode(String errorMsg, ProgramNode program, boolean invert,
			FCString name) {
		super(errorMsg, program, invert);
		this.name = name;
	}

	/**
	 * Metoda koja dohvaća ime s kojim se uspoređuje ime datoteke kod korisnika.
	 * @return ime s kojim se uspoređuje ime datoteke kod korisnika.
	 */
	public FCString getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
