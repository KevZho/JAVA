package hr.fer.zemris.bool;

import java.util.Arrays;

/**
 * Razred <code>Mask</code> omogućava rad sa maskama.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Mask {

	private MaskValue[] value;

	
	/**
	 * Konstruktor razreda <code>Mask</code>.
	 * 
	 * @param value Polje koje sadrži MaskValue.
	 * @throws IllegalArgumentException ako polje sadrži null
	 * 			reference.
	 */
	public Mask(MaskValue[] value) {
		for(int i = 0; i < value.length; i++) {
			if(value[ i ] == null) {
				String msg = "Null reference in MaskValue[]";
				throw new IllegalArgumentException(msg);
			}
		}
		
		this.value = Arrays.copyOf(value, value.length);
	}
	
	
	/**
	 * Metoda koja dohvaća MaskValue na poziciji m.
	 * 
	 * @param m Pozicija na kojoj se dohvaća vrijednost.
	 * @return MaskValue na poziciji m.
	 * @throws IndexOutOfBoundsException ako indeks nije odgovarajući.
	 */
	public MaskValue getValue(int m) {
		if(m < 0 || m > value.length - 1) {
			String msg = "Mask.getValue";
			throw new IndexOutOfBoundsException(msg);
		}
		
		return value[ m ];
	}
	
	
	/**
	 * Metoda koja parsira string i stvara instancu razreda Mask.
	 * 
	 * @param s String koji se parsira.
	 * @return Instanca razreda Mask nastala iz stringa.
	 * @throws IllegalArgumentException ako je dobiveni string prazan.
	 */
	public static Mask parse(String s) {
		if(s.isEmpty()) {
			String msg = "Mask.parse empty string";
			throw new IllegalArgumentException(msg);
		}
		
		MaskValue[] array = new MaskValue[ s.length() ];
		
		for(int i = 0, end = s.length(); i < end; i++) {
			if(s.charAt(i) == '0') {
				array[ i ] = MaskValue.ZERO;
			}
			else if(s.charAt(i) == '1') {
				array[ i ] = MaskValue.ONE;
			}
			else if(s.charAt(i) == 'x') {
				array[ i ] = MaskValue.DONT_CARE;
			}
			else {
				String msg = "Mask.parse illegal mask value";
				throw new IllegalArgumentException(msg);
			}
		}
		
		return new Mask(array);
	}
	
	
	/**
	 * Metoda koja stvara instancu razreda Mask iz 
	 * indexa i duljine maske.
	 * 
	 * @param size Duljina maske.
	 * @param index Index iz kojeg nastaje maska.
	 * @return instanca razreda Mask nastala iz indexa.
	 * @throws IllegalArgumentException ako je veličina maske premala
	 * 			ili indeks nije dobar.
	 */
	public static Mask fromIndex(int size, int index) {
		if(index < 0) {
			String msg = "Mask fromIndex negative index";
			throw new IllegalArgumentException(msg);
		}
		
		String binaryString = Integer.toBinaryString(index);
		
		if(binaryString.length() > size) {
			String msg = "Mask.fromIndex illegal size";
			throw new IllegalArgumentException(msg);
		}
		
		int zeros = size - binaryString.length();
		int i = 0;
		MaskValue[] array= new MaskValue[ size ];
		
		while(zeros > 0) {
			array[ i++ ] = MaskValue.ZERO;
			zeros--;
		}
		
		for(int j = 0, end = binaryString.length(); j < end; j++) {
			if(binaryString.charAt(j) == '1') {
				array[ i++ ] = MaskValue.ONE;
			}
			else {
				array[ i++ ] = MaskValue.ZERO;
			}
		}
		
		return new Mask(array);
	}
	
	
	/**
	 * Metoda koja ispituje koja ispituje da li je trenutna maska općenitija
	 * od maske dobivene kao parametar.
	 * 
	 * @param m Maska s kojom se uspoređuje.
	 * @return true maska je općenitija, inače false.
	 */
	public boolean isMoreGeneralThan(Mask m) {
		if(m == null) {
			return false;
		}
		if(this.getSize() != m.getSize()) {
			return false;
		}
		
		int equal = 0;
		boolean moreGeneral = false;
		for(int i = 0, end = this.getSize(); i < end; i++) {
			// druga je općenitija
			if(this.getValue(i) != MaskValue.DONT_CARE 
					&& m.getValue(i) == MaskValue.DONT_CARE) {
				return false;
			}
			// iste vrijednosti
			if(this.getValue(i) == m.getValue(i)) {
				equal++;
			}
			
			if(this.getValue(i) == MaskValue.DONT_CARE &&
					m.getValue(i) != MaskValue.DONT_CARE) {
				moreGeneral = true;
			}
		}
		return (equal == m.getSize()) ? false : moreGeneral;
	}
	
	
	/**
	 * Statička metoda koja radi kombinaciju dobivenih maski.
	 * 
	 * @param m1 Prva maska.
	 * @param m2 Druga maska.
	 * @return Rezultat kombiniranja maski, null ako nije moguće.
	 * @throws IllegalArgumentException ako je neka od maski null ili 
	 * 			maske nisu iste veličine.
	 */
	public static Mask combine(Mask m1, Mask m2) {
		if(m1 == null || m2 == null) {
			String msg = "Mask.combine null reference";
			throw new IllegalArgumentException(msg);
		}
		if(m1.getSize() != m2.getSize()) {
			String msg = "Mask.combine different sizes";
			throw new IllegalArgumentException(msg);
		}
		
		boolean combineable = checkMasks(m1, m2);
		if(combineable == false) {
			return null;
		}
		
		MaskValue[] array  = new MaskValue[ m1.getSize() ];
		
		for(int i = 0, end = m1.getSize(); i < end; i++) {
			if(m1.getValue(i) != m2.getValue(i)) {
				array[ i ] = MaskValue.DONT_CARE;
			}
			else {
				array[ i ] = m1.getValue(i);
			}
		}
		
		return new Mask(array);
	}

	
	/**
	 * Metoda koja provjerava mogu li se maske kombinirati u 
	 * općenitiju.
	 * 
	 * @param m1 Prva maska.
	 * @param m2 Druga maska.
	 * @return true ako se maske mogu kombinirati, inače false.
	 */
	private static boolean checkMasks(Mask m1, Mask m2) {
		int equal = 0;
		for(int i = 0, end = m1.getSize(); i < end; i++) {
			MaskValue first = m1.getValue(i);
			MaskValue second = m2.getValue(i);
			
			if(first == MaskValue.DONT_CARE 
					&& second != MaskValue.DONT_CARE) {
				return false;
			}
			else if(second == MaskValue.DONT_CARE 
					&& first != MaskValue.DONT_CARE) {
				return false;
			}
			else if(first == second) {
				equal++;
			}
		}
		
		// ako na jednoj poziciji imamo razlicite vrijednosti 0 i 1
		// ako ih je vise ne pokrivamo sve moguce maske
		return m1.getSize() - equal == 1 ? true : false;
	}
	
	
	/**
	 * Metoda koja vraća broj nula u maski.
	 * 
	 * @return Broj nula u maski.
	 */
	public int getNumberOfZeros() {
		int zeros = 0;
		
		for(int i = 0; i < value.length; i++) {
			if(value[ i ] == MaskValue.ZERO) {
				zeros++;
			}
		}
		
		return zeros;
	}
	
	
	/**
	 * Metoda koja vraća broj jedinica u maski.
	 * 
	 * @return Broj jedinica u maski.
	 */
	public int getNumberOfOnes() {
		int ones = 0;
		
		for(int i = 0; i < value.length; i++) {
			if(value[ i ] == MaskValue.ONE) {
				ones++;
			}
		}
		
		return ones;
	}
	
	
	/**
	 * Metoda koja vraća veličinu maske.
	 * 
	 * @return Veličina maske.
	 */
	public int getSize() {
		return value.length;
	}
		
}
