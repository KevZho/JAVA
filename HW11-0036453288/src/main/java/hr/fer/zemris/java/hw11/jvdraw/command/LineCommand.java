package hr.fer.zemris.java.hw11.jvdraw.command;

import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.go.Line;

import java.awt.Color;
import java.awt.Point;

/**
 * Razred koji instancira linije.
 * @author Igor Smolkovič
 *
 */
public class LineCommand implements DrawCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject createObject(Point first, Point second, Color fg,
			Color bg, int id) {

		return new Line("Line " + id, first, second, fg);
	}
}
