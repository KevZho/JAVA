package hr.fer.zemris.java.filechecking;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.lexical.FCLexicalException;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;
import hr.fer.zemris.java.filechecking.syntax.FCSyntaxException;

/**
 * Razred koji radi provjeru izvornog koda programa.
 * @author Igor Smolkovič
 *
 */
public class FCProgramChecker {
	/**
	 * Lista grešaka.
	 */
	private List<String> errors = new ArrayList<>();
	
	/**
	 * Konstruktor.
	 * @param program izvorni kod programa.
	 */
	public FCProgramChecker(String program) {
		try {
			FCTokenizer tokenizer = new FCTokenizer(program);
			new FCParser(tokenizer);
		} catch (FCLexicalException le) {
			errors.add(le.getMessage());
		} catch (FCSyntaxException se) {
			errors.add(se.getMessage());
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	/**
	 * Metoda koja ispituje postoje li greške u izvornom kodu programa.
	 * @return true ako postoje greške, inače false.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Metoda koja vraća listu grešaka u izvornom kodu programu.
	 * @return list grešaka.
	 */
	public List<String> errors() {
		return new ArrayList<>(errors);
	}
}
