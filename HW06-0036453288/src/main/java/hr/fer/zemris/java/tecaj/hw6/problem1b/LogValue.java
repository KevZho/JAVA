package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Promatrač <code>LogValue</code> omogućava zapisivanje svih promjena
 * vrijednost subjekta. Svaka promjena bilježi se u log datoteku. 
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LogValue implements IntegerStorageObserver {
	/**
	 * Sprema putanju do log datoteke.
	 */
	private Path path;
	
	/**
	 * Konstruktor razreda <code>LogValue</code> prima putanju do log
	 * datoteke.
	 *
	 * @param path Putanja do log datoteke.
	 */
	public LogValue(Path path) {
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (istorage == null) {
			throw new IllegalArgumentException("Null pointer");
		}
		Writer bw = null;
		try {
			// stvara writer za appendenje u datoteku
			bw = new BufferedWriter(
					 new OutputStreamWriter(
					 new BufferedOutputStream(
					 new FileOutputStream(path.toString(), true)), StandardCharsets.UTF_8));
			bw.write(String.valueOf(istorage.getCurrentValue()));
			bw.write("\r\n"); // jer write ne dodaje /n
		} catch (IOException e) {
			System.err.printf("Log file %s write error.", path.getFileName());
		} finally {
			try { bw.close(); } catch (Exception ignorable) { }
		}
	}
}
