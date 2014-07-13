package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet koji omogućava generiranje xls datoteke koja sadrži potencije od 1 do n
 * za brojeve iz intervala a do b.
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;

		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
		} catch (Exception ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		if (checkParameter(a) || checkParameter(b)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		if (n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		OutputStream out = null;
		try {
			HSSFWorkbook document = createXLSDocument(a, b, n);
			resp.setContentType("application/xls");
	        resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
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
	 * Metoda koja stvara xls datoteku sa potencijama.
	 * @param a prvi broj za koji se generira potencija.
	 * @param b zadnji broj za koji se generira potencija.
	 * @param n potencije od 1 do n.
	 * @return xls datoteka sa potencijama.
	 */
	private HSSFWorkbook createXLSDocument(Integer a, Integer b, Integer n) {
		HSSFWorkbook document = new HSSFWorkbook();
		/**
		 * Idemo po svim n-ovima.
		 */
		for (int i = 1; i <= n; i++) {
			/**
			 * Napravi stranicu.
			 */
			HSSFSheet sheet = document.createSheet(String.format("%d-th power", i));
			/**
			 * Dodaj prvi redak.
			 */
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("x");
			rowhead.createCell(1).setCellValue(String.format("x^%d", i));
			/**
			 * Dodaj j^i za svaki j iz [a, b]
			 */
			int counter = 1;
			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(counter);
				row.createCell(0).setCellValue(Double.valueOf(j));
				row.createCell(1).setCellValue(Math.pow(Double.valueOf(j), Double.valueOf(i)));
				counter++;
			}
		}
		return document;
	}

	/**
	 * Metoda koja provjera da li je parametar u predviđenom intervalu.
	 * @param param parametar koji se provjera.
	 * @return <code>true</code> ako je u intervalu, inače false.
	 */
	private boolean checkParameter(Integer param) {
		return (param > 100) || (param < -100);
	}
}
