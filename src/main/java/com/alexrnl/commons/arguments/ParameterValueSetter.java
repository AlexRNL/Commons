package com.alexrnl.commons.arguments;

import java.util.logging.Logger;

import com.alexrnl.commons.arguments.validators.ParameterValidator;

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
	
	/**
	 * Validate the value with the validator of the parameter.
	 * @param <T>
	 *        the type of the value.
	 * @param validatorClass
	 *        the class of the validator.
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
	default <T> T validateValueForParameter (final Class<? extends ParameterValidator> validatorClass, final T value,
			final ParsingResults results, final ParsingParameters parameters) throws ReflectiveOperationException {
		if (!validatorClass.equals(ParameterValidator.class)) {
			if (validatorClass.isInterface()) {
				results.addError("Validator " + validatorClass.getName() + " could not be instantiated for parameter "
						+ parameters.getArgument());
				Logger.getLogger(ParameterValueSetter.class.getName())
					.warning("Parameter " + parameters.getArgument() + "'s validator cannot be instantiated: validator "
						+ validatorClass + " is an interface");
				return value;
			}
			validatorClass.newInstance().validate(value);
		}
		return value;
	}
}
