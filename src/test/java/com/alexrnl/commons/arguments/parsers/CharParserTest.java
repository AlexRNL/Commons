package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link CharParser} class.
 * @author Alex
 */
public class CharParserTest {
	/** The char parser */
	private CharParser	parser;
	/** The field to set */
	private char		field;
	/** The reference to the field to set */
	private Field		fieldReference;
	
	/**
	 * Set up test attributes.
	 * @throws NoSuchFieldException
	 *         if the field does not exists.
	 * @throws SecurityException
	 *         if the field is not accessible.
	 */
	@Before
	public void setUp () throws NoSuchFieldException, SecurityException {
		parser = new CharParser();
		fieldReference = CharParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link CharParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		parser.parse(this, fieldReference, "e");
		assertEquals('e', field);
	}
	
	/**
	 * Test method for error cases of {@link CharParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalArgument () {
		parser.parse(this, fieldReference, "");
	}
}
