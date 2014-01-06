package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link StringParser} class.
 * @author Alex
 */
public class StringParserTest {
	/** The class parser to test */
	private StringParser	stringParser;
	/** The field to set */
	private String			field;
	/** The reference to the field */
	private Field			fieldReference;
	
	/**
	 * Set up test attributes.
	 * @throws NoSuchFieldException
	 *         if the field does not exists.
	 * @throws SecurityException
	 *         if the field is not accessible.
	 */
	@Before
	public void setUp () throws NoSuchFieldException, SecurityException {
		stringParser = new StringParser();
		fieldReference = StringParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(String.class, stringParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		stringParser.parse(this, fieldReference, "abaManLau");
		assertEquals("abaManLau", field);
	}
	
}
