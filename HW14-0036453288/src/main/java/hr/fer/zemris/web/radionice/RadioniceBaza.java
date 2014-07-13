package hr.fer.zemris.web.radionice;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Razred koji implementira bazu podataka za radionice.
 * @author Igor Smolkovič
 *
 */
public class RadioniceBaza {

	/**
	 * Lokacija baze podataka.
	 */
	private String direktorij;

	/**
	 * Naziv datoteke s tipovima opreme.
	 */
	private static final String OPREMA_FILE = "oprema.txt";

	/**
	 * Naziv datoteke tipovima publike. 
	 */
	private static final String PUBLIKA_FILE = "publika.txt";

	/**
	 * Naziv datoteke s tipovima trajanja.
	 */
	private static final String TRAJANJE_FILE = "trajanje.txt";

	/**
	 * Naziv datoteke u kojoj su spremljene radionice.
	 */
	private static final String RADIONICE_FILE = "radionice.txt";

	/**
	 * Naziv datoteke u kojoj su spremljeni tipovi publike koje podržava radionica.
	 */
	private static final String RADIONICE_PUBLIKA_FILE = "radionice_publika.txt";

	/**
	 * Naziv datoteke u kojo je spremljena oprema koju podržava radionica.
	 */
	private static final String RADIONICE_OPREMA_FILE = "radionice_oprema.txt";

	/**
	 * Mapa koja pamti trenutne radionice.
	 */
	private Map<Long, Radionica> radionice;

	/**
	 * Mapa koja pamti tipove opreme.
	 */
	private Map<Long, Opcija> oprema;

	/**
	 * Mapa koja pamti tipove publike.
	 */
	private Map<Long, Opcija> publika;

	/**
	 * Mapa koja pamti tipove trajanja.
	 */
	private Map<Long, Opcija> trajanje;

	/**
	 * Konstruktor.
	 * @param direktorij direktorij u kojem se nalazi baza.
	 * @param radionice mapa s učitanim radionicama.
	 * @param oprema mapa s učitanom opremom.
	 * @param publika mapa s tipovima publike.
	 * @param trajanje mapa s tipovima trajanja.
	 */
	public RadioniceBaza(String direktorij, Map<Long, Radionica> radionice,
			Map<Long, Opcija> oprema, Map<Long, Opcija> publika,
			Map<Long, Opcija> trajanje) {
		super();
		this.direktorij = direktorij;
		this.radionice = radionice;
		this.oprema = oprema;
		this.publika = publika;
		this.trajanje = trajanje;
	}

	/**
	 * Get metoda za dohvaćanje radionica spremljenih u bazi.
	 * @return radionice spremljene u bazi.
	 */
	public Map<Long, Radionica> getRadionice() {
		return new TreeMap<>(radionice);
	}

	/**
	 * Metoda koja dohvaća radionicu.
	 * @param id id radionice koja se dohvaća.
	 * @return radionica na traženom id-u.
	 * @throws IllegalArgumentException ako je zatražena radionca koja ne postoji.
	 */
	public Radionica dohvatiRadionicu(Long id) {
		if (!radionice.containsKey(id)) {
			throw new IllegalArgumentException("Radionica sa traženim id-em ne postoji " + id);
		}
		return radionice.get(id);
	}

	/**
	 * Get metoda za dohvaćanje podržane opreme.
	 * @return podržana oprema.
	 */
	public Map<Long, Opcija> getOprema() {
		return new TreeMap<>(oprema);
	}

	/**
	 * Get metoda za dohvaćanje tipova publike.
	 * @return tipovi publike.
	 */
	public Map<Long, Opcija> getPublika() {
		return new TreeMap<>(publika);
	}

	/**
	 * Get metoda za dohvaćanje tipova trajanja.
	 * @return tipovi trajanja.
	 */
	public Map<Long, Opcija> getTrajanje() {
		return new TreeMap<>(trajanje);
	}

	/**
	 * Metoda koja učitava bazu iz datoteka.
	 * @param direktorij direktorij u kojem se nalazi baza.
	 * @return pročitana baza.
	 */
	public static RadioniceBaza ucitaj(String direktorij) {
		Map<Long, Radionica> radionice = new TreeMap<>();
		Map<Long, Opcija> oprema = new TreeMap<>();
		Map<Long, Opcija> publika = new TreeMap<>();
		Map<Long, Opcija> trajanje = new TreeMap<>();

		try {
			ucitajOpcije(direktorij, OPREMA_FILE, oprema);
			ucitajOpcije(direktorij, PUBLIKA_FILE, publika);
			ucitajOpcije(direktorij, TRAJANJE_FILE, trajanje);
			ucitajRadionice(direktorij, RADIONICE_FILE, radionice, oprema, publika, trajanje);
		} catch (IOException e) {
		}
		return new RadioniceBaza(direktorij, radionice, oprema, publika, trajanje);
	}

