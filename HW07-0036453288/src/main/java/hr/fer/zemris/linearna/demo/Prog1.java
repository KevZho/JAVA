package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Razred <code>Prog1</code> sadrži jednostvni demo primjer 
 * koji učitava matrice te ispisuje pravu i transponiranu 
 * matricu prije i nakon promjene neke od vrijednosti.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Prog1 {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije. Program ne koristi argumente 
	 * 				komadne linije.
	 */
	public static void main(String[] args) {
		IMatrix m1 = Matrix.parseSimple ( "1 2 3 | 4 5 6" ) ;
		IMatrix m2 = m1.nTranspose ( true ) ;
		System.out.println("m1:");
		System.out.println(m1.toString());
		System.out.println("m2:");
		System.out.println(m2.toString());

		m2.set(2, 1, 9);
		
		System.out.println("m1:");
		System.out.println(m1.toString());
		System.out.println("m2:");
		System.out.println(m2.toString());
	}
}
