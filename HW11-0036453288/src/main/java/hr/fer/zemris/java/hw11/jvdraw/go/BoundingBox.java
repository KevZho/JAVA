package hr.fer.zemris.java.hw11.jvdraw.go;

/**
 * Razred koji implementira strukturu za traženje BoundinxBoxa.
 * @author Igor Smolkovič
 *
 */
public class BoundingBox {

	/**
	 * Min x.
	 */
	int minX;
	/**
	 * Min y.
	 */
	int minY;
	/**
	 * Max x.
	 */
	int maxX;
	/**
	 * Max y.
	 */
	int maxY;

	/**
	 * Konstruktor.
	 * @param minX početni minX.
	 * @param minY početni minY.
	 * @param maxX početni maxX.
	 * @param maxY početni maxY.
	 */
	public BoundingBox(int minX, int minY, int maxX, int maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/**
	 * Metoda koja dohvaća minX.
	 * @return minX.
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * Metoda koja postavlja novi minX. Postavljen je minimum trenutne vrijednost i nove.
	 * @param minX potencijalni novi minX.
	 */
	public void setMinX(int minX) {
		this.minX = Math.min(minX, this.minX);
	}

	/**
	 * Metoda koja dohvaća minY.
	 * @return minY.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Metoda koja postavlja novi minY. Postavljen je minimum trenutne vrijednosti i nove.
	 * @param minY potencijalni novi minY.
	 */
	public void setMinY(int minY) {
		this.minY = Math.min(this.minY, minY);
	}

	/**
	 * Metoda koja dohvaća maxX.
	 * @return maxX.
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * Metoda koja postavlja novi maxX. Postavljen je maksimum trenutne vrijednosti i nove.
	 * @param maxX potencijalni novi maxX.
	 */
	public void setMaxX(int maxX) {
		this.maxX = Math.max(maxX, this.maxX);
	}

	/**
	 * Metoda koja dohvaća maxY.
	 * @return maxY.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Metoda koja postavlja novi maxY. Postavljen je maksimum trenutne vrijednosti i nove.
	 * @param maxY potencijalni novi maxY.
	 */
	public void setMaxY(int maxY) {
		this.maxY = Math.max(this.maxY, maxY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("%d %d %d %d%n", minX, maxX, minY, maxY);
	}
}
