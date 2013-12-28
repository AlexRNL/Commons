package com.alexrnl.commons.arguments;

import java.lang.reflect.Field;

/**
 * Interface for the parameters parsers.<br />
 * Allow to dynamically override default behavior of argument parsing, or enrich it.
 * @author Alex
 */
public interface ParameterParser {
	
	/**
	 * Parse the parameter retrieved into the specified field.
	 * @param target
	 *        the target object to modify.
	 * @param field
	 *        the field to update.
	 * @param parameter
	 *        the value to assign.
	 */
	void parse (Object target, Field field, String parameter);
	
}
