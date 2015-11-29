package com.alexrnl.commons.arguments;

/**
 * Interface for classes that are setting the actual field values in parameter parsing.
 * @author Alex
 */
public interface ParameterValueSetter {
	
	/**
	 * Set the value in the parameter.<br />
	 * TODO set the 3 parameters into a custom class?
	 * @param results
	 *        the parsing result to update.
	 * @param target
	 *        the target object.
	 * @param value
	 *        the value to set.
	 * @param argument
	 *        the name of the argument used.
	 */
	void setValue (final ParsingResults results, final Object target, final String value, final String argument);
}
