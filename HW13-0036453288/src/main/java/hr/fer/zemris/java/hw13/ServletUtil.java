package hr.fer.zemris.java.hw13;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pomoćni razred koji implementira korisne metode.
 *
 * @author Igor Smolkovič
 *
 */
public class ServletUtil {

	/**
	 * Metoda koja čita sadržaj datoteke i vraća listu pročitanih linija.
	 *
	 * @param fileName
	 *            putanja do datoteke koja se čita.
	 * @return lista pročitanih linija, ili null ako čitanje nije uspjelo.
	 */
	public static List<String> readLines(String fileName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (Exception ex) {
		}
		return lines;
	}

	/**
	 * Metoda koja spaja listu definicija i listu rezultata.
	 *
	 * @param def
	 *            lista definicija.
	 * @param rez
	 *            lista rezultata.
	 * @return spojena lista rezultata i definicija.
	 */
	public static List<Rezultati> dohvatiRezultate(List<String> def,
			List<String> rez) {
		Map<Integer, Integer> mapRez = new HashMap<>();
		List<Rezultati> rezultati = new ArrayList<>();

		try {
			for (String line : rez) {
				String[] parts = line.split("\t");
				mapRez.put(Integer.parseInt(parts[0].trim()),
						Integer.parseInt(parts[1].trim()));
			}
			for (String line : def) {
				String[] parts = line.split("\t");
				Integer id = Integer.parseInt(parts[0].trim());
				Integer votes = mapRez.get(id) != null ? mapRez.get(id) : 0;
				rezultati.add(new Rezultati(parts[1].trim(), parts[2].trim(), votes, id));
			}
		} catch (Exception e) {
		}
		return rezultati;
	}

	/**
	 * Razred koji implementira strukturu za pamčenje svih informacija o bendu.
	 *
	 * @author Igor Smolkovič
	 *
	 */
	public static class Rezultati {
		String name;
		String link;
		Integer votes;
		Integer id;

		/**
		 * Konstruktor.
		 *
		 * @param name
		 *            ime benda.
		 * @param link
		 *            link za download pjesme.
		 * @param votes
		 *            broj glasova.
		 * @param id
		 *            id benda.
		 */
		public Rezultati(String name, String link, Integer votes, Integer id) {
			super();
			this.name = name;
			this.link = link;
			this.votes = votes;
			this.id = id;
		}

		/**
		 * Metoda koja dohvaća ime benda.
		 *
		 * @return ime benda.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Metoda koja dohvaća link za download pjesme.
		 *
		 * @return link za download pjesme.
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Meoda koja dohvaća broj glasova.
		 *
		 * @return broj glasova.
		 */
		public Integer getVotes() {
			return votes;
		}

		/**
		 * Metoda koja dohvaća id benda.
		 *
		 * @return id benda.
		 */
		public Integer getId() {
			return id;
		}
	}
}
