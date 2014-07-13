package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;
import hr.fer.zemris.bool.fimpl.iterable.ListGenerator;
import hr.fer.zemris.bool.opimpl.BooleanOperatorAND;
import hr.fer.zemris.bool.opimpl.BooleanOperatorNOT;
import hr.fer.zemris.bool.opimpl.BooleanOperatorOR;

/**
 * Razred <code>MaskBasedBF</code>.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MaskBasedBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private boolean masksAreMinterms;
	private List<Mask> masks;
	private List<Mask> dontCares;
	
	
	/**
	 * Konstruktor razreda <code>MaskBasedBF</code>.
	 * 
	 * @param name Ime funkcije.
	 * @param domain Domena funkcije.
	 * @param masksAreMinterms Zastavica da li su maske mintermi.
	 * @param masks Lista maski koja sadrži minterme / masterme.
	 * @param dontCares Lista maski koja sadrži don't careove.
	 * @throws IllegalArgumentException ako parametri nisu ispravni.
	 */
	public MaskBasedBF(String name, List<BooleanVariable> domain,
			boolean masksAreMinterms, List<Mask> masks, 
			List<Mask> dontCares) {
		
		if(name.isEmpty()) {
			String msg = "MaskBasedBF empy string";
			throw new IllegalArgumentException(msg);
		}
		
		if(domain == null || masks == null || dontCares == null) {
			String msg = "MaskBasedBF null reference";
			throw new IllegalArgumentException(msg);
		}
		
		this.name = name;
		this.domain = new ArrayList<BooleanVariable>(domain);
		
		
		if(!checkMasks(masks) || !checkMasks(dontCares)) {
			String msg = "MaskBasedBF illegal mask length";
			throw new IllegalArgumentException(msg);
		}
		
		this.masksAreMinterms = masksAreMinterms;
		this.masks =  new ArrayList<Mask>(masks);
		this.dontCares = new ArrayList<Mask>(dontCares);
	}

	
	private boolean checkMasks(List<Mask> listMask) {
		for(Mask m : listMask) {
			if(m.getSize() != domain.size()) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Overridana metoda koja vraća ime funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	
	/**
	 * Overridana metoda koja vraća vrijednost funkcije za trenutno
	 * postavljanje vrijednosti u domeni.
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
		
		return masksAreMinterms ? calculcateMintermsSum() : 
			calcucalteMaxtermsProduct();
	}
	
	
	/**
	 * Metoda koja računa produkt maksterma za trenutno postavljene
	 * varijable domene.
	 * 
	 * @return Rezultat izračuna produkta maksterma.
	 */
	private BooleanValue calcucalteMaxtermsProduct() {
		List<BooleanSource> sum = new ArrayList<>();
		
		for(Mask value : masks) {			
			List<BooleanSource> maxterm = new ArrayList<>();
			
			for(int i = 0; i < domain.size(); i++) {
				BooleanValue current = domain.get(i).getValue();
				if(current == BooleanValue.DONT_CARE) {
					continue;
				}
				if(value.getValue(i) == MaskValue.ZERO) {
					maxterm.add(domain.get(i));
				}
				else {
					maxterm.add(new BooleanOperatorNOT(domain.get(i)));
				}
			}
			
			sum.add(new BooleanOperatorOR(maxterm));
		}
			
		return new BooleanOperatorAND(sum).getValue();		
	}

	
	/**
	 * Metoda koja računa sumu minterma za trenutno postavljene vrijednosti
	 * domene.
	 * 
	 * @return Rezultat izračuna sume minterma.
	 */
	private BooleanValue calculcateMintermsSum() {
		List<BooleanSource> sum = new ArrayList<>();
		
		for(Mask value : masks) {			
			List<BooleanSource> minterm = new ArrayList<>();
			
			for(int i = 0; i < domain.size(); i++) {
				BooleanValue current = domain.get(i).getValue();
				if(current == BooleanValue.DONT_CARE) {
					continue;
				}
				if(value.getValue(i) == MaskValue.ONE) {
					minterm.add(domain.get(i));
				}
				else {
					minterm.add(new BooleanOperatorNOT(domain.get(i)));
				}
			}
			
			sum.add(new BooleanOperatorAND(minterm));
		}
			
		return new BooleanOperatorOR(sum).getValue();		
	}

	
	/**
	 * Overridana metoda koja vraća domenu funkcije.
	 * 
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(domain);
	}

	
	/**
	 * Overrida metoda koja ispituje sadrži li funkcija minterm i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinterm(int i) {
		if(masksAreMinterms == true) {
			return maskContains(i, masks);
		}
		
		return !maskContains(i, masks) && !maskContains(i, dontCares);
	}

	
	/**
	 * Overrida metoda koja ispituje sadrži li funkcija maksterm i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMaxterm(int i) {
		if(masksAreMinterms == false) {
			return maskContains(i, masks);
		}
		
		return !maskContains(i, masks) && !maskContains(i, dontCares);
	}

	
	/**
	 * Overrida metoda koja ispituje sadrži li funkcija don't care i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasDontCare(int i) {
		return maskContains(i, dontCares);
	}
	
	
	/**
	 * Metoda koja ispituje postoji li index u listi maski.
	 * 
	 * @param index Indeks koji se traži.
	 * @param masksList Lista maski u kojoj se traži.
	 * @return true ako postoji, inače false.
	 */
	private boolean maskContains(int index, List<Mask> masksList) {
		String binaryString = FunctionUtil.convertToBinaryString(
				domain.size(),
				index
		);
		
		if(binaryString == null) {
			return false;
		} 
		
		for(Mask m : masksList) {
			Boolean contains = true;
			for(int i = 0; i < binaryString.length(); i++) {
				char current = binaryString.charAt(i);
				MaskValue value = current == '0' ?  MaskValue.ZERO 
												 : MaskValue.ONE;
				
				if(m.getValue(i) == MaskValue.DONT_CARE) {
					continue;
				}
				else if(m.getValue(i) != value) {
					contains = false;
				}
			}
			
			if(contains) {
				return true;
			}
		}
		
		return false;
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
	 * koji omogućava iteriranje po maksterima.
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

	/**
	 * Metoda koja vraća listu maski.
	 * @return lista maski.
	 */
	public List<Mask> getMasks() {
		return masks;
	}
	
	/**
	 * Metoda koja vraća listu dontcareova.
	 * @return lista dontcareova.
	 */
	public List<Mask> getDontCareMasks() {
		return dontCares;
	}
	
	/**
	 * Metoda koja ispituje da li su maske produkti.
	 * @return true ako su maske produkti, inače false.
	 */
	public boolean areMasksProducts() {
		return !masksAreMinterms;
	}
}
