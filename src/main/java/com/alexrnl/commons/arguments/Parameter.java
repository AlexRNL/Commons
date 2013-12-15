package com.alexrnl.commons.arguments;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A class which describe a parameter.<br />
 * @author Alex
 */
public class Parameter {
	/** The reference to the field of this parameter */
	private final Field			field;
	/** The names of this parameter */
	private final Set<String>	names;
	/** <code>true</code> if the parameter is required */
	private final boolean		required;
	/** The description of the parameter */
	private final String		description;
	
	/**
	 * Constructor #1.<br />
	 * @param field
	 *        the field of this parameter.
	 * @param param
	 *        the {@link Param} annotation associated to the field.
	 */
	public Parameter (final Field field, final Param param) {
		super();
		this.field = field;
		names = new HashSet<>(Arrays.asList(param.names()));
		required = param.required();
		description = param.description();
	}
	
	/**
	 * Return the attribute field.
	 * @return the attribute field.
	 */
	public Field getField () {
		return field;
	}
	
	/**
	 * Return the attribute names.
	 * @return the attribute names.
	 */
	public Set<String> getNames () {
		return Collections.unmodifiableSet(names);
	}
	
	/**
	 * Return the attribute required.
	 * @return the attribute required.
	 */
	public boolean isRequired () {
		return required;
	}
	
	/**
	 * Return the attribute description.
	 * @return the attribute description.
	 */
	public String getDescription () {
		return description;
	}
	
}
