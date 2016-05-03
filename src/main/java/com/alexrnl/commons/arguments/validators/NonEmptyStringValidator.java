package com.alexrnl.commons.arguments.validators;

/**
 * Validate that a {@link String} is not empty.
 * @author Alex
 */
public class NonEmptyStringValidator implements ParameterValidator<String> {
	
	@Override
	public void validate (final String parameterValue) throws IllegalArgumentException {
		if (parameterValue.isEmpty()) {
			throw new IllegalArgumentException("string cannot be empty");
		}
	}
}
