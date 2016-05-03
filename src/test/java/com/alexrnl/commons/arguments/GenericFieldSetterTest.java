package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.arguments.parsers.WIntegerParser;

/**
 * Test suite for the {@link GenericFieldSetter} class.
 * @author Alex
 */
public class GenericFieldSetterTest {
	/** The field setter to test */
	private GenericFieldSetter	fieldSetter;
	/** The target object for argument parsing */
	private Target				target;
	/** The parsing results */
	private ParsingResults		results;
	
	/**
	 * A class representing the target for the argument parsing.
	 */
	private static class Target {
		/** The number parameter to test */
		@Param(names = { "-n" }, description = "The number")
		private Integer				n;
	}
	
	/**
	 * Set up test parameters.
	 */
	@Before
	public void setUp () {
		final Parameter parameter = ArgumentsTests.getParameterForField(Target.class, "n");
		fieldSetter = new GenericFieldSetter(parameter, new WIntegerParser());
		target = new Target();
		results = new ParsingResults(Collections.singleton(parameter));
	}
	
	/**
	 * Test method for {@link GenericFieldSetter#setValue(ParsingResults, ParsingParameters)}.
	 */
	@Test
	public void testSetValue () {
		fieldSetter.setValue(results, new ParsingParameters(target, "20", "-n"));
		assertEquals(Integer.valueOf(20), target.n);
		assertTrue(results.getRequiredParameters().isEmpty());
	}
	
	/**
	 * Test method for {@link GenericFieldSetter#setValue(ParsingResults, ParsingParameters)} with
	 * an invalid value.
	 */
	@Test
	public void testSetInvalidValue () {
		fieldSetter.setValue(results, new ParsingParameters(target, "2a0", "-n"));
		assertNull(target.n);
		assertEquals(1, results.getRequiredParameters().size());
		assertEquals(1, results.getErrors().size());
	}
}
