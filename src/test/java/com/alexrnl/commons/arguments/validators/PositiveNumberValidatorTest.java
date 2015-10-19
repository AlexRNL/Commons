package com.alexrnl.commons.arguments.validators;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link PositiveNumberValidator} class.
 * @author Alex
 */
public class PositiveNumberValidatorTest {
	/** The validator to test */
	private PositiveNumberValidator<Long>	validator;
	
	/**
	 * Set up test parameters.
	 */
	@Before
	public void setUp () {
		validator = new PositiveNumberValidator<>();
	}
	
	/**
	 * Test method for {@link PositiveNumberValidator#validate(Number)} with a positive number.
	 */
	@Test
	public void testValidatePositive () {
		validator.validate(1L);
	}
	
	/**
	 * Test method for {@link PositiveNumberValidator#validate(Number)} with a negative number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidateNegative () {
		validator.validate(-1L);
	}
	
	/**
	 * Test method for {@link PositiveNumberValidator#validate(Number)} with zero.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidateNull () {
		validator.validate(0L);
	}
}
