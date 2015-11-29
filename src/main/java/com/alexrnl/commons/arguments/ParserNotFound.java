package com.alexrnl.commons.arguments;

/**
 * Implementation of {@link ParameterValueSetter} interface that handle cases when no parser was
 * found.
 * @author Alex
 */
public class ParserNotFound implements ParameterValueSetter {
	/** The parameter for which parsers were not found */
	private final Parameter	parameter;
	
	/**
	 * Constructor #1.<br />
	 * @param parameter
	 *        the parameter for which parsers were not found.
	 */
	public ParserNotFound (final Parameter parameter) {
		super();
		this.parameter = parameter;
	}
	
	@Override
	public void setValue (final ParsingResults results, final Object target, final String value, final String argument) {
		results.addError("No parser found for type " + parameter.getField().getType().getName()
				+ " (parameter " + argument + ").");
	}
	
}
