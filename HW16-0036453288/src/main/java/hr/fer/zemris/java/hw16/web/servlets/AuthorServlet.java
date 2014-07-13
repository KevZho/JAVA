package hr.fer.zemris.java.hw16.web.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.form.BlogEntyForm;
import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogučava manipulacije nad blogovima i komentarima.
 * 
 * @author Igor Smolkovič
 * 
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -3376397475643476847L;

	/**
	 * Zastavica koja označava da je korisnik logiran.
	 */
	private boolean logged = false;

	/**
	 * Path uzet iz url-a.
	 */
	private String path;

	/**
	 * Nadimak korisnika.
	 */
	private String nick;

	/**
	 * Id bloga koji se editira.
	 */
	private Long eid;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/*
		 * Dohvati path info.
		 */
		path = req.getPathInfo();

		/*
		 * Pogledaj da li si prijavljen.
		 */
		logged = req.getSession().getAttribute("current.user.id") != null;
		path = path.substring(1);
		/*
		 * Ako se traži ispis blogova autora.
		 */
		if (path.indexOf('/') == -1) {
			if (logged) {
				logged = path.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			nick = path;
			renderBlogList(req, resp);
			return;
		} else if (path.indexOf("/new") != -1) {
			nick = path.substring(0, path.indexOf('/'));
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			renderNewBlog(req, resp);
			return;
		} else if (path.indexOf("/edit") != -1) {
			nick = path.substring(0, path.indexOf('/'));
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			renderEditBlog(req, resp);
			return;
		} else if (path.matches(".+/\\d+")) {
			int index = path.indexOf('/');
			nick = path.substring(0, index);
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			eid = Long.valueOf(path.substring(index + 1));
			renderBlogEntry(req, resp);
			return;
		}

		/*
		 * U svim ostalim slučajevima se vrati na index.jsp
		 */
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

	/**
	 * Metoda koja omogućava ispis liste blogova traženog korisnika.
	 * 
	 * @param req
	 *            request.
	 * @param resp
	 *            response.
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderBlogList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntriesForNick(
				nick);
		req.setAttribute("entries", entries);
		req.setAttribute("autor", nick);
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/BlogsList.jsp").forward(req,
				resp);
	}

	/**
	 * Metoda koja omogućava dodavanje bloga.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderNewBlog(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (logged == false || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}

		BlogEntyForm fm = new BlogEntyForm();
		BlogEntry e = new BlogEntry();
		fm.popuniIzBlogEntry(e);

		/*
		 * Zakaći atribute i pošalji na renderiranje.
		 */
		req.setAttribute("autor", nick);
		req.setAttribute("zapis", fm);
		req.setAttribute("mode", "new");
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
				resp);
	}

	/**
	 * Metoda koja omogućava editiranje bloga.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderEditBlog(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (logged == false || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}

		eid = (Long) req.getSession().getAttribute("blogID");
		/*
		 * Dohvati zapis iz baze i popuni formular.
		 */
		BlogEntyForm fm = new BlogEntyForm();
		BlogEntry e = DAOProvider.getDAO().getBlogEntry(eid);
		
		if (e == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}
		
		fm.popuniIzBlogEntry(e);

		/*
		 * Zakaći atribute i pošalji na renderiranje.
		 */
		req.setAttribute("autor", nick);
		req.setAttribute("zapis", fm);
		req.setAttribute("mode", "edit");
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
				resp);
	}

	/**
	 * Metoda koja omogućava dohvaćanje traženog bloga.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderBlogEntry(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
		BlogEntry e = DAOProvider.getDAO().getBlogEntry(eid);
		if (e == null || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}
		
		/*
		 * Zakvaći atribute i pošalji na renderiranje.
		 */
		req.setAttribute("zapis", e);
		req.setAttribute("autor", nick);
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req,
				resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		/*
		 * Ako se radi o dodavanju / editiranju bloga.
		 */
		String metodaBlog = req.getParameter("metoda-Blog");
		if (metodaBlog != null) {
			String path = req.getPathInfo().substring(1);
			String autor = path.substring(0, path.indexOf('/'));
			if (!metodaBlog.equals("Pohrani")) {
				resp.sendRedirect(req.getContextPath() + "/servleti/author/"
						+ autor);
				return;
			}

			/*
			 * Popuni novi obrazac i azuriraj vremena;
			 */
			BlogEntry novi = new BlogEntry();
			BlogEntyForm fm = new BlogEntyForm();
			fm.popuniIzHttpRequesta(req);
			fm.popuniUBlogEntry(novi);
			novi.setCreatedAt(new Date());
			novi.setLastModifiedAt(new Date());

			BlogUser usr = DAOProvider.getDAO().getBlogUser(autor);
			if (usr == null) {
				// greska
			} else {
				novi.setCreator(usr);
			}

			/*
			 * Izmjena postojećeg bloga.
			 */
			if (req.getParameter("id") != null) {
				Long id = Long.valueOf(req.getParameter("id"));
				BlogEntry stari = DAOProvider.getDAO().getBlogEntry(id);
				stari.setLastModifiedAt(novi.getLastModifiedAt());
				stari.setTitle(novi.getTitle());
				stari.setText(novi.getText());
				DAOProvider.getDAO().addUpdateBlogEntry(stari, true);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/"
						+ autor);
				return;
			}

			/*
			 * Dodavanje novog bloga.
			 */
			DAOProvider.getDAO().addUpdateBlogEntry(novi, false);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/"
					+ autor);
			return;
		}

		/*
		 * Ako se dodaje komentar.
		 */
		String metodaKomentar = req.getParameter("metoda-komentar");
		if (metodaKomentar != null) {
			/*
			 * Dohvati komentar.
			 */
			String komentar = req.getParameter("comment");

			/*
			 * Dohvati blog kojemu komentar pripada.
			 */
			Long blogID = Long.valueOf(req.getParameter("blogID"));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(blogID);

			/*
			 * Generiraj komentar za spremanju u bazu.
			 */
			BlogComment comment = new BlogComment();
			comment.setBlogEntry(entry);
			comment.setMessage(komentar);
			comment.setPostedOn(new Date());

			/*
			 * Odredi da li je anonymous ili je logirani korisnik.
			 */
			if (req.getSession().getAttribute("current.user.nick") != null) {
				String nick = (String) req.getSession().getAttribute(
						"current.user.nick");
				BlogUser usr = DAOProvider.getDAO().getBlogUser(nick);
				if (usr != null) {
					comment.setUsersEMail(usr.getEmail());
				}
			} else {
				comment.setUsersEMail("anonymous@anonymous.com");
			}

			/*
			 * Spremi u bazu.
			 */
			DAOProvider.getDAO().addComment(comment);

			resp.sendRedirect(req.getContextPath() + req.getServletPath()
					+ req.getPathInfo());
			return;
		}
		
	}
	
	/**
	 * Metoda koja provjera postoji li traženi nick u bazi.
	 * @param nick nick koji se testira.
	 * @return true ako postoji, inače false.
	 */
	private boolean checkIfNickExists(String nick) {
		return DAOProvider.getDAO().getBlogUser(nick) != null;
	}
}
