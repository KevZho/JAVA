package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred <code>Cypto</code> omogućava kriptiranje/dekriptiranje 
 * datoteka uz korištenje AES-128 simetričnog kriptoalgoritma u ECB
 * modu. Podržano je računanje i provjera SHA1 kriptografskih sažetaka.  
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Crypto {
	/** Checksha regex **/
	private static final String REGEX_CHECKSHA = "(?i)checksha";
	/** Encrypt regex **/
	private static final String REGEX_ENCRYPT = "(?i)encrypt";
	/** Decrypt regex **/
	private static final String REGEX_DECRYPT = "(?i)decrypt";
	/** Regex za hexText **/
	private static final String REGEX_HEX = "[0-9a-fA-f]+";
	/** String večina sha sažetka **/
	private static final int SHA_SIZE = 40;
	/** Večina inicijalizacijskog vektora i ključa u hex znakovima **/
	private static final int AES_SIZE = 32;
	/**  Veličina buffera **/
	private static final int BUFFER_SIZE = 4096;
	/**
	 * Opis korištenja programa.
	 */
	private static final String usage = "Usage:\tCrypto checksha <file>\r\n"
			+ "\tCrypto encrypt <file> <output file>\r\n"
			+ "\tCrptyo decrypt <file> <output file>";
	
	/**
	 * Metoda koja se poziva prilikom pokretanja program.
	 *
	 * @param args Argumenti komandne linije. Ako se koriste 2 argumenta mora se
	 * 				provoditi provjera sažetka, args[1] je ulazna datoteka. 
	 * 				Ako su 3 argumenta provodi se kriptiranje ili dekriptiranje,
	 * 				args[1] je ulazna datoteka, args[2] je izlazna datoteka.
	 * 				U svim slučajevima args[0] određuje operaciju.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println(usage);
		} else if (args.length == 2 && args[0].matches(REGEX_CHECKSHA)) {
			shaDigesting(Paths.get(args[1]));
		} else if (args.length == 3 && args[0].matches(REGEX_ENCRYPT)) {
			aesDelegate(Paths.get(args[1]), Paths.get(args[2]), true);
		} else if (args.length == 3 && args[0].matches(REGEX_DECRYPT)) {
			aesDelegate(Paths.get(args[1]), Paths.get(args[2]), false);
		} else {
			System.err.println(usage);
		}
	}
	
	/**
	 * Metoda koja učitava aes ključ i inicijalizacijski vektor te pokreće 
	 * kriptiranje ili dekriptiranje datoteka.
	 *
	 * @param inFile Ulazna datoteka.
	 * @param outFile Izlazna datoteka.
	 * @param encrypt ako je true provodi se kriptiranje, inače dekriptiranje.
	 */
	private static void aesDelegate(Path inFile, Path outFile, boolean encrypt) {
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		String keyString = readHexText(AES_SIZE);
		byte[] key = hextobyte(keyString);
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
		String ivString = readHexText(AES_SIZE);
		byte[] iv = hextobyte(ivString);

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (NoSuchPaddingException e) {
			System.exit(-1);
		} catch (InvalidKeyException e) {
			System.exit(-1);
		} catch (InvalidAlgorithmParameterException e) {
			System.exit(-1);
		}
		boolean status = encryptDecrypt(inFile, outFile, cipher);
		if (status) {
			System.out.print(encrypt ? "Encryption" : "Decryption");
			System.out.printf(" completed. Generated file %s based on file %s.%n",
					outFile.getFileName(), inFile.getFileName());
		} 
	}
	
	
	/**
	 * Metoda koja provodi aes kriptiranje i dekriptiranje.
	 *
	 * @param inFile Ulazna datoteka.
	 * @param outFile Izlazna datoteka.
	 * @param cipher Cipher objekt koji provodi kriptiranje/dekriptiranje.
	 * @return true ako je uspješno provedeno kriptiranje/dekriptiranje, inače
	 * 				 false.
	 */
	private static boolean encryptDecrypt(Path inFile, Path outFile, Cipher cipher) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(inFile.toString());
			output = new FileOutputStream(outFile.toString());
			byte[] buffer = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = input.read(buffer)) != -1) {
				byte[] current = cipher.update(buffer, 0, read);
				if (current != null) {
					output.write(current);
				}
			}
			byte[] current = cipher.doFinal();
			if (current != null) {
				output.write(current);
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (IllegalBlockSizeException e) {
			return false;
		} catch (BadPaddingException e) {
			return false;
		} finally {
			if (input != null) {
				try { input.close(); } catch (IOException ignorable) { }
			}
			if (output != null) {
				try { output.close(); } catch (IOException ignorable) { }
			}
		}
		return true;
	}

	/**
	 * Metoda koja računa sha sažetak ulazne datoteke i uspoređuje ga s učitanim
	 * sažetkom.
	 *
	 * @param inFile Binarna datoteka na temelju koje se računa sažetak.
	 */
	private static void shaDigesting(Path inFile) {
		byte[] digest = null;
		MessageDigest sha = null;
		try (FileInputStream input = new FileInputStream(inFile.toString())) {
			sha = MessageDigest.getInstance("SHA");
			byte[] buffer = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = input.read(buffer)) != -1) {
				sha.update(buffer, 0, read);
			}
			digest = sha.digest();
		} catch (FileNotFoundException e) {
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} 
		System.out.printf("Please provide expected sha signature for file %s:%n> ", inFile.getFileName());
		String digestString = readHexText(SHA_SIZE);
		byte[] digestInput = hextobyte(digestString);
		if (digest == null || digestInput == null) {
			System.out.println("Error while comparing digests.");
			System.exit(-1);
		}
		System.out.printf("Digesting completed. Digest of %s ", inFile.getFileName());
		if (Arrays.equals(digest, digestInput)) {
			System.out.println("matches expected digest.");
		} else {
			System.out.println("does not match the expected digest. Digest was:" + bytetohex(digest));
		}
	}
	
	/**
	 * Metoda koja učitava hex string sa tipkovnice. Program završava
	 * ukoliko hex string nije dovoljne duljine ili sadrži nedozvoljene 
	 * znakove.
	 *
	 * @param size Veličina stringa koji se učitava.
	 * @return Hex string učitan s tipkovnice.
	 */
	private static String readHexText(int size) {
		String input = null;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in), StandardCharsets.UTF_8)
		);
		try {
			input = reader.readLine();
			if (input == null) {
				System.exit(-1);
			}
			input = input.trim();
			if (!input.matches(REGEX_HEX) || input.length() != size) {
				System.err.println("Illegal hex string");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.out.println("Error while reading hex string");
			System.exit(-1);
		}
		return input;
	}
	
	/**
	 * Metoda koja pretvara hex string u polje bajtova.
	 *
	 * @param text Hex string koji se pretvara u polje bajtova.
	 * @return Polje bajtova nastalo iz hex stringa.
	 */
	private static byte[] hextobyte(String text) {
		int size = text.length();
		byte[] out = new byte[size / 2];
		for (int i = 0; i < size; i += 2) {
			out[i / 2] = (Integer.decode("0x" + text.substring(i, i + 2))).byteValue();
		}
		return out;
	}
	
	/**
	 * Metoda koja pretvara polje bajtova u hex string.
	 *
	 * @param array Polje bajtova koje se pretvara u hex string.
	 * @return Hex string nastao iz polja bajtova.
	 */
	private static String bytetohex(byte[] array) {
		StringBuilder builder = new StringBuilder();
		for (byte b : array) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
}
