package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred <code>AbstractVector</code> sadrži implementacije metoda koje su
 * zajedničke svim vektorima.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public abstract class AbstractVector implements IVector {

	/**
	 * Epsilon za usporedbu sličnosti double brojeva.
	 */
	private static final double EPS = 1E-8;

	/**
	 * Default konstruktor razreda <code>AbstractVector</code>.
	 */
	public AbstractVector() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract double get(int index);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector set(int index, double value) throws UnmodifiableObjectException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getDimension();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector copy();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector copyPart(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Negative n.");
		}
		IVector retValue = this.newInstance(n);
		for (int i = 0, end = this.getDimension(); i < end; i++) {
			retValue.set(i, this.get(i));
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector newInstance(int dimension);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		IVector retValue = this.newInstance(this.getDimension());
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			retValue.set(i, this.get(i) + other.get(i));
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		IVector retValue = this.newInstance(this.getDimension());
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			retValue.set(i, this.get(i) - other.get(i));
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nScalarMultiply(double byValue) {
		IVector retValue = this.newInstance(this.getDimension());
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			retValue.set(i, this.get(i) * byValue);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double norm() {
		double sum = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			sum += this.get(i) * this.get(i);
		}
		return Math.sqrt(sum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector normalize() {
		double norm = this.norm();
		if (norm < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nNormalize() {
		double norm = this.norm();
		if (norm < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		IVector retValue = this.newInstance(this.getDimension());
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			retValue.set(i, this.get(i) / norm);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double aDotB = this.scalarProduct(other);
		double denum = this.norm() * other.norm();
		if (denum < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		return aDotB / denum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double scalarProduct(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double scalarProduct = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			scalarProduct += this.get(i) * other.get(i);
		}
		return scalarProduct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nVectorProduct(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != 3 || other.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}
		IVector v = this.newInstance(this.getDimension());
		v.set(0, this.get(1) * other.get(2) - this.get(2) * other.get(1));
		v.set(1, this.get(2) * other.get(0) - this.get(0) * other.get(2));
		v.set(2, this.get(0) * other.get(1) - this.get(1) * other.get(0));
		return v;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() < 2) {
			throw new IncompatibleOperandException();
		}
		double h = this.get(this.getDimension() - 1);
		if (h < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		IVector retValue = this.newInstance(this.getDimension() - 1);
		for (int i = 0, end = this.getDimension() - 1; i < end; i++) {
			retValue.set(i, this.get(i) / h);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(true, this);
		}
		return new MatrixVectorView(true, this.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(false, this);
		}
		return new MatrixVectorView(false, this.copy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] toArray() {
		double[] retValue = new double[this.getDimension()];
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			retValue[i] = this.get(i);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int dimension = this.getDimension();
		builder.append("(");
		for (int i = 0; i < dimension; i++) {
			builder.append(String.format("%.3f", this.get(i)));
			if (i < dimension - 1) {
				builder.append(", ");
			}
		}
		builder.append(")");
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
		if (!(obj instanceof IVector)) {
			return false;
		}
		IVector other = (IVector) obj;
		if (this.getDimension() != other.getDimension()) {
			return false;
		}
		for (int i = 0; i < this.getDimension(); i++) {
			if (Double.compare(this.get(i), other.get(i)) != 0) {
				return false;
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
