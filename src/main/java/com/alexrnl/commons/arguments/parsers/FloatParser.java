package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

/**
 * Parser for the float primitive type.
 * @author Alex
 */
public class FloatParser implements ParameterParser {
	
	@Override
	public Class<?> getFieldType () {
		return float.class;
	}
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setFloat(target, Float.parseFloat(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a float", e);
		}
	}
}
