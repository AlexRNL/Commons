package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

/**
 * Parser for the char primitive type.
 * @author Alex
 */
public class CharParser implements ParameterParser {
	
	@Override
	public Class<?> getFieldType () {
		return char.class;
	}
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setChar(target, parameter.charAt(0));
		} catch (final IndexOutOfBoundsException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a char", e);
		}
	}
}
