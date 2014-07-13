package hr.fer.zemris.java.tecaj.hw1;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class HofstadterQ {
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Na standardni izlaz ispisuje i-ti član Hofstadterovog
	 * Q-reda. Metoda i preuzima kao argument komandne linije,
	 * i je pozitivan cijeli broj.
	 * 
	 * @param Argumenti komandne linije
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) { 
			System.err.println("Please provide i.");
		}
		else if(Integer.parseInt(args[ 0 ]) <= 0) {
			System.err.println("Error, i must be postive.");
		}
		else {
			int i = Integer.parseInt(args[ 0 ]);
			long Qvalue = getQValue(i);
			
			System.out.println("You requested calculation of " + i
					+ ". number of Hofstadter's Q-sequence."
					+ "The requested number is " + Qvalue + "."
			);			
		}
	}
	
	/**
	 * Metoda računa i-ti član Hofstadterovog Q-reda prema
	 * formuli: Q[i] = Q[i - Q[i-1]] + Q[i - Q[i-2]], n > 2
	 * 
	 * @param i Redni broj člana čija se vrijednost traži
	 * @return Vrijednost i-tog člana reda
	 */
	static long getQValue(int i) {
		if (i <= 2) return 1;
		
		long Q[] = new long[ i + 1 ];
		Q[ 1 ] = Q[ 2 ] = 1;
			
		// Q[n] = Q[ n - Q[n-1] ] + Q[ n - Q[n-2] ], n > 2
		for(int j = 3; j <= i; j++) {
			Q[ j ]  = Q[j - (int)Q[j - 1]]; 
			Q[ j ] += Q[j - (int)Q[j - 2]];
		}
		
		return Q[ i ];	
	}

}
