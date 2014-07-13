package hr.fer.zemris.java.tecaj.hw5.print;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.AlignmentValue;
import hr.fer.zemris.java.tecaj.hw5.IPrint;

/**
 * Razred <code>PrintHiden</code> dohvaća informaciju o tome
 * da li je objekt skriven.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class PrintHiden implements IPrint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(File f) {
		if(f.isHidden()) {
			return "h";
		}
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AlignmentValue getAlignment() {
		return AlignmentValue.CENTERED;
	}

}
