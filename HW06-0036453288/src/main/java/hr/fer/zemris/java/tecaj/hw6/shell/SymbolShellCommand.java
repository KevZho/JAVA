package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Razred <code>SymbolShellCommand</code> omogućava dohvaćanje ili promjenu
 * simbola za PROMPT, MORELINES i MULTILINE.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SymbolShellCommand implements ShellCommand {
	private String prompt;
	private String moreLines;
	private String multiLine;
	
	/**
	 * Konstruktor razreda <code>SymbolShellPrompt</code> prima početne simbole
	 * za PROMPT, MORELINES i MULTILINE.
	 *
	 * @param prompt Početni simbol za PROMPT.
	 * @param moreLines Početni simbol za MORELINES.
	 * @param multiLine Početni simbol za MULTILINE.
	 */
	public SymbolShellCommand(String prompt, String moreLines, String multiLine) {
		super();
		this.prompt = prompt;
		this.moreLines = moreLines;
		this.multiLine = multiLine;
	}

	/**
	 * Get metoda za dohvaćanje trenutnog PROMPT simbola.
	 *
	 * @return Trenutni simbol za PROMPT.
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * Get metoda za dohvaća trenutnog MORELINES simbola.
	 *
	 * @return Trenutni simbol za MORELINES.
	 */
	public String getMoreLines() {
		return moreLines;
	}

	/**
	 * Get metoda za dohvaćanje trenutnog MULTILINE simbola.
	 *
	 * @return Trenutni MULTILINE simbol.
	 */
	public String getMultiLine() {
		return multiLine;
	}

	/**
	 * Metoda za promjenu PROMPT simbola.
	 *
	 * @param prompt Novi PROMPT simbol.
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * Metoda za promjenu MORELINES simbola.
	 *
	 * @param moreLines Novi MORELINES simbol.
	 */
	public void setMoreLines(String moreLines) {
		this.moreLines = moreLines;
	}

	/**
	 * Metoda za promjenu MULTILINE simbola.
	 *
	 * @param multiLine Novi MULTILINE simbol.
	 */
	public void setMultiLine(String multiLine) {
		this.multiLine = multiLine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) {
		if (arguments == null) {
			ShellUtil.writeToShell(out, String.format("Illegal usage of symbol command.%n"));
			return ShellStatus.CONTINUE;
		}
		String output = null;
		switch(arguments[0].trim()) {
			case "PROMPT" : {
				output = "Symbol for PROMPT";
				if (arguments.length > 1) {
					output += String.format(" changed from \'%s\' to \'%s\'%n", getPrompt(), arguments[1].trim());
					setPrompt(arguments[1].trim());
				} else {
					output += String.format(" is \'%s\'%n", getPrompt());
				}
				break;
			}
			case "MORELINES" : {
				output = "Symbol for MORELINES";
				if (arguments.length > 1) {
					output += String.format(" changed from \'%s\' to \'%s\'%n", getMoreLines(), arguments[1].trim());
					setMoreLines(arguments[1].trim());
				} else {
					output += String.format(" is \'%s\'%n", getMoreLines());
				}
				break;
			}
			case "MULTILINE" : {
				output = "Symbol for MULTILINE";
				if (arguments.length > 1) {
					output += String.format(" changed from \'%s\' to \'%s\'%n", getMoreLines(), arguments[1].trim());
					setMultiLine(arguments[1].trim());
				} else {
					output += String.format(" is \'%s\'%n", getMultiLine());
				}
				break;
			}
			default : {
				output = String.format("Illegal usage of symbol command.%n");
			}
		}
		ShellUtil.writeToShell(out, output);
		return ShellStatus.CONTINUE;
	}
}
