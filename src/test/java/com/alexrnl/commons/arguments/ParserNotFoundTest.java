package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ParserNotFound} class.
 * @author Alex
 */
public class ParserNotFoundTest {
	/** The parser not found setter to test */
	private ParserNotFound	parserNotFound;
	/** The parsing results to update */
	private ParsingResults		results;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		parserNotFound = new ParserNotFound("java.lang.String");
		results = new ParsingResults(Collections.<Parameter>emptySet());
	}
	
	/**
	 * Test method for {@link ParserNotFound#setValue(ParsingResults, ParsingParameters)}.
	 */
	@Test
	public void testSetValue () {
		parserNotFound.setValue(results, new ParsingParameters(new Object(), "hjo", "--s"));
		assertEquals(1, results.getErrors().size());
		assertTrue(results.getRequiredParameters().isEmpty());
	}
}
