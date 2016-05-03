package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.arguments.parsers.AbstractParser;
import com.alexrnl.commons.arguments.parsers.StringParser;

/**
 * Test suite for the {@link CollectionFieldSetter} class.
 * @author Alex
 */
public class CollectionFieldSetterTest {
	/** The field setter to test */
	private CollectionFieldSetter<String>	fieldSetter;
	/** The target object for the collection setter */
	private Target							target;
	/** The parsing results to update */
	private ParsingResults					results;
	
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
		final Parameter parameter = ArgumentsTests.getParameterForField(Target.class, "strings");
		results = new ParsingResults(Collections.singleton(parameter));
		fieldSetter = new CollectionFieldSetter<>(parameter, new StringParser());
	}
	
	/**
	 * Test method for {@link CollectionFieldSetter#setValue(ParsingResults, ParsingParameters)}.
	 */
	@Test
	public void testSetValue () {
		fieldSetter.setValue(results, new ParsingParameters(target, "hjo", "--s"));
		assertTrue(results.getErrors().isEmpty());
		assertEquals(Arrays.asList("hjo"), target.strings);
		assertTrue(results.getRequiredParameters().isEmpty());
	}
	
	/**
	 * Test method for {@link CollectionFieldSetter#setValue(ParsingResults, ParsingParameters)} on
	 * a <code>null</code> collection.
	 */
	@Test
	public void testSetValueOnNullCollection () {
		target.strings = null;
		fieldSetter.setValue(results, new ParsingParameters(target, "hjo", "--s"));
		assertEquals(1, results.getErrors().size());
		assertNull(target.strings);
		assertEquals(1, results.getRequiredParameters().size());
	}
	
	/**
	 * Test method for {@link CollectionFieldSetter#setValue(ParsingResults, ParsingParameters)}
	 * with a parser that failed to parse the value.
	 */
	@Test
	public void testSetValueFailed () {
		fieldSetter = new CollectionFieldSetter<>(ArgumentsTests.getParameterForField(Target.class, "strings"),
				new AbstractParser<String>(String.class) {
					@Override
					public String getValue (final String parameter) {
						throw new IllegalArgumentException("Parsing error");
					}
		});
		fieldSetter.setValue(results, new ParsingParameters(target, "hjo", "--s"));
		assertEquals(1, results.getErrors().size());
		assertTrue(target.strings.isEmpty());
		assertEquals(1, results.getRequiredParameters().size());
	}
}
