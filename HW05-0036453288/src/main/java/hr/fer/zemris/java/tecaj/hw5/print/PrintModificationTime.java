package hr.fer.zemris.java.tecaj.hw5.print;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.tecaj.hw5.AlignmentValue;
import hr.fer.zemris.java.tecaj.hw5.IPrint;

/**
 * Razred <code>PrintModifactionTime</code> dohvaća zadnje vrijeme
 * promjene objekta.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 * 
 */
public class PrintModificationTime implements IPrint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue(File f) {
		Date date = new Date(f.lastModified());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatiraniDatum = sdf.format(date);

		return formatiraniDatum;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AlignmentValue getAlignment() {
		return AlignmentValue.CENTERED;
	}

}
