package hr.fer.zemris.java.hw11.jvdraw.interfaces;

/**
 * Sučelje koje moraju implementira promatrači subjekta tipa DrawingModel.
 * @author Igor Smolkovič
 *
 */
public interface DrawingModelListener {

	/**
	 * Metoda koja se poziva kada je objekt dodan.
	 * @param source referenca na drawing model.
	 * @param index0 indeks od kojeg su ubačeni objekti.
	 * @param index1 indeks do kojeg su ubačeni objekti.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Metoda koja se poziva kada je objekt obrisan.
	 * @param source referenca na drawing model.
	 * @param index0 indeks od kojeg su obrisani objekti.
	 * @param index1 indeks do kojeg su obrisani objekti.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Metoda koja se poziva kada je objekt promijenjen.
	 * @param source referenca na drawing model.
	 * @param index0 indeks od kojeg su promijenjeni objekti.
	 * @param index1 indeks do kojeg su promijenjeni objekti.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
