package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixSubMatrixView</code> omogućava uzimanje podmatrice
 * iz originalne bez provođenja kopiranja. Indeksiranje podmatrice kreće
 * od nula, a odgovarajući stupci u originalnoj matrici određuju se u 
 * trenutku dohvata/spremanja vrijednosti.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {

	private int[] rowIndex;
	private int[] collIndex;
	private IMatrix original;

	/**
	 * Privatni konstruktor koji prima originalnu matricu, polje redaka i stupaca
	 * koje podmatrica sadrži iz originalne.
	 *
	 * @param original Originalna matrica.
	 * @param rowIndex Polje redaka.
	 * @param collIndex Polje stupaca.
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rowIndex, int[] collIndex) {
		super();
		this.rowIndex = rowIndex;
		this.collIndex = collIndex;
		this.original = original;
	}

	/**
	 *
	 * @param original
	 * @param row
	 * @param col
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		this(original,
			 generateArray(original.getRowsCount(), row),
			 generateArray(original.getColsCount(), col)
		);
	}

	/**
	 * Metoda koja prima broj redaka/stupaca te generira polje indeksa
	 * koje sadrži sve moguće indeks bez rowCol.
	 *
	 * @param size Broj redaka/stupaca.
	 * @param rowCol Indeks retka/stupca koji se izbacuje.
	 * @return Polje indeksa bez rowCol.
	 */
	private static int[] generateArray(int size, int rowCol) {
		if (size <= 1) {
			throw new IllegalArgumentException();
		}
		int[] array = new int[size - 1];
		for (int i = 0, index = 0; i < size; i++) {
			if (i != rowCol) {
				array[index++] = i;
			}
		}
		return array;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return rowIndex.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return collIndex.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		if (row < 0 || row >= this.getRowsCount()) {
			throw new IndexOutOfBoundsException();
		}
		if (col < 0 || col >= this.getColsCount()) {
			throw new IndexOutOfBoundsException();
		}
		return original.get(rowIndex[row], collIndex[col]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row >= this.getRowsCount()) {
			throw new IndexOutOfBoundsException();
		}
		if (col < 0 || col >= this.getColsCount()) {
			throw new IndexOutOfBoundsException();
		}
		return original.set(rowIndex[row], collIndex[col], value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(original.copy(), rowIndex.clone(), collIndex.clone());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return original.newInstance(rows, cols);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(original, row, col);
		}
		return new MatrixSubMatrixView(original.copy(), row, col);
	}
}
