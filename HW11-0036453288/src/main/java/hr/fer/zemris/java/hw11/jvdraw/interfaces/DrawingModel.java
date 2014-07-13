package hr.fer.zemris.java.hw11.jvdraw.interfaces;

import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;

/**
 * Sučelje koje mora implementirati subjekt koji sprema geometrijske objekte.
 * @author Igor Smolkovič
 *
 */
public interface DrawingModel {

	/**
	 * Broj objekata za crtanje.
	 * @return broj objektata za crtanje
	 */
	public int getSize();

	/**
	 * Dohvat geometrijskog objekta.
	 * @param index indeks objekta koji se dohvaća
	 * @return objekt na traženom indeksu.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Metoda koja dodaje geometrijski objekt.
	 * @param object geometrijski objekt koji se dodaje.
	 */
	public void add(GeometricalObject object);

	/**
	 * Metoda koja briše sve geometrijske objekte iz modela.
	 */
	public void clear();

	/**
	 * Metoda koja briše geometrijski objekt na poziciji index.
	 * @param index indeks elementa koji se briše.
	 */
	public void removeObject(int index);

	/**
	 * Metoda koja osvježava parametre geomtrijskog objekta.
	 * @param index indeks elementa koji se osvježava.
	 */
	public void updateObject(int index);

	/**
	 * Metoda koja dodaje promatrače.
	 * @param listener promatrač koji se dodaje.
	 */
	public void addDrawingModelListener(DrawingModelListener listener);

	/**
	 * Metoda koja briše promatrače.
	 * @param listener promatrač koji se briše.
	 */
	public void removeDrawingModelListener(DrawingModelListener listener);

}
