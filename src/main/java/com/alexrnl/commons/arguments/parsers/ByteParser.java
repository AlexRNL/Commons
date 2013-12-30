package com.alexrnl.commons.arguments.parsers;

import java.lang.reflect.Field;

import com.alexrnl.commons.arguments.ParameterParser;

/**
 * Parser for the byte primitive type.
 * @author Alex
 */
public class ByteParser implements ParameterParser {
	
	@Override
	public void parse (final Object target, final Field field, final String parameter) {
		try {
			field.setByte(target, Byte.parseByte(parameter));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Could not parse " + parameter + " as a byte", e);
		}
	}
}
