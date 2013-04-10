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
	 * Return the {@link Object#toString() toString} of the specified object, or its replacement if
	 * it is <code>null</code>.
	 * @param o
	 *        the object to display.
	 * @param replace
	 *        its replacement, is the object is <code>null</code>.
	 * @return the {@link Object#toString() toString} of the specified object, or
	 *         <code>replace</code> if <code>o</code> is <code>null</code>.
	 */
	public static String replaceIfNull (final Object o, final String replace) {
		return o == null ? replace : o.toString();
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
	 * Return the {@link Object#toString() toString} of the specified object, or an empty
	 * {@link String}, if it is <code>null</code>.
	 * @param o
	 *        the object to display.
	 * @return an empty String if the object is <code>null</code>, else the
	 *         {@link Object#toString() toString} of the object.
	 */
	public static String emptyIfNull (final Object o) {
		return replaceIfNull(o, "");
	}
}
