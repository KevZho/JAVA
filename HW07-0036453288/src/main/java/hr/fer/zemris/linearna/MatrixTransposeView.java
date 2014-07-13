package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixTransposeView</code> omogućava transponiranje matrice
 * bez provođenja kopiranja.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	private IMatrix original;

	/**
	 * Konstruktor koji prima originalnu matricu. 
	 *
	 * @param original Originalna matrica.
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		return original.get(col, row);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		return original.set(col, row, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		return new MatrixTransposeView(original.copy());
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
	public double[][] toArray() {
		double[][] array = new double[this.getColsCount()][this.getRowsCount()];
		for (int i = 0; i < this.getColsCount(); i++) {
			for (int j = 0; j < this.getRowsCount(); j++) {
				array[i][j] = this.get(j, i);
			}
		}
		return array;
	}
}
