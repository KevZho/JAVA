package hr.fer.zemris.web.radionice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

/**
 * Razred koji implementira String verziju razreda Radionice.
 * @author Igor Smolkovič
 *
 */
public class RadionicaForm {

	/**
	 * Id radionice.
	 */
	private String id;

	/**
	 * Naziv radionice.
	 */
	private String naziv;

	/**
	 * Datum kada se održava.
	 */
	private String datum;

	/**
	 * Oprema koja se koristi.
	 */
	private Set<String> oprema;

	/**
	 * Trajanje radionce.
	 */
	private String trajanje;

	/**
	 * Tip/ tipovi publike.
	 */
	private Set<String> publika;

	/**
	 * Maksimalni broj polaznika [10, 50].
	 */
	private String maksPolaznika;

	/**
	 * Email.
	 */
	private String email;

	/**
	 * Dodatne informacije.
	 */
	private String dopuna;

	// Map<property,tekst-greske>
	Map<String, String> greske = new HashMap<String, String>();

	/**
	 * Maks duljina naziva.
	 */
	private static final int NAZIV_LENGTH = 40;

	/**
	 * Minimalan broj polaznika.
	 */
	private static final int POLAZNIKA_MIN = 10;

	/**
	 * Maksimalan broj polaznika.
	 */
	private static final int POLAZNIKA_MAX = 50;

