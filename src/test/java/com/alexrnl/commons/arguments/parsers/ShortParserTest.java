package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ShortParser} class.
 * @author Alex
 */
public class ShortParserTest {
	/** The short parser */
	private ShortParser	parser;
	/** The field to set */
	private short		field;
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
		parser = new ShortParser();
		fieldReference = ShortParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link ShortParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(short.class, parser.getFieldType());
	}
	
	/**
	 * Test method for {@link ShortParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		parser.parse(this, fieldReference, "4");
		assertEquals(4, field);
	}
	
	/**
	 * Test method for error cases of {@link ShortParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalArgument () {
		parser.parse(this, fieldReference, ".12");
	}
}
