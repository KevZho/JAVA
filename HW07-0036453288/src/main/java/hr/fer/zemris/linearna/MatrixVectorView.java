package hr.fer.zemris.linearna;

/**
 * Razred <code>MatrixVectorView</code> omogućava pretvaranje vektora u
 * jedno retčanu/stupčanu matricu bez samog kopiranja elemenata.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MatrixVectorView extends AbstractMatrix {

	private boolean asRowMatrix;
	private IVector vector;

	/**
	 * Konstruktor koji prima referencu na vektor te zastavicu da li se na vektor
	 * gleda kao stupčanu ili retčanu matricu.
	 * 
	 * @param asRowMatrix
	 * @param vector
	 */
	public MatrixVectorView(boolean asRowMatrix, IVector vector) {
		super();
		this.asRowMatrix = asRowMatrix;
		this.vector = vector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		if (asRowMatrix) {
			return vector.getDimension();
		}
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return 1;
		}
		return vector.getDimension();
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
		if (asRowMatrix) {
			return vector.get(row);
		}
		return vector.get(col);
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
		if (asRowMatrix) {
			vector.set(row, value);
		}
		vector.set(col, value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		return new MatrixVectorView(asRowMatrix, vector.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return LinAlgDefaults.defaultMatrix(rows, cols);
	}
}
