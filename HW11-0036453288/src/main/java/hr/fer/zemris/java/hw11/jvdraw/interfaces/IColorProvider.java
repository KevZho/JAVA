package hr.fer.zemris.java.hw11.jvdraw.interfaces;

import java.awt.Color;

/**
 * Sučelje koje implementiraju svi subjekti koji pamte trenutno postavlje
 * boje.
 * @author Igor Smolkovič
 *
 */
public interface IColorProvider {

	/**
	 * Metoda koja vraća trenutnu boju.
	 * @return trenutna boja.
	 */
	public Color getCurrentColor();
}
