package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link UndefinedItemClass} class.
 * @author Alex
 */
public class UndefinedItemClassTest {
	/** The undefined item class setter to test */
	private UndefinedItemClass	undefinedItemClass;
	/** The target object for the collection setter */
	private Target				target;
	/** The parsing results to update */
	private ParsingResults		results;
								
	/**
	 * A class representing the target for the argument parsing.
	 */
	private static class Target {
		/** The parameter to test */
		@Param(names = { "-s" }, description = "List of strings")
		private List<String> strings;
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		target = new Target();
		target.strings = new ArrayList<>();
		final Parameter parameter = CollectionFieldSetterTest.getParameterForField(Target.class, "strings");
		results = new ParsingResults(Collections.singleton(parameter));
		undefinedItemClass = new UndefinedItemClass(parameter);
	}
	
	/**
	 * Test method for {@link UndefinedItemClass#setValue(ParsingResults, ParsingParameters)}.
	 */
	@Test
	public void testSetValue () {
		undefinedItemClass.setValue(results, new ParsingParameters(target, "hjo", "--s"));
		assertEquals(1, results.getErrors().size());
		assertTrue(target.strings.isEmpty());
		assertEquals(1, results.getRequiredParameters().size());
	}
}
