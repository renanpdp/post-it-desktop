package app.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class responsible for implementing encrypting methods.
 * 
 * @author renan
 */
public class Encrypter {
	
	/**
	 * Method that encrypts a message using md5 hash and returns it.
	 * 
	 * @param message
	 * @return {@link String}
	 */
	public static String md5Hash(final String message) {
		String digest = null;
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			final byte[] hash = md.digest(message.getBytes("UTF-8"));
			// converting byte array to Hexadecimal String
			final StringBuilder sb = new StringBuilder(2 * hash.length);
			for (final byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest;
	}
	
}
