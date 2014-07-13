package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred <code>AbstractMatrix</code> sadrži implementacije metoda koje su
 * zajedničke svim matricama.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	private static final double ONE = 1.0;
	private static final double ZERO = 0.0;
	/**
	 * Epsilon za usporedbu sličnočnosti double brojeva.
	 */
	private static final double EPS = 1E-8;

	/**
	 * Default konstruktroktor razreda <code>AbstractMatrix</code>.
	 */
	public AbstractMatrix() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getRowsCount();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getColsCount();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract double get(int row, int col);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix set(int row, int col, double value);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix copy();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix newInstance(int rows, int cols);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		}
		return new MatrixTransposeView(this.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix add(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nAdd(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		IMatrix m = this.newInstance(this.getRowsCount(), this.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				m.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return m;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nSub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		IMatrix m = this.newInstance(this.getRowsCount(), this.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				m.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return m;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = this.getRowsCount();
		int cols = other.getColsCount();
		IMatrix m = this.newInstance(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double sum = 0;
				for (int k = 0; k < rows; k++) {
					sum += this.get(i, k) * other.get(k, j);
				}
				m.set(i, j, sum);
			}
		}
		return m;
	}

	/**
	 * {@inheritDoc}
	 * @see <a href="http://en.wikipedia.org/wiki/Determinant">Determinat</a>
	 * @see <a href="http://en.wikipedia.org/wiki/Laplace_expansion">Laplace_expansion</a>
	 * @see <a href="http://vikparuchuri.com/blog/find-the-determinant-of-a-matrix/">Algorithm</a>
	 *
	 */
	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("ColsCount != RowsCount");
		}
		double det = 0;
		if (this.getColsCount() == 1) {
			det = this.get(0, 0);
		} else if (this.getColsCount() == 2) {
			det = this.get(0, 0) * this.get(1, 1) - this.get(0, 1) * this.get(1, 0);
		} else if (this.getColsCount() == 3) {
			det += this.get(0, 0) * (this.get(1, 1) * this.get(2, 2) - this.get(1, 2) * this.get(2, 1));
			det	-= this.get(0, 1) * (this.get(1, 0) * this.get(2, 2) - this.get(2, 0) * this.get(1, 2));
			det	+= this.get(0, 2) * (this.get(1, 0) * this.get(2, 1) - this.get(1, 1) * this.get(2, 0));
		} else {
			for (int j = 0; j < this.getColsCount(); j++) {
				double signum = Math.pow(-1, j);
				det += this.get(0, j) * signum * this.subMatrix(0, j, true).determinant();
			}
		}
		return det;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this, row, col);
		}
		return new MatrixSubMatrixView(this.copy(), row, col);
	}
	
	/**
	 * {@inheritDoc}
	 * @see <a href="http://www.homepages.ucl.ac.uk/~ucahjbu/Cofactor.ppt">Cofactor invert</a>
	 */
	@Override
	public IMatrix nInvert() {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("ColsCount != RowsCount");
		}
		IMatrix m  = this.newInstance(this.getRowsCount(), this.getColsCount());
		double det = this.determinant();
		if (Math.abs(det) < EPS) {
			throw new ArithmeticException("Singularna matrica.");
		}
		for (int i = 0; i < this.getRowsCount(); i++) {
			for (int j = 0; j < this.getColsCount(); j++) {
				double signum = Math.pow(-1, i + j);
				double localDet = this.subMatrix(i, j, true).determinant();
				m.set(i, j, signum * localDet / det);
			}
		}
		return m.nTranspose(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[][] toArray() {
		double[][] array = new double[this.getRowsCount()][this.getColsCount()];
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				array[i][j] = this.get(i, j);
			}
		}
		return array;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector toVector(boolean liveView) {
		if (liveView) {
			return new VectorMatrixView(this);
		}
		return new VectorMatrixView(this.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix scalarMultiply(double value) {
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nScalarMultiply(double value) {
		IMatrix m = this.newInstance(this.getRowsCount(), this.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount(); j >= 0; j--) {
				m.set(i, j, this.get(i, j) * value);
			}
		}
		return m;
	}

	/**
	 * {@inheritDoc}
	 * @throws IncompatibleOperandException ako matrica nije kvadratna.
	 */
	@Override
	public IMatrix makeIdentity() {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				if (i == j) {
					this.set(i, j, ONE);
				} else {
					this.set(i, j, ZERO);
				}
			}
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for (int i = 0; i < rows; i++) {
			builder.append("[");
			for (int j = 0; j < cols; j++) {
				builder.append(String.format("%.3f", this.get(i, j)));
				if (j < cols - 1) {
					builder.append(", ");
				}
			}
			builder.append("]\n");
		}
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IMatrix)) {
			return false;
		}
		IMatrix other = (IMatrix) obj;
		if (this.getColsCount() != other.getColsCount()) {
			return false;
		}
		if (this.getRowsCount() != other.getRowsCount()) {
			return false;
		}
		for (int i = 0; i < this.getRowsCount(); i++) {
			for (int j = 0; j < this.getColsCount(); j++) {
				if (Double.compare(this.get(i, j), other.get(i, j)) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.toArray());
	}
}
