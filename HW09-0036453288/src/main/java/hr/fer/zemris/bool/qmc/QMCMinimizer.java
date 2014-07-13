package hr.fer.zemris.bool.qmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

/**
 * Razred koji implementira QMC minimizator funkcija.
 * @author Igor Smolkovič
 * @version 1.0
 */
public final class QMCMinimizer {

	/**
	 * Zastavica koja označava želimo li kompletan ispis.
	 */
	private static final boolean DEBUG = false;

	/**
	 * Rješenja prvog koraka Quine-McCluskey postupka.
	 */
	private static Set<Mask> implicants;

	/**
	 * Mapa koja pamti minterme / maksterme koje pojedina maska može generirati.
	 */
	private static Map<Mask, Set<Integer>> mapper;

	/**
	 * Set početnih minterma/maksterma.
	 */
	private static Set<Integer> initial;

	/**
	 * Veličina maske.
	 */
	private static int maskSize;

	/**
	 * Tablica koja pamti koji implikanti pokrivaju koji minterm/maksterm.
	 */
	private static Map<Integer, List<MaskSet>> table;


	/**
	 * Metoda koja pokreće minimizaciju funkcije pomoću Quine-McCluskey postupka.
	 * Metoda vraća sve funkcije minimalnog oblika.Ako su minimizirani maxtermi onda
	 * se gledaju kao produkti suma, inače suma produkata.
	 *
	 * @param f funkcija koja se minimizira.
	 * @param minimizeMinterms minimiziraju li se mintermi ili makstermi.
	 * @return polje minimalnih oblika.
	 */
	public static MaskBasedBF[] minimize(BooleanFunction f, boolean minimizeMinterms) {
		/**
		 * Potrebno je počistiti svaki puta. Inače ostanu stare vrijednosti.
		 */
		maskSize = f.getDomain().size();
		table = new HashMap<>();
		initial = new HashSet<>();
		mapper = new HashMap<>();
		implicants = new HashSet<>();

		/** Inicijaliziraj QMC postupak. **/
		Map<Integer, Set<Mask>> elements = new HashMap<Integer, Set<Mask>>();
		for (Integer i : f.dontcareIterable()) {
			Mask m = Mask.fromIndex(maskSize, i.intValue());
			QMCUtil.addToMap(elements,  new Mask(m, true));
			mapper.put(m, new HashSet<>(Arrays.asList(i)));
		}

		Iterable<Integer> mintermsMaxterms = minimizeMinterms ? f.mintermIterable() : f.maxtermIterable();
		for (Integer i : mintermsMaxterms) {
			Mask m = Mask.fromIndex(maskSize, i.intValue());
			QMCUtil.addToMap(elements, new Mask(m, false));
			initial.add(i);
			mapper.put(m, new HashSet<>(Arrays.asList(i)));
		}

		if (DEBUG) {
			System.out.println("Početno: ");
			QMCUtil.print(elements);
		}

		/** prvi korak Quine-McCluskey postupka. **/
		findImplicants(elements, maskSize);

		/** pronađi primarne implikante **/
		List<Mask> primaryImplicants = findPrimaryImplicants();

		if (DEBUG) {
			System.out.println("Primarni implikanti:");
			QMCUtil.print(primaryImplicants);
		}

		/** Ako su svi pokriveni minimizacija je gotova. **/
		if (table.size() == 0) {
			return new MaskBasedBF[] {
					new MaskBasedBF("f", f.getDomain(), minimizeMinterms, primaryImplicants, new ArrayList<Mask>())
			};
		}

		/** Pronađi ostale minimalne implikante postupkom Pyne-McCluskey. **/
		List<List<Mask>> pyneImplicants = runPyneMcCluskey();

		if (DEBUG) {
			System.out.println("Minimalni članovi dobiveni u Pyne-McCluskey postupku:");
			QMCUtil.printList(pyneImplicants);
		}

		/** Zapiši rješenja i vrati ih. **/
		MaskBasedBF[] minForm = new MaskBasedBF[pyneImplicants.size()];
		for (int i = 0; i < pyneImplicants.size(); i++) {
			List<Mask> solution = new ArrayList<>(primaryImplicants);
			solution.addAll(pyneImplicants.get(i));
			minForm[ i ] = new MaskBasedBF("f" + i, f.getDomain(), minimizeMinterms, solution, new ArrayList<Mask>());
		}
		return minForm;
	}

