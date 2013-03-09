package com.alexrnl.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class which provides an easy way to compare attributes of a same object.<br />
 * This class also contains a utility method to compute the hash code of a list of objects.
 * @author Alex
 */
public class AttributeComparator {
	/** Logger */
	private static Logger		lg					= Logger.getLogger(AttributeComparator.class.getName());
	
	/**
	 * Represent a couple of attributes of the same type.
	 * @author Alex
	 * @param <T>
	 *        the type of the attributes compared.
	 */
	public static class Attributes<T> {
		/** The 'left' attribute */
		private final T	left;
		/** The 'right' attribute */
		private final T	right;
		
		/**
		 * Constructor #.<br />
		 * @param left
		 *        the left attribute.
		 * @param right
		 *        the right attribute.
		 */
		public Attributes (final T left, final T right) {
			super();
			this.left = left;
			this.right = right;
		}
		
		/**
		 * Compare both attributes and returns <code>true</code> if the attributes are equals.
		 * Uses the {@link Object#equals(Object)} method to compare the objects.
		 * @return <code>true</code> if both objects are equals.
		 */
		public boolean areEquals () {
			return AttributeComparator.areEquals(left, right);
		}
	}
	
	/** List of the attributes to be compared */
	private final List<Attributes<?>> attributes;
	
	/**
	 * Constructor #1.<br />
	 */
	public AttributeComparator () {
		super();
		attributes = new ArrayList<>();
	}
	
	/**
	 * Add attributes to be compared.
	 * @param left
	 *        the left attribute.
	 * @param right
	 *        the right attribute.
	 */
	public <T> void add (final T left, final T right) {
		add(new Attributes<>(left, right));
	}
	
	/**
	 * Add a new attribute to be compared.
	 * @param attribute
	 *        the attribute to add.
	 */
	public <T> void add (final Attributes<T> attribute) {
		attributes.add(attribute);
	}
	
	/**
	 * Clear the list of attributes.<br />
	 * Allow to use the comparator for other purposes.
	 */
	public void clear () {
		attributes.clear();
	}
	
	/**
	 * Return the current number of attributes to be compared.
	 * @return the number of attributes.
	 */
	public int getAttributeNumber () {
		return attributes.size();
	}
	
	/**
	 * Compare the attributes provided.
	 * @return <code>true</code> if all the attributes are equals.
	 */
	public boolean areEquals () {
		for (final Attributes<?> attribute : attributes) {
			if (!attribute.areEquals()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Compare both attributes and returns <code>true</code> if the attributes are equals.<br />
	 * Works with arrays as well.
	 * @param left
	 *        the left attribute.
	 * @param right
	 *        the right attribute.
	 * @return <code>true</code> if both objects are equals.
	 */
	public static <T> boolean areEquals(final T left, final T right) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Comparing " + left + " and " + right);
		}
		
		// Arrays required a different process
		if (left != null && left.getClass().isArray()) {
			return Objects.deepEquals(left, right);
		}
		
		return Objects.equals(left, right);
	}
	
}
