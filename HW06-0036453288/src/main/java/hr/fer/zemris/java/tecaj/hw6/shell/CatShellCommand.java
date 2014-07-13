package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Razred <code>CatShellCommand</code> implementira komandu koja na izlaz(konzolu)
 * ispisuje sadržaj datoteke.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length < 1 || arguments.length > 2) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of cat command%n"));
			return ShellStatus.CONTINUE;
		}
		Path file = Paths.get(arguments[0].trim());
		Charset charset = arguments.length > 1 ? Charset.forName(arguments[1].trim()) : Charset.defaultCharset();
		
		if (charset == null) {
			ShellUtil.writeToShell(out, String.format("Unsupported charset: %s", arguments[1].trim()));
			return ShellStatus.CONTINUE;
		}
	
		printFileContent(file, charset, out);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja ispisuje sadržaj datoteke u konzolu.
	 *
	 * @param path Putanja do datoteke.
	 * @param charset Kodna stranica koja se koristi prilikom ispisa.
	 * @param out Izlaz na koji se datoteka ispisuje.
	 */
	private void printFileContent(Path path, Charset charset, BufferedWriter out) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(new FileInputStream(path.toFile())),
							charset)
			);
			String line = reader.readLine();
			while (line != null) {
				ShellUtil.writeToShell(out, String.format("%s%n", line));
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			ShellUtil.writeToShell(out, String.format("File not found %s%n", path));
		} catch (IOException e) {
			ShellUtil.writeToShell(out, String.format("File reading error.%n"));
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException ignorable) { }
			}
		}
	}
}
