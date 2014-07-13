package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;
import hr.fer.zemris.java.filechecking.syntax.node.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.visitor.ProgramExecutorVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Razred koji izvršava program za provjeru ispravnosti datoteka.
 * Izvršavaju se svi predviđeni testovi te se generira lista 
 * obrazloženja zašto neki od testova nije prošao.
 *
 * @author Igor Smolkovič
 *
 */
public class FCFileVerifier {

	private List<String> errors = new ArrayList<>();
	
	/**
	 * Konstruktor.
	 * @param file Datoteka koja se ispituje.
	 * @param fileName Naziv datoteke kod korisnika.
	 * @param program Program koji se koristi za provjeru ispravnosti datoteke.
	 * @param initialData Početni podaci potrebni za provjeru.
	 */
	public FCFileVerifier(File file, String fileName, String program,
			Map<String, Object> initialData) {
		FCTokenizer tokenizer = new FCTokenizer(program);
		FCParser parser = new FCParser(tokenizer);
		ProgramNode programNode = parser.getProgramNode();

		ProgramExecutorVisitor exec = new ProgramExecutorVisitor(initialData, errors, file, fileName);
		programNode.accept(exec);
	}

	/**
	 * Metoda koja ispituje da li neki od testova nije prošao.
	 * @return true ako ima grešaka, inače false.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Metoda koja vraća listu upozorenja o testovima koji nisu prošli.
	 * @return lista upozorenja.
	 */
	public List<String> errors() {
		return new ArrayList<>(errors);
	}
}
