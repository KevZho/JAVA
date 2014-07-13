package hr.fer.zemris.java.hw11.jvdraw.go;

import java.awt.Graphics;

/**
 * Vršni razred geometrijskih objekata.
 * @author Igor Smolkovič
 *
 */
public abstract class GeometricalObject {

	/**
	 * Ime objekta.
	 */
	private String name;

	/**
	 * Konstruktor koji postavlja ime.
	 * @param name ime objekta.
	 */
	public GeometricalObject(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Metoda koju svi izvedeni razredi moraju implementirati kako bi se mogli jednako tretitrati
	 * prilikom crtanja.
	 * @param g referenca na grafički objekt.
	 */
	public abstract void draw(Graphics g, BoundingBox offset);

	/**
	 * Metoda koja poziva update trenutnog geometrijskog objekta.
	 */
	public abstract void update();

	/**
	 * Metoda koja traži opis trenutnog geometrijskog objekta.
	 * @return opis geometrijskog objekta.
	 */
	public abstract String getDescription();

	/**
	 * Metoda koja traži minimalni x i y, te maksimalni x i y. Svaki geometrijski objekt osvježava
	 * dobiveni objekt <code>box</code>. Kad se prođe kroz sve komponente u <code>box</code> je zapisan
	 * bounding box za trenutno nacrtanu sliku.
	 * @param box referenca na objekt tipa BoundingBox.
	 */
	public abstract void findBoundingBox(BoundingBox box);
}
