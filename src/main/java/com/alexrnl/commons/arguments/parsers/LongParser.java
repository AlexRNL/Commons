package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

/**
 * Parser for the long primitive type.
 * @author Alex
 */
public class LongParser implements ParameterParser {
	
	@Override
	public Class<?> getFieldType () {
		return long.class;
	}
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setLong(target, Long.parseLong(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a long", e);
		}
	}
}
