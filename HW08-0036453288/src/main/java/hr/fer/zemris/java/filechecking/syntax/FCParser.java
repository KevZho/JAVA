package hr.fer.zemris.java.filechecking.syntax;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.lexical.FCStringType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.node.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.node.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.node.FailNode;
import hr.fer.zemris.java.filechecking.syntax.node.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.node.Node;
import hr.fer.zemris.java.filechecking.syntax.node.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.node.TerminateStatement;
import hr.fer.zemris.java.filechecking.syntax.node.TestNode;
import hr.fer.zemris.java.filechecking.syntax.node.ZIPFormat;

/**
 * Implementacija parsera za jezik za provjeru datoteka rekurzivnim
 * spustom.
 *
 * @author Igor Smolkovič
 *
 */
public class FCParser {
	/**
	 * Tokenizer izvornog koda.
	 */
	private FCTokenizer tokenizer;
	/**
	 * Korijesni čvor stabla koje je rezultat parsiranja.
	 */
	private ProgramNode programNode;

	/**
	 * Stog koji parser koristi.
	 */
	Stack<Node> stack = new Stack<>();

	private static final Set<String> FORMATS;

	static {
		 FORMATS = new HashSet<>();
		 FORMATS.add("zip");
	}

	/**
	 * Konstruktor koji prima tokenizer.
	 *
	 * @param tokenizer tokenizer izvornog koda.
	 */
	public FCParser(FCTokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.programNode = parse();
	}

	/**
	 * Dohvaća korijen stabla nastalog parsiranjem.
	 * @return korijen sintaksnog stabla.
	 */
	public ProgramNode getProgramNode() {
		return programNode;
	}

	/**
	 * Metoda koja parsira izvorni kod koristeći rekurzivni spust.
	 * @return korijen dobivenog stabla.
	 */
	private ProgramNode parse() {
		ProgramNode programNode = new ProgramNode();
		stack.push(programNode);

		while (true) {
			// ako je stog prazan onda je došlo do greške.
			if (stack.empty()) {
				throw new FCSyntaxException("Empty stack");
			}

			// ako smo dosli do kraja programa
			if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.EOF) {
				break;
			}

			// ako je došla zatvorena zagrada izbaci vrh stoga
			if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.CLOSED_BRACKET) {
				stack.pop();
				tokenizer.nextToken();
				continue;
			}

			// terminate
			if ("terminate".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(new TerminateStatement());
				tokenizer.nextToken();
				continue;
			}

			// define
			if ("def".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(parseDef());
				tokenizer.nextToken();
				continue;
			}

