package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import java.util.Arrays;

/**
 * Razred koji omogućava testiranje QMC minimizatora i mjerenje
 * vremena minimizacije.
 * @author Igor Smolkovič
 *
 */
public class Main {
	/**
	 * Metoda koja se pokreće prilikom pokretanja programa.
	 * @param args Argumenti komandne linije. Ne koriste se.
	 */
	public static void main(String[] args) {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");

		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(4,5,6,7,8,9,11),
				Arrays.asList(2,3,12,15)
		);
		long t0 = System.currentTimeMillis();
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		long t1 = System.currentTimeMillis();
		System.out.println("Minimalnih oblika ima: " + fje.length);
		for (MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for (Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
		System.out.println("Vrijeme: " + (t1 - t0) + " ms");
	}
}
