package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

import com.alexrnl.commons.arguments.ParameterParser;

/**
 * Parser for the double primitive type.
 * @author Alex
 */
public class DoubleParser implements ParameterParser {
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setDouble(target, Double.parseDouble(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a double", e);
		}
	}
}
