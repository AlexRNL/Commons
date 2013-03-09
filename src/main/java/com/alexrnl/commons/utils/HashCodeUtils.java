package com.alexrnl.commons.utils;

import java.util.Arrays;
import java.util.Objects;

import com.alexrnl.commons.CommonsConstants;

/**
 * Utility methods for computing {@link Object}'s {@link Object#hashCode() hash code}.
 * @author Alex
 */
public final class HashCodeUtils {
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private HashCodeUtils () {
		super();
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 */
	public static int hashCode (final int nullValue, final int prime, final Iterable<Object> attributes) {
		int result = 1;
		for (final Object object : attributes) {
			result = prime * result + Objects.hashCode(object);
		}
		return result;
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final int prime, final Object[] attributes) {
		return hashCode(nullValue, prime, Arrays.asList(attributes));
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final Iterable<Object> attributes) {
		return hashCode(nullValue, CommonsConstants.PRIME_FOR_HASHCODE, attributes);
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param nullValue
	 *        the value to use when the attribute is <code>null</code>.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final int nullValue, final Object[] attributes) {
		return hashCode(nullValue, Arrays.asList(attributes));
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final Iterable<Object> attributes) {
		return hashCode(0, attributes);
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, int, Iterable)
	 */
	public static int hashCode (final Object[] attributes) {
		return hashCode(Arrays.asList(attributes));
	}
}
