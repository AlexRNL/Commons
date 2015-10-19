package com.alexrnl.commons.arguments.validators;

/**
 * Interface for parameter validators.<br />
 * @author barfety_a
 * @param <T>
 *        the type of the parameters to validate. TODO keep generic?
 */
public interface ParameterValidator<T> {
	
	/**
	 * Validate the value of a parameter.<br />
	 * @param parameterValue
	 *        the value to validate.
	 * @throws IllegalArgumentException
	 *         if the parameter value is not valid.
	 */
	void validate (T parameterValue) throws IllegalArgumentException;
}