			// invert dolazi samo prije testova, inače je greška
			boolean invert = false;
			if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.INVERT) {
				invert = true;
				tokenizer.nextToken();
			}

			// Inače prema sintaksi mora doći ključna riječ:
			if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.KEYWORD) {
				throw new FCSyntaxException("Očekivano: " + FCTokenType.KEYWORD + ", a dobiveno: "
												+ tokenizer.getCurrentToken().getTokenType());
			}

			if ("exists".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(parseExists(invert));
				continue;
			}

			if ("filename".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(parseFileName(invert));
				continue;
			}

			if ("format".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(parseFormat(invert));
				continue;
			}

			if ("fail".equals(tokenizer.getCurrentToken().getValue())) {
				((ProgramNode) (stack.peek())).addStatement(parseFail(invert));
				continue;
			}
			throw new FCSyntaxException("Neočekivana ključna riječ: " + tokenizer.getCurrentToken().getValue());
		}

		//ako je stog prazan vrati null;
		if (stack.empty()) { return null; }
		//inace vrati vrh stoga
		return (ProgramNode) stack.pop();
	}

	/**
	 * Metoda koja na stog stavlja novi čvor ako za parsirani test postoji blok naredbi
	 * koji se izvodi za uspješan test.
	 *
	 * @return ako naredbe postoje vraća referencu na stvoreni čvor koji pamti program,
	 * 			inače null.
	 */
	private ProgramNode getBlockProgram() {
		if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.OPEN_BRACKET) {
			tokenizer.nextToken();
			ProgramNode program = new ProgramNode();
			stack.push(program);
			return program;
		}
		return null;
	}

	/**
	 * Metoda koja dohvaća poruku pogreške za trenutni test.
	 * @return vraća poruku ako postoji, inače null.
	 */
	private String getTestErrorMsg() {
		if (tokenizer.getCurrentToken().getTokenType() == FCTokenType.STRING) {
			FCString msg = (FCString) tokenizer.getCurrentToken().getValue();
			if(msg.getStringType() != FCStringType.FAIL) {
				return null;
			}
			tokenizer.nextToken();
			return msg.getValue();
		}
		return null;
	}

	/**
	 * Metoda koja parsira naredbu exist.
	 * @param invert ako se rezultat testa invertira.
	 * @return referenca na stvorenu naredbu.
	 */
	private ExistsNode parseExists(boolean invert) {
		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.IDN) {
			throw new FCSyntaxException("Exist očekuje kao prvi argument tip IDN.");
		}

		String value = ((String) (tokenizer.getCurrentToken().getValue()));
		boolean file = false;
		if (value.matches("d|di|dir")) {
			file = false;
		} else if (value.matches("f|fi|fil|file")) {
			file = true;
		} else {
			throw new FCSyntaxException("Exist očekuje kao prvi argument tip objekta.");
		}

		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Exist očekuje kao drugi argument putanju.");
		}
		FCString path = (FCString) tokenizer.getCurrentToken().getValue();

		tokenizer.nextToken();
		String errorMsg = getTestErrorMsg();
		ProgramNode program = getBlockProgram();

		return new ExistsNode(errorMsg, program, invert, path, file);
	}

	/**
	 * Metoda koja parsira naredbu fail.
	 * @param invert ako se rezultat testa invertira.
	 * @return referenca na stvorenu naredbu.
	 */
	private FailNode parseFail(boolean invert) {
		tokenizer.nextToken();
		String errorMsg = getTestErrorMsg();
		ProgramNode program = getBlockProgram();

		return new FailNode(errorMsg, program, invert);
	}

	/**
	 * Metoda koja parsira naredbu format.
	 * @param invert ako se rezultat testa invertira.
	 * @return referenca na stvorenu naredbu.
	 */
	private TestNode parseFormat(boolean invert) {
		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.IDN) {
			throw new FCSyntaxException("Exist očekuje kao prvi argument tip IDN.");
		}
		String value = ((String) (tokenizer.getCurrentToken().getValue()));

		if (!FORMATS.contains(value)) {
			throw new FCSyntaxException("Nepodržana provjera formata" + value);
		}

		tokenizer.nextToken();
		String errorMsg = getTestErrorMsg();
		ProgramNode program = getBlockProgram();

		return formatNodeFactory(value, errorMsg, program, invert);
	}

	/**
	 * Metoda koja instancira čvorove za provjeru formata.
	 * @param type tip formata.
	 * @param errorMsg poruka o pogrešci.
	 * @param program blok naredbe ako je test uspješan.
	 * @param invert ako se rezultat testa invertia.
	 * @return referenca na stvorenu instancu.
	 */
	private TestNode formatNodeFactory(String type, String errorMsg, ProgramNode program, boolean invert) {
		TestNode node = null;
		if (type.equals("zip")) {
			node = new ZIPFormat(errorMsg, program, invert);
		}
		return node;
	}

	/**
	 * Metoda koja parsira naredbu filename.
	 * @param invert ako se rezultat naredbe invertira.
	 * @return referenca na stvorenu naredbu.
	 */
	private FileNameNode parseFileName(boolean invert) {
		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Exist očekuje kao drugi argument putanju.");
		}
		FCString path = (FCString) tokenizer.getCurrentToken().getValue();

		tokenizer.nextToken();
		String errorMsg = getTestErrorMsg();
		ProgramNode program = getBlockProgram();

		return new FileNameNode(errorMsg, program, invert, path);
	}

	/**
	 * Metoda koja parsira def naredbu.
	 * @return instanca razreda <code>DefStatement</code>.
	 * @throws FCSyntaxException ako je došlo do greške prilikom parsiranja.
	 */
	private DefStatement parseDef() {
		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.IDN) {
			throw new FCSyntaxException("Def očekuje kao prvi argument IDN.");
		}
		String variable = (String) tokenizer.getCurrentToken().getValue();

		tokenizer.nextToken();
		if (tokenizer.getCurrentToken().getTokenType() != FCTokenType.STRING) {
			throw new FCSyntaxException("Def očekuje kao drugi argument STRING putanju.");
		}

		FCString path = (FCString) tokenizer.getCurrentToken().getValue();
		return new DefStatement(variable, path);
	}
}
