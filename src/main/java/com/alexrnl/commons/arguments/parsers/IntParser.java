package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

import com.alexrnl.commons.arguments.ParameterParser;

/**
 * Parser for the integer primitive type.
 * @author Alex
 */
public class IntParser implements ParameterParser {
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setInt(target, Integer.parseInt(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as an integer", e);
		}
	}
}