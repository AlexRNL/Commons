package com.alexrnl.commons.utils.object;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities methods for reflection and dynamic Java usage.<br />
 * @author Alex
 */
public final class ReflectUtils {
	/** Logger */
	private static final Logger		LG							= Logger.getLogger(ReflectUtils.class.getName());
	
	/** The common prefix for attribute setters in classes */
	public static final String		SETTER_PREFIX				= "set";
	/** The common prefix for attribute getters in classes */
	public static final String		GETTER_PREFIX				= "get";
	/** The common prefix for boolean attribute getters in classes */
	public static final String		GETTER_BOOL_PREFIX			= "is";
	/** The package separator */
	public static final Character	PACKAGE_SEPARATOR			= '.';
	/** The name of the valueOf method in an enum */
	private static final String		ENUM_VALUE_OF_METHOD_NAME	= "valueOf";
	/** The name of the values method in an enum */
	private static final String		ENUM_VALUES_METHOD_NAME		= "values";

	/**
	 * Constructor #1.<br />
	 * Private default constructor.
	 */
	private ReflectUtils () {
		super();
		throw new InstantiationError("Instantiation of class " + ReflectUtils.class + " is forbidden");
	}
	
	/**
	 * Retrieve the methods for the class marked with the specified annotation.<br />
	 * @param objClass
	 *        the class to parse.
	 * @param annotationClass
	 *        the annotation to find.
	 * @return a {@link Set} with the {@link Method} annotated with <code>annotationClass</code>.
	 * @see Class#getMethods()
	 */
	public static Set<Method> retrieveMethods (final Class<?> objClass, final Class<? extends Annotation> annotationClass) {
		final Set<Method> methods = new HashSet<>();
		for (final Method method : objClass.getMethods()) {
			if (annotationClass == null || method.getAnnotation(annotationClass) != null) {
				if (LG.isLoggable(Level.FINE)) {
					LG.fine("Added method: " + method.getName());
				}
				methods.add(method);
			}
		}
		return methods;
	}
	
	/**
	 * Retrieve the fields of the class which are annotated with the specified class.<br />
	 * <em>Note that this method will return private fields as well.</em>
	 * @param objClass
	 *        the class to parse.
	 * @param annotationClass
	 *        the annotation to find.
	 * @return a {@link Set} with the {@link Field}s annotated with the <code>annotationClass</code>.
	 * @see Class#getDeclaredFields()
	 */
	public static Set<Field> retrieveFields (final Class<?> objClass, final Class<? extends Annotation> annotationClass) {
		final Set<Field> fields = new HashSet<>();
		for (final Field field : objClass.getDeclaredFields()) {
			if (annotationClass == null || field.getAnnotation(annotationClass) != null) {
				if (LG.isLoggable(Level.FINE)) {
					LG.fine("Added field: " + field.getName());
				}
				fields.add(field);
			}
		}
		return fields;
	}
	
	/**
	 * Invoke the following list of methods (with no parameter) and return the result in a
	 * {@link List}.
	 * @param target
	 *        the target object.
	 * @param methods
	 *        the methods to invoke.
	 * @return the result of the method call.
	 * @throws InvocationTargetException
	 *         if one of the underlying method throws an exception
	 * @throws IllegalArgumentException
	 *         if the method is an instance method and the specified object argument is not an
	 *         instance of the class or interface declaring the underlying method (or of a subclass
	 *         or implementor thereof); if the number of actual and formal parameters differ; if an
	 *         unwrapping conversion for primitive arguments fails; or if, after possible
	 *         unwrapping, a parameter value cannot be converted to the corresponding formal
	 *         parameter type by a method invocation conversion.
	 * @throws IllegalAccessException
	 *         if one of the Method object is enforcing Java language access control and the underlying
	 *         method is inaccessible.
	 */
	public static List<Object> invokeMethods (final Object target, final List<Method> methods)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final List<Object> results = new ArrayList<>(methods.size());
		
		for (final Method method : methods) {
			results.add(method.invoke(target, (Object[]) null));
		}
		
		return results;
	}
	
	/**
	 * Find all public interfaces of the specified class.<br />
	 * @param objClass
	 *        the class to find all interfaces.
	 * @return a set with all the public interfaces possible for the specified class.
	 */
	public static Set<Class<?>> getAllInterfaces (final Class<?> objClass) {
		Objects.requireNonNull(objClass);
		final Set<Class<?>> allInterfaces = new HashSet<>();
		Class<?> currentClass = objClass;
		while (currentClass != null) {
			allInterfaces.add(currentClass);
			allInterfaces.addAll(Arrays.asList(currentClass.getInterfaces()));
			currentClass = currentClass.getSuperclass();
		}
		if (LG.isLoggable(Level.FINE)) {
			LG.fine("Public interfaces of " + objClass + ": " + allInterfaces);
		}
		return allInterfaces;
	}
	
	/**
	 * Method to allow having a complete code coverage over Java enum.
	 * @param enumClass
	 *        the class of the enumeration.
	 */
	public static void fullEnumCoverage (final Class<? extends Enum<?>> enumClass) {
		try {
			for (final Enum<?> o : (Enum[]) enumClass.getMethod(ENUM_VALUES_METHOD_NAME).invoke(null)) {
				enumClass.getMethod(ENUM_VALUE_OF_METHOD_NAME, String.class).invoke(null, o.name());
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
