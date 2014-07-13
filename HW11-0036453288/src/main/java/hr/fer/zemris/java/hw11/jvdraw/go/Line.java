package hr.fer.zemris.java.hw11.jvdraw.go;


import hr.fer.zemris.java.hw11.jvdraw.component.JColorArea;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Razred koji implementira crtanje linije.
 * @author Igor Smolkovič
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Prva točka.
	 */
	private Point first;

	/**
	 * Posljednja točka.
	 */
	private Point second;

	/**
	 * Boja linije.
	 */
	private Color color;

	/**
	 * Konstruktor.
	 * @param name ime objekta.
	 * @param first prva točka.
	 * @param second posljednja točka.
	 * @param color boja crte.
	 */
	public Line(String name, Point first, Point second, Color color) {
		super(name);
		this.first = first;
		this.second = second;
		this.color = color;
	}

	/**
	 * Metoda koja mijenja početnu točku.
	 * @param first nova početna točka.
	 */
	public void setFirst(Point first) {
		this.first = first;
	}

	/**
	 * Metoda koja mijenja završnu točku.
	 * @param second nova završna točka.
	 */
	public void setSecond(Point second) {
		this.second = second;
	}

	/**
	 * Metoda koja mijenja boju linije.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics g, BoundingBox offset) {
		g.setColor(color);
		g.drawLine(first.x - offset.getMinX(), first.y - offset.getMinY(),
				second.x - offset.getMinX(), second.y - offset.getMinY());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		JPanel panel = new JPanel();
		JTextField tfFirst = new JTextField(first.x + ", " + first.y);
		JTextField tfSecond = new JTextField(second.x + ", " + second.y);
		JColorArea newColor = new JColorArea(color);
		panel.add(new JLabel("First point:"));
		panel.add(tfFirst);
		panel.add(new JLabel("Second point:"));
		panel.add(tfSecond);
		panel.add(new JLabel("Color:"));
		panel.add(newColor);
		int status = JOptionPane.showConfirmDialog(null, panel);
		if (status == JOptionPane.OK_OPTION) {
			Point newFirst = null;
			Point newSecond = null;
			try {
				newFirst = new Point(
						Integer.parseInt(tfFirst.getText().split(",")[0].trim()),
						Integer.parseInt(tfFirst.getText().split(",")[1].trim())
				);
				newSecond = new Point(
						Integer.parseInt(tfSecond.getText().split(",")[0].trim()),
						Integer.parseInt(tfSecond.getText().split(",")[1].trim())
				);
			} catch (NumberFormatException ex) {
				return;
			}
			setColor(newColor.getCurrentColor());
			setFirst(newFirst);
			setSecond(newSecond);
		}
 	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return String.format("LINE %d %d %d %d %d %d %d%n", first.x, first.y, second.x, second.y,
				color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void findBoundingBox(BoundingBox box) {
		box.setMinX(Math.min(first.x, second.x));
		box.setMaxX(Math.max(first.x, second.x));
		box.setMinY(Math.min(first.y, second.y));
		box.setMaxY(Math.max(first.y, second.y));
	}
}
