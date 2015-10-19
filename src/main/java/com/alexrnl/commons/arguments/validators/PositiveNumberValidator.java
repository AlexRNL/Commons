package com.alexrnl.commons.arguments.validators;


/**
 * Validates that a number is strictly positive.
 * @author barfety_a
 * @param <T> The type of data to validate.
 */
public class PositiveNumberValidator<T extends Number> implements ParameterValidator<T> {
	
	@Override
	public void validate (final T parameterValue) throws IllegalArgumentException {
		if (parameterValue.doubleValue() <= 0) {
			throw new IllegalArgumentException("parameter must be positive");
		}
	}
	
}
