package hr.fer.zemris.java.sorters;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Razred koji sadrži implementaciju paraleliziranog quick sorta.
 * Pivot se odabire metodom medijan-od-tri.
 * @author Igor Smolkovič
 *
 */
public class QSortParallel2 {

	/**
	 * Prag za prekid rekurzije. Ako elemenata ima više od
	 * ovoga, quicksort nastavlja rekurzivnu dekompoziciju.
	 * U suprotnom ostatak sortira algoritmom umetanja.
	 */
	private final static int CUT_OFF = 20;

	/**
	 * Sučelje prema klijentu: prima polje i vraća se
	 * tek kada je polje sortirano. Primjenjujući gornje
	 * pragove najprije posao paralelizira, a kada posao
	 * postane dovoljno mali, rješava ga slijedno.
	 *
	 * @param array polje koje treba sortirati
	 */
	public static void sort(int[] array) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new QSortJob(array, 0, array.length - 1));
		pool.shutdown();
	}

	/**
	 * Model posla sortiranja podpolja čiju su elementi na pozicijama
	 * koje su veće ili jednake <code>startIndex</code> i manje
	 * ili jednake <code>endIndex</code>.
	 *
	 */
	static class QSortJob extends RecursiveAction {
		/**
		 * UID
		 */
		private static final long serialVersionUID = -696473393673056718L;
		private int[] array;
		private int startIndex;
		private int endIndex;

		/**
		 * Konstruktor.
		 * @param array polje za sortiranje
		 * @param startIndex indeks prvog elementa
		 * @param endIndex indeks posljednjeg elementa
		 */
		public QSortJob(int[] array, int startIndex, int endIndex) {
			super();
			this.array = array;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		protected void compute() {
			if (endIndex - startIndex + 1 < CUT_OFF) {
				this.computeDirect();
				return;
			}
			
			int p = selectPivot();
			swap(array, p, endIndex);
			int pivot = array[endIndex];
			int i = startIndex;
			int j = endIndex - 1;

			for (;;) {
				while (array[i] < pivot) { ++i; }
				while (array[j] > pivot) { --j; }
				if (i >= j) { break; }
				swap(array, i++, j--);
			}

			if (array[i] > pivot) {
				swap(array, i, endIndex);
			}

			if (startIndex < i) {
				invokeAll(new QSortJob(array, startIndex, i-1));
			}
			if (endIndex > i) {
				invokeAll(new QSortJob(array, i + 1, endIndex));
			}
		}
		
		/**
		 * Metoda koja obavlja sortiranje umetanjem.
		 */
		public void computeDirect() {
			for (int i = startIndex + 1; i <= endIndex; i++) {
				for (int j = i; j > 0 && array[j - 1] > array[j]; j--) {
					swap(array, j, j - 1);
				}
			}
		}

		/**
		 * Odabir pivota metodom medijan-od-tri u dijelu polja
		 * <code>array</code> koje je ograđeno indeksima
		 * <code>startIndex</code> i <code>endIndex</code>
		 * (oba uključena).
		 *
		 * @return vraća indeks na koj se nalazi odabrani pivot
		 */
		public int selectPivot() {
			int middle = (startIndex + endIndex) / 2;
			if (array[middle] < array[startIndex]) {
				swap(array, middle, startIndex);
			}
			if (array[endIndex] < array[startIndex]) {
				swap(array, endIndex, startIndex);
			}
			if (array[endIndex] < array[middle]) {
				swap(array, endIndex, middle);
			}
			return middle;
		}

		/**
		 * U predanom polju <code>array</code> zamjenjuje
		 * elemente na pozicijama <code>i</code> i <code>j</code>.
		 *
		 * @param array polje u kojem treba zamijeniti elemente
		 * @param i prvi indeks
		 * @param j drugi indeks
		 */
		public void swap(int[] array, int i, int j) {
			int value = array[j];
			array[j] = array[i];
			array[i] = value;
		}
	}

	/**
	 * Pomoćna metoda koja provjerava je li predano polje
	 * doista sortirano uzlazno.
	 *
	 * @param array polje
	 * @return <code>true</code> ako je, <code>false</code> inače
	 */
	public static boolean isSorted(int[] array) {
		for (int i = 1, end = array.length; i < end; i++) {
			if (array[i] < array[i - 1]) {
				return false;
			}
		}
		return true;
	}
}
