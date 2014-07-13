package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/**
 * Razred <code>MkdirShellCommand</code> implementira komandu koja 
 * omogućava stvaranje direktorija i prema potrebi potrebne strukture
 * direktorija. 
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of mkdir command"));
			return ShellStatus.CONTINUE;
		}
		File directory = new File(arguments[0].trim());
		if (!directory.mkdirs()) {
			ShellUtil.writeToShell(out, String.format("Failed to create directory %s%n", directory.toString()));
		}
		return ShellStatus.CONTINUE;
	}
}
