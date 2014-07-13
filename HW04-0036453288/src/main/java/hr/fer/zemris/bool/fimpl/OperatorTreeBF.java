package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.fimpl.iterable.ListGenerator;

/**
 * Razred <code>OperatorTreeBF</code>.
 * 
 * @author Igor Smolkovič
 *
 */
public class OperatorTreeBF implements BooleanFunction {

	private String name;
	private List<BooleanVariable> domain;
	private BooleanOperator operatorTree;
	
	
	/**
	 * Konstruktor razreda <code>OperatorTreeBF</code>.
	 * 
	 * @param name Ime funkcije.
	 * @param domain Domena funkcije.
	 * @param operatorTree Operatorsko stablo.
	 * @throws IllegalArgumentException ako argumenti nisu dobri.
	 */
	public OperatorTreeBF(String name, List<BooleanVariable> domain,
			BooleanOperator operatorTree) {
		if(name.isEmpty()) {
			String msg = "OperatorTreeBF empty name";
			throw new IllegalArgumentException(msg);
		}
		if(domain == null || operatorTree == null) {
			String msg = "OperatorTreeBF null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		List<BooleanVariable> operatorDomain = operatorTree.getDomain();
		int counter = 0;
		
		//sadrži li funkcija sve varijable koje ima operatorsko stablo	
		for(BooleanVariable oprVar : operatorDomain) {
			for(int i = 0, end = domain.size(); i < end; i++) {
				if(oprVar.getName().equals(domain.get(i).getName())) {
					counter++;
				}
			}
		}
	
		if(counter != operatorDomain.size()) {
			String msg = "Illegal function domain";
			throw new IllegalArgumentException(msg);
		}
			
		this.name = name;
		this.domain = new ArrayList<BooleanVariable>(domain);
		this.operatorTree = operatorTree;
	}

	
	/**
	 * Overridana metoda getName() koja vraća ime funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	
	/**
	 * Overridana metoda getValue() koja vraća vrijednost funkcije
	 * za trenutno postavlje varijable domene.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {		
		return operatorTree.getValue();
	}

	
	/**
	 * Overridana metoda getDomain() koja vraća domenu
	 * funkcije.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(domain);
	}

	
	/**
	 * Overrida metoda koja ispituje postoji li minterm i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMinterm(int i) {
		boolean flag = setVariableValues(i);
		return flag && (this.getValue() == BooleanValue.TRUE);
	}

	
	/**
	 * Overrida metoda koja ispituje postoji li maksterm i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasMaxterm(int i) {
		boolean flag = setVariableValues(i);
		return flag && (this.getValue() == BooleanValue.FALSE);
	}

	
	/**
	 * Overrida metoda koja ispituje postoji li don't care i.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasDontCare(int i) {
		boolean flag = setVariableValues(i);
		return flag && (this.getValue() == BooleanValue.DONT_CARE);
	}
	
	
	/**
	 * Metoda koja postavlja vrijednosti domene za pojedini index.
	 * 
	 * @param index Indeks za koji se postavljaju vrijednosti.
	 * @return true - ako je postavljanje uspješno, inače false.
	 */
	private boolean setVariableValues(int index) {
		String binaryString = FunctionUtil.convertToBinaryString(
				domain.size(), 
				index
		);
		
		if(binaryString == null) {
			return false;
		}
				
		for(int j = 0, end = domain.size(); j < end; j++) {
			BooleanValue value = binaryString.charAt(j) == '0' ? BooleanValue.FALSE :
								 	BooleanValue.TRUE;
			domain.get(j).setValue(value);
		}

		return true;
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
