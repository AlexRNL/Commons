package com.alexrnl.commons.arguments;

import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.ParameterParser;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Implementation of {@link ParameterValueSetter} for any kind of field.
 * @author Alex
 */
public class GenericFieldSetter implements ParameterValueSetter {
	/** Logger */
	private static final Logger		LG		= Logger.getLogger(GenericFieldSetter.class.getName());
	
	/** The parameter whose value to set */
	private final Parameter			parameter;
	/** The parser for the parameter */
	private final ParameterParser	parser;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter whose value to set.
	 * @param parser
	 *        the parser to use.
	 */
	public GenericFieldSetter (final Parameter parameter, final ParameterParser parser) {
		super();
		this.parameter = parameter;
		this.parser = parser;
	}
	
	@Override
	public void setValue (final ParsingResults results, final Object target, final String value, final String argument) {
		try {
			parser.parse(target, parameter.getField(), value);
			results.removeRequiredParameter(parameter);
		} catch (final IllegalArgumentException e) {
			results.addError("Value " + value + " could not be assigned to parameter " + argument);
			LG.warning("Parameter " + argument + " value could not be set: "
					+ ExceptionUtils.display(e));
		}
	}
}
