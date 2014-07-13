package hr.fer.zemris.java.hw11.jvdraw.interfaces;

import java.awt.Color;

/**
 * Sučelje koje implementiraju svi promatrači od subjekta koji je tipa
 * IColorProvider.
 * @author Igor Smolkovič
 *
 */
public interface ColorChangeListener {

	/**
	 * Metoda koja se poziva kad je došlo do promjene boje pozadine ili boje crte.
	 * @param source subjekt kod kojeg je došlo do promjene podataka.
	 * @param oldColor stara boja.
	 * @param newColor nova boja.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
