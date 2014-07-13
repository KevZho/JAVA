package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * Razred <code>CharsetsShellCommand</code> implementira komandu koja na izlaz
 * ispisuje kodne stranice koje su podržane na trenutnoj platformi.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class CharsetsSHellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments != null) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of charsets command"));
			return ShellStatus.CONTINUE;
		}
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (String name : map.keySet()) {
			ShellUtil.writeToShell(out, String.format("%s%n", name));
		}
		return ShellStatus.CONTINUE;
	}

}
