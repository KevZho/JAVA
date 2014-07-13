package hr.fer.zemris.java.tecaj.hw3;


/**
 * 
 * Razred <code>CString</code> implementacija je razreda koja korisnicima
 * pruža rad sa stringovima.
 * 
 * <p>
 * Instanca razreda interno sprema polje znakova čije se vrijednosti
 * tijekom izvođenja ne mogu promijeniti. Koristi se djeljeno polje  
 * znakova te se neke operacije mogu izvoditi u konstantnom vremenu. 
 * Primjer uporabe je u nastavku.
 * </p>
 * 
 * <pre>
 * CString c1 = new CString("Java");
 * CString c2 = c1.substring(0,2);
 *  
 * System.out.println(c2);
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 * 
 */
public class CString {
	
	/**
	 * Broj znakova u polju koji pripadaju trenutnom stringu.
	 */
	private int count;
	
	/**
	 * Indeks prvog znaka trenutnog stringa.
	 */
	private int offset;
	
	/**
	 * Polje znakova koje sadrži trenutni string.
	 */
	private char[] value; 
	
	
	/**
	 * Konstruktor koji stvara novu instancu na temelju polja znakova iz kojeg se 
	 * kopiraju znakovi od pozicije offset. 
	 * 
	 * @param data Polje znakova koje se koristiti u izgradnji instance.
	 * @param offset Prvi znak od kojeg se kopira.
	 * @param length Broj znakova koji se kopiraju.
	 * @throws IllegalArgumentException ako offset ili duljina nisu odgovarajući.
	 */
	public CString(char[] data, int offset, int length) {
		if(offset < 0) {
			throw new IllegalArgumentException("CString : offset < 0");
		}
		
		if(length < 0) {
			throw new IllegalArgumentException("CString : length < 0");
		}
		
		if(data.length - length < offset) {
			throw new IllegalArgumentException("CString : offset too big");
		}
	
		copyArray(data, offset, length);
	}
		
	
	/**
	 * Konstruktor koji stvara novu instancu na temelju polja znakova.
	 * Dobiveno polje znakova se kopira.
	 * 
	 * @param data Polje znakova koje se koristi za izgradnju instance.
	 */
	public CString(char[] data) {
		copyArray(data, 0, data.length);
	}
		
	
	/**
	 * Konstruktor koji obavlja kopiju instance razreda CString. 
	 * Kopija internog polja se radi samo ako dobivena instanca
	 * ne koristi cijelo polje.
	 * 
	 * @param original Instanca razreda CString čija se kopija radi.
	 * @throws IllegalArgumentException ako je original null referenca.
	 */
	public CString(CString original) {
		if(original == null) {
			throw new IllegalArgumentException("CString null pointer");
		}
		
		if(original.value.length == original.count) {
			this.value = original.value;
			this.offset = original.offset;
			this.count = original.count;
		}
		else {
			int length = original.value.length - original.offset;
			copyArray(original.value, original.offset, length);
		}
	}
	
	
	/**
	 * Metoda koja alocira memoriju te obavlja kopiranje znakova iz 
	 * dobivenog polja od pozicije offset.
	 * 
	 * @param data Polje znakova iz kojeg se kopira.
	 * @param offset Početni znak od kojeg se kopira.
	 * @param length Broj znakova koji se kopiraju.
	 */
	private void copyArray(char[] data, int offset, int length) {
		this.value = new char[ length ];
		this.count = length;
		this.offset = 0;
		
		for(int i = 0; i < length; i++) {
			this.value[ i ] = data[ i + offset ];
		}
	}
	
	
	/**
	 * Konstruktor koji stvara instancu razreda CString na temelju 
	 * Java Stringa. Dobiveni string se kopira.
	 * 
	 * @param s Java String.
	 */
	public CString(String s) {
		this.count = s.length();
		this.value = new char[ this.count ];
		this.offset = 0;
		
		for(int i = 0; i < this.count; i++) {
			this.value[ i ] = s.charAt(i);
		}
	}
		
	
	/**
	 * Privatni konstruktor koji ne radi kopiju polja znakova.
	 * Omogućava korištenje djeljenog polja znakova.
	 * 
	 * @param offset Indeks prvog znaka
	 * @param length Broj znakova niza.
	 * @param data Polje znakova koje sadrži originalni string.
	 */
	private CString(int offset, int length, char[] data) {
		this.offset = offset;
		this.count = length;
		this.value = data;
	}
	
	
	/**
	 * Metoda koja vraća broj znakova trenutnog stringa.
	 * 
	 * @return Broj znakova trenutnog stringa.
	 */
	public int length() {
		return count;
	}
	
	
	/**
	 * Metoda koja vraća znak koji se nalazi na poziciji index.
	 * 
	 * @param index Pozicija znaka koji se dohvaća.
	 * @return Znak koji se nalazi na poziciji index.
	 * @throws IndexOutOfBoundsException ako index nije dobar.
	 */
	public char charAt(int index) {
		if(index < 0 || index > this.count - 1) {
			throw new IndexOutOfBoundsException("charAt: IndexOutOfBound " + index);
		}
		
		return this.value[ index + offset ];
	}
	
	
	/**
	 * Metoda koja vraća kopiju polja znakova koju sadrži trenutni 
	 * string.
	 * 
	 * @return Kopija polja znakova trenutnog stringa.
	 */
	public char[] toCharArray() {
		char[] array  = new char[ this.count ];
		
		for(int i = 0; i < this.count; i++) {
			array[ i ] = this.charAt(i);
		}
		
		return array;
	}
	
	
	/**
	 * Overridana metoda toString() koja vraća string reprezentaciju
	 * internog polja znakova.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < this.count; i++) {
			builder.append(this.charAt(i));
		}
		
		return builder.toString();
	}
	
	
	/**
	 * Metoda koja vraća indeks prvog pojavljivanja znaka
	 * unutar stringa.
	 * 
	 * @param c Znak koji se traži.
	 * @return -1 ako znak nije pronađen, inače pozicija prvog pojavljivanja.
	 */
	public int indexOf(char c) {
		for(int i = 0; i < this.count; i++) {
			if(this.charAt(i) == c) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * Metoda koja provjerava počinje li trenutni string sa stringom
	 * dobivenim kao parametar.
	 * 
	 * @param s String s kojim se uspoređuje početak trenutnog stringa.
	 * @return true ako trenutni string započinje sa stringom s,
	 *  		inače false. Vraća true ako je s prazan string.
	 */
	public boolean startsWith(CString s) {
		if(s.length() > this.length()) {
			return false;
		}
		
		for(int i = 0, end = s.length(); i < end; i++) {
			if(this.charAt(i) != s.charAt(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Metoda koja provjerava završava li trenutni string sa stringom
	 * dobivenim kao parametar.
	 * 
	 * @param s String s kojim se uspoređuje završetak trenutnog stringa.
	 * @return true ako trenutni string završava sa "s", inače false.
	 * 			Vraća true ako je s prazan string.
	 */
	public boolean endsWith(CString s) {
		if(s.length() > this.length()) {
			return false;
		}
		
		int start = this.length() - s.length();
		for(int i = 0, end = s.length(); i < end; i++) {
			if(s.charAt(i) != this.charAt(start + i)) {
				return false;
			}
		}
		
		return true;
	}

	
	/**
	 * Metoda koja ispituje nalazi li se u trenutnom stringu nalazi string 
	 * dobiven kao parametar.
	 *  
	 * @param s String čije se postojanje ispituje.
	 * @return true ako trenutni string sadrži s, inače false. Vraća true
	 * 			ako je traženi string prazan string.
	 */
	public boolean contains(CString s) {
		if(s.length() > this.length()) {
			return false;
		}
		
		if(s.length() == 0) {
			return true;
		}
		
		return firstOccurrenceOfString(0, s) != -1;
	}
	
	
	/**
	 * Metoda koja traži prvo pojavljivanje stringa s unutar trenutnog 
	 * stringa.
	 * 
	 * @param start Početna pozicija od koje se gleda trenutni string.
	 * @param s String čije se postojanje traži.
	 * @return -1 ako nije pronađen string, inače pozicija prvog pojavljivanja
	 * 				traženog stringa.
	 */
	private int firstOccurrenceOfString(int start, CString s) {
		int sLength = s.length();
		int end = this.length() - sLength;
		
		for(int i = start; i <= end; i++) {
			boolean contains = isOverlap(i, s);
			
			if(contains) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * Metoda koja provjera da li u trenutnom stringu od pozicije start 
	 * sljedi string dobiven kao parametar. 
	 * 
	 * @param start Pozicija od koje se gleda trenutni string.
	 * @param s String čije se postojanje gleda.
	 * @return true ako od znaka start sljedi string s, inače false. 
	 */
	private boolean isOverlap(int start, CString s) {
		if(start + s.length() > this.count) {
			return false;
		}
		
		for(int i = 0, end = s.length(); i < end; i++) {
			if(this.charAt(start + i) != s.charAt(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Metoda koja vraća podstring trenutnog stringa a sadrži znakove koji se 
	 * nalaze u intevalu [startIndex, endIndex>.
	 * .  
	 * 
	 * @param startIndex Prvi znak od kojega počinje novi string.
	 * @param endIndex Posljednji znak novog stringa; nije uključen.
	 * @return Željeni podstring.
	 * @throws IndexOutOfBoundsException ako granice nisu dobre.
	 */
	public CString substring(int startIndex, int endIndex) {
		if(startIndex < 0) {
			String msg = "Substring: startIndex < 0";
			throw new IndexOutOfBoundsException(msg);
		}
		
		if(endIndex < startIndex) {
			String msg = "Substring: endIndex < startIndex";
			throw new IndexOutOfBoundsException(msg);
		}
		
		if(endIndex > this.count) {
			String msg = "Substring: endIndex > length";
			throw new IndexOutOfBoundsException(msg);
		}
		
		
		final int start = this.offset + startIndex;
		final int lenght = endIndex - startIndex;
			
		return new CString(start, lenght, this.value);
	}
	
	
	/**
	 * Metoda koja vraća string koji predstavlja početnih n znakova
	 * trenutnog stringa.
	 * 
	 * @param n Koliko početnih znakova se uzima; n >= 0.
	 * @return String koji sadrži početnih n znakova trenutnog stringa.
	 * @throws IllegalArgumentException ako n nije odgovarajući.
	 */
	public CString left(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("CString right: n < 0");
		}
		
		if(n > this.count) {
			throw new IllegalArgumentException("CString right: n > length");
		}
		
		return new CString(this.offset, n, this.value);
	}
	
	
	/**
	 * Metoda koja vraća string koji predstavlja posljenjih n znakova
	 * trenutnog stringa.
	 * 
	 * @param n Koliko posljednjih znakova se uzima; n >=0.
	 * @return String koji sadrži posljednjih n znakova trenutnog stringa.
	 * @throws IllegalArgumentException ako n nije odgovarajući.
	 */
	public CString right(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("CString left: n < 0");
		}
		
		if(n > this.count) {
			throw new IllegalArgumentException("CString left: n > length");
		}
		
		final int start = this.count - n + offset;
		return new CString(start, n, this.value);
	}
	
	
	/**
	 * Metoda koja trenutnom stringu na kraj dodaje string dobiven
	 * kao parametar. 
	 * 
	 * @param s String koji se dodaje trenutnom.
	 * @return String nastao kao rezultat spajanja stringova.
	 */
	public CString add(CString s) {
		int length = this.count + s.length();
		
		if(length == this.count) {
			return new CString(0, this.count, this.value);
		}
		
		char[] array = new char[ length ];
		int index = 0;
		
		for(int i = 0; i < this.count; i++) {
			array[ index++ ] = this.charAt(i);
		}
		
		for(int i = 0, end = s.length(); i < end; i++) {
			array[ index++ ] = s.charAt(i);
		}
		
		return new CString(0, length, array);
	}
	
	
	/**
	 * Metoda mijenja svako pojavljanjivanje znaka oldChar sa newChar
	 * unutar trenutnog stringa.
	 * 
	 * @param oldChar Znak koji se mijenja.
	 * @param newChar Znak sa kojim se mijenja.
	 * @return String koji je rezultat zamjene znaka.
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if(oldChar == newChar) {
			return new CString(0, this.count, this.value);
		}
		
		char[] array = new char[ this.count ];
		
		for(int i = 0; i < this.count; i++) {
			char c = this.charAt(i);
			array[ i ] = (c == oldChar) ? newChar : c;
		}
		
		return new CString(0, this.count, array);
	}
	
	
	/**
	 * Metoda mijenja svako pojavljivanje podstringa oldStr sa newStr
	 * unutar trenutnog stringa.
	 * 
	 * @param oldStr Podstring koji se mijenja.
	 * @param newStr Podstring sa kojim se mijenja.
	 * @return String koji je rezultat zamjene podstringa.
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		int index = firstOccurrenceOfString(0, oldStr);
		int length = this.count;
		
		if(index == -1) {
			return new CString(0, this.count, this.value);
		}
		
		// traži se potrebna duljina novog polja znakova
		do {
			index += oldStr.length();
			length += (newStr.length() - oldStr.length());
			index = firstOccurrenceOfString(index, oldStr);
		} while(index != -1);
		
		char[] array = new char[ length ];
		
		int original = 0, replaced = 0;
		
		while(original < this.count) {
			//pronađen je oldStr i u novo polje se kopira newStr
			if(this.isOverlap(original, oldStr)) {
				for(int j = 0, end = newStr.length(); j < end; j++) {
					array[ replaced++ ] = newStr.charAt(j);
				}
				original += oldStr.length();
			}
			else { // ostali znakovi samo se kopiraju iz originala.
				array[ replaced++ ] = this.charAt(original++);
			}
		}
		
		return new CString(0, length, array);
	}
	
	
	/**
	 * Metoda koja se koristi za provjeru da li instance dijele isto
	 * polje znakova.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if( !(obj instanceof CString)) {
			return false;
		}
		
		CString s = (CString) obj;
		return s.value == this.value;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 42;
	}
	
}
