package hr.fer.zemris.java.custom.scripting.demo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Implementacija obilaska stabla dokumenta pomoću obrasca visitor.
 * @author Igor Smolkovič
 *
 */
public class TreeWriter {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti komadne linije. args[0] putanja do datoteke iz
	 * 				koje se čita.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: TreeWriter <file>");
			return;
		}
		Path path = Paths.get(args[0]);
		if (!Files.exists(path)) {
			System.err.println("Datoteka ne postoji.");
			return;
		}
		String docBody = readFile(path);
		if (docBody == null) {
			System.err.println("Greška prilikom čitanja datoteke");
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriteVisitor visitor = new WriteVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Metoda koja čita sadržaj datoteke i vraća string reprezentaciju
	 * pročitanog.
	 * @param path putanja do datoteke koja se čita.
	 * @return string reprezentacija datoteke u utf8 formatu.
	 */
	private static String readFile(Path path) {
		byte[] data;
		try {
			data = Files.readAllBytes(path);
		} catch(Exception ex) {
			return null;
		}
		return new String(data, StandardCharsets.UTF_8);
	}

	/**
	 * Razred koji implementira visitor za obilazak stabla dokumenta.
	 * @author Igor Smolkovič
	 *
	 */
	private static class WriteVisitor implements INodeVisitor{

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR " + node.asText() + " $}");
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$ END $}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$= " + node.asText() + " $}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
