package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

/**
 * Razred koji implementira naredbu exists.
 * @author Igor Smolkovič
 *
 */
public class ExistsNode extends TestNode {
	/**
	 * Putanja koja se ispituje.
	 */
	private FCString path; 
	/**
	 * Zastavica koja označava provjerava li se datoteka ili direktorij.
	 */
	private boolean checkFile; 

	/**
	 * Konstruktor.
	 * @param errorMsg poruka greške - ako je vrijednost null onda poruka ne postoji.
	 * @param program program koji se ukoliko je test prošao.
	 * @param invert promatra li se invertirani rezultat naredbe.
	 * @param path Putanja koja se provjerava.
	 * @param file zastavica koja označava provjerava li se datoteka ili direktorij.
	 */
	public ExistsNode(String errorMsg, ProgramNode program, boolean invert,
			FCString path, boolean file) {
		super(errorMsg, program, invert);
		this.path = path;
		this.checkFile = file;
	}

	/**
	 * Metoda koja dohvaća putanja koja se provjerava.
	 * @return putanja koja se provjerava naredbom exists.
	 */
	public FCString getPath() {
		return path;
	}

	/**
	 * Metoda koja ispituje provjerava li se direktorij ili datoteka.
	 * @return true - ako se provjerava datoteka, inače false.
	 */
	public boolean isCheckFile() {
		return checkFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}
}
