package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link LongParser} class.
 * @author Alex
 */
public class LongParserTest {
	/** The long parser */
	private LongParser	parser;
	/** The field to set */
	private long		field;
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
		parser = new LongParser();
		fieldReference = LongParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link LongParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		parser.parse(this, fieldReference, "123456789");
		assertEquals(123456789, field);
	}
	
	/**
	 * Test method for error cases of {@link LongParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalArgument () {
		parser.parse(this, fieldReference, "1a82");
	}
}
