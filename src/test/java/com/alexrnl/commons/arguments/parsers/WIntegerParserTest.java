package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WIntegerParser} class.
 * @author Alex
 */
public class WIntegerParserTest {
	/** The class parser to test */
	private WIntegerParser	wIntegerParser;
	/** The field to set */
	private Integer			field;
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
		wIntegerParser = new WIntegerParser();
		fieldReference = WIntegerParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Integer.class, wIntegerParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wIntegerParser.parse(this, fieldReference, "88");
		assertEquals(Integer.valueOf(88), field);
	}
	
	/**
	 * Test method for not a integer in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wIntegerParser.parse(this, fieldReference, ".32^");
	}
}
