package hr.fer.zemris.java.hw11.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModelListener;

/**
 * Razred koji sadrži implementraciju sučelja <code>DrawingModel</code>.
 * @author Igor Smolkovič
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * Lista geometrijskih objekata.
	 */
	private List<GeometricalObject> list;

	/**
	 * Lista promatrača.
	 */
	private List<DrawingModelListener> listeners;

	/**
	 * Konstruktor.
	 */

	public DrawingModelImpl() {
		list = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= getSize()) {
			throw new IndexOutOfBoundsException("index < 0 || index >= size");
		}
		return list.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		if (object == null) {
			throw new IllegalArgumentException("Null reference.");
		}
		int index = list.size();
		list.add(object);
		fireIntevalAdded(index, index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Null reference.");
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Metoda koja obavještava promatrače kada su dodani novi objekti na intevalu
	 * index0 do index1.
	 * @param index0 indeks gdje je dodan prvi objekt.
	 * @param index1 indeks gdje je dodan zadnji objekt.
	 */
	public void fireIntevalAdded(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index0, index1);
		}
	}

	/**
	 * Metoda koja obavještava promatrače kada su izmjenjeni objekta na intevalu
	 * index0 do index1.
	 * @param index0 indeks prvog promatrača koji je izmijenjen.
	 * @param index1 indeks zadnjeg promatrača koji je izmijenjen.
	 */
	public void fireIntervalChanged(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index0, index1);
		}
	}

	/**
	 * Metoda koja obavještava promatrače kada su obrisani objekti na intevalu
	 * index0 do index1.
	 * @param index0 indeks prvog objekta koji je obrisan.
	 * @param index1 indeks posljednje objekta koji je obrisan.
	 */
	public void fireIntevalRemoved(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index0, index1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObject(int index) {
		if (index < 0 || index >= list.size()) {
			return;
		}
		list.remove(index);
		fireIntevalRemoved(index, index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateObject(int index) {
		if (index < 0 || index >= list.size()) {
			return;
		}
		list.get(index).update();
		fireIntervalChanged(index, index);
	}
}
