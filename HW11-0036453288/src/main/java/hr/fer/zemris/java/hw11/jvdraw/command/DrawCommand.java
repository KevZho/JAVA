package hr.fer.zemris.java.hw11.jvdraw.command;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;

/**
 * Sučelje koje predstaljva ostanovu za instanciranje geometrijskih objekata.
 * @author Igor Smolkovič
 *
 */
public interface DrawCommand {

	/**
	 * Metoda koja koja na temelju dviju točaka i boja pozadine i crte instancira novi objekt.
	 * @param first prva točka.
	 * @param second druga točka.
	 * @param fg boja crte.
	 * @param bg boja ispune.
	 * @param id id objekta.
	 * @return nova instanca tipa GeomtricalObjekt.
	 */
	public GeometricalObject createObject(Point first, Point second, Color fg, Color bg, int id);
}
