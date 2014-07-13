package hr.fer.zemris.bool;

/**
 * Sučelje <code>BooleanFunction</code> omogučava korištenje
 * funkcija koje rade s Boolean varijablama.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface BooleanFunction extends NamedBooleanSource {
	
	/**
	 * Metoda koja ispituje postoji li minterm i.
	 * 
	 * @param i Minterm koji se ispituje.
	 * @return true ako minterm postoji, inače false.
	 */
	public boolean hasMinterm(int i);
	
	
	/**
	 * Metoda koja ispituje postoji li maksterm i. 
	 * 
	 * @param i Maksterm koji se ispituje.
	 * @return true ako maksterm postoji, inače false.
	 */
	public boolean hasMaxterm(int i);
	
	
	/**
	 * Metoda koja ispituje postoji li don't care i.
	 * 
	 * @param i Don't care koji se ispituje.
	 * @return true ako dont' care postoji, inače false.
	 */
	public boolean hasDontCare(int i);
	
	
	/**
	 * Metoda koja vraća iterator koji zna iterirati po mintermima.
	 * 
	 * @return Minterm iterator.
	 */
	public Iterable<Integer> mintermIterable();
	
	
	/**
	 * Metoda koja vraća iterator koji zna iterirati po makstermima.
	 * 
	 * @return Maksterm iterator.
	 */
	public Iterable<Integer> maxtermIterable();
	
	
	/**
	 * Metoda koja vraća iterator koji zna iterirati po dont'careovima.
	 * 
	 * @return Dontcare iterator.
	 */
	public Iterable<Integer> dontcareIterable();
}
