package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Razred <code>Prog3</code> sadrži demo primjer koji 
 * traži rješenje sustava linearnih jednadžbi.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Prog3 {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije, u ovom programu se ne koriste.
	 */
	public static void main(String[] args) {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10");
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		System.out.println("Rjesenje sustava je:");
		System.out.print(v);
	}
}
