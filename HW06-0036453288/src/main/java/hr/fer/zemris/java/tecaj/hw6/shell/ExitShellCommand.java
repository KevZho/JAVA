package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Razred <code>ExitShellCommand</code> implementira komandu koja
 * omogućava izlaz iz ljuske.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		return ShellStatus.TERMINATE;
	}
}
