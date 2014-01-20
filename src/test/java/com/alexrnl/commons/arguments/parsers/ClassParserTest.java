package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ClassParser} class.
 * @author Alex
 */
public class ClassParserTest {
	/** The class parser to test */
	private ClassParser	classParser;
	/** The class parser with a root package defined */
	private ClassParser packageClassParser;
	/** The field to set */
	private Class<?>	field;
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
		classParser = new ClassParser();
		packageClassParser = new ClassParser(ParsersTests.class.getPackage());
		fieldReference = ClassParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Class.class, classParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		classParser.parse(this, fieldReference, "com.alexrnl.commons.arguments.parsers.ParsersTests");
		assertEquals(ParsersTests.class, field);
	}
	
	/**
	 * Test method for the package parser in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParseWithPackage () {
		packageClassParser.parse(this, fieldReference, "ClassParserTest");
		assertEquals(ClassParserTest.class, field);
	}
	
	/**
	 * Test method for class not found in {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testParseClassNotFound () {
		classParser.parse(this, fieldReference, "fr.alexrnl.Main");
	}
}
