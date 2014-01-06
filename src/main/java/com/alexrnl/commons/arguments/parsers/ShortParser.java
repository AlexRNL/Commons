package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

/**
 * Parser for the short primitive type.
 * @author Alex
 */
public class ShortParser implements ParameterParser {
	
	@Override
	public Class<?> getFieldType () {
		return short.class;
	}
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setShort(target, Short.parseShort(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a short", e);
		}
	}
}
