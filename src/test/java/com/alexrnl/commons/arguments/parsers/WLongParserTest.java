package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WLongParser} class.
 * @author Alex
 */
public class WLongParserTest {
	/** The class parser to test */
	private WLongParser	wLongParser;
	/** The field to set */
	private Long		field;
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
		wLongParser = new WLongParser();
		fieldReference = WLongParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Long.class, wLongParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wLongParser.parse(this, fieldReference, "884288");
		assertEquals(Long.valueOf(884288), field);
	}
	
	/**
	 * Test method for not a long in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		wLongParser.parse(this, fieldReference, "^128");
	}
}
