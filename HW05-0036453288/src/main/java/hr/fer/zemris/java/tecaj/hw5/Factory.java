package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.filter.ExtensionFilter;
import hr.fer.zemris.java.tecaj.hw5.filter.FileFilter;
import hr.fer.zemris.java.tecaj.hw5.filter.InverseFilter;
import hr.fer.zemris.java.tecaj.hw5.filter.LSizeFilter;
import hr.fer.zemris.java.tecaj.hw5.filter.SSizeFilter;
import hr.fer.zemris.java.tecaj.hw5.print.PrintHiden;
import hr.fer.zemris.java.tecaj.hw5.print.PrintModificationTime;
import hr.fer.zemris.java.tecaj.hw5.print.PrintName;
import hr.fer.zemris.java.tecaj.hw5.print.PrintSize;
import hr.fer.zemris.java.tecaj.hw5.print.PrintType;
import hr.fer.zemris.java.tecaj.hw5.sort.ExecutionSort;
import hr.fer.zemris.java.tecaj.hw5.sort.InverseSort;
import hr.fer.zemris.java.tecaj.hw5.sort.LastModificationSort;
import hr.fer.zemris.java.tecaj.hw5.sort.LengthSort;
import hr.fer.zemris.java.tecaj.hw5.sort.NameSort;
import hr.fer.zemris.java.tecaj.hw5.sort.SizeSort;
import hr.fer.zemris.java.tecaj.hw5.sort.TypeSort;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Razred </code>Factory</code> sadrži metode tvornice koje
 * znaju instancirati potrebne filtere, sortere i razrede za 
 * ispisivanje podataka.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Factory {

	/**
	 * Statička metoda koja vraća instancu koja predstavlja neki od tipova
	 * sortiranja: prema veličini, imenu, tipu, duljini imena ili prema 
	 * izvršivosti datoteke.
	 * 
	 * @param command Naredba koja se parsira.
	 * @return komparator za traženi tip usporedbe. 
	 */
	public static Comparator<File> factorySort(String command) {
		String regexSort = RegexValue.getSortRegex();
		Matcher m = Pattern.compile(regexSort).matcher(command);
		m.matches();
		
		Comparator<File> ret = null;
		if(m.group(2).compareTo("s") == 0) {
			ret = new SizeSort();
		}
		else if(m.group(2).compareTo("n") == 0) {
			ret = new NameSort();
		}
		else if(m.group(2).compareTo("m") == 0) {
			ret = new LastModificationSort();
		}
		else if(m.group(2).compareTo("t") == 0) {
			ret = new TypeSort();
		}
		else if(m.group(2).compareTo("l") == 0) {
			ret = new LengthSort();
		}
		else {
			ret = new ExecutionSort();
		}
		
		if(m.group(1).startsWith("-")) {
			return new InverseSort(ret);
		}
		
		return ret;
	}
	
	
	/**
	 * Statička metoda koja vraća instancu koja podržava neki od tipova 
	 * filtriranje: prema tipu datoteke, prema tome dali datoteka ima 
	 * ekstenziju, prema veličini datoteke ili duljini imena.
	 * 
	 * @param command Specifikator koji se parsira.
	 * @return instanca filtera za traženi specifikator.
	 */
	public static IFilter factoryFilter(String command) {
		String regexFilter = RegexValue.getFilterRegex();
		Matcher m = Pattern.compile(regexFilter).matcher(command);
		m.matches();
		
		IFilter ret = null;
		if(m.group(2).startsWith("f")) {
			ret = new FileFilter();
		}
		else if(m.group(2).startsWith("e")) {
			ret = new ExtensionFilter();
		}
		else if(m.group(2).startsWith("s")) {
			long size = Long.parseLong(m.group(2).substring(1));
			ret = new SSizeFilter(size);
		}
		else {
			int size = Integer.parseInt(m.group(2).substring(1));
			ret = new LSizeFilter(size);
		}
		
		if(m.group(1).startsWith("-")) {
			return new InverseFilter(ret);
		}
		
		return ret;
	}
	
	
	/**
	 * Statička metoda koja vraća instancu koja podržava neki od 
	 * tipova ispisa na standardni izlaz: ime, tip, večina, vrijeme
	 * zadnje promjene, informacija o tome da li je datoteka skrivena.
	 * 
	 * @param command Specifikator koji se parsira.
	 * @return instanca razreda koji zna dohvatiti informaciju koju 
	 * 			definira specifikator.
	 */
	public static IPrint factoryPrint(String command) {
		String regexPrint = RegexValue.getPrintRegex();
		Matcher m = Pattern.compile(regexPrint).matcher(command);
		m.matches();
		
		if(m.group(1).startsWith("n")) {
			return new PrintName();
		}
		else if(m.group(1).startsWith("t")) {
			return new PrintType();
		}
		else if(m.group(1).startsWith("s")) {
			return new PrintSize();
		}
		else if(m.group(1).startsWith("m")) {
			return new PrintModificationTime();
		}
		
		return new PrintHiden();		
	}
}
