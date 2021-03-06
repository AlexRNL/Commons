package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

/**
 * Abstract class to ease implementation of parsers.<br />
 * @author Alex
 * @param <T>
 *        the type of the field parsed.
 */
public abstract class AbstractParser<T> implements ParameterParser {
	/** The field type generated by this parser. */
	private final Class<T>	fieldType;
	
	/**
	 * Constructor #1.<br />
	 * @param fieldType
	 *        the type of the field which will be parsed by this parser.
	 */
	public AbstractParser (final Class<T> fieldType) {
		super();
		this.fieldType = fieldType;
	}
	
	@Override
	public Class<?> getFieldType () {
		return fieldType;
	}
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.set(target, getValue(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a "
					+ fieldType, e);
		}
	}
	
	/**
	 * Convert the {@link String} value into the appropriate type for the target field.
	 * @param parameter
	 *        the string to convert.
	 * @return the generated object.
	 * @throws IllegalArgumentException
	 *         if the specified parameter could not be read properly.
	 */
	public abstract T getValue (String parameter);
}
