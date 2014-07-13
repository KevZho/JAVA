package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred <code>ObjectMultistack</code> omogućava spremanje više vrijednosti za 
 * isti ključ uz korištenje stogovne strukture.
 *
 * <pre>
 * 	ObjectMultistack mapStack = new ObjectMultistack();
 * 	mapStack.push("key", new ValueWrapper("100"));
 * 	mapStack.push("key", new ValueWrapper(200));
 *
 * 	mapStack.peek("key").getValue(); // ispisuje 200
 * 	mapStack.pop("key"); 
 *	mapStack.peek("key").getvalue(); // ispisuje String 100 
 * </pre>
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ObjectMultistack {

	/**
	 * Mapa koja pohranjuje virtualne stogove.
	 */
	private Map<String, MultistackEntry> map;
	
	/**
	 * Konstruktor razreda <code>ObjectMultistack</code> inicijalizira
	 * praznu mapu.
	 *
	 */
	public ObjectMultistack() {
		map = new HashMap<String, ObjectMultistack.MultistackEntry>();
	}
	
	/**
	 * Metoda push dodaje element na vrh stoga koji pripada traženom ključu.
	 * Ako je stog ne postoji on se stvara, inače se dodaje na vrh stoga.
	 *
	 * @param name Ključ koji određuje stog na koji se vrijednost dodaje.
	 * @param valueWrapper Vrijednost koja se dodaje na stog koji odgovara 
	 * 						traženom ključu.
	 * @throws IllegalArgumentException ako je ključ prazan/null ili ako je 
	 * 									vrijednost koja se dodaje null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null) {
			throw new IllegalArgumentException("Key null reference.");
		}
		if (valueWrapper == null) {
			throw new IllegalArgumentException("ValueWrapper null reference.");
		}
		MultistackEntry value = map.get(name);
		// ako stog ne postoji get vraća null pa se stvara novi stog
		map.put(name, new MultistackEntry(value, valueWrapper));
		
	}
	
	/**
	 * Metoda koja vraća element vrha stoga sa stoga koji odgovara traženom
	 * ključu. Nakon provedbe metode element nestaje sa vrha stoga.
	 *
	 * @param name Ključ koji odgovara stogu s kojeg se uzima vrijednost.
	 * @return Vrijednost vrha stoga na traženom ključu.
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry entry = this.getEntry(name);
		// zamjena se obavlja u O(1)
		map.put(name, entry.getPrevious());
		return entry.getValue();
	}
	
	/**
	 * Metoda koja vraća element vrha stoga sa stoga koji odgovara traženom
	 * ključu. Nakon provedbe metode element ostaje na vrhu stoga.
	 *
	 * @param name Ključ koji odgovara stogu s kojem se uzima vrijednost.
	 * @return Vrijednost vrha stoga na traženom ključu.
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry entry = this.getEntry(name);
		return entry.getValue();
	}
	
	/**
	 * Privatna metoda koja dohvaća čvor virtualnog stoga pohranjen na
	 * traženom ključu.
	 *
	 * @param name Ključ koji odgovara virtualnom stogu koji se dohvaća.
	 * @return Čvor virtualnog stoga spremljen na traženom ključu.
	 * @throws EmptyStackException ako je stog prazan.
	 */
	private MultistackEntry getEntry(String name) {
		MultistackEntry entry = map.get(name);
		if (entry == null) {
			throw new EmptyStackException();
		}
		return entry;
	}
	
	/**
	 * Metoda koja ispituje da li ObjectMultistack sadrži elemente.
	 *
	 * @return true ako postoje elementi, inače false.
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	
	/**
	 * Privatni razred <code>MultistackEntry</code> omogućava stvaranje 
	 * strukture stog implementirane kao jednostruko povezana lista.
	 *
	 * @author Igor Smolkovič
	 * @version 1.0
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Referenca na prethodni element stoga.
		 */
		private MultistackEntry previous;
		/**
		 * Vrijednost vrha stoga.
		 */
		private ValueWrapper value;
		
		/**
		 * Konstruktor razreda <code>MultistackEntry</code> prima referencu
		 * na prethodni element i vrijednost koja se dodaje na vrh stoga.
		 *
		 * @param previous Referenca na prethodni element stoga.
		 * @param value Vrijednost koja se dodaje na vrh stoga.
		 */
		public MultistackEntry(MultistackEntry previous, ValueWrapper value) {
			super();
			this.previous = previous;
			this.value = value;
		}

		/**
		 * Metoda koja dohvaća vrijednost vrha stoga.
		 * 
		 * @return Element vrha stoga.
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * Metoda dohvaća referencu na prethodni element stoga.
		 *
		 * @return Prethodni element stoga.
		 */
		public MultistackEntry getPrevious() {
			return previous;
		}
	
	}
	
}
