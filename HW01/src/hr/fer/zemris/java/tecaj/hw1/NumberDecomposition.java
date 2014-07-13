package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class NumberDecomposition {
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Na standardni izlaz ispisuje dekompoziciju broja n na 
	 * proste faktore. Metoda n preuzima kao argument komandne
	 * linije, n je prirodni broj veći od 1.
	 * 
	 * @param args Argumenti komandne linije
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("You must provide argument n.");
		}
		else if(Long.parseLong(args[ 0] ) < 2) {
			System.err.println("Error, you must provide n > 1.");
		}
		else {
			long n = Long.parseLong(args[ 0 ]);
			long prime = 2, index = 1;
			
			System.out.println("You requested decomposition of number " 
					+ n + " onto prime factors. Here they are: "
			);
			
			while(n > 1) {
				if( n % prime == 0) {
					System.out.println(index + ". " + prime);
					n /= prime; index++;
				}
				else prime = nextPrime(prime); 
			}
		}
	}
	
	/**
	 * Metoda koja na temelju trenutnog prostog broja pronalazi
	 * prvi sljedeći prosti broj.
	 * 
	 * @param current Prosti broj za koji se traži prvi sljedeći
	 * @return Prvi sljedeći prosti broj
	 */
	static long nextPrime(long current) {
		boolean prime = false;
		
		while(prime == false) {
			prime = true;
			current++;
			
			// provjera da li je trenutni broj prosti
			for(long i = 2; i * i <= current; i++) {
				if(current % i == 0 && i != current) {
					prime = false;
				}
			}
		}
	
		return current;
	}

}
