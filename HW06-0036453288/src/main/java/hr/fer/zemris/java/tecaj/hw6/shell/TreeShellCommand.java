package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Razred <code>TreeShellCommand</code> implementira komandu koja na izlaz
 * ispisuje stablo direktorija koji se nalaze unutar unutar traženog 
 * direktorija.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Privatni razred <code>Printer</code> implementira sučelje <code>FileVisitor</code>
	 * te omogućava rekurzivni obilazak svih direktorija koji se nalaze unutar traženog 
	 * direktorija.
	 *
	 * @author Igor Smolkovič
	 * @version 1.0
	 *
	 */
	private static class Printer implements FileVisitor<Path> {

		private int indent = 0;
		private BufferedWriter out;
		
		/**
		 * Konstrutor razreda <code>Printer</code> prima izlaz na koji se
		 * zapisuje.
		 *
		 * @param out Izlaz na koji se zapisuje.
		 */
		public Printer(BufferedWriter out) {
			super();
			this.out = out;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indent == 0) {
				ShellUtil.writeToShell(out, String.format("%s%n", dir.toString()));
			} else {
				writeToShell(dir.getFileName().toString());
			}
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * Metoda koja zapisuje na izlaz naziv direktorija te potrebne uvlake.
		 *
		 * @param name Ime direktorija koje se zapisuje na izlaz.
		 */
		private void writeToShell(String name) {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("%0" + (indent) + "d", 0).replace("0"," "))
					.append(name).append("\r\n");
			ShellUtil.writeToShell(out, builder.toString());
		}
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of tree command%n"));
			return ShellStatus.CONTINUE;
		}
		Path dir = Paths.get(arguments[0].trim());
		if (!Files.isDirectory(dir)) {
			ShellUtil.writeToShell(out, String.format("%s is not directory.%n", dir.toString()));
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(dir, new Printer(out));
		} catch (IOException e) {
		}
		return ShellStatus.CONTINUE;
	}
}
