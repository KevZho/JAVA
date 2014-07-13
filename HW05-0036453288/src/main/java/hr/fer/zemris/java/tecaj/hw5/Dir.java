package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.filter.CompositeFilter;
import hr.fer.zemris.java.tecaj.hw5.print.PrintName;
import hr.fer.zemris.java.tecaj.hw5.sort.CompositeSorter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Razred <code>Dir</code> omogućava ispisivanje sadržaj direktorija
 * uz moguća sortiranja, filtriranja te ispisivanja samo informacija koje
 * su tražene. Primjer uporaba
 * 
 * <pre>
 * 	 Dir . -w:n -s:n -f:f
 * 
 * 	 ispisuje imena datoteka u trenutnom direktoriju, sortirana 
 *   leksikografski po imenu.
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Dir {
	private static IFilter FILTER;
	private static Comparator<File> COMPARATOR;
	private static Printer OUT_PRINTER;
		
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args Argumenti komadne linije.
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Usage: <dir> <sort, filter, print options>");
			System.exit(-1);
		}
		
		try {
			File dir = new File(args[ 0 ]);
			if(dir.isDirectory() == false) {
				System.err.println("Usage: <dir> <sort, filter, print options>");
				System.exit(-1);
			}
		
			parseArguments(args);
			printFiles(dir);
			
		} catch(Exception e) {
		}
	}
	
	
	/**
	 * Metoda koja pokreće filtriranje, sortiranje te ispis
	 * informacija o datotekama koje se nalaze unutar traženog
	 * direktorija.
	 * 
	 * @param dir Direktorij o kojem se ispisuju informacije.
	 */
	private static void printFiles(File dir) {
		List<File> files = new ArrayList<>();
		File[] children = dir.listFiles();
				
		for(File f : children) {
			// ako nije bilo filtera kompozitni filter
			// uvijek vraca true
			if(FILTER.accepts(f)) {
				files.add(f);
				// odmah saznamo maksimalne duljine onih 
				// dijelova koji se ispisuju
				OUT_PRINTER.getMaxLength(f);
			}
		}
		
		if(files.size() == 0) {
			System.exit(-1);
		}
		
		Collections.sort(files, COMPARATOR);	
		OUT_PRINTER.printHeader();
		
		for(File f : files) {
			OUT_PRINTER.printLine(f);
		}
		
		OUT_PRINTER.printHeader();
	}

	
	/**
	 * Metoda koja obrađuje ulazne argumente.
	 * 
	 * @param args Argumenti komandne linije.
	 */
	private static void parseArguments(String[] args) {
		List<IFilter> listFilters = new ArrayList<>();
		List<Comparator<File>> listSorters = new ArrayList<>();
		List<IPrint> listPrint = new ArrayList<>();
		
		for(int i = 1; i < args.length; i++) {
			String current = args[ i ];
			if(current.matches(RegexValue.getSortRegex())) {
				listSorters.add(Factory.factorySort(current));
			}
			else if(current.matches(RegexValue.getFilterRegex())) {
				listFilters.add(Factory.factoryFilter(current));
			}
			else if(current.matches(RegexValue.getPrintRegex())) {
				listPrint.add(Factory.factoryPrint(current));
			}
			else {
				System.err.println("Illegal specifier: " + current);
				System.exit(-1);
			}
		}
		
		if(listPrint.isEmpty()) {
			listPrint.add(new PrintName());
		}
		
		FILTER = new CompositeFilter(listFilters);	
		COMPARATOR = new CompositeSorter(listSorters);
		OUT_PRINTER = new Printer(listPrint);		
	}
}
