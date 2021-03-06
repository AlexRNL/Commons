package com.alexrnl.commons.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.html.HTML.Tag;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.error.TopLevelError;


/**
 * Utility methods for {@link String}.<br />
 * @author Alex
 */
public final class StringUtils {
	/** Logger */
	private static final Logger			LG					= Logger.getLogger(StringUtils.class.getName());
	
	/** The HTML tag for a new HTML document */
	public static final String			HTML_HTML_START		= "<" + Tag.HTML + ">";
	/** The HTML tag for the end of an HTML document */
	public static final String			HTML_HTML_END		= "</" + Tag.HTML + ">";
	/** The HTML tag for new line */
	public static final String			HTML_NEW_LINE		= "<" + Tag.BR + " />";
	/** The new line (line feed) character */
	public static final Character		NEW_LINE			= '\n';
	/** The new line (carriage return) character */
	public static final Character		NEW_LINE_CR			= '\r';
	/** The space character */
	public static final Character		SPACE				= ' ';
	/** The name of the MD5 algorithm */
	private static final String			MD5_ALGORITHM_NAME	= "MD5";
	/** The MD5 message digest algorithm */
	private static final MessageDigest	MD5_MESSAGE_DIGEST;
	/** The name of the SHA-1 algorithm */
	private static final String			SHA1_ALGORITHM_NAME	= "SHA-1";
	/** The SHA-1 message digest algorithm */
	private static final MessageDigest	SHA1_MESSAGE_DIGEST;
	/**
	 * Load the message digest algorithm
	 */
	static {
		try {
			MD5_MESSAGE_DIGEST = MessageDigest.getInstance(MD5_ALGORITHM_NAME);
			SHA1_MESSAGE_DIGEST = MessageDigest.getInstance(SHA1_ALGORITHM_NAME);
		} catch (final NoSuchAlgorithmException e) {
			LG.severe("Message digest algorithm not found: "
					+ ExceptionUtils.display(e));
			throw new TopLevelError(e);
		}
	}

	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private StringUtils () {
		super();
		throw new InstantiationError("Instantiation of class " + StringUtils.class + " is forbidden");
	}
	
	/**
	 * Check if the character is a new line (CR or LF).
	 * @param c the character to test.
	 * @return <code>true</code> if the character is a new line.
	 */
	public static boolean isNewLine (final char c) {
		return c == NEW_LINE || c == NEW_LINE_CR;
	}
	
	/**
	 * Check if the string specified is <code>null</code> or empty.
	 * @param s
	 *        the string to check.
	 * @return <code>true</code> if the string is <code>null</code> or empty.
	 */
	public static boolean nullOrEmpty (final String s) {
		return s == null || s.isEmpty();
	}
	
	/**
	 * Check if the string specified is neither <code>null</code> nor empty.
	 * @param s
	 *        the string to check.
	 * @return <code>true</code> if the string is neither <code>null</code> nor empty.
	 */
	public static boolean neitherNullNorEmpty (final String s) {
		return s != null && !s.isEmpty();
	}
	
	/**
	 * Return the string specified, or its replacement if it is <code>null</code>.
	 * @param s
	 *        the string to return.
	 * @param replace
	 *        its replacement, is the first string is <code>null</code>.
	 * @return <code>s</code>, or <code>replace</code> if <code>s</code> is <code>null</code>.
	 */
	public static String replaceIfNull (final String s, final String replace) {
		return s == null ? replace : s;
	}
	
	/**
	 * Return the string specified, or an empty {@link String}, if it is <code>null</code>.
	 * @param s
	 *        the string to return.
	 * @return an empty String if the parameter is <code>null</code>, else the string.
	 */
	public static String emptyIfNull (final String s) {
		return replaceIfNull(s, "");
	}
	
	/**
	 * Separate a list of objects with a given separator.<br />
	 * @param separator
	 *        the separator.
	 * @param s
	 *        the objects to display.
	 * @return the objects separated by the separator specified.
	 */
	public static String separateWith (final String separator, final Object... s) {
		return separateWith(separator, Arrays.asList(s));
	}
	
	/**
	 * Separate a collection of objects with a given separator.<br />
	 * @param separator
	 *        the separator.
	 * @param s
	 *        the objects to display.
	 * @return the objects separated by the separator specified.
	 */
	public static String separateWith (final String separator, final Iterable<?> s) {
		final StringBuilder builder = new StringBuilder();
		
		for (final Object object : s) {
			builder.append(object).append(separator);
		}
		
		return builder.substring(0, builder.length() - separator.length());
	}
	
	/**
	 * Computes the MD5 of a string.<br />
	 * Uses the JVM's default charset.
	 * @param text
	 *        the text to hash.
	 * @return the MD5 hash of the text.
	 */
	public static String getMD5 (final String text) {
		return getMD5(text, Charset.defaultCharset());
	}
	
