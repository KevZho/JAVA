package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Sučelje <code>ShellCommand</code> koje pruža metodu koju svaka
 * komanda mora implementira.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface ShellCommand {
	/**
	 * Metoda koja pokreće izvršavanje neke od komandi ljuske.
	 *
	 * @param in Bufferirani stream iz kojeg se čita.
	 * @param out Bufferirani stream na koji se piše.
	 * @param arguments Argumenti koje komanda koristi.
	 * @return ShellStatus.CONTINUE ako ljuska nakon obavljanja naredbe nastavlja
	 * 			s radom, inače vraća ShellStatus.TERMINATE.
	 */
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments);
}
