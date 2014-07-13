package hr.fer.zemris.java.hw13;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji se koristi za bilježenje glasova korisnika.
 *
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/**
		 * Ako datoteka glasanje-rezultati.txt već postoji pročitaj njezin
		 * sadržaj.
		 */
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		Map<Integer, Integer> glasovi = new HashMap<>();
		if (Files.exists(Paths.get(fileName))) {
			try {
				List<String> lines = ServletUtil.readLines(fileName);
				for (String line : lines) {
					String[] parts = line.split("\t");
					glasovi.put(Integer.parseInt(parts[0].trim()),
							Integer.parseInt(parts[1].trim()));
				}
			} catch (Exception ex) {
			}
		}
		/**
		 * Odredi za koji je bend glasano i povečaj brojač.
		 */
		Integer a = Integer.valueOf(req.getParameter("id"));
		Integer brojGlasova = glasovi.get(a);
		if (brojGlasova == null) {
			brojGlasova = 0;
		}
		brojGlasova++;
		glasovi.put(a, brojGlasova);

		/**
		 * Zapiši nove podatke u datoteku.
		 */
		Writer bw = new BufferedWriter(new OutputStreamWriter(
				new BufferedOutputStream(new FileOutputStream(fileName)), "UTF-8"));

		synchronized (bw) {
			for (Map.Entry<Integer, Integer> entry : glasovi.entrySet()) {
				bw.write(String.format("%d\t%d%n", entry.getKey(), entry.getValue()));
			}
			bw.flush();
			bw.close();
		}
		/**
		 * Preusmjeri na servlet koji priprema podatke za ispis.
		 */
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
