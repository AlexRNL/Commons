package com.alexrnl.commons.arguments;

import java.util.logging.Logger;

import com.alexrnl.commons.arguments.parsers.ParameterParser;
import com.alexrnl.commons.arguments.validators.ParameterValidator;
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
	public void setValue (final ParsingResults results, final ParsingParameters parameters) {
		try {
			parser.parse(parameters.getTarget(), parameter.getField(), parameters.getValue());
			// TODO factorize with collection field setter
			if (!parameter.getValidator().equals(ParameterValidator.class)) {
				if (parameter.getValidator().isInterface()) {
					results.addError("Validator " + parameter.getValidator().getName()
							+ " could not be instantiated for parameter " + parameters.getArgument());
					LG.warning("Parameter " + parameters.getArgument() + "'s validator cannot be instantiated: validator "
							+ parameter.getValidator() + " is an interface");
					return;
				}
				parameter.getValidator().newInstance().validate(parameter.getField().get(parameters.getTarget()));
			}
			results.removeRequiredParameter(parameter);
		} catch (final IllegalArgumentException | InstantiationException | IllegalAccessException e) {
			results.addError("Value " + parameters.getValue() + " could not be assigned to parameter "
					+ parameters.getArgument());
			LG.warning("Parameter " + parameters.getArgument() + " value could not be set: "
					+ ExceptionUtils.display(e));
		}
	}
}