	/**
	 * Computes the MD5 of a string.
	 * @param text
	 *        the text to hash.
	 * @param charset the charset.
	 * @return the hash of the text.
	 */
	public static String getMD5 (final String text, final Charset charset) {
		return getHash(text, charset, MD5_MESSAGE_DIGEST);
	}
	
	/**
	 * Computes the SHA-1 of a string.<br />
	 * Uses the JVM's default charset.
	 * @param text
	 *        the text to hash.
	 * @return the hash of the text.
	 */
	public static String getSHA1 (final String text) {
		return getSHA1(text, Charset.defaultCharset());
	}
	
	/**
	 * Computes the SHA-1 of a string.
	 * @param text
	 *        the text to hash.
	 * @param charset
	 *        the charset of the text.
	 * @return the hash of the text.
	 */
	public static String getSHA1 (final String text, final Charset charset) {
		return getHash(text, charset, SHA1_MESSAGE_DIGEST);
	}
	
	/**
	 * Computes the hash of a given text.<br />
	 * @param text
	 *        the text to hash.
	 * @param charset
	 *        the charset of the text.
	 * @param algorithm
	 *        the algorithm to use for the hash.
	 * @return the hash of the text.
	 */
	public static String getHash (final String text, final Charset charset, final MessageDigest algorithm) {
		final StringBuilder buffer = new StringBuilder();
		algorithm.reset();
		algorithm.update(text.getBytes(charset));
		
		final byte[] digest = algorithm.digest();
		for (final byte element : digest) {
			int value = element;
			if (value < 0) {
				value += Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
			}
			// Add a zero in case of 'short' hash.
			final String hex = Integer.toHexString(value);
			if (hex.length() == 1) {
				buffer.append(0);
			}
			buffer.append(hex);
		}
		return buffer.toString();
	}
	
	/**
	 * Remove the multiple spaces which compose a String.<br />
	 * @param input
	 *        the String to clean-up.
	 * @return the input, with only single spaces.
	 */
	public static String removeMultipleSpaces (final String input) {
		Objects.requireNonNull(input, "Cannot work on null input String");
		// If the string has only spaces
		if (input.trim().isEmpty()) {
			return input.trim();
		}
		
		// Other cases
		final StringBuilder result = new StringBuilder();
		for (final String part : input.split(SPACE.toString())) {
			if (!part.isEmpty()) {
				result.append(part).append(SPACE);
			}
		}
		return result.substring(0, result.length() - 1);
	}
	
	/**
	 * Cut a {@link String} in multiple line, so they don't exceed a specified length.<br />
	 * @param input
	 *        the input string to be cut.
	 * @param maxLength
	 *        the maximum length allowed.
	 * @return the string divided in several lines.
	 */
	public static String splitInLines (final String input, final int maxLength) {
		Objects.requireNonNull(input, "Cannot work on null input String");
		if (maxLength < 1) {
			LG.warning("Cannot split line with maxLength=" + maxLength);
			throw new IllegalArgumentException("maxLength for spliting in line cannot be less than 1 (was " + maxLength + ")");
		}
		
		// If input is shorter than the limit
		if (input.trim().length() < maxLength) {
			return input.trim();
		}
		
		// Other cases
		final StringBuilder result = new StringBuilder();
		String remaining = removeMultipleSpaces(input);
		while (remaining.length() > maxLength) {
			String nextLine = remaining.substring(0, maxLength);
			if (LG.isLoggable(Level.FINE)) {
				LG.fine("Next characters to split '" + nextLine + "'");
			}
			final int lastSpace = nextLine.lastIndexOf(SPACE);
			if (lastSpace > 0) {
				nextLine = nextLine.substring(0, lastSpace);
			} else {
				LG.warning("No space in line '" + nextLine + "' cannot properly split String.");
			}
			if (LG.isLoggable(Level.FINE)) {
				LG.fine("Next line is '" + nextLine + "'");
			}
			result.append(nextLine).append(NEW_LINE);
			remaining = remaining.substring(nextLine.length()).trim();
		}
		result.append(remaining);
		
		return result.toString();
	}
	
	/**
	 * Cut a {@link String} in multiple line, so they don't exceed a certain length.<br />
	 * @param input
	 *        the input string to be cut.
	 * @param maxLength
	 *        the maximum length allowed.
	 * @return the content spread on several lines (using <code>&lt;br /&gt;</code>) and between
	 *         <code>&lt;html&gt;</code> tags.
	 * @see #splitInLines(String, int)
	 */
	public static String splitInLinesHTML (final String input, final int maxLength) {
		return (HTML_HTML_START + splitInLines(input, maxLength) + HTML_HTML_END).replace(String.valueOf(NEW_LINE), HTML_NEW_LINE);
	}

	/**
	 * Build a list with the characters of the string.<br />
	 * @param s
	 *        the string to parse.
	 * @return the list with the characters of the string.
	 */
	public static List<Character> toCharList (final String s) {
		final List<Character> charList = new ArrayList<>();
		for (final char c : s.toCharArray()) {
			charList.add(c);
		}
		return charList;
	}
}