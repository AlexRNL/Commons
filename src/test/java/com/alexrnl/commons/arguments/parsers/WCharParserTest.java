package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link WCharParser} class.
 * @author Alex
 */
public class WCharParserTest {
	/** The class parser to test */
	private WCharParser	wCharParser;
	/** The field to set */
	private Character	field;
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
		wCharParser = new WCharParser();
		fieldReference = WCharParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Character.class, wCharParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		wCharParser.parse(this, fieldReference, "28");
		assertEquals('2', field.charValue());
	}
	
}
