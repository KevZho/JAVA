package hr.fer.zemris.java.sorters;

import java.util.Random;

/**
 * Program koji služi za testiranje sortiranja uz korištenje
 * paraleliziranog quick sorta.
 * @author Igor Smolkovič
 *
 */
public class Glavni {

	/**
	 * Koliko elemenata se sortira.
	 */
	private static final int SIZE = 1500000;

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti komandne linije. Program ne koristi argumente komandne linije.
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		int[] data = new int[SIZE];

		for (int i = 0; i < data.length; i++) {
			data[i] = rand.nextInt();
		}

		long t0 = System.currentTimeMillis();
		QSortParallel.sort(data);
		long t1 = System.currentTimeMillis();

		System.out.println("Sortirano: " + QSortParallel.isSorted(data));
		System.out.println("Vrijeme: " + (t1 - t0) + " ms");
	}
}
