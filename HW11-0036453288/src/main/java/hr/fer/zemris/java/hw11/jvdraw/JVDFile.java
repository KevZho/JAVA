package hr.fer.zemris.java.hw11.jvdraw;


import hr.fer.zemris.java.hw11.jvdraw.go.BoundingBox;
import hr.fer.zemris.java.hw11.jvdraw.go.Circle;
import hr.fer.zemris.java.hw11.jvdraw.go.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.go.Line;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Razred koji omogućava čitanje iz datoteke i spremanje u datoteku.
 * @author Igor Smolkovič
 *
 */
public class JVDFile {

	/**
	 * Metoda koja učitava model iz datoteke.
	 * @param model referenca na model u koji se dodaju objekti.
	 */
	public static void openFile(DrawingModel model) {
		JFileChooser fc = new JFileChooser("./");
		int status = fc.showOpenDialog(null);
		if (status == JFileChooser.OPEN_DIALOG) {
			File selected = fc.getSelectedFile();
			if (!Files.isRegularFile(selected.toPath())) {
				return;
			}
			readModelFromFile(selected, model);
		}
	}

	/**
	 * Metoda koja dohvaća lokaciju datoteke za spremenje.
	 * @return lokacija datoteke za spremanje.
	 */
	public static File getSaveFile() {
		JFileChooser fc = new JFileChooser("./");
		fc.setFileFilter(new FileNameExtensionFilter("JVD (*.jvd)", ".jvd"));
		int status = fc.showSaveDialog(null);
		if (status == JFileChooser.OPEN_DIALOG) {
			return setExtension(fc.getSelectedFile(), ".jvd") ;
		}
		return null;
	}

	/**
	 * Metoda koja čita sadržaj datoteke i puni DrawingModel učitanim objektima.
	 * @param f datoteka iz koje se čita model.
	 * @param model model u koji se dodaju objekti.
	 */
	private static void readModelFromFile(File f, DrawingModel model) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					f.toPath(),
					StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			return;
		}
		model.clear();
		if (lines != null) {
			int id = 0;
			for (String line : lines) {
				String[] parts = line.split("\\s+");
				GeometricalObject object = null;
				try {
					if (parts[0].equals("LINE")) {
						object = new Line(
								"Line " + id++,
								new Point(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim())),
								new Point(Integer.parseInt(parts[3].trim()), Integer.parseInt(parts[4].trim())),
								new Color(Integer.parseInt(parts[5].trim()), Integer.parseInt(parts[6].trim()), Integer.parseInt(parts[7].trim()))
						);

					} else if (parts[0].equals("CIRCLE")) {
						object = new Circle(
								"Circle " + id++,
								new Point(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim())),
								Double.parseDouble(parts[3].trim()),
								new Color(Integer.parseInt(parts[4].trim()), Integer.parseInt(parts[5].trim()), Integer.parseInt(parts[6].trim()))
						);
					} else if (parts[0].equals("FCIRCLE")) {
						object = new FilledCircle(
								"Circle " + id++,
								new Point(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim())),
								Double.parseDouble(parts[3].trim()),
								new Color(Integer.parseInt(parts[4].trim()), Integer.parseInt(parts[5].trim()), Integer.parseInt(parts[6].trim())),
								new Color(Integer.parseInt(parts[7].trim()), Integer.parseInt(parts[8].trim()), Integer.parseInt(parts[9].trim()))
						);
					}
				} catch (NumberFormatException ex) {
				}
				if (object == null) { continue; }
				model.add(object);
			}
		}
	}

	/**
	 * Metoda koja sprema model u datoteku.
	 * @param f datoteka u koju se sprema model.
	 * @param model model koji se sprema u datoteku.
	 */
	public static void saveModelToFile(File f, DrawingModel model) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(
							new BufferedOutputStream(
									new FileOutputStream(f)),
									StandardCharsets.UTF_8)
			);
		} catch (FileNotFoundException e) {
		}
		if (writer == null) { return; }
		for (int i = 0; i < model.getSize(); i++) {
			String description = model.getObject(i).getDescription();
			try {
				writer.write(description);
			} catch (IOException e) {
			}
		}
		try { writer.close(); } catch (IOException ignorable) { }
	}

	/**
	 * Metoda koja exporta model u sliku.
	 * @param model model koji se exporta.
	 * @param box referencan na BoundingBox objekt.
	 */
	public static void exportModel(DrawingModel model, BoundingBox box) {
		/**
		 * Treba pronaći xmin, xmax, ymin, ymax.
		 */
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).findBoundingBox(box);
		}

		JFileChooser fc = new JFileChooser("./");

		FileNameExtensionFilter png = new FileNameExtensionFilter("PNG (*.png)", ".png");
		FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF (*.gif)", ".gif");
		FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPG (*.jpg)", ".jpg");

		fc.addChoosableFileFilter(png);
		fc.addChoosableFileFilter(gif);
		fc.addChoosableFileFilter(jpg);
		fc.setFileFilter(png);
		int status = fc.showSaveDialog(null);

		if (status != JFileChooser.OPEN_DIALOG) { return; }
		File f = fc.getSelectedFile();

		int width = Math.abs(box.getMaxX() - box.getMinX());
		int height = Math.abs(box.getMaxY() - box.getMinY());
		BufferedImage image = null;
		try {
			image = new BufferedImage(width + 10, height + 10, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);

			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).draw(g, box);
			}
			g.dispose();
		} catch (Exception e) {
			return;
		}
		try {
			if (fc.getFileFilter().equals(png)) {
				f = setExtension(f, ".png");
				ImageIO.write(image, "png", f);
			} else if (fc.getFileFilter().equals(gif)) {
				f = setExtension(f, ".gif");
				ImageIO.write(image, "gif", f);
			} else if (fc.getFileFilter().equals(jpg)) {
				f = setExtension(f, ".jpg");
				ImageIO.write(image, "jpg", f);
			}
		} catch (Exception ex) {
		}
	}
	
	private static File setExtension(File f, String extension) {
		if (f.getAbsoluteFile().toString().endsWith(extension) || 
				f.getAbsoluteFile().toString().endsWith(extension.toUpperCase())) {
			return f;
		}
		return new File(f.getAbsolutePath() + extension);
	}
}
