package com.alexrnl.commons.arguments.validators;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link NonEmptyStringValidator}.
 * @author Alex
 */
public class NonEmptyStringValidatorTest {
	/** The string validator to test */
	private NonEmptyStringValidator stringValidator;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		stringValidator = new NonEmptyStringValidator();
	}
	
	/**
	 * Validate that a non-empty string is validated.
	 */
	@Test
	public void testNonEmptyStringValidate () {
		stringValidator.validate("hello, world");
	}
	
	/**
	 * Validate that an empty string is failing the validation.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyStringValidate () {
		stringValidator.validate("");
	}
}
