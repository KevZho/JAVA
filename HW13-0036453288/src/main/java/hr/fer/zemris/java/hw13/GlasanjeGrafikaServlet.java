package hr.fer.zemris.java.hw13;

import hr.fer.zemris.java.hw13.ServletUtil.Rezultati;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet koji priprema grafički prikaz rezultata glasanja.
 *
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/**
		 * Pročitaj datoteke i generiraj listu rezultata.
		 */
		String fileRez = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		String fileDef = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		List<String> definicije = ServletUtil.readLines(fileDef);
		List<String> rezultati = ServletUtil.readLines(fileRez);
		List<Rezultati> rez = ServletUtil.dohvatiRezultate(definicije,
				rezultati);

		/**
		 * Na socket output stream vrati sliku.
		 */
		OutputStream out = null;
		try {
			resp.setContentType("image/png");
			out = resp.getOutputStream();
			JFreeChart chart = createChart(rez);
			BufferedImage img = chart.createBufferedImage(500, 500);
			ImageIO.write(img, "png", out);
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Metoda koja na temelju rezultata stvara grafički prikaz tih rezultata.
	 *
	 * @param rez
	 *            lista rezultata.
	 * @return graf dobivenih rezultata.
	 */
	private JFreeChart createChart(List<Rezultati> rez) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (Rezultati r : rez) {
			result.setValue(r.getName(), r.getVotes());
		}

		JFreeChart chart = ChartFactory.createPieChart3D("", result);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
