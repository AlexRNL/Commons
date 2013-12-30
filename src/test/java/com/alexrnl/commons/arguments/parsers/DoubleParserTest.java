package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link DoubleParser} class.
 * @author Alex
 */
public class DoubleParserTest {
	/** The delta for comparing double numbers */
	private static final double	DELTA_DOUBLE_COMPARISON	= 0.001;
	
	/** The double parser */
	private DoubleParser		parser;
	/** The field to set */
	private double				field;
	/** The reference to the field to set */
	private Field				fieldReference;
	
	/**
	 * Set up test attributes.
	 * @throws NoSuchFieldException
	 *         if the field does not exists.
	 * @throws SecurityException
	 *         if the field is not accessible.
	 */
	@Before
	public void setUp () throws NoSuchFieldException, SecurityException {
		parser = new DoubleParser();
		fieldReference = DoubleParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link DoubleParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(double.class, parser.getFieldType());
	}
	
	/**
	 * Test method for {@link DoubleParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		parser.parse(this, fieldReference, "123456789.987654321");
		assertEquals(123456789.987654321, field, DELTA_DOUBLE_COMPARISON);
	}
	
	/**
	 * Test method for error cases of {@link DoubleParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalArgument () {
		parser.parse(this, fieldReference, "1.8.2");
	}
}
