package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred <code>Vector</code> omogućava korištenje korištenje vektora.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Vector extends AbstractVector {

	/**
	 * Polje koje sadrži elemente vektora.
	 */
	private double[] elements;
	/**
	 * Zastavica koja označava da li se smije samo čitati.
	 */
	private boolean readOnly;
	/**
	 * Dimenzije vektora.
	 */
	private int dimension;

	/**
	 * Konstruktor razreda <code>Vector</code> koji prima samo polje
	 * elemenata koji čine taj vektor.
	 *
	 * @param elements Polje elemenata vektora.
	 */
	public Vector(double[] elements) {
		this(false, false, elements);
	}

	/**
	 * Konstruktor razreda <code>Vector</code>.
	 * @param readOnly Zastavica koja označava da vektor mora ostati nepromijenjiv.
	 * @param reuse Zastavica koja označava smilje li vektor zapamtiti referencu na
	 * 				 dobiveno polje ili treba provesti kopiranje.
	 * @param elements Polje koje sadrži elemente vektora.
	 * @throws IllegalArgumentException ako je predana null reference za polje elemenata.
	 */
	public Vector(boolean readOnly, boolean reuse, double[] elements) {
		if (elements == null) {
			throw new IllegalArgumentException("Vector data null reference");
		}
		this.readOnly = readOnly;
		this.dimension = elements.length;
		if (reuse) {
			this.elements = elements;
		} else {
			this.elements = Arrays.copyOf(elements, dimension);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Vector.get negative index");
		}
		if (index >= dimension) {
			throw new IndexOutOfBoundsException("Vector.get index >= dimension");
		}
		return this.elements[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector set(int index, double value) throws UnmodifiableObjectException {
		if (readOnly) {
			throw new UnmodifiableObjectException("Vector set");
		}
		if (index < 0) {
			throw new IndexOutOfBoundsException("Vector.set negative index");
		}
		if (index >= dimension) {
			throw new IndexOutOfBoundsException("Vector.set index >= dimension");
		}
		this.elements[index] = value;
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
		return new Vector(readOnly, false, elements);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector newInstance(int dimension) {
		return new Vector(new double[dimension]);
	}

	/**
	 * Metoda koja parsira dobiveni string i vraća vektor koji sadrži elemente
	 * koji su dobiveni iz stringa.
	 *
	 * @param s String koji sadrži elemente vektora.
	 * @return Vektor koji sadrži elemente dobivene iz stringa.
	 */
	public static Vector parseSimple(String s) {
		String[] parts = s.trim().split("\\s+");
		double[] array = new double[parts.length];
		for (int i = 0; i < parts.length; i++) {
			try {
				array[i] = Double.parseDouble(parts[i].trim());
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}
		return new Vector(array);
	}
}
