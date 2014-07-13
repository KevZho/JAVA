package hr.fer.zemris.java.tecaj.hw4.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Glavni razred koji omogućava učitavanje podataka u bazu studenata
 * te upite koji se mogu provoditi nad dobivenom bazom.
 * 
 * <p>
 * Podržani su upiti prema JMBAG-u i prezimenu. Primjer uporabe slijedi
 * u nastavku.
 * </p>
 * 
 * <pre>
 *   > query jmbag="0000000003"
 *   > query lastName="B*"
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class StudentDB {
		
	private static StudentDatabase DB;
	private static String FORM1 = "^query\\s+jmbag\\s*=\\s*\\\"(\\d+)\\\"";
	private static String FORM2 = "^query\\s+lastName\\s*=\\s*\\\"([^\\\"]*\\*?[^\\\"]*)\\\"";
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args Argumenti komandne linije.
	 * @throws IOException ako je došlo do greške tijekom unosa korisnika.
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
					Paths.get("./database.txt"),
					StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		DB = new StudentDatabase(lines);
		queryMode();
	}

	
	/**
	 * Metoda koja korisniku omogućava pisanje upita. 
	 * 
	 * @throws IOException ako je došlo do greške prilikom unosa
	 * 			podatak s tipkovnice.
	 */
	private static void queryMode() throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in),
						StandardCharsets.UTF_8)
		);
		
		System.out.println("Query mode. Type \"quit\" for exit.");
		
		while(true) {
			System.out.print("> ");
			String line = reader.readLine();
			List<StudentRecord> selected = null;
			
			if(line == null) {
				System.exit(-1);
			}
			else if(line.compareToIgnoreCase("quit") == 0) {
				System.out.println("Exit.");
				System.exit(1);
			}
			else if(line.matches(FORM1)) {
				selected = jmbagFilter(line);
			}
			else if(line.matches(FORM2)) {
				selected = lastNameFilter(line);
			}
			else {
				System.out.println("Illegal input: " + line);
				continue;
			}
	
			printSelected(selected);
			System.out.print("Records selected: ");
			System.out.println(selected == null ? 0 : selected.size());
		}
	}
	
	
	/**
	 * Metoda koja obrađuje upite prema JMABG-u.
	 * 
	 * @param input uneseni upit.
	 * @return Lista zapisa o studentima koji odgovaraju upitu.
	 */
	private static ArrayList<StudentRecord> jmbagFilter(String input) {
		StudentRecord record = null;
		
		try {
			Matcher matcher = Pattern.compile(FORM1).matcher(input);		
			matcher.find();
			record = DB.forJMBAG(matcher.group(1));
		}
		catch(Exception e) {
		}
		
		return record == null ? null : 
			new ArrayList<StudentRecord>(Arrays.asList(record));	
	}
	
	
	/**
	 * Metoda koja obrađuje upite prema prezimenu.
	 * 
	 * @param input Upit koji se obrađuje.
	 * @return Lista zapisa o studentima koji odgovaraju upitu.
	 */
	private static List<StudentRecord> lastNameFilter(String input) {
		List<StudentRecord> list = null;
		
		try {
			Matcher matcher = Pattern.compile(FORM2).matcher(input);		
			matcher.find();
			IFilter filter = new LastNameFilter(matcher.group(1));
			list  = DB.filter(filter);
		}
		catch(Exception e) {
		}
		
		return list;
	}
	
	
	/**
	 * Metoda koja na standradni izlaz ispisuje rezultate upita.
	 * 
	 * @param selected Lista studenata nastala na temelju upita.
	 */
	private static void printSelected(List<StudentRecord> selected) {
		if(selected == null || selected.isEmpty()) {
			return;
		}
		
		int jmbagSize = 0;
		int firstNameSize = 0;
		int lastNameSize = 0;
		
		for(StudentRecord record : selected) {
			jmbagSize = Math.max(jmbagSize, record.getJmbag().length());
			firstNameSize = Math.max(firstNameSize, record.getFirstName().length());
			lastNameSize = Math.max(lastNameSize, record.getLastName().length());
		}
		
		// ne koristi se LinkedHashSet jer nigdje ne pise da u database.txt
		// jmbagovi moraju ici po redu pa pisem tu komparator za jmbagove
		// jer se koristi samo tu
		Collections.sort(selected,  
			new Comparator<StudentRecord>() {
				@Override
				public int compare(StudentRecord o1, StudentRecord o2) {
					return o1.getJmbag().compareTo(o2.getJmbag());
				}
			}
		);
		
		String header = createHeader(jmbagSize, lastNameSize, firstNameSize);
		System.out.println(header);
		
		for(StudentRecord record : selected) {
			String output = createOutputLine(
					jmbagSize, 
					lastNameSize, 
					firstNameSize, 
					record);
			System.out.println(output);
		}
		
		System.out.println(header);
	}
	
	
	/**
	 * Metoda koja radi zaglavlje ispisa.
	 * +====+...
	 * 
	 * @param f1 Najveca duljina jmbaga.
	 * @param f2 Najveca duljina prezimena.
	 * @param f3 Najveca duljina imena.
	 * @return Zaglavlje.
	 */
	private static String createHeader(int f1, int f2, int f3) {
		StringBuilder builder = new StringBuilder();
		builder.append("+")
			.append(repeatCharString(f1 + 2, '='))
			.append("+")
			.append(repeatCharString(f2 + 2, '='))
			.append("+")
			.append(repeatCharString(f3 + 2, '='))
			.append("+")
			.append(repeatCharString(1 + 2, '='))
			.append("+");
		
		return builder.toString();
	}
	
	
	/**
	 * Metoda koja generira jedan redak ispisa podataka o studentu.
	 * 
	 * @param f1 Najveca duljina jmbg-a.
	 * @param f2 Najveca duljina prezimena.
	 * @param f3 Najveca duljina imena.
	 * @param record Zapis o studentu.
	 * @return Redak informacija o studentu koji se ispisuje na ekran.
	 */
	private static String createOutputLine(int f1, int f2, int f3, StudentRecord record) {
		StringBuilder builder = new StringBuilder();
		builder.append("| ")
			.append(record.getJmbag())
			.append(repeatCharString(f1 - record.getJmbag().length(), ' '))
			.append(" | ")
			.append(record.getLastName())
			.append(repeatCharString(f2 - record.getLastName().length(), ' '))
			.append(" | ")
			.append(record.getFirstName())
			.append(repeatCharString(f3 - record.getFirstName().length(), ' '))
			.append(" | ")
			.append(record.getFinalGrade())
			.append(" |");
			
		return builder.toString();
	}
		
	
	/**
	 * Metoda koja generira string koji sadrži n znakova c.
	 * 
	 * @param len Željena duljina stringa.
	 * @param c Znak koji sadrži string.
	 * @return String koji sadrži n istih znakova c.
	 */
	private static String repeatCharString(int len, char c) {
		char[] chars = new char[len];
		Arrays.fill(chars, c);
		return new String(chars);
	}
}
