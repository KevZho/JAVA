package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Razred <code>HexdumpShellCommand</code> omogućava korištenje komandne koja 
 * ispisuje hex zapis datoteke uz paralelni ascii izlaz.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	private static int BUFFER_SIZE = 16;
	private static int LOWER_BOUND = 32;
	private static int UPPER_BOUND = 127;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of hexdump command.%n"));
			return ShellStatus.CONTINUE;
		}
		Path file = Paths.get(arguments[0]);
		if (Files.isDirectory(file)) {
			ShellUtil.writeToShell(out, String.format("Directory was provided.%n"));
			return ShellStatus.CONTINUE;
		}
		if (!Files.isReadable(file)) {
			ShellUtil.writeToShell(out, String.format("Can't read from file.%n"));
			return ShellStatus.CONTINUE;
		}
		printHexOutput(file, out);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja čita datoteke zapisuje bajtove u hex zapisuje i paralelno u 
	 * ascii.
	 *
	 * @param file Putanja do datoteke iz koje se čita.
	 * @param out Izlaz na koji se zapisuje.
	 */
	private void printHexOutput(Path file, BufferedWriter out) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file.toFile());
			int read = 0;
			int counter = 0;
			while (true) {
				byte[] buffer = new byte[BUFFER_SIZE];
				read = input.read(buffer);
				if(read == -1) {
					break;
				}
				String leftHalf = convertToHexString(buffer, 0, read < 8 ? read: 8);
				String rightHalf = "";
				if (read > 8)  {
					rightHalf = convertToHexString(buffer, 8, read - 8);
				}
				String text = converToString(buffer, read);
				String output = String.format("%08x: %-23s|%-24s| %s%n", counter, leftHalf, rightHalf, text);
				ShellUtil.writeToShell(out, output);
				counter += read;
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (input != null) {
				try { input.close(); } catch (IOException ignorable) { }
			}
		}
	}

	/**
	 * Metoda koja pretvara polje bajtova u ascii znakove.
	 *
	 * @param buffer Polje bajtova.
	 * @param read Broj pročitanih znakova.
	 * @return String reprezentacija polja bajtova.
	 */
	private String converToString(byte[] buffer, int read) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < read; i++) {
			int value = Byte.valueOf(buffer[i]).intValue();
			if (value < LOWER_BOUND || value > UPPER_BOUND) {
				builder.append(".");
			} else {
				builder.append(Character.toChars(value));
			}
		}
		return builder.toString();
	}
	
	/**
	 * Metoda koja pretvara polje bajtova u hex string uz početni pomak
	 * i definiran broj bajtova koji se gledaju.
	 *
	 * @param buffer Polje bajtova koje se čita.
	 * @param offset Početni pomak u polju.
	 * @param length Duljina bajtova koji se pretvaraju u hex string.
	 * @return Hex string nastao iz polja bajtova.
	 */
	private String convertToHexString(byte[] buffer, int offset, int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(String.format("%02X", buffer[i + offset]));
			if (i < length-1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}
}
