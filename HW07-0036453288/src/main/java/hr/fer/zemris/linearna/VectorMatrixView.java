package hr.fer.zemris.linearna;

/**
 * Razred <code>VectorMatrixView</code> omogućava gledanje na 
 * jedno retčanu/stupčanu matricu kao vektor.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class VectorMatrixView extends AbstractVector {

	private int dimension;
	private boolean rowMatrix;
	private IMatrix matrix;

	/**
	 * Konstruktor koji prima referencu na matricu.
	 *
	 * @param matrix Referenca na matricu.
	 */
	public VectorMatrixView(IMatrix matrix) {
		super();
		if (matrix.getColsCount() != 1 && matrix.getRowsCount() != 1) {
			throw new IncompatibleOperandException();
		}
		this.matrix = matrix;
		if (matrix.getColsCount() == 1) {
			rowMatrix = false;
			dimension = matrix.getRowsCount();
		} else {
			rowMatrix = true;
			dimension = matrix.getColsCount();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int index) {
		if (rowMatrix) {
			return matrix.get(0, index);
		}
		return matrix.get(index, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector set(int index, double value) throws UnmodifiableObjectException {
		if (rowMatrix) {
			matrix.set(0, index, value);
		} else {
			matrix.set(index, 0, value);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDimension() {
		return dimension;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector copy() {
		return new VectorMatrixView(matrix.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}
}
