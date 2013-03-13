package com.alexrnl.commons.utils;


/**
 * Utility methods for {@link String}.<br />
 * @author Alex
 */
public final class StringUtils {
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private StringUtils () {
		super();
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
}
