package hr.fer.zemris.java.tecaj.hw5.print;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.AlignmentValue;
import hr.fer.zemris.java.tecaj.hw5.IPrint;

/**
 * Razred <code>PrintType</code> dohvaća tip objekta. 
 * Vraća f za datoteke, d za direktorije.
 * 
 * @author Igor Smolkovič
 *
 */
public class PrintType implements IPrint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(File f) {
		if(f.isDirectory()) {
			return "d";
		}
		return "f";
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AlignmentValue getAlignment() {
		return AlignmentValue.CENTERED;
	}

}