	/**
	 * Metoda koja učitava radionice iz datoteke.
	 * @param direktorij direktorij u kojem se nalazi datoteka s radionicama.
	 * @param datoteka datoteka iz koje se čitaju radionice.
	 * @param odrediste odredište na koje se sprema pročitani sadržaj.
	 * @param oprema mapa koja sadrži sve tipove opreme.
	 * @param publika mapa koja sadrži sve tipove publike.
	 * @param trajanje mapa koja sadrži sve tipove trajanja.
	 * @throws IOException ako je došlo do greške prilikom čitanja datoteke.
	 */
	private static void ucitajRadionice(String direktorij, String datoteka, Map<Long, Radionica> odrediste,
			Map<Long, Opcija> oprema, Map<Long, Opcija> publika, Map<Long, Opcija> trajanje) throws IOException {

		Map<Long, List<Long>> opremaMap = new HashMap<>();
		Map<Long, List<Long>> publikaMap = new HashMap<>();
		/**
		 * Ucitaj mapiranja radionice na tip publike i opreme.
		 */
		ucitajMapiranja(direktorij, RADIONICE_PUBLIKA_FILE, publikaMap);
		ucitajMapiranja(direktorij, RADIONICE_OPREMA_FILE, opremaMap);

		List<String> radioniceLines = procitajDatoteku(direktorij, datoteka);
		for (String radioniceLine : radioniceLines) {
			radioniceLine = radioniceLine.trim();
			if (radioniceLine.isEmpty()) { continue; }
			try {
				/**
				 * Parsiraj retke koje sadri datoteka radionice.txt
				 */
				String[] parts = radioniceLine.split("\t");
				Long id = Long.parseLong(parts[0]);
				String naziv = parts[1].trim();
				String datum = parts[2].trim();
				Integer maksPolaznika = Integer.parseInt(parts[3].trim());
				Long trajanjeId = Long.parseLong(parts[4].trim());
				String email = parts[5].trim();

				/**
				 * Ako dopuna sadrži escapane znakove vrati ih na originalne.
				 */
				String dopuna = "";
				if (parts.length != 6) {
					dopuna = parts[6].trim();
					dopuna = dopuna.replaceAll("\\\\n", "\n");
					dopuna = dopuna.replaceAll("\\\\t", "\t");
					dopuna = dopuna.replaceAll("\\\\\\\\", "\\");
				}

				/**
				 * Pronađi naziv opcije za traženi id.
				 */
				Opcija trajanjeOpcija = trajanje.get(trajanjeId);

				/**
				 * Pronađi opcije za radinicu - oprema i publika.
				 */
				Set<Opcija> opremaSet = pronadiOpcijeZaKljuc(id, opremaMap, oprema);
				Set<Opcija> publikaSet = pronadiOpcijeZaKljuc(id, publikaMap, publika);

				/**
				 * Zapamti stvorenu radionicu.
				 */
				odrediste.put(
						id, 
						new Radionica(id, naziv, datum, opremaSet, trajanjeOpcija,
									  publikaSet, maksPolaznika, email, dopuna)
				);
			} catch (NumberFormatException ex) {
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * Metoda koja za traženi ključ pronalazi sve opcije koje su mu pridružene.
	 * @param id ključ za koji se traže opcije.
	 * @param kljucevi mapa svih ključeva.
	 * @param opcije mapa svih opcija.
	 * @return set opcija koje su pridružene traženom ključu.
	 */
	private static Set<Opcija> pronadiOpcijeZaKljuc(Long id, Map<Long, List<Long>> kljucevi, Map<Long, Opcija> opcije) {
		Set<Opcija> set = new TreeSet<>();
		List<Long> list = kljucevi.get(id);
		if (list != null) {
			for (Long key : list) {
				set.add(opcije.get(key));
			}
		}
		return set;
	}

	/**
	 * Metoda koja učitava datoteke koje sadrže mapiranja radionice na publiku/ opremu.
	 * @param direktorij direktorij u kojem se nalazi datoteka.
	 * @param datoteka datoteka iz koje se čita.
	 * @param mapa mapa u koju se sprema rezultat čitanja.
	 * @throws IOException ako je došlo do greške prilikom čitanja datoteke.
	 */
	private static void ucitajMapiranja(String direktorij, String datoteka, Map<Long, List<Long>> mapa) throws IOException {
		List<String> lines = procitajDatoteku(direktorij, datoteka);
		for (String line : lines) {
			String[] parts = line.split("\t");
			Long first = Long.parseLong(parts[0].trim());
			if (!mapa.containsKey(first)) {
				mapa.put(first, new ArrayList<Long>());
			}
			Long second = Long.parseLong(parts[1].trim());
			mapa.get(first).add(second);
		}
	}

	/**
	 * Metoda koja čita sadržaj datoteke.
	 * @param direktorij direktorij u kojem je datoteka.
	 * @param datoteka datoteka iz koje se čita.
	 * @return lista pročitanih redaka.
	 * @throws IOException ako je došlo do greške prilikom čitanja datoteke.
	 */
	private static List<String> procitajDatoteku(String direktorij, String datoteka) throws IOException {
		Path file = Paths.get(direktorij, datoteka);
		if (!Files.exists(file)) {
			return new ArrayList<>();
		}
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		return lines;
	}

	/**
	 * Metoda koja ucitava datoteke s opcijama.
	 * @param direktorij direktorij u kojem se nalazi datoteka s opcijama.
	 * @param datoteka datoteka iz koje se čita.
	 * @param odrediste odrediste u koje se sprema parsirani sadrzaj.
	 * @throws IOException ako je došlo do greške prilikom čitanja.
	 */
	private static void ucitajOpcije(String direktorij, String datoteka, Map<Long, Opcija> odrediste) throws IOException {
		List<String> lines = procitajDatoteku(direktorij, datoteka);
		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) { continue; }
			String[] parts = line.split("\t");
			Long id = Long.parseLong(parts[0].trim());
			odrediste.put(id, new Opcija(parts[0].trim(), parts[1].trim()));
		}
	}

	/**
	 * Metoda koja sprema bazu u direktorij iz kojeg je pročitana.
	 */
	public void snimi() {
		snimi(direktorij);
	}

	/**
	 * Metoda koja ispituje ispravnost podataka koje sadrže radionice.
	 * @throws InconsistentDatabaseException ako podaci nisu ispravni.
	 */
	private void provjeriIspravnostOpcija() throws InconsistentDatabaseException {
		for (Entry<Long, Radionica> entry : radionice.entrySet()) {
			provjeriRadionicu(entry.getValue());
		}
	}

	/**
	 * Metoda koja provjerava ispravnost podataka dobivene radionice.
	 * @param r radionica koja se ispituje.
	 * @throws InconsistentDatabaseException ako podaci nisu ispravni.
	 */
	private void provjeriRadionicu(Radionica r) throws InconsistentDatabaseException {
		for (Opcija o : r.getOprema()) {
			if (!oprema.containsValue(o)) {
				throw new InconsistentDatabaseException(
						String.format("Oprema: %s nije podržana.", o.getVrijednost())
				);
			}
		}
		for (Opcija o : r.getPublika()) {
			if (!publika.containsValue(o)) {
				throw new InconsistentDatabaseException(
						String.format("Publika: %s nije podržana.", o.getVrijednost())
				);
			}
		}
		if (!trajanje.containsValue(r.getTrajanje())) {
			throw new InconsistentDatabaseException(
					String.format("Trajanje: %s nije podržano.", r.getTrajanje().getVrijednost())
			);
		}
	}

	/**
	 * Metoda koja sprema bazu u dobiveni direktorij.
	 * @param direktorij direktorij u koji se sprema baza.
	 */
	public void snimi(String direktorij) {
		/**
		 * Ako provjera nije uspjela izađi.
		 */
		provjeriIspravnostOpcija();
		/**
		 * Spremi bazu u predviđeni direktorij.
		 */
		try {
			snimiOpcije(direktorij, OPREMA_FILE, oprema);
			snimiOpcije(direktorij, PUBLIKA_FILE, publika);
			snimiOpcije(direktorij, TRAJANJE_FILE, trajanje);
			snimiRadinioce(direktorij, RADIONICE_FILE, radionice);
			snimiOpcijeRadionica(direktorij, RADIONICE_OPREMA_FILE, radionice, true);
			snimiOpcijeRadionica(direktorij, RADIONICE_PUBLIKA_FILE, radionice, false);
		} catch (IOException e) {
			System.err.println("Spremanje baze nije uspjelo");
		}
	}

	/**
	 * Metoda koja sprema opcije radinioce u datoteku (oprema / publika).
	 * @param direktorij direktorij u kojem se nalazi baza.
	 * @param datoteka datoteka u koju se zapisuje.
	 * @param radionica mapa iz koje se uzimaju podaci za zapisivanje.
	 * @param oprema true spremaju se oprema, inače publika.
	 * @throws IOException ako je došlo do greške prilikom zapisivanja u datoteku.
	 */
	private void snimiOpcijeRadionica(String direktorij,
			String datoteka, Map<Long, Radionica> radionice, boolean oprema) throws IOException {
		Path path = Paths.get(direktorij, datoteka);
		Writer bw = new BufferedWriter(
				 new OutputStreamWriter(
				 new BufferedOutputStream(
				 new FileOutputStream(path.toFile())),"UTF-8"));
		for (Entry<Long, Radionica> entry : radionice.entrySet()) {
			Set<Opcija> set = null;
			Long id = entry.getKey();
			if (oprema) {
				set = entry.getValue().getOprema();
			} else {
				set = entry.getValue().getPublika();
			}
			for (Opcija o : set) {
				String line = String.format("%d\t%s%n", id, o.getId());
				bw.write(line);
			}
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Metoda koja zapisuje podatke o radionici.
	 * @param direktorij direktorij u kojem je baza.
	 * @param datoteka datoteka u koju se zapisuje.
	 * @param radionice mapa radionica iz koje se uzimaju podaci.
	 * @throws IOException ako je došlo do greške prilikom zapisavanja u datoteku.
	 */
	private void snimiRadinioce(String direktorij, String datoteka,
			Map<Long, Radionica> radionice) throws IOException {
		Path path = Paths.get(direktorij, datoteka);
		Writer bw = new BufferedWriter(
				 new OutputStreamWriter(
				 new BufferedOutputStream(
				 new FileOutputStream(path.toFile())),"UTF-8"));

		for (Entry<Long, Radionica> entry : radionice.entrySet()) {
			Radionica r = entry.getValue();
			String line = stvoriZapisORadinici(r);
			bw.write(line);
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Metoda koja stvara zaspis o radionici koji se sprema u datoteku radionice.txt.
	 * @param r radionica za koju se stvara zapis.
	 * @return redak dobiven iz podataka radionice.
	 */
	public String stvoriZapisORadinici(Radionica r) {
		String dopuna = dopunaEscape(r.getDopuna());
		String line = String.format(
				"%d\t%s\t%s\t%d\t%s\t%s\t%s%n",
				r.getId(),
				r.getNaziv(),
				r.getDatum(),
				r.getMaksPolaznika(),
				r.getTrajanje().getId(),
				r.getEmail(),
				dopuna
		);
		return line;
	}

	/**
	 * Metoda koja escapa znakove \n, \t, i \\ u dopuni.
	 * @param dopuna dopuna koja se escapa.
	 * @return escapana dopuna.
	 */
	private String dopunaEscape(String dopuna) {
		StringBuilder builder = new StringBuilder(dopuna.length());
		for (int i = 0; i < dopuna.length(); i++) {
			char c = dopuna.charAt(i);
			if (c == '\r') { continue; }
			if (c == '\n') {
				builder.append("\\").append("n");
			} else if (c == '\t') {
				builder.append("\\").append("t");
			} else if (c == '\\') {
				builder.append("\\").append("\\");
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * Metoda koja sprema opcije u datoteku.
	 * @param direktorij direktorij u kojem se nalazi baza.
	 * @param datoteka datoteka u koju se sprema.
	 * @param opcije mapa opcija koje se spremaju.
	 * @throws IOException ako je došlo do greške prilikom zapisivanja u datoteku.
	 */
	private void snimiOpcije(String direktorij, String datoteka, Map<Long, Opcija> opcije) throws IOException {
		Path path = Paths.get(direktorij, datoteka);
		Writer bw = new BufferedWriter(
				 new OutputStreamWriter(
				 new BufferedOutputStream(
				 new FileOutputStream(path.toFile())),"UTF-8"));

		for (Entry<Long, Opcija> entry : opcije.entrySet()) {
			String line = String.format(
					"%s\t%s%n", entry.getValue().getId(), entry.getValue().getVrijednost()
			);
			bw.write(line);
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Metoda koja dodaje novu radionicu u bazu podataka.
	 * @param r radionica koja se sprema u bazu.
	 */
	public void snimi(Radionica r) {
		Long id = r.getId();
		/**
		 * Updejtanje postojećeg.
		 */
		if (id != null && radionice.containsKey(id)) {
			radionice.put(id, r);
			/**
			 * Snimi radionicu u postojeću bazu.
			 */
			snimi();
			return;
		}
		/**
		 * Nije postavljen id.
		 */
		if (id == null) {
			/**
			 * Pronađi jedinstveni id;
			 */
			id = Long.valueOf(radionice.size() + 1);
			while (true) {
				if (radionice.containsKey(id)) {
					id++;
				} else {
					break;
				}
			}
			r.setId(id);
			radionice.put(id, r);
		} else {
			/**
			 * Postavljen je id, ali takav ne postoji već u bazi.
			 */
			radionice.put(id, r);
		}

		/**
		 * Snimi radionicu u postojeću bazu.
		 */
		snimi();
	}
}
