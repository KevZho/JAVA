package hr.fer.zemris.java.hw16.web.servlets;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Razred koji omogućava generiranje SHA-1 kriptografski sažetaka za lozinke.
 * 
 * @author Igor Smolkovič
 * 
 */
public class SHACrypto {

	public static String hashValue(String password) {
		String sha1 = null;
		MessageDigest crypt = null;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e1) {
		} catch (UnsupportedEncodingException e) {
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
