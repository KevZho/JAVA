package hr.fer.zemris.java.hw11.jvdraw.command;

import hr.fer.zemris.java.hw11.jvdraw.go.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;

import java.awt.Color;
import java.awt.Point;

/**
 * Razred koji instancira popunjene krugove.
 * @author Igor Smolkovič
 *
 */
public class FilledCircleCommand implements DrawCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject createObject(Point first, Point second, Color fg,
			Color bg, int id) {

		double radius = Math.pow((first.x - second.x), 2) + Math.pow((first.y - second.y), 2);
		radius = Math.sqrt(radius);
		return new FilledCircle("Filled circle " + id, first, radius, fg, bg);
	}
}
