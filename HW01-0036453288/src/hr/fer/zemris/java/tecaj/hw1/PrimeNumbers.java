package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class PrimeNumbers {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Na standardni izlaz ispisuje prvih n prostih brojeva.
	 * Broj n prima kao argument komandne linije, n je 
	 * pozitivan cijeli broj.
	 * 
	 * @param args Argumenti komandne linije
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("You must provide argument n.");
		}
		else if(Integer.parseInt(args[ 0 ]) <= 0) {
			System.err.println("You must provide argument n > 0");
		}
		else {
			int n = Integer.parseInt(args[ 0 ]); 
			
			System.out.println("You requested calculation of " + n  + 
					" prime numbers. Here they are:");
	
			int index = 1; long current = 2;
			while(index <= n) {
				boolean prime = true;
				
				// provjera da li je trenutni broj prosti
				for(long i = 2; i * i <= current; i++) {
					if(current % i == 0 && i != current) {
						prime = false; 
					}
				}
				
				if(prime) { 
					System.out.println(index + ". " + current);
					index++;
				}
				
				current++;
			}
		}
	}

}
