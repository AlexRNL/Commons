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
	 * Return the string specified, or an empty {@link String}, if it is <code>null</code>.
	 * @param s
	 *        the string to return.
	 * @return an empty String if the parameter is <code>null</code>, else the string.
	 */
	public static String emptyIfNull (final String s) {
		return replaceIfNull(s, "");
	}
	
	/**
	 * Separate a list of object with a given separator.<br />
	 * @param separator
	 *        the separator.
	 * @param s
	 *        the objects to display.
	 * @return the objects separated by the separator specified.
	 */
	public static String separateWith (final String separator, final Object... s) {
		final StringBuilder builder = new StringBuilder();
		
		for (final Object object : s) {
			builder.append(object).append(separator);
		}
		
		return builder.substring(0, builder.length() - separator.length()).toString();
	}
	
}