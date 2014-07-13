package hr.fer.zemris.java.filechecking.syntax.visitor;

import java.io.File;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.lexical.FCStringType;
import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.node.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.node.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.node.FailNode;
import hr.fer.zemris.java.filechecking.syntax.node.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.node.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.node.Node;
import hr.fer.zemris.java.filechecking.syntax.node.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.node.TerminateStatement;

/**
 * Stroj za izvođenje naredbi programskog jezika za provjeru ispravnosti datoteka.
 * @author Igor Smolkovič
 *
 */
public class ProgramExecutorVisitor implements FCNodeVisitor {
	/**
	 * Mapa koja pamti trenutne varijable.
	 */
	private Map<String, Object> variables;
	/**
	 * Lista grešaka.
	 */
	private List<String> errors;
	/**
	 * Objekt koji pamti datoteku koja se ispituje.
	 */
	private File file;
	/**
	 * Korisnikovo ime datoteke.
	 */
	private String fileName;
	/**
	 * Zastavica koja označava da je kraj programa.
	 */
	private boolean programEnd = false;
	/**
	 * Lista koja sadrži sve direktorije i datoteke koje dobivena datoteka sadrži.
	 */
	private List<String> fileContent;

	/**
	 * Konstruktor.
	 * @param initialData početni podaci.
	 * @param errors lista u koju se spremaju greške.
	 * @param file Objekt koji pamti datoteku dobivenu od korisnika.
	 * @param fileName Ime datoteke kod korisnika.
	 */
	public ProgramExecutorVisitor(Map<String, Object> initialData,
			List<String> errors, File file, String fileName) {
		this.variables = initialData;
		this.errors = errors;
		this.file = file;
		this.fileName = fileName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(DefStatement node) {
		String variable = node.getVariable();
		String value = node.getPath().getValue();

		String replaced = replaceString(value);
		variables.put(variable, replaced);
	}

	/**
	 * Pomoćna metoda koja radi izmjene putanje ako se u putanji nalaze varijable.
	 * @param input String koji se mijenja.
	 * @return Izmijenjeni string.
	 */
	private String replaceString(String input) {
		while (true) {
			if (input.matches(".*\\$\\{.+\\}.*")) {
				int startIndex = input.indexOf('$');
				int endIndex = input.indexOf('}');
				String key = input.substring(startIndex + 2, endIndex);
				StringBuilder builder = new StringBuilder(input.substring(0, startIndex));
				Object o = variables.get(key.trim());
				if (o == null) {
					errors.add("Nepostojaća varijabla: " + key);
					return input;
				}
				builder.append((String) o);
				builder.append(input.substring(endIndex + 1));
				input = builder.toString();
				continue;
			}
			if (input.indexOf(':') != -1) {
				int index = input.indexOf(':');
				String roi = input.substring(index + 1);
				roi = roi.replaceAll("\\.", "/");
				StringBuilder builder = new StringBuilder(input.substring(0, index));
				builder.append("/").append(roi);
				input = builder.toString();
				continue;
			}
			break;
		}
		return input;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(TerminateStatement node) {
		programEnd = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(ProgramNode node) {
		for (Node n : node.getStatements()) {
			if (programEnd) {
				return;
			}
			n.accept(this);
		}
 	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(ExistsNode node) {
		FCString pathOrg = node.getPath();
		String path = replaceString(pathOrg.getValue());
		if (node.isCheckFile() == false) {
			if (!path.endsWith("/")) {
				path = path + "/";
			}
		}
		if(fileContent == null) {
			errors.add("exist naredba mora biti definirana unutar format bloka.");
			return;
		}
		boolean accept = fileContent.contains(path);
		if (node.isInvert()) {
			accept = !accept;
		}
		if (accept) {
			if (node.getProgram() != null) {
				visit(node.getProgram());
			}
		} else if (node.getErrorMsg() != null) {
			errors.add(node.getErrorMsg());
		} else {
			errors.add("Tražena datoteka/direktorij " + path + " nije pronađena.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FormatNode node) {
		boolean accept = node.acceptType(file);
		if (node.isInvert()) {
			accept = !accept;
		}
		if (accept) {
			fileContent = node.getFileContent(file);
			if (fileContent == null) {
				errors.add("Došlo je do greške prilikom čitanja sadržaja dobivene datoteke.");
				programEnd = true;
				return;
			}
			if (node.getProgram() != null) {
				visit(node.getProgram());
			}
		} else if (node.getErrorMsg() != null) {
			errors.add(node.getErrorMsg());
		} else {
			errors.add("Predana datoteka nije predviđenog formata.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FailNode node) {
		if (node.getErrorMsg() != null) {
			errors.add(node.getErrorMsg());
		} else {
			errors.add("Izvršavanje programa nije uspjelo");
		}
		if (node.getProgram() != null) {
			visit(node.getProgram());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FileNameNode node) {
		FCString name = node.getName();
		String value = replaceString(name.getValue());
		boolean accept = false;
		if (name.getStringType() == FCStringType.IGNORE) {
			accept = value.compareToIgnoreCase(fileName) == 0;
		} else {
			accept = value.compareTo(fileName) == 0;
		}

		if (node.isInvert()) {
			accept = !accept;
		}
		if (accept) {
			if (node.getProgram() != null) {
				visit(node.getProgram());
			}
		} else if (node.getErrorMsg() != null) {
			errors.add(node.getErrorMsg());
		} else {
			errors.add("Predano ime datoteke nije u skladu s predviđenim.");
		}
	}
}
