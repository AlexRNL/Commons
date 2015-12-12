package com.alexrnl.commons.arguments;

/**
 * Implementation of {@link ParameterValueSetter} interface that handle cases when no parser was
 * found.
 * @author Alex
 */
public class ParserNotFound implements ParameterValueSetter {
	/** The parameterType for which there was not found */
	private final String	parameterType;
	
	/**
	 * Constructor #1.<br />
	 * @param parameterType
	 *        the parameterType type for which parsers were not found.
	 */
	public ParserNotFound (final String parameterType) {
		super();
		this.parameterType = parameterType;
	}
	
	@Override
	public void setValue (final ParsingResults results, final Object target, final String value, final String argument) {
		results.addError("No parser found for type " + parameterType + " (parameterType " + argument + ").");
	}
	
}
