package com.alexrnl.commons.arguments.validators;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ExistingPathValidator} class.
 * @author Alex
 */
public class ExistingPathValidatorTest {
	/** The path validator to test. */
	private ExistingPathValidator pathValidator;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		pathValidator = new ExistingPathValidator();
	}
	
	/**
	 * Test that a file that exists is validated.
	 * @throws URISyntaxException
	 *         if there is an issue with the URI.
	 */
	@Test
	public void testValidateExistingFile () throws URISyntaxException {
		pathValidator.validate(Paths.get(ExistingPathValidatorTest.class.getResource("/locale.xml").toURI()));
	}
	
	/**
	 * Test that a file that does not exists fails the validation.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidateNonExistingFile () {
		pathValidator.validate(Paths.get("cb.xml"));
	}
}
