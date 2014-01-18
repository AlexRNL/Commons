package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WFloatParser} class.
 * @author Alex
 */
public class WFloatParserTest {
	/** The class parser to test */
	private WFloatParser	wFloatParser;
	/** The field to set */
	private Float			field;
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
		wFloatParser = new WFloatParser();
		fieldReference = WFloatParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Float.class, wFloatParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wFloatParser.parse(this, fieldReference, "28.8");
		assertEquals(28.8, field, 0.001);
	}
	
	/**
	 * Test method for not a float in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wFloatParser.parse(this, fieldReference, ".la");
	}
}
