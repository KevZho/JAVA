package hr.fer.zemris.web.radionice.test;


import hr.fer.zemris.web.radionice.InconsistentDatabaseException;
import hr.fer.zemris.web.radionice.Opcija;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testovi za bazu.
 * @author Igor Smolkovič
 *
 */
public class BazaTest {

	@Test
	public void test() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("./baza");
		Path dir = Files.createTempDirectory(Paths.get("./"), "baza2");
		try {
			baza.snimi(dir.toString());
		} catch (InconsistentDatabaseException ex) {
		}
		boolean iste = usporediDatoteke(
				Paths.get("./baza", "oprema.txt"),
				Paths.get(dir.toString(), "oprema.txt")
		);

		iste = usporediDatoteke(
				Paths.get("./baza", "publika.txt"),
				Paths.get(dir.toString(), "publika.txt")
		);

		iste = usporediDatoteke(
				Paths.get("./baza", "radionice_oprema.txt"),
				Paths.get(dir.toString(), "radionice_oprema.txt")
		);

		iste = usporediDatoteke(
				Paths.get("./baza", "radionice_publika.txt"),
				Paths.get(dir.toString(), "radionice_publika.txt")
		);

		iste = usporediDatoteke(
				Paths.get("./baza", "radionice.txt"),
				Paths.get(dir.toString(), "radionice.txt")
		);

		iste = usporediDatoteke(
				Paths.get("./baza", "trajanje.txt"),
				Paths.get(dir.toString(), "trajanje.txt")
		);
		try {
			for (File f : dir.toFile().listFiles())  {
				f.delete();
			}
			Files.delete(dir);
		} catch (DirectoryNotEmptyException ex) {
		}
		Assert.assertTrue(iste);
	}

	boolean usporediDatoteke(Path prva, Path druga) {
		BufferedReader brPrva = null;
		BufferedReader brDruga = null;
		try {
		brPrva = new BufferedReader(
				 new InputStreamReader(
				 new BufferedInputStream(
				 new FileInputStream(prva.toFile())),"UTF-8"));
		brDruga = new BufferedReader(
				 new InputStreamReader(
				 new BufferedInputStream(
				 new FileInputStream(druga.toFile())),"UTF-8"));
		while (true) {
			String a = brPrva.readLine();
			String b = brDruga.readLine();
			if (a == null && b == null) {
				return true;
			} else if (a == null || b == null) {
				return false;
			}
			if (a.compareTo(b) != 0) {
				return false;
			}
		}
		} catch (Exception ex) {
			return false;
		} finally {
			try { brPrva.close(); } catch (IOException ignorable) { }
			try { brDruga.close(); } catch (IOException ignorable) { }
		}
	}

	@Test
	public void test2() throws IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj("./baza");
		Radionica radionica = baza.dohvatiRadionicu(1L);
		radionica.getOprema().add(new Opcija("101", "USB stick"));
		Path dir = Files.createTempDirectory(Paths.get("./"), "baza2");
		boolean fail = false;
		try {
			baza.snimi(dir.toString());
		} catch (InconsistentDatabaseException ex) {
			fail = true;
		}
		try {
			for (File f : dir.toFile().listFiles())  {
				f.delete();
			}
			Files.delete(dir);
		} catch (DirectoryNotEmptyException ex) {
		}
		Assert.assertTrue(fail);
	}
}
