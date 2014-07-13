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
 * Razred koji implementira crtanje obojanih krugova.
 * @author Igor Smolkovič
 *
 */
public class FilledCircle extends Circle {

	/**
	 * Boja ispune kruga.
	 */
	private Color bgColor;

	/**
	 * Konstruktor.
	 * @param name ime objekta.
	 * @param center centar kruga.
	 * @param radius radijus kruga.
	 * @param color boja crte.
	 * @param bgColor boja ispune.
	 */
	public FilledCircle(String name, Point center, double radius, Color color,
			Color bgColor) {
		super(name, center, radius, color);
		this.bgColor = bgColor;
	}

	/**
	 * Metoda koja postavlja novu boju ispune.
	 * @param bgColor nova boja ispune.
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics g, BoundingBox offset) {
		g.setColor(bgColor);
		g.fillOval(center.x - (int)radius - offset.getMinX(), center.y - (int)radius - offset.getMinY(),
				(int)(2 * radius), (int)(2 * radius));
		super.draw(g, offset);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		JPanel panel = new JPanel();
		JTextField tfFirst = new JTextField(center.x + ", " + center.y);
		JTextField tfRadius = new JTextField(String.valueOf(radius));
		JColorArea newColor = new JColorArea(this.color);
		JColorArea newBGColor = new JColorArea(bgColor);
		panel.add(new JLabel("Center:"));
		panel.add(tfFirst);
		panel.add(new JLabel("Radius"));
		panel.add(tfRadius);
		panel.add(new JLabel("FG Color:"));
		panel.add(newColor);
		panel.add(new JLabel("BG Color:"));
		panel.add(newBGColor);
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
			setBgColor(newBGColor.getCurrentColor());
			setCenter(newCenter);
			setRadius(newRadius);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return String.format("FCIRCLE %d %d %.1f %d %d %d %d %d %d%n", center.x, center.y, radius,
				color.getRed(), color.getGreen(), color.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
	}
}
