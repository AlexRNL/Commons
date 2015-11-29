package com.alexrnl.commons.arguments;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.ParameterParser;

/**
 * Factory for the parameter value setters.
 * @author Alex
 */
public interface ParameterValueSetterFactory {
	
	/**
	 * Add a parameter parser to the current arguments.<br />
	 * Using an abstract parser allows to dynamically build collection using the parser.
	 * @param <T>
	 *        the type of parameter parsed.
	 * @param parser
	 *        the parser to add.
	 * @return <code>true</code> if a previous parser was already set for this field type.
	 * @see ParameterParser
	 * @see AbstractParser
	 */
	<T> boolean addParameterParser (final AbstractParser<T> parser);
	
	/**
	 * Create the {@link ParameterValueSetter} for the specified parameter.
	 * @param parameter
	 *        the parameter whose value to set.
	 * @return the parameter value setter.
	 */
	ParameterValueSetter createParameterValueSetter (Parameter parameter);
}
