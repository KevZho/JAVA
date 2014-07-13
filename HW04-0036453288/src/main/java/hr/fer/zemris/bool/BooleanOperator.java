package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Razred <code>BooleanOperator</code> implementira sučelje
 * <code>BooleanSource</code>. Predstavlja apstrakciju iznad 
 * boolean operatora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public abstract class BooleanOperator implements BooleanSource {

	private List<BooleanSource> sources;
	
	/**
	 * Zastićeni konstuktor koji prima listu izvora.
	 * 
	 * @param sources Lista izvora.
	 */
	protected BooleanOperator(List<BooleanSource> sources) {
		if(sources == null) {
			String msg = "BooleanOperator null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		if(sources.size() == 0) {
			String msg = "BooleanOperator empty sources list";
			throw new IllegalArgumentException(msg);
		}
		
		this.sources = new ArrayList<>(sources);		
	}
	
	
	/**
	 * Zaštićena metoda koja vraća listu izvora.
	 * 
	 * @return Lista izvora.
	 */
	protected List<BooleanSource> getSources() {
		return Collections.unmodifiableList(sources);
	}
	
	
	/**
	 * Overridana metoda koja vraća domenu.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		Set<String> variables = new HashSet<>();
		List<BooleanVariable> list = new ArrayList<>();
		
		for(BooleanSource s : sources) {
			List<BooleanVariable>  domain = s.getDomain();
			
			for(BooleanVariable v : domain) {
				if(variables.add(v.getName())) {
					variables.add(v.getName());
					list.add(v);
				}
			}
		}
		
		return Collections.unmodifiableList(list);
	}
}
