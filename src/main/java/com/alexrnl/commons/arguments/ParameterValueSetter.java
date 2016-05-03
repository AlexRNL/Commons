package com.alexrnl.commons.arguments;

/**
 * Interface for classes that are setting the actual field values in parameter parsing.
 * @author Alex
 */
public interface ParameterValueSetter {
	
	/**
	 * Set the value in the parameter.<br />
	 * @param results
	 *        the parsing result to update.
	 * @param parameters
	 *        the parameters of the parsing.
	 */
	void setValue (final ParsingResults results, ParsingParameters parameters);
}
