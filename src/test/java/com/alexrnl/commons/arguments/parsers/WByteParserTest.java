package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WByteParser} class.
 * @author Alex
 */
public class WByteParserTest {
	/** The class parser to test */
	private WByteParser	wByteParser;
	/** The field to set */
	private Byte		field;
	/** The reference to the field */
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
		wByteParser = new WByteParser();
		fieldReference = WByteParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Byte.class, wByteParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wByteParser.parse(this, fieldReference, "28");
		assertEquals(Integer.valueOf(28).byteValue(), field.byteValue());
	}
	
	/**
	 * Test method for class not found in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wByteParser.parse(this, fieldReference, "4a");
	}
}
