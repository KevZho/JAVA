package hr.fer.zemris.java.tecaj.hw3;


/**
 * Razred <code>IteratorTester</code> omogućava testiranje
 * implementiranog razreda <code>IntegerSequence</code>.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 * 
 */
public class IteratorTester {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		
		for(int i : range) {
			for(int j : range) {
				System.out.println("i="+i+", j="+j);
			}
		}
		
	}

}