	/**
	 * Metoda koja provodi prvi korak Quine-McCluskey postupka.
	 * @param elements mapa elemenata trenutne razine.
	 * @param maskSize veličina maske.
	 */
	private static void findImplicants(Map<Integer, Set<Mask>> elements, int maskSize) {
		Map<Integer, Set<Mask>> newElements = new HashMap<>(); /** prostor za spojene **/
		Set<Mask> marked = new HashSet<>(); /** hash vrijednosti onih maski koje su kombinirane **/

		/**
		 * Pogledajmo koji se mogu kombinirati. Zapamti one koji su bili kombinirani. Dodaj
		 * dobivene kombinacijom u newElements. Zapamti koje minterme/maksterme maska generira.
		 */
		for (int i = 0; i <= maskSize - 1; i++) {
			Set<Mask> first = elements.get(i);
			if (first == null) { continue; }
			for (int j = i + 1; j <= maskSize; j++) {
				Set<Mask> second = elements.get(j);
				if (second == null) { continue; }
				for (Mask m1 : first) {
					for (Mask m2 : second) {
						Mask combined = Mask.combine(m1, m2, m1.isDontCare() & m2.isDontCare());
						if (combined != null) {
							/** Označi da si spojio s nečim. **/
							marked.add(m1);
							marked.add(m2);

							/** Odredi koje minterme / maksterme generira **/
							Set<Integer> result = new HashSet<>(mapper.get(m1));
							result.addAll(mapper.get(m2));
							mapper.put(combined, result);

							/** Dodaj novi element u mapu **/
							QMCUtil.addToMap(newElements, combined);
						}
					}
				}
			}
		}

		/**
		 * Pogledaj koji nisu kombinirani. To su implikanti koji idu u potencijalne primarne.
		 */
		for (Map.Entry<Integer, Set<Mask>> mapEntry : elements.entrySet()) {
			for (Mask m : mapEntry.getValue()) {
				if (!marked.contains(m) && !m.isDontCare()) {
					implicants.add(m);
				}
			}
		}

		/**
		 * Ako si došao do kraja izađi, inače ideš na novu razinu.
		 */
		if (newElements.isEmpty()) { return; }

		if (DEBUG) {
			System.out.println("Sljedeći korak: ");
			QMCUtil.print(newElements);
		}

		findImplicants(newElements, maskSize);
	}


	/**
	 * Metoda koja traži primarne implikante. Traži one implikante koji jedini pokrivaju
	 * neki od minterma / maksterma.
	 *
	 * @return lista primarnih implikanata.
	 */
	private static List<Mask> findPrimaryImplicants() {
		Set<Mask> primaryImplicants = new HashSet<>();
		/** Generiraj tablicu preostalh minterma/maksterma. **/
		for (Integer i : initial) {
			List<MaskSet> list = new ArrayList<>();
			for (Mask m : implicants) {
				if (mapper.get(m).contains(i)) {
					list.add(new MaskSet(m));
				}
			}
			table.put(i, list);
		}

		/** Generiraj set primarnih implikanata. **/
		for (Map.Entry<Integer, List<MaskSet>> entry : table.entrySet()) {
			/** Ako minterm / maksterm pokriva samo jedan implikant. **/
			if (entry.getValue().size() == 1) {
				Set<Mask> set = entry.getValue().get(0).getValue();
				/** U nutra je sigurno 1. **/
				primaryImplicants.addAll(set);
			}
		}

		/** Pobriši one minterme/maksterme koje pokrivaju primarni implikanti. **/
		for (Mask m : primaryImplicants) {
			for (Integer i : mapper.get(m)) {
				table.remove(i);
			}
		}
		return new ArrayList<>(primaryImplicants);
	}

	/**
	 * Metoda koja provodi Pyne-McCluskey postupak. Metoda traži ostale implikante minimalne
	 * veličine. Ako se kao potencijalno rješenje pojavi više implikanata iste veličine onda
	 * među njima odabire one koji zahtijevaju najmanje varijabli.
	 * @return lista članova dobivena provođenjem postupka
	 */
	private static List<List<Mask>> runPyneMcCluskey() {
		List<List<Mask>> retValue = new ArrayList<>();
		/**
		 * Dok ne bude samo jedna suma pomnoži produkte, primjeni pravilo ekvivalencije.
		 */
		while (table.size() != 1) {
			Iterator<Entry<Integer, List<MaskSet>>> it = table.entrySet().iterator();
			int firstIndex = it.next().getKey();
			int secondIndex = it.next().getKey();

			/** Pomnoži prva dva. **/
			List<MaskSet> product = multiplySums(firstIndex, secondIndex);

			/** Obriši drugi implikant, a prvi zamijeni novim. **/
			table.remove(secondIndex);
			table.put(firstIndex, product);
		}

		/**
		 * Preostala je samo jedna suma. Pronađi one članove koji su najmanji i generiraj za
		 * svaki od njih novu listu.
		 */
		List<MaskSet> sum = table.entrySet().iterator().next().getValue();
		List<MaskSet> solution = findMinSolution(sum);
		for (MaskSet set : solution) {
			retValue.add(new ArrayList<Mask>(set.getValue()));
		}
		return retValue;
	}

