package hr.fer.zemris.java.tecaj.hw15.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw15.dao.DAO;
import hr.fer.zemris.java.tecaj.hw15.dao.DAOException;
import hr.fer.zemris.java.tecaj.hw15.model.PollData;
import hr.fer.zemris.java.tecaj.hw15.model.PollOptionsData;

/**
 * Razred koji implementira sučelje DAO.
 * 
 * @author Igor Smolkovič
 * 
 */
public class SQLDao implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollData> getAvailablePolls() {
		List<PollData> ankete = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollData data = new PollData(rs.getLong(1),
								rs.getString(2), "");
						ankete.add(data);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return ankete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PollData getPoll(long id) {
		PollData data = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						data = new PollData(rs.getLong(1), rs.getString(2),
								rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addPoll(String title, String message) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		long noviID = -1;
		try {
			pst = con.prepareStatement(
					"INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, message);
			try {
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				try {
					if (rs != null && rs.next()) {
						noviID = rs.getLong(1);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return noviID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollOptionsData> getPollOptions(long id) {
		List<PollOptionsData> opcije = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("select id, optionTitle, optionLink, votesCount from PollOptions where pollID=? order by id");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptionsData option = new PollOptionsData(
								rs.getLong(1), rs.getString(2),
								rs.getString(3), rs.getInt(4));
						opcije.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return opcije;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addPollOption(String title, String link, long pollID, int votes) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		long noviID = -1;
		try {
			pst = con
					.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, link);
			pst.setLong(3, pollID);
			pst.setInt(4, votes);
			try {
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				try {
					if (rs != null && rs.next()) {
						noviID = rs.getLong(1);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return noviID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getOptionCount(long pollID, long optionID) {
		Integer data = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("select id, optionTitle, optionLink, votesCount from PollOptions where pollID=? and id=?");
			pst.setLong(1, Long.valueOf(pollID));
			pst.setLong(2, optionID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						data = Integer.valueOf(rs.getInt(4));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOptionCount(long pollID, long optionID, int count) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("update pollOptions set votesCount=? where id=? and pollID=?");
			pst.setLong(1, Integer.valueOf(count));
			pst.setLong(2, Long.valueOf(optionID));
			pst.setLong(3, pollID);
			try {
				pst.executeUpdate();
			} catch (Exception ex) {
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Pogreška prilikom dohvata liste korisnika.", ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkIfPollExist(String title) {
		boolean exists = false;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls where title=?");
			pst.setString(1, title);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						exists = true;
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(
					"Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return exists;
	}
}
