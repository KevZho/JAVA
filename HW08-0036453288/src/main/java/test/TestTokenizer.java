package test;

import java.nio.file.Paths;

import hr.fer.zemris.java.filechecking.FileChecker;
import hr.fer.zemris.java.filechecking.lexical.FCToken;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;

/**
 * Razred koji sadrži implementaciju testera za tokenizator.
 * @author Igor Smolkovič
 *
 */
public class TestTokenizer {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		String program = FileChecker.readProgram(Paths.get("./provjere.txt"));
		FCTokenizer tokenizer = new FCTokenizer(program);

		while (true) {
			FCToken token = tokenizer.getCurrentToken();
			System.out.println("Trenutni token: " + token.getTokenType()
					+ ", vrijednost '" + token.getValue() + "'");
			if (token.getTokenType() == FCTokenType.EOF) {
				break;
			}
			tokenizer.nextToken();
		}
	}
}
