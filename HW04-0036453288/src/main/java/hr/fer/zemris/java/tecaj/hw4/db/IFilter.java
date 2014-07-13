package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Sučelje <code>IFilter</code> predstavlja apstrakciju koja razredu
 * <code>StudentDatabase</code> omogućava filtiranje prema nekom
 * kriteriju koji je definiran u razredu koji implementira ovo sučelje.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface IFilter {

	public boolean accepts(StudentRecord record);
}
