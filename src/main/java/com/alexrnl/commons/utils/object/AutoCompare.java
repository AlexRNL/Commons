package com.alexrnl.commons.utils.object;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Class which automate hash code and comparison method on objects.
 * @author Alex
 */
public final class AutoCompare {
	/** Logger */
	private static Logger						lg			= Logger.getLogger(AutoCompare.class.getName());
	
	/** Unique instance of the class */
	private static AutoCompare					singleton	= new AutoCompare();
	
	/** Method for equals per class */
	private final Map<Class<?>, Set<Method>>	equalsMethods;
	/** Comparator for the equals method */
	private final AttributeComparator			comparator;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private AutoCompare () {
		super();
		comparator = new AttributeComparator();
		equalsMethods = new HashMap<>();
	}
	
	/**
	 * Return the unique instance of the class.
	 * @return the singleton.
	 */
	public static AutoCompare getInstance () {
		return singleton;
	}
	
	/**
	 * Retrieve the methods used to compare objects.
	 * @param objClass
	 *        the class of the objects.
	 * @return the set with the equals methods.
	 */
	private Set<Method> getEqualsMethods (final Class<?> objClass) {
		if (!equalsMethods.containsKey(objClass)) {
			equalsMethods.put(objClass, ReflectUtils.retrieveMethods(objClass, Field.class));
		}
		return equalsMethods.get(objClass);
	}
	
	/**
	 * Compare two object using the return value of the methods marked as {@link Field}.
	 * @param left
	 *        the first object to compare.
	 * @param right
	 *        the second object to compare.
	 * @param <T>
	 *        the type of the objects to be compared
	 * @return <code>true</code> if all the attributes marked {@link Field} are equals.
	 */
	public <T> boolean compare (final T left, final T right) {
		// Basic cases
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (!left.getClass().equals(right.getClass())) {
			return false;
		}
		
		final List<Method> methods = new ArrayList<>();
		final List<Object> leftAttributes;
		final List<Object> rightAttributes;
		methods.addAll(getEqualsMethods(left.getClass()));
		try {
			leftAttributes = ReflectUtils.invokeMethods(left, methods);
			rightAttributes = ReflectUtils.invokeMethods(right, methods);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			lg.warning("Could not compute list of attribute, exception while invoking methods ("
					+ ExceptionUtils.display(e) + ")");
			// FIXME re-throw exception? the result will be inconsistent, it might be better to do it.
			throw new RuntimeException("Comparison exception", e);
		}
		
		synchronized (this) {
			comparator.clear();
			for (int indexAttribute = 0; indexAttribute < leftAttributes.size(); ++indexAttribute) {
				comparator.add(leftAttributes.get(indexAttribute), rightAttributes.get(indexAttribute));
			}
			
			return comparator.areEquals();
		}
	}
	
}