package hr.fer.zemris.java.filechecking.syntax.node;

import hr.fer.zemris.java.filechecking.syntax.FCNodeVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Razred koji omogućava provjeru da li je datoteka zip formata.
 * @author Igor Smolkovič
 *
 */
public class ZIPFormat extends FormatNode {

	/**
	 * Konstruktor.
	 * @param errorMsg poruka o pogrešci. Argument je null ako ne postoji.
	 * @param program niz naredbi koje se izvršavaju ako je test bio uspješan. Argument je null
	 * 			ako ne postoje.
	 * @param invert zastavica koja označava invertira li se rezultat testa.
	 */
	public ZIPFormat(String errorMsg, ProgramNode program, boolean invert) {
		super(errorMsg, program, invert);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(FCNodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean acceptType(File f) {
		ZipFile zip = null;
		try {
			zip = new ZipFile(f);
		} catch (Exception e) { // Ako se dogodi bilo kakva greška nije dobra datoteka.
			return false;
		} finally {
			if(zip != null) {
				try { zip.close(); } catch (IOException ignorable) { }
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getFileContent(File f) {
		List<String> entriesList = new ArrayList<>();
		ZipFile zip = null;
		try {
			zip = new ZipFile(f);
			Enumeration <? extends ZipEntry> entries;
			for (entries = zip.entries(); entries.hasMoreElements();) {
				entriesList.add(entries.nextElement().getName());
			}
		} catch (ZipException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			if(zip != null) {
				try { zip.close(); } catch (IOException ignorable) { }
			}
		}
		return entriesList;
	}
}
