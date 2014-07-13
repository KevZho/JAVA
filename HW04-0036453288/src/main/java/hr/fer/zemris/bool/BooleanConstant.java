package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>BooleanConstant</code> implementira sučelje 
 * <code>BooleanSource</code>. Omogućava korištenje TRUE i
 * FALSE konstanti.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanConstant implements BooleanSource {

	private BooleanValue value;
	
	public static final  BooleanConstant TRUE = new 
			BooleanConstant(BooleanValue.TRUE);
	
	public static final BooleanConstant FALSE = 
			new BooleanConstant(BooleanValue.FALSE);

	
	/**
	 * Privatni konstruktor razreda <code>BooleanConstant</code>.
	 * 
	 * @param value Vrijednost na koju se postavlja property value.
	 */
	private BooleanConstant(BooleanValue value) {
		this.value = value;
	}

	
	/**
	 * Overridana metoda getValue() koja vraća vrijednost konstante.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {
		return value;
	}

	
	/**
	 * Overridana metoda getDomain() koja vraća domenu instance
	 * razreda <code>BooleanConstant</code>. Domena je uvijek 
	 * prazna lista.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return new ArrayList<BooleanVariable>();
	}

}
