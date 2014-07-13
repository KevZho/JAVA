package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class ProgramListe {
	
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	/**
	 * Metoda koja se poziva kod pokretanja programa
	 * 
	 * @param args Argumenti komandne linije
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	
	/**
	 * Metoda koja određuje broj čvorova liste.
	 * 
	 * @param cvor Prvi čvor liste
	 * @return Veličina liste
	 */
	static int velicinaListe(CvorListe cvor) {
		int velicina = 0;
		
		// ide kroz listu i broji cvorove
		while(cvor != null) {
			velicina++;
			cvor = cvor.sljedeci;
		}
		
		return velicina;
	}

	/**
	 * Metoda koja dodaje novi čvor na kraj liste.
	 * 
	 * @param prvi Prvi čvor liste
	 * @param podatak Vrijednost podatka u čvoru koji se dodaje
	 * @return Prvi element liste
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		if(prvi == null) { // prvi cvor liste
			prvi = new CvorListe();
			prvi.podatak = podatak; 
			prvi.sljedeci = null;
		}
		else if(prvi.sljedeci != null){ // traži predzadnji
			ubaci(prvi.sljedeci, podatak);
		}
		else { // ubacuje na kraj liste
			prvi.sljedeci = new CvorListe();
			prvi.sljedeci.podatak = podatak;
			prvi.sljedeci.sljedeci = null;
		}
		
		return prvi; // vrati prvi cvor liste
	}
	
	
	/**
	 * Metoda koja na standardni izlaz ispisuje listu
	 * od prvog čvora.
	 * 
	 * @param cvor Čvor od kojeg se ispisuje lista
	 */
	static void ispisiListu(CvorListe cvor) {
		if(cvor == null) { 
			System.err.println("Lista je prazna!");
		}
		
		while(cvor != null) {
			System.out.println(cvor.podatak);
			cvor = cvor.sljedeci;
		}
	}
	
	
	/**
	 * Metoda koja radi uzlazno sortiranje liste 
	 * počevši od dobivenog čvora.
	 * 
	 * @param cvor Čvor od kojeg se sortira
	 * @return Prvi čvor sortirane liste
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		int velicina = velicinaListe(cvor);
		if(velicina == 1) return cvor;
		
		for(int i = 0; i < velicina - 1; i++) {
			CvorListe prvi = cvor;
			for(int j = 0; j < velicina - 1 - i; j++) {
				// ako je sljedeci podatak manjih od trenutnog zamijeni ih
				if(prvi.podatak.compareTo(prvi.sljedeci.podatak) > 0) {
					String vrijednost = prvi.sljedeci.podatak;
					prvi.sljedeci.podatak = prvi.podatak;
					prvi.podatak = vrijednost;
				}
				
				prvi = prvi.sljedeci;
			}
		}
		
		return cvor;
	}
}
