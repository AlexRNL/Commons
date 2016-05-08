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
	
	/**
	 * Validate the value with the validator of the parameter.
	 * @param <T>
	 * @param validatorClass
	 *        the class of the validator
	 * @param value
	 *        the value of the parameter.
	 * @param results
	 *        the results to update.
	 * @param parameters
	 *        the parameters of the parsing.
	 * @return the value validated.
	 * @throws ReflectiveOperationException
	 *         if a reflection operation fails.
	 */
	static <T> T validateValueForParameter (final Class<? extends ParameterValidator> validatorClass, final T value,
			final ParsingResults results, final ParsingParameters parameters) throws ReflectiveOperationException {
		if (!validatorClass.equals(ParameterValidator.class)) {
			if (validatorClass.isInterface()) {
				results.addError("Validator " + validatorClass.getName() + " could not be instantiated for parameter "
						+ parameters.getArgument());
				LG.warning("Parameter " + parameters.getArgument() + "'s validator cannot be instantiated: validator "
						+ validatorClass + " is an interface");
				return value;
			}
			validatorClass.newInstance().validate(value);
		}
		return value;
	}
	
	@Override
	public void setValue (final ParsingResults results, final ParsingParameters parameters) {
		try {
			parser.parse(parameters.getTarget(), parameter.getField(), parameters.getValue());
			// TODO factorize with collection field setter
			validateValueForParameter(parameter.getValidator(), parameter.getField().get(parameters.getTarget()), results, parameters);
			results.removeRequiredParameter(parameter);
		} catch (final IllegalArgumentException | ReflectiveOperationException e) {
			results.addError("Value " + parameters.getValue() + " could not be assigned to parameter "
					+ parameters.getArgument());
			LG.warning("Parameter " + parameters.getArgument() + " value could not be set: "
					+ ExceptionUtils.display(e));
		}
	}
}
