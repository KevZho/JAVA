package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModelListener;

import javax.swing.AbstractListModel;

/**
 * Razred koji implementira model za jList. Predstavlja objektni prilagodnik za
 * instance razreda DrawingModel.
 * @author Igor Smolkovič
 *
 */
public class DrawingObjectListModel extends
		AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Referenca na DrawingModel.
	 */
	private DrawingModel model;

	/**
	 * Konstruktor.
	 * @param model referenca na DrawingModel.
	 */
	public DrawingObjectListModel(DrawingModel model) {
		super();
		this.model = model;
	}

	/**
	 * UID
	 */
	private static final long serialVersionUID = -6464697730725614313L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}
}
