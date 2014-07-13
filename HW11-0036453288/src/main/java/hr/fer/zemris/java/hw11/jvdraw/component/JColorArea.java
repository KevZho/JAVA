package hr.fer.zemris.java.hw11.jvdraw.component;

import hr.fer.zemris.java.hw11.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.IColorProvider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Razred koji implementira komponentu za odabir boje.
 * @author Igor Smolkovič
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -9168612548686012306L;

	/**
	 * Trenutno odabrana boja.
	 */
	private Color selectedColor;

	/**
	 * Lista promatrača.
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Dimenzije komponente.
	 */
	private Dimension dimension = new Dimension(15,15);

	/**
	 * Konstruktor koji prima početnu boju.
	 * @param initial početna boja.
	 */
	public JColorArea(Color initial) {
		selectedColor = initial;
		listeners = new ArrayList<>();

		/**
		 * Kada se klikne na komponentu otvori jColorChoser.
		 */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Chose color", Color.BLUE);
				if (newColor != null) {
					Color oldColor = selectedColor;
					selectedColor = newColor;
					repaint();
					fire(oldColor, newColor);
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Metoda koja dodaje promatrača l.
	 * @param l promatrač koji se dodaje.
	 * @throws IllegalArgumentException ako je listener null.
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Listener null reference.");
		}
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	/**
	 * Metoda koja briše promatrača l ako on postoji.
	 * @param l promatrač koji se briše.
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Metoda koja obavještava promatrače o novoj boji.
	 * @param oldColor stara boja.
	 * @param newColor nova boja.
	 */
	public void fire(Color oldColor, Color newColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, newColor);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(getInsets().left, getInsets().top, dimension.width, dimension.height);
	}
}
