package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Bazni razred za hijerarhiju razreda koji modeliraju 
 * strukturu dokumenta.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 */

public class Node {
	
	private ArrayBackedIndexedCollection children;
	
	/**
	 * Konstruktor razreda Node.
	 * 
	 */
	public Node() {
	}
	
	/**
	 * Metoda koja dodaje dijete u stablo. Za više od jednog dijeteta
	 * stvara se kolekcija ArrayBackedIndexedCollection.
	 * 
	 * @param child Dijete koje se dodaje u stablo.
	 * @throws IllegalArgumentException ako je novo dijete null.
	 */
	public void addChildNode(Node child) throws IllegalArgumentException {
		if(child == null) {
			throw new IllegalArgumentException();
		}
		
		if(children == null) {
			children = new ArrayBackedIndexedCollection(4);
		}
		
		children.add(child);
	}

	
	/**
	 * Metoda koja vraća broj izravne djece u trenutnom čvoru.
	 * 
	 * @return Broj djece u trenutnom čvoru.
	 */
	public int numberOfChildren() {
		return children == null ? 0 : children.size();
	}
	
	
	/**
	 * Metoda koja dohvaća referencu na dijete na poziciji index
	 * dobivenoj kao parametar.
	 * 
	 * @param index Pozicija djeteta koje se dohvaća.
	 * @return Referenca djeteta na poziciji index.
	 * @throws IndexOutOfBoundsException ako index nije ispravan:
	 * 			index < 0 || index > size - 1
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException {
		if(index < 0 && index > (children.size()-1)) {
			throw new IndexOutOfBoundsException();
		}

		return (Node)children.get(index);
	}
	
	/**
	 * Metoda koju će izvedeni razredi moci overridati a služi
	 * za vraćanje tekstualne reprezentacije onoga što ti 
	 * razredi pohranjuju. 
	 * 
	 * @return String reprezentacija onoga što razred pohranjuje; bazni
	 * 			vraća null.
	 */
	public String asText() {
		return null;
	}
}
