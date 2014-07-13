package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class ProgramStabla {

	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args Argumenti komandne linije
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		
		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);
		
		cvor = okreniPoredakStabla(cvor);
		
		System.out.println("Ispisujem okrenuto stablo inorder:");
		ispisiStablo(cvor);
	
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: "+vel);
	
		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: "+pronaden);
	}
	
	/**
	 * Metoda koja ispituje postojanje podatka u stablu.
	 * 
	 * @param korijen Korijen stabla
	 * @param podatak Podatak koji se traži
	 * @return Postojanje podatka; true -postoji, false -ne postoji
	 */
	static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
		if(korijen == null) return false;		
		if(korijen.podatak == podatak) return true;
		
		boolean s1 = korijen.lijevi != null ? 
				sadrziPodatak(korijen.lijevi, podatak) : false;
		boolean s2 = korijen.desni != null  ? 
				sadrziPodatak(korijen.desni, podatak)  : false;
				
		return s1 | s2;
	}
	
	/**
	 * Metoda koja određuje broj čvorova stabla
	 * 
	 * @param cvor Korijen stabla
	 * @return Broj čvorova u stablu
	 */
	static int velicinaStabla(CvorStabla cvor) {
		if(cvor == null) return 0;
		
		int v1 = cvor.lijevi != null ? velicinaStabla(cvor.lijevi) : 0;
		int v2 = cvor.desni  != null ? velicinaStabla(cvor.desni)  : 0;
		
		return v1 + v2 + 1;
	}
	
	/**
	 * Metoda koja dodaje novi čvor u binarno stablo na 
	 * odgovarajuću poziciju, lijevo dijete manji podatak
	 * dok je desno veći. 
	 * 
	 * @param korijen Korijen stabla
	 * @param podatak Vrijednost podatka čvora koji se dodaje
	 * @return Korijen novog stabla
	 */
	static CvorStabla ubaci(CvorStabla korijen, String podatak) {
		if(korijen == null) { // stablo je prazno
			korijen = new CvorStabla();
			korijen.podatak = podatak;
			korijen.lijevi = korijen.desni = null;
		}
		// dodaje čvor kao lijevo dijete
		else if(korijen.podatak.compareTo(podatak) > 0) {
			if(korijen.lijevi != null){ 
				ubaci(korijen.lijevi, podatak);
			}
			else {
				korijen.lijevi = new CvorStabla();
				korijen.lijevi.podatak = podatak;
				korijen.lijevi.lijevi = korijen.lijevi.desni = null;
			}
		}
		// dodaje čvor kao desno dijete
		else if(korijen.podatak.compareTo(podatak) < 0) {
			if(korijen.desni != null) 
				ubaci(korijen.desni, podatak);
			else{
				korijen.desni = new CvorStabla();
				korijen.desni.podatak = podatak;
				korijen.desni.lijevi = korijen.desni.desni =  null;
			}
		}
		
		return korijen;
	}
	
	/**
	 * Metoda koja provodi inorder obilazak stabla te na 
	 * standardni izlaz ispisuje podatke u čvorovima.
	 * 
	 * @param cvor Korijen stabla
	 */
	static void ispisiStablo(CvorStabla cvor) {
		if(cvor == null) return;
		ispisiStablo(cvor.lijevi);
		
		System.out.println(cvor.podatak);
		
		ispisiStablo(cvor.desni);
	}
	
	/**
	 * Metoda koja okreće binarno stablo.
	 * 
	 * @param korijen Korijen stabla
	 * @return Korijen okrenutog stabla
	 */
	static CvorStabla okreniPoredakStabla(CvorStabla korijen) {
		if(korijen == null) return null;
		
		if(korijen.lijevi != null) okreniPoredakStabla(korijen.lijevi);
		if(korijen.desni  != null) okreniPoredakStabla(korijen.desni);
		
		// zamjena lijevog i desnog djeteta
		CvorStabla tmp = korijen.lijevi;
		korijen.lijevi = korijen.desni;
		korijen.desni = tmp;
		
		return korijen;
	}

}
