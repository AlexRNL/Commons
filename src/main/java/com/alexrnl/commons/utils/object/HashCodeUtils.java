package com.alexrnl.commons.utils.object;

import java.util.Arrays;
import java.util.Objects;

/**
 * Utility methods for computing {@link Object}'s {@link Object#hashCode() hash code}.
 * @author Alex
 */
public final class HashCodeUtils {
	
	/** Default prime number to use for hash code computation */
	public static final int		PRIME_FOR_HASHCODE		= 31;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private HashCodeUtils () {
		super();
		throw new InstantiationError("Instantiation of class " + HashCodeUtils.class + " is forbidden");
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 */
	public static int hashCode (final int prime, final Iterable<Object> attributes) {
		int result = 1;
		int iteration = 1;
		for (final Object object : attributes) {
			result = iteration++ * prime * result + Objects.hashCode(object);
		}
		return result;
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @param prime
	 *        the prime value to use for the hash code.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, Iterable)
	 */
	public static int hashCode (final int prime, final Object[] attributes) {
		return hashCode(prime, Arrays.asList(attributes));
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, Iterable)
	 */
	public static int hashCode (final Iterable<Object> attributes) {
		return hashCode(PRIME_FOR_HASHCODE, attributes);
	}
	
	/**
	 * Compute the hash code of the list of objects provided.<br />
	 * @param attributes
	 *        the list of attributes.
	 * @return the hash code for the given set of attributes.
	 * @see #hashCode(int, Iterable)
	 */
	public static int hashCode (final Object[] attributes) {
		return hashCode(Arrays.asList(attributes));
	}
	
}
