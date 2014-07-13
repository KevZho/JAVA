package hr.fer.zemris.java.tecaj.hw5.print;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.AlignmentValue;
import hr.fer.zemris.java.tecaj.hw5.IPrint;


/**
 * Razred <code>PrintName</code> omogućava dohvaćanje
 * imena objekta.
 * 
 * @author Igor Smolkovič
 *
 */
public class PrintName implements IPrint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(File f) {
		return f.getName();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AlignmentValue getAlignment() {
		return AlignmentValue.LEFT;
	}

}
