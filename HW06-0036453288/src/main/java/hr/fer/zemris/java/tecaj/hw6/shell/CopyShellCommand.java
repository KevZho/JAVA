package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Razred <code>CopyShellCommand</code> implementira komandu koja kopira datoteku na 
 * određišnju lokaciju. Ako je odredišnja lokacija direktorij, u tom direktoriju se stvara
 * datoteka imena koje je preuzeto iz originalnog. Ako određišnja datoteka već postoji
 * korisnika se pita želi li je prebrisati.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Veličina buffera.
	 */
	private static int BUFFER_SIZE = 1024;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length != 2) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of copy command"));
			return ShellStatus.CONTINUE;
		}
		Path originalPath = Paths.get(arguments[0]);
		if (!originalPath.toFile().isFile()) {
			ShellUtil.writeToShell(out, String.format("Original is not a file.%n"));
			return ShellStatus.CONTINUE;
		}
		if (!Files.isReadable(originalPath)) {
			ShellUtil.writeToShell(out, String.format("Can't read from provided file.%n"));
			return ShellStatus.CONTINUE;
		}
		Path destPath = Paths.get(arguments[1]);
		if (Files.isDirectory(destPath)) {
			destPath = destPath.resolve(originalPath.getFileName());
		}
		if (Files.isRegularFile(destPath)) {
			ShellUtil.writeToShell(out, String.format("File exists. Overwrite? Type yes/no: "));
			String userInput = ShellUtil.readFromShell(in);
			if(userInput.trim().compareTo("yes") != 0) {
				return ShellStatus.CONTINUE;
			} 
		}
		boolean status = copyFile(originalPath, destPath);
		if (!status) {
			ShellUtil.writeToShell(out, String.format("File copy failed.%n"));
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja provodi kopiranje datoteka.
	 *
	 * @param originalPath Datoteka koja se kopira.
	 * @param destPath Putanja do određisnje datoteke.
	 * @return true ako je kopiranje uspješno, inače false.
	 */
	private boolean copyFile(Path originalPath, Path destPath) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(originalPath.toString());
			output = new FileOutputStream(destPath.toString());
			byte[] buffer = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = input.read(buffer)) != -1) {
				output.write(buffer, 0, read);
			}
		} catch (FileNotFoundException e1) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			if (input != null) {
				try { input.close(); } catch (IOException ignorable) {}
			}
			if (output != null) {
				try { output.close(); } catch (IOException ignorable) {}
			}
		}
		return true;
	}
}
