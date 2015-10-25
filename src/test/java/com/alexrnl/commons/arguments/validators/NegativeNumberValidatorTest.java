package com.alexrnl.commons.arguments.validators;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link NegativeNumberValidator} class.
 * @author Alex
 */
public class NegativeNumberValidatorTest {
	/** The validator to test */
	private NegativeNumberValidator<Long>	validator;
	
	/**
	 * Set up test parameters.
	 */
	@Before
	public void setUp () {
		validator = new NegativeNumberValidator<>();
	}
	
	/**
	 * Test method for {@link NegativeNumberValidator#validate(Number)} with a positive number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidatePositive () {
		validator.validate(1L);
	}
	
	/**
	 * Test method for {@link NegativeNumberValidator#validate(Number)} with a negative number.
	 */
	@Test
	public void testValidateNegative () {
		validator.validate(-1L);
	}
	
	/**
	 * Test method for {@link NegativeNumberValidator#validate(Number)} with zero.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidateNull () {
		validator.validate(0L);
	}
}
