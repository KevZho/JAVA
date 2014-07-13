package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Razred <code>LsShellCommand</code> implementira komandu koja ispisuje sadržaj
 * trenutnog direktorija sa informacijama o datotekama: veličina, vrijeme stvaranja,
 * ime datoteke i dozvole pristupa (read, write, execute) te tip datoteke
 * datoteka/direktorij.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of ls command%n"));
			return ShellStatus.CONTINUE;
		}
		Path dir = Paths.get(arguments[0].trim());
		if (!Files.isDirectory(dir)) {
			ShellUtil.writeToShell(out, String.format("%s is not directory.%n", dir.toString()));
			return ShellStatus.CONTINUE;
		}
		printDirectoryInformation(dir, out);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja ispisuje sadržaj dobivenog direktorija.
	 *
	 * @param dir Direktorij čiji sadržaj se ispisuje.
	 * @param out Izlaz na koji se ispisuje sadržaj direktorija.
	 */
	private void printDirectoryInformation(Path dir, BufferedWriter out) {
		File[] children = dir.toFile().listFiles();
		if (children!=null) {
			List<File> files = Arrays.asList(children);
			Collections.sort(files, new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});
			
			for (File f : files) {
				String modeBits = getFileModeBits(f);
				String size = getFileSize(f);
				String creationDateTime = getCreationDateTime(f);
				String name = f.getName();
				ShellUtil.writeToShell(out, 
						String.format("%s %s %s %s%n", modeBits, size, creationDateTime, name));
			}
		}
	}

	/**
	 * Metoda koja dohvaća razine pristupa za dobivenu datoteku
	 * te tip datoteke.
	 *
	 * @param f Datoteka za koju se određuju razine pristupa i tip.
	 * @return String reprezentacija prava pristupa i tipa datoteke.
	 */
	private String getFileModeBits(File f) {
		StringBuilder builder = new StringBuilder();
		builder.append(f.isDirectory() 	? "d" : "-");
		builder.append(f.canRead() 		? "r" : "-");
		builder.append(f.canWrite() 	? "w" : "-");
		builder.append(f.canExecute() 	? "x" : "-");
		return builder.toString();
	}

	/**
	 * Metoda koja vraća veličinu datoteke desno formatirano uz maksimalno
	 * 10 znakova.
	 *
	 * @param f Datoteka za koju se određuje veličina.
	 * @return Veličina datoteke desno formatirana.
	 */
	private String getFileSize(File f) {
		long size = f.length();
		return String.format("%10d", size);
	}

	/**
	 * Metoda koja dohvaća datum i vrijeme stvaranja datoteke.
	 *
	 * @param f Datoteka za koju se određuje datum i vrijeme stvaranja.
	 * @return Datum i vrijeme stvaranja dobivene datoteke.
	 */
	private String getCreationDateTime(File f) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = f.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(
		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			return null;
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}
}