	/**
	 * Konstruktor.
	 * @param id id radionice.
	 * @param naziv naziv radionice.
	 * @param datum datum održavanja radionice.
	 * @param oprema oprema koja se koristi na radionici.
	 * @param trajanje trajanje radionice.
	 * @param publika tipovi publike prisutne na radionici.
	 * @param maksPolaznika maksimalni broj polaznika.
	 * @param email email za kontakt.
	 * @param dopuna dodatne informacije o radionici.
	 */
	public RadionicaForm(String id, String naziv, String datum,
			Set<String> oprema, String trajanje, Set<String> publika,
			String maksPolaznika, String email, String dopuna) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.datum = datum;
		this.oprema = oprema;
		this.trajanje = trajanje;
		this.publika = publika;
		this.maksPolaznika = maksPolaznika;
		this.email = email;
		this.dopuna = dopuna;
	}

	/**
	 * Default konstruktor.
	 */
	public RadionicaForm() {
	}

	/**
	 * Metoda za dohvaćanje id-a.
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metoda za promjenu id-a.
	 * @param id novi id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Metoda za dohvaćanje naziva radionice.
	 * @return naziv radionice.
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Metoda za promjenu naziva radionice.
	 * @param naziv novi naziv radionice.
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Metoda za dohvaćanje datuma radionice.
	 * @return datum radionice.
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * Metoda za promjenu datuma radionice.
	 * @param datum novi datum radionice.
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * Metoda za dohvaćanje opreme koja se koristi na radionice.
	 * @return set identifikatora opreme koja se koristi na radionici.
	 */
	public Set<String> getOprema() {
		return oprema;
	}

	/**
	 * Metoda koja se koristi za promjenu opreme koja se koristi na radionici.
	 * @param oprema nova oprema koja se koristi na radionici.
	 */
	public void setOprema(Set<String> oprema) {
		this.oprema = oprema;
	}

	/**
	 * Metoda za dohvaćanje trajanja radionce.
	 * @return trajanje radionice.
	 */
	public String getTrajanje() {
		return trajanje;
	}

	/**
	 * Metoda za promjenu trajanja radionice.
	 * @param trajanje novi tip trajanja radionice.
	 */
	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	/**
	 * Metoda za dohvaćanje tipova publike koji su prisutni na radionici.
	 * @return set tipova publike prisutne na radionici.
	 */
	public Set<String> getPublika() {
		return publika;
	}

	/**
	 * Metod za promjenu tipova publike koja je prisutna na radionici.
	 * @param publika novi tipovi publike prisutni na radionici.
	 */
	public void setPublika(Set<String> publika) {
		this.publika = publika;
	}

	/**
	 * Metoda za dohvaćanje maksimalnog broja polaznika.
	 * @return maksimalni broj polaznika.
	 */
	public String getMaksPolaznika() {
		return maksPolaznika;
	}

	/**
	 * Metoda za promjenu maksimalnog broja polaznika.
	 * @param maksPolaznika novi maksimalni broj polaznika.
	 */
	public void setMaksPolaznika(String maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}

	/**
	 * Metoda za dohvaćanje emaila.
	 * @return email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda za promjenu emaila.
	 * @param email novi email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda za dohvaćanje dopune.
	 * @return dopuna.
	 */
	public String getDopuna() {
		return dopuna;
	}

	/**
	 * Metoda za promjenu dopune.
	 * @param dopuna nova dopouna.
	 */
	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}

	/**
	 * Metoda koja dohvaća pogrešku za traženo ime.
	 * @param ime ime za koje se dohvaća pogreške.
	 * @return pogreška za traženo ime.
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Ispituje da li postoje pogreške.
	 * @return <code>true</code> ako postoje pogreške, inače false.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Metoda koja ispituje da li postoji pogreška za traženo ime.
	 * @param ime ime za koje se isputuje postojanje pogreške.
	 * @return pogreška pridjeljenja traženom imenu.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Metoda koja uzima podatke iz dobivenog HttpRequesta.
	 * @param req request iz kojeg se uzimaju podaci.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.naziv = pripremi(req.getParameter("naziv"));
		this.datum = pripremi(req.getParameter("datum"));
		this.maksPolaznika = pripremi(req.getParameter("maksPolaznika"));
		this.trajanje = pripremi(req.getParameter("trajanje"));
		this.email = pripremi(req.getParameter("email"));
		this.dopuna = pripremi(req.getParameter("dopuna"));

		String[] opremaArray = req.getParameterValues("oprema");
		this.oprema = new HashSet<>();
		if (opremaArray != null) {
			for (int i = 0; i < opremaArray.length; i++) {
				this.oprema.add(pripremi(opremaArray[i]));
			}
		}

		String[] publikaArray = req.getParameterValues("publika");
		this.publika = new HashSet<>();
		if (publikaArray != null) {
			for (int i = 0; i < publikaArray.length; i++) {
				this.publika.add(pripremi(publikaArray[i]));
			}
		}
	}

	/**
	 * Metoda prilagođava dobiveni string. Osigurava da je minimalno
	 * što postoji prazan string.
	 * @param s string koji se priprema.
	 * @return prazan string ako je dobiven null, inače dobiveni string.
	 */
	private String pripremi(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	/**
	 * Metoda koja stvara RadinicaForm iz objekta Radionice.
	 * @param r radionica iz koje se uzimaju podaci.
	 */
	public void popuniIzRadionice(Radionica r) {
		if (r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		this.naziv = r.getNaziv();
		this.datum = r.getDatum();
		this.email = r.getEmail();
		this.dopuna = r.getDopuna();
		if (r.getMaksPolaznika() == null) {
			this.maksPolaznika = "";
		} else {
			this.maksPolaznika = r.getMaksPolaznika().toString();
		}
		if (r.getTrajanje() == null) {
			this.trajanje = "";
		} else {
			this.trajanje = r.getTrajanje().getId().toString();
		}
		this.oprema = new HashSet<>();
		this.publika = new HashSet<>();

		for (Opcija o : r.getOprema()) {
			this.oprema.add(o.getId().toString());
		}
		for (Opcija o : r.getPublika()) {
			this.publika.add(o.getId().toString());
		}
	}

	/**
	 * Metoda koja popunjava radionicu na temelju trenutnih podataka.
	 * @param r radionica u koju se premaju podaci.
	 * @param oprema mapa dostupne opreme.
	 * @param publika mapa dostupne publike.
	 * @param trajanje mapa dostuspnih trajanja.
	 */
	public void popuniURadionicu(Radionica r, Map<Long, Opcija> oprema, 
			Map<Long, Opcija> publika, Map<Long, Opcija> trajanje) {
		if (this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}

		/*
		 * Postavi jednostavne atribute. 
		 */
		r.setNaziv(this.naziv);
		r.setDatum(this.datum);
		r.setEmail(this.email);
		r.setDopuna(this.dopuna);
		r.setMaksPolaznika(Integer.parseInt(this.maksPolaznika));

		/*
		 * Postavi trajanje.
		 */
		Long trajanjeId = Long.valueOf(this.trajanje);
		r.setTrajanje(trajanje.get(trajanjeId));

		/*
		 * Postavi traženu opremu.
		 */
		Set<Opcija> opremaSet = new TreeSet<>();
		for (String o : this.oprema) {
			Long opremaId = Long.valueOf(o);
			opremaSet.add(oprema.get(opremaId));
		}
		r.setOprema(opremaSet);

		/*
		 * Postavi traženu publiku.
		 */
		Set<Opcija> publikaSet = new TreeSet<>();
		for (String o : this.publika) {
			Long publikaId = Long.valueOf(o);
			publikaSet.add(publika.get(publikaId));
		}
		r.setPublika(publikaSet);
	}

	/**
	 * Metoda koja provjerava ispravnost unesenih podataka.
	 */
	public void validiraj() {
		greske.clear();
		if (!this.id.isEmpty()) {
			try {
				long value = Long.parseLong(this.id);
				if (value < 1) {
					greske.put("id", "Vrijednost identifikatora mora biti pozitivna.");
				}
			} catch (NumberFormatException ex) {
				greske.put("id", "Vrijednost identifikatora nije valjana.");
			}
		}
		if (this.naziv.isEmpty()) {
			greske.put("naziv", "Naziv je obavezan");
		} else if (this.naziv.length() > NAZIV_LENGTH) {
			greske.put("naziv", "Naziv mora biti do 40 znakova");
		}
		if (this.datum.isEmpty()) {
			greske.put("datum", "Datum je obavezan");
		} else {
			if (!provjeriDatum(this.datum)) {
				greske.put("datum", "Format datuma: yyyy-mm-dd");
			}
		}
		if (this.trajanje.isEmpty()) {
			greske.put("trajanje", "Potrebno odabrati trajanje");
		}
		if (this.publika.isEmpty()) {
			greske.put("publika", "Potrebno je odabrati barem jedan tip publike");
		}
		if (this.maksPolaznika.isEmpty()) {
			greske.put("maksPolaznika", "Maksimalan broj polaznika mora biti zadan");
		} else {
			Integer value = Integer.valueOf(this.maksPolaznika);
			if (value < POLAZNIKA_MIN) {
				greske.put("maksPolaznika", "Minimalan broj polaznika je " + POLAZNIKA_MIN);
			}
			if (value > POLAZNIKA_MAX) {
				greske.put("maksPolaznika", "Maksimalan broj polaznika je " + POLAZNIKA_MAX);
			}
		}
		if (this.email.isEmpty()) {
			greske.put("email", "EMail je obavezan");
		} else {
			if (!provjeriEmail(this.email)) {
				greske.put("email", "Email nije ispravnog formata");
			}
		}
		/*
		 * Dopuna može biti i empty. Publika, Oprema i Trajanje se provjeravaju prilikom
		 * spremanja u bazu.
		 */
	}

	/**
	 * Metoda koja provjerava ispravnost emaila.
	 * @param email email koji se provjerava.
	 * @return <code>true</code> ako je ispravan, inače false.
	 */
	private boolean provjeriEmail(String email) {
		return email.matches(
				"^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
		);
	}

	/**
	 * Metoda koja provjerava ispravnost zadanog datuma radionice.
	 * Datum mora biti formata yyyy-mm-dd.
	 * @param datum datume koji se provjerava.
	 * @return <code>true</code> ako je ispravan, inače false.
	 */
	private boolean provjeriDatum(String datum) {
		return datum.matches("[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])");
	}
}
