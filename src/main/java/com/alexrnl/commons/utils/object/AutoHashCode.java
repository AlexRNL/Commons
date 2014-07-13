package com.alexrnl.commons.utils.object;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Class which automate hash code and comparison method on objects.
 * @author Alex
 */
public final class AutoHashCode {
	/** Logger */
	private static Logger						lg			= Logger.getLogger(AutoHashCode.class.getName());
	
	/** Unique instance of the class */
	private static AutoHashCode					singleton	= new AutoHashCode();
	
	/** Method for hash code per class */
	private final Map<Class<?>, Set<Method>>	hashCodeMethods;
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private AutoHashCode () {
		super();
		hashCodeMethods = new HashMap<>();
	}
	
	/**
	 * Return the unique instance of the class.
	 * @return the singleton.
	 */
	public static AutoHashCode getInstance () {
		return singleton;
	}
	
	/**
	 * Retrieve the methods used to compare objects.
	 * @param objClass
	 *        the class of the objects.
	 * @return the set with the equals methods.
	 */
	private Set<Method> getHashCodeMethods (final Class<?> objClass) {
		synchronized (hashCodeMethods) {
			if (!hashCodeMethods.containsKey(objClass)) {
				hashCodeMethods.put(objClass, ReflectUtils.retrieveMethods(objClass, Field.class));
			}
			return hashCodeMethods.get(objClass);
		}
	}
	
	/**
	 * Compute the hash code of an object using the values of the methods marked {@link Field}
	 * with the use for hash code parameter set at <code>true</code>.
	 * @param obj
	 *        the object used to compute the hash code.
	 * @return the hash code for the specified object.
	 */
	public int hashCode (final Object obj) {
		final List<Object> attributes = new ArrayList<>();
		for (final Method method : getHashCodeMethods(obj.getClass())) {
			try {
				if (lg.isLoggable(Level.FINE)) {
					lg.fine("method: " + method.getName());
				}
				attributes.add(method.invoke(obj, (Object[]) null));
			} catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				lg.warning("Could not add the value of the method " + method.getName() + " because "
						+ ExceptionUtils.display(e));
			}
		}
		return HashCodeUtils.hashCode(attributes);
	}
}
