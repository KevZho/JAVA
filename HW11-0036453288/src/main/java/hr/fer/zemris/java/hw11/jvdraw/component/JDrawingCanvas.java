package hr.fer.zemris.java.hw11.jvdraw.component;

import hr.fer.zemris.java.hw11.jvdraw.command.DrawCommand;
import hr.fer.zemris.java.hw11.jvdraw.go.BoundingBox;
import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.IColorProvider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * Razred koji implemenetira komponentu za crtanje geometrijskih objekata.
 * @author Igor Smolkovič
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5889530764580555089L;

	/**
	 * Pozicija prve točke na kojoj je bila akcija mouse click.
	 */
	private Point first;

	/**
	 * Pozicija druge točke na koju trenutno pokazuje miš ili je kliknuto.
	 */
	private Point second;

	/**
	 * Trenutni objekt koji se iscrtava.
	 */
	private GeometricalObject current;

	/**
	 * Referenca na trenutni drawing model;
	 */
	private DrawingModel model;

	/**
	 * Referenca na provider boje crte.
	 */
	private IColorProvider fgColor;

	/**
	 * Referenca na provider boje ispune.
	 */
	private IColorProvider bgColor;

	/**
	 * Id konačnog objekta.
	 */
	private int id = 0;

	/**
	 * Zastavica koja označava da je model mijenjan od posljednjeg spremanja.
	 */
	private boolean canvasChanged = false;

	/**
	 * Naredba koja se izvršava kada se odabere, linija, krug, ili obojani krug.
	 */
	private DrawCommand command; 

	/**
	 * Konstruktor.
	 * @param fgColor provider boje crte.
	 * @param bgColor provider boje ispune.
	 * @param model referenca na DrawingModel.
	 */
	public JDrawingCanvas(IColorProvider fgColor, IColorProvider bgColor, DrawingModel model) {
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.model = model;

		/**
		 * Generički mouselistener za sve objekte. Uvijek se zadaje početna točka prvim klikom i
		 * završna drugim klikom.
		 */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (command == null) { return; }
				if (first == null) {
					first = e.getPoint();
				} else {
					second = e.getPoint();
					createNewGeometricalObject(true);
				}
			}
		});

		/**
		 * Generički mousemotionlistener.
		 */
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (first == null) { return; }
				second = e.getPoint();
				createNewGeometricalObject(false);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (model != null) {
			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).draw(g, new BoundingBox(0, 0, 0, 0));
			}
		}
		if (current != null) {
			current.draw(g, new BoundingBox(0, 0, 0, 0));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		setCanvasChanged(true);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		setCanvasChanged(true);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		setCanvasChanged(true);
		repaint();
	}

	/**
	 * Metoda koja crta novi objekt na canvas. Ukoliko objekt nije konačan samo ga iscrtava i poziva repaint.
	 * Ako je konačan dodaje ga u model, a repaint se onda poziva automatski.
	 * @param finalObject zastavica koja označava da je objet konačan i da mora biti dodan u DrawingModel.
	 */
	public void createNewGeometricalObject(boolean finalObject) {
		int currentId = 0;
		if (finalObject) {
			currentId = id++;
		}
		current = command.createObject(
				first,
				second,
				fgColor.getCurrentColor(),
				bgColor.getCurrentColor(),
				currentId
		);
		if (finalObject) {
			model.add(current);
			resetCurrent();
		} else {
			repaint();
		}
	}

	/**
	 * Metoda koja postavlja novi tip objekta za crtanje 
	 * @param c refenca na objet koji zna instancirati trenutno odabarani geometrijski objekt.
	 */
	public void setCommand(DrawCommand c) {
		command = c;
		resetCurrent();
	}

	/**
	 * Metoda koja briše trenutni objekt za crtanje.
	 */
	public void resetCurrent() {
		first = null;
		second = null;
		current = null;
	}

	/**
	 * Metoda koja obavještava da li je model mijenjan od posljednjeg spremanja.
	 * @return true ako je model mijenjan, inače false.
	 */
	public boolean isCanvasChanged() {
		return canvasChanged;
	}

	/**
	 * Metoda koja mijenja zastavicu o tome da li je model mijenjan.
	 * @param canvasChanged nova vrijednost propertya canvasChanged.
	 */
	public void setCanvasChanged(boolean canvasChanged) {
		this.canvasChanged = canvasChanged;
	}
}
