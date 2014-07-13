package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.fimpl.iterable.ListGenerator;
import hr.fer.zemris.bool.opimpl.BooleanOperatorAND;
import hr.fer.zemris.bool.opimpl.BooleanOperatorNOT;
import hr.fer.zemris.bool.opimpl.BooleanOperatorOR;

/**
 * Razred <code>IndexedBF</code>.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class IndexedBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private boolean indexesAreMinterms;
	private List<Integer> indexes;
	private List<Integer> dontCares;
	
	
	/**
	 * Konstruktor razreda <code>IndexedBF</code>.
	 * 
	 * @param name Ime funkcije.
	 * @param domain Domena funkcije.
	 * @param indexesAreMinterms true - indexi su mintermi, inače su makstermi.
	 * @param indexes Lista indexa.
	 * @param dontCares Lista dontCareova.
	 * @throws IllegalArgumentException ako je došlo do greške.
	 */
	public IndexedBF(String name, List<BooleanVariable> domain,
			boolean indexesAreMinterms, List<Integer> indexes,
			List<Integer> dontCares) {
		
		if(name.isEmpty()) {
			String msg = "IndexedBF empty name";
			throw new IllegalArgumentException(msg);
		}
		
		if(domain == null || indexes == null || dontCares == null) {
			String msg = "IndexedBF null reference";
			throw new IllegalArgumentException(msg);
		}
	
		int max = (int) Math.pow(2, domain.size());
		if(!checkIndexValues(indexes, max) || !checkIndexValues(dontCares, max)) {
			String msg = "IndexedBF indexValue > max || < 0";
			throw new IllegalArgumentException(msg);
		}
		
		Set<Integer> sIndex = new HashSet<>(indexes);
		Set<Integer> sDont = new HashSet<>(dontCares);
		
		if(sIndex.removeAll(sDont)) {
			String msg = "IndexedBF illegal indexes";
			throw new IllegalArgumentException(msg);
		}
		
		this.name = name;
		this.domain =  new ArrayList<BooleanVariable>(domain);
		this.indexesAreMinterms = indexesAreMinterms;
		this.indexes = new ArrayList<Integer>(indexes) ;
		this.dontCares = new ArrayList<Integer>(dontCares);
	}

	
	/**
	 * Metoda koja provjerava da li je lista indexa ispravna.
	 * 
	 * @param indexes Lista indeksa koja se provjerava.
	 * @param max Maksimalna vrijednost indexa.
	 * @return true ako je lista ispravna, inače false.
	 */
	private boolean checkIndexValues(List<Integer> indexes, int max) {
		for(Integer i : indexes) {
			if(i < 0 || i > max) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Overridana metoda vraća ime funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	
	/**
	 * Overridana metoda koja vraća vrijednost funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {
		int index = FunctionUtil.convertInputToInt(getDomain());
		if(index != -1) {
			if(hasMinterm(index)) {
				return BooleanValue.TRUE;
			}
			else if(hasMaxterm(index)) {
				return BooleanValue.FALSE;
			}
			else {
				return BooleanValue.DONT_CARE;
			}
		}
		
		return indexesAreMinterms ? calculcateMintermsSum() : 
				calcucalteMaxtermsProduct();
	}

	
	/**
	 * Metoda koja računa sumu minterna iz vrijednosti koje 
	 * se nalaze u domeni funkcije. Metoda osigurava nepromijenjenost
	 * varijabli domene.
	 * 
	 * @return Rezultat računanja sume minterma za postavljene vrijednosti
	 * 			u domeni funkcije.
	 */
	private BooleanValue calculcateMintermsSum() {
		List<BooleanSource> sum = new ArrayList<>();
		
		for(Integer value : indexes) {
			String binaryString = FunctionUtil.convertToBinaryString(
					domain.size(),
					value
			);
			
			List<BooleanSource> minterm = new ArrayList<>();
			for(int i = 0; i < binaryString.length(); i++) {
				if(domain.get(i).getValue() == BooleanValue.DONT_CARE) {
					continue;
				}
				if(binaryString.charAt(i) == '0') {
					minterm.add(new BooleanOperatorNOT(domain.get(i)));
				}
				else {
					minterm.add(domain.get(i));
				}
			}
			sum.add(new BooleanOperatorAND(minterm));
		}
			
		return new BooleanOperatorOR(sum).getValue();
	}
	
	
	/**
	 * Metoda koja računa produkt maksterma iz vrijednosti koje
	 * se nalaze u domenu funkcije. Funkcija osigurava nepromjenjenost
	 * varijabli domene.
	 * 
	 * @return Rezultat izračunog produkta makstrema za vrijednosti postavljene
	 * 			u domeni funkcije.
	 */
	private BooleanValue calcucalteMaxtermsProduct() {
		List<BooleanSource> product = new ArrayList<>();
		
		for(Integer value : indexes) {
			String binaryString = FunctionUtil.convertToBinaryString(
					domain.size(),
					value
			);
			
			List<BooleanSource> maxterm = new ArrayList<>();
			
			for(int i = 0; i < binaryString.length(); i++) {
				if(domain.get(i).getValue() == BooleanValue.DONT_CARE) {
					continue;
				}
				if(binaryString.charAt(i) == '1') {
					maxterm.add(new BooleanOperatorNOT(domain.get(i)));
				}
				else {
					maxterm.add(domain.get(i));
				}
			}
			product.add(new BooleanOperatorOR(maxterm));
		}
			
		return new BooleanOperatorAND(product).getValue();
	}
	
	
	/**
	 * Overridana metoda koja vraća domenu funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(domain);
	}

	
	/**
	 * Overridana metoda koja ispituje postoji li minterm i.
	 *
	 * {@inheritDoc} 
	 */
	@Override
	public boolean hasMinterm(int i) {
		Integer value = Integer.valueOf(i);
		if(indexesAreMinterms == true) {
			return indexes.contains(value);
		}
		
		return !dontCares.contains(value) && !indexes.contains(value);
	}

	
	/**
	 * Overridana metoda koja ispituje postoji li maksterm i.
	 *
	 * {@inheritDoc} 
	 */
	@Override
	public boolean hasMaxterm(int i) {
		Integer value = Integer.valueOf(i);
		if(indexesAreMinterms == false) {
			return indexes.contains(value);
		}
		
		return !dontCares.contains(value) && !indexes.contains(value);
	}

	
	/**
	 * Overridana metoda koja ispituje postoji li don't care i.
	 *
	 * {@inheritDoc} 
	 */
	@Override
	public boolean hasDontCare(int i) {
		return dontCares.contains(Integer.valueOf(i));
	}

	
	/**
	 * Overridana metoda mintermIterable koja vraća instancu razreda 
	 * koji omogućava iteriranje po mintermima.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<Integer> mintermIterable() {
		List<Integer> list = ListGenerator.getMinterms(this);
		return list;
	}


	/**
	 * Overridana metoda mintermIterable koja vraća instancu razreda 
	 * koji omogućava iteriranje po makstermima.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<Integer> maxtermIterable() {
		List<Integer> list = ListGenerator.getMaxterms(this);
		return list;
	}


	/**
	 * Overridana metoda mintermIterable koja vraća instancu razreda 
	 * koji omogućava iteriranje po don't careovima.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<Integer> dontcareIterable() {
		List<Integer> list = ListGenerator.getDontcares(this);
		return list;
	}

}
