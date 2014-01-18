package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WDoubleParser} class.
 * @author Alex
 */
public class WDoubleParserTest {
	/** The class parser to test */
	private WDoubleParser	wDoubleParser;
	/** The field to set */
	private Double			field;
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
		wDoubleParser = new WDoubleParser();
		fieldReference = WDoubleParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Double.class, wDoubleParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wDoubleParser.parse(this, fieldReference, "28.1988");
		assertEquals(28.1988, field, 0.00001);
	}
	
	/**
	 * Test method for not a double in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wDoubleParser.parse(this, fieldReference, "4.a");
	}
}
