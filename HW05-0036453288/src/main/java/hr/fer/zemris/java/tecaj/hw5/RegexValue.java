package hr.fer.zemris.java.tecaj.hw5;

/**
 * Razred <code>RegexValue</code> definira regexe za pojedine
 * specifikatore.
 * 
 * <pre>
 * 	regexSort - sortiranja
 * 		s - prema veličini, manje datoteke prije
 * 		n - prema imenu, leksikografski manje ide prije
 * 		m - prema vremenu zadnje promjene, manje ide prije
 * 		t - prema vrsti objekta, direktoriji idu prije datoteka
 * 		l - prema duljini imena, manja imena idu prije
 * 		e - prema izvršivosti, datoteke koje se mogu izvršavati
 * 			idu prije.
 * 		minus(-) ispred specifikatora označava obrnuti poredak od onoga 
 * 			koji bi sorter inače dao.
 * </pre>
 * 
 * <pre>
 *  regexFilter - filtriranja
 *  	sSIZE - prihvaća objekte čija je večina manja od SIZE
 *  	f - prihvaća objekte koji su datoteke
 *  	lSIZE - prihvaća objekte čija je duljina imena manja od SIZE
 *  	e - prihvaća objekte koji imaju ekstenziju.
 *  
 *  	minus(-) ispred filtera označa da se prihvaća ono što filter odbije.
 * </pre>
 * 
 * <pre>
 * 	regexPrint - ispisivanje informacija
 * 		n - ime datoteke, lijevo poravnato
 * 		t - tip objekta: za direktorije d, za datoteke f
 * 		s - veličina objekta, desno poravnato
 * 		m - vrijeme zadnje modifikacije, format tipa 2014-12-27 17:21:15.
 * 		h - ako je objekt skriven ispisuje h.
 * 		
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class RegexValue {

	private static String REGEX_SORT = "^-s:([-]?)([snmtle])";
	private static String REGEX_FILTER = "^-f:([-]?)(e|f|s\\d+|l\\d+)";
	private static String REGEX_PRINT = "-w:([ntsmh])";
	
	/**
	 * Metoda koja dohvaća regex za tipove sortiranja.
	 * 
	 * @return Regex za tipove sortiranja.
	 */
	public static String getSortRegex() {
		return REGEX_SORT;
	}
	
	
	/**
	 * Metoda koja dohvaća regex za tipove filtriranja.
	 * 
	 * @return Regex za tipove filtiranja.
	 */
	public static String getFilterRegex() {
		return REGEX_FILTER;
	}
	
	
	/**
	 * Metoda koja dohvaća regex za tipove ispisa.
	 * 
	 * @return Regex za tipove ispisa.
	 */
	public static String getPrintRegex() {
		return REGEX_PRINT;
	}
}
