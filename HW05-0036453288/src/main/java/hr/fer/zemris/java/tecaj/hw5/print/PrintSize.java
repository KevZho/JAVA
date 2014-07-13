package hr.fer.zemris.java.tecaj.hw5.print;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.AlignmentValue;
import hr.fer.zemris.java.tecaj.hw5.IPrint;

/**
 * Razred <code>PrintSize</code> omogućava dohvaćanje
 * veličine objekta.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class PrintSize implements IPrint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(File f) {
		return String.valueOf(f.length());
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AlignmentValue getAlignment() {
		return AlignmentValue.RIGHT;
	}

}
