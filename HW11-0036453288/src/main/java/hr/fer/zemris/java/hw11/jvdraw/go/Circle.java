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
 * Razred koji implementira crtanje kružnice.
 * @author Igor Smolkovič
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Centar kružnice.
	 */
	protected Point center;
	
	/**
	 * Radijus kružnice.
	 */
	protected double radius;
	
	/**
	 * Boja kružnice.
	 */
	protected Color color;

	
	/**
	 * Konstruktor.
	 * @param name ime objekta.
	 * @param center centar kružnice.
	 * @param radius radijus kružnice.
	 * @param color boja kružnice.
	 */
	public Circle(String name, Point center, double radius, Color color) {
		super(name);
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	/**
	 * Metoda koja mijenja centar kružnice.
	 * @param center novi centar kružnice.
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Metoda koja mijenja radijus kružnice.
	 * @param radius novi radijus kružnice.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Metoda koja mijenja boju kružnice.
	 * @param color nova boja kružnice.
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
		g.drawOval(center.x - (int)radius - offset.getMinX(), center.y - (int)radius - offset.getMinY(),
				(int)(2 * radius), (int)(2 * radius));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		JPanel panel = new JPanel();
		JTextField tfFirst = new JTextField(center.x + ", " + center.y);
		JTextField tfRadius = new JTextField(String.valueOf(radius));
		JColorArea newColor = new JColorArea(color);
		panel.add(new JLabel("Center:"));
		panel.add(tfFirst);
		panel.add(new JLabel("Radius"));
		panel.add(tfRadius);
		panel.add(new JLabel("Color:"));
		panel.add(newColor);
		int status = JOptionPane.showConfirmDialog(null, panel);
		if (status == JOptionPane.OK_OPTION) {
			Point newCenter = null;
			double newRadius = 0;
			try {
				newCenter = new Point(
						Integer.parseInt(tfFirst.getText().split(",")[0].trim()),
						Integer.parseInt(tfFirst.getText().split(",")[1].trim())
				);
				newRadius = Double.parseDouble(tfRadius.getText().trim());
			} catch (NumberFormatException ex) {
				return;
			}
			setColor(newColor.getCurrentColor());
			setCenter(newCenter);
			setRadius(newRadius);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return String.format("CIRCLE %d %d %.1f %d %d %d%n", center.x, center.y, radius,
				color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void findBoundingBox(BoundingBox box) {
		box.setMinX(center.x - (int)radius);
		box.setMaxX(center.x + (int)radius);
		box.setMinY(center.y - (int)radius);
		box.setMaxY(center.y + (int)radius);
	}
}