	/**
	 * Metoda koja od članova dobivenih u Pyne-McCluskey postupku odabire one koji su najmanje
	 * veličine i sadrže najmanje varijabli.
	 * @param list lista izračunatih članova.
	 * @return list minimalnih članova.
	 */
	private static List<MaskSet> findMinSolution(List<MaskSet> list) {
		Set<MaskSet> sets = new HashSet<>();
		/** Pronađi najmanju veličinu člana. **/
		int min = list.get(0).getSize();
		for (int i = 1; i < list.size(); i++) {
			int current = list.get(i).getSize();
			min = Math.min(current, min);
		}
		/**
		 * Dodaj u set samo one članove koji imaju najmanju veličinu i potraži među njima
		 * najmanji broj varijabli potreban da bi se funkcija minimizirala.
		 */
		int minVaribles = 2 << maskSize;
		for (MaskSet m : list) {
			if (m.getSize() == min) {
				sets.add(m);
				minVaribles = Math.min(minVaribles, m.sumOfMasks());
			}
		}
		/** Uzmi samo one maske koje zahtijevaju najmanje varijabli. **/
		List<MaskSet> solution = new ArrayList<>();
		for (MaskSet m : sets) {
			if (m.sumOfMasks() == minVaribles) {
				solution.add(m);
			}
		}
		return solution;
	}

	/**
	 * Metoda koja provodi množenje suma produkata u Pyne-McCluskey postupku.
	 * @param firstIndex indeks prvog produkta.
	 * @param secondIndex indeks drugog produkta.
	 * @return rezultat množenja.
	 */
	private static List<MaskSet> multiplySums(int firstIndex, int secondIndex) {
		List<MaskSet> first = table.get(firstIndex);
		List<MaskSet> second = table.get(secondIndex);
		Set<MaskSet> retValue = new HashSet<>();

		for (MaskSet set1 : first) {
			for (MaskSet set2 : second) {
				MaskSet result = new MaskSet(set1);
				result.add(set2);
				retValue.add(result);
			}
		}
		return new ArrayList<MaskSet>(retValue);
	}


	/**
	 * Razred koji implementira korisne metode.
	 * @author Igor Smolkovič
	 *
	 */
	private static class QMCUtil {

		/**
		 * Metoda koja dodaje masku u mapu i odmah radi grupiranje po broju jedinica.
		 * @param map mapa u koju se dodaje.
		 * @param value maska koja se dodaje.
		 */
		public static void addToMap(Map<Integer, Set<Mask>> map, Mask value) {
			int key = value.getNumberOfOnes();
			if (!map.containsKey(key)) {
				map.put(key, new HashSet<Mask>());
			}
			map.get(key).add(value);
		}

		/**
		 * Metoda koja ispisuje sadržaj mape koja pamti minterme / maksterme.
		 * @param elements mapa iz koje se čita.
		 */
		public static void print(Map<Integer, Set<Mask>> elements) {
			for (Map.Entry<Integer, Set<Mask>> entry : elements.entrySet()) {
				print(entry.getValue());
				System.out.println("-----");
			}
		}

		/**
		 * Metoda koja ispisuje maske spremljene u setu i za svaku ispisuje koje
		 * minterme / maksterme generira.
		 * @param set set koji se ispisuje
		 */
		public static void print(Set<Mask> set) {
			for (Mask m : set) {
				System.out.println(m + " " + mapper.get(m));
			}
		}

		/**
		 * Metoda koja ispisuje maske zapisane u listi i za svaku ispisuje koje
		 * minterme / maksterme generira.
		 * @param list maski
		 */
		public static void print(List<Mask> list) {
			for (Mask m : list) {
				System.out.println(m + " " + mapper.get(m));
			}
		}

		/**
		 * Metoda koja ispisuje liste listi maski i za svaku ispisuje koje
		 * minterme / maksterme generira.
		 * @param listMasks lista listi maski
		 */
		public static void printList(List<List<Mask>> listMasks) {
			for (List<Mask> list : listMasks) {
				print(list);
				System.out.println("-----");
			}
		}
	}
}
