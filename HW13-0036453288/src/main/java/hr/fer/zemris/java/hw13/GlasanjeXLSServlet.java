package hr.fer.zemris.java.hw13;

import hr.fer.zemris.java.hw13.ServletUtil.Rezultati;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji omogućava pripremu xls datoteke sa rezultatima glasanja.
 *
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileRez = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		String fileDef = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		List<String> definicije = ServletUtil.readLines(fileDef);
		List<String> rezultati = ServletUtil.readLines(fileRez);
		List<Rezultati> rez = ServletUtil.dohvatiRezultate(definicije,
				rezultati);

		OutputStream out = null;
		try {
			HSSFWorkbook document = createXLSDocument(rez);
			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition",
					"attachment; filename=rezultati.xls");
			out = resp.getOutputStream();
			document.write(out);
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Metoda koja priprema xls datoteku sa rezultatima glasanja.
	 *
	 * @param rez
	 *            rezultati na temelju koji se generira xls datoteka.
	 * @return xls datoteka.
	 */
	private HSSFWorkbook createXLSDocument(List<Rezultati> rez) {
		HSSFWorkbook document = new HSSFWorkbook();
		HSSFSheet sheet = document.createSheet("Rezultati");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Ime benda:");
		rowhead.createCell(1).setCellValue("Broj glasova:");

		for (int i = 0; i < rez.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(rez.get(i).getName());
			row.createCell(1).setCellValue(
					Double.valueOf(rez.get(i).getVotes()));
		}

		return document;
	}
}
