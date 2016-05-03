package com.alexrnl.commons.arguments.validators;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Validate that a {@link Path} represent an existing file.
 * @author Alex
 */
public class ExistingPathValidator implements ParameterValidator<Path> {
	
	@Override
	public void validate (final Path parameterValue) throws IllegalArgumentException {
		if (!Files.exists(parameterValue)) {
			throw new IllegalArgumentException(parameterValue + " does not exists");
		}
	}
	
}
