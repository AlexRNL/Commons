package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WShortParser} class.
 * @author Alex
 */
public class WShortParserTest {
	/** The class parser to test */
	private WShortParser	wShortParser;
	/** The field to set */
	private Short			field;
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
		wShortParser = new WShortParser();
		fieldReference = WShortParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Short.class, wShortParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wShortParser.parse(this, fieldReference, "2648");
		assertEquals(Integer.valueOf(2648).shortValue(), field.shortValue());
	}
	
	/**
	 * Test method for not a short in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wShortParser.parse(this, fieldReference, "18486484608489489940486458");
	}
}
