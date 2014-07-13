package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Razred <code>MyShell</code> korisnicima pruža neke od usluga 
 * ljuske. Korisnicima je na raspolaganju ispisivanje sadržaja direktorija,
 * datoteka, kopiranje datoteka, stvaranje direktorija, ispisivanje podržanih
 * kodnih stranica te stablaste strukture direktorija.
 *
 * Program ne koristi argumente komandne linije.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MyShell {
	private static Map<String, ShellCommand> commands;
	private static String DEFAULT_PROMPT = "<";
	private static String DEFAULT_MULTILINE = "|";
	private static String DEFAULT_MORELINES = "\\";
	private static BufferedReader in;
	private static BufferedWriter out;
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komanandne linije.
	 */
	public static void main(String[] args) {
		commands = new HashMap<String, ShellCommand>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("charsets", new CharsetsSHellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand(DEFAULT_PROMPT, DEFAULT_MORELINES, DEFAULT_MULTILINE));
		
		System.out.println("Welcome to MyShell v 1.0");
		in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
		ShellStatus  status = null;
		ShellCommand command = null;
		/**
		 * Dohvaća se korisnikov unos, ako je unesena nepodržana naredba ili je
		 * naredba krivo korištenja korisnik će biti obavješten o tome.
		 */
		do {
			String l = "";
			String commandName = null;
			String[] arguments = null;
				try {
					l = readLineOrLines();
				} catch (IOException e) {
					System.err.println("ReadLineOrLines error.");
					continue;
				}
				try {
					commandName = extractCommand(l);
					arguments = extractArguments(l);
					command = commands.get(commandName);
					status = command.executeCommand(in, out, arguments);
				} catch (Exception e) {
					System.err.println("Unsupported command: " + l);
					continue;
				}
		} while (status!=ShellStatus.TERMINATE);
	}

	/**
	 * Metoda koja dohvaća argumente iz jedne ili više linija korisnikovog unosa. 
	 *
	 * @param l Linija iz koje se uzimaju argumenti.
	 * @return Polje stringova koji predstavljaju argumente.
	 */
	private static String[] extractArguments(String l) {
		if (l.matches("(\\w+)\\s+(.*)")) {
			Pattern pattern = Pattern.compile("(\\w+)\\s+(.*)");
			Matcher matcher = pattern.matcher(l);
			matcher.matches();
			return matcher.group(2).split("\\s+");
		}
		return null;
	}

	/**
	 * Metoda koja dohvaća komandu iz jedne ili više linija korisnikovog unosa.
	 *
	 * @param l Linija iz koje se uzima komanda.
	 * @return Komanda preuzeta iz korisnikovog unosa.
	 */
	private static String extractCommand(String l) {
		if (l.matches("(\\w+).*")) {
			Pattern pattern = Pattern.compile("(\\w+).*");
			Matcher matcher = pattern.matcher(l);
			matcher.matches();
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * Metoda koja dohvaća jednu ili više linija unosa od korisnika.
	 *
	 * @return String vrijednost koja sadrži korisnikov unos.
	 * @throws IOException ako je došlo do greške prilikom čitanja s tipkovnice.
	 */
	private static String readLineOrLines() throws IOException {
		SymbolShellCommand symbol = (SymbolShellCommand) commands.get("symbol");
		StringBuilder builder = new StringBuilder();
		ShellUtil.writeToShell(out, symbol.getPrompt() + " ");
		do {
			String line = ShellUtil.readFromShell(in);
			if (line.contains(symbol.getMoreLines())) {
				builder.append(line.substring(0, line.indexOf(symbol.getMoreLines())));
				ShellUtil.writeToShell(out, symbol.getMultiLine() + " ");
			} else {
				builder.append(line);
				break;
			}
		} while (true);
		return builder.toString();
	}
}
