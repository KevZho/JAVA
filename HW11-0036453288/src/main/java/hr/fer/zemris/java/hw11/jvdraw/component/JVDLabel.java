package hr.fer.zemris.java.hw11.jvdraw.component;

import java.awt.Color;

import hr.fer.zemris.java.hw11.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.IColorProvider;

import javax.swing.JLabel;

/**
 * Razred koji implementira labelu koja ispisuje trenutnu boju crte i pozadine.
 * @author Igor Smolkovič
 *
 */
public class JVDLabel extends JLabel implements ColorChangeListener {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4257284718640714751L;

	/**
	 * Boja ispune.
	 */
	private Color bgColor;

	/**
	 * Boja crte.
	 */
	private Color fgColor;

	/**
	 * Referenca na provider boje crte.
	 */
	IColorProvider fgProvider;

	/**
	 * Referenca na provider boje ispune.
	 */
	IColorProvider bgProvider;

	/**
	 * Konstruktor.
	 * @param fgProvider referenca na provider boje crte.
	 * @param bgProvider referenca na provider boje ispune.
	 */
	public JVDLabel(IColorProvider fgProvider, IColorProvider bgProvider) {
		this.fgProvider = fgProvider;
		this.bgProvider = bgProvider;
		this.fgColor = fgProvider.getCurrentColor();
		this.bgColor = bgProvider.getCurrentColor();
		this.setText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source == fgProvider && fgColor == oldColor) {
			fgColor = newColor;
		}
		if (source == bgProvider && bgColor == oldColor) {
			bgColor = newColor;
		}
		setText();
	}

	/**
	 * Metoda koja postavlja text labele.
	 */
	public void setText() {
		String text = String.format("Foreground color: (%d, %d, %d), background color (%d, %d,%d)",
				fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(),
				bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
		super.setText(text);
	}
}
