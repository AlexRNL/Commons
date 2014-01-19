package com.alexrnl.commons.arguments.parsers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link PathParser} class.
 * @author Alex
 */
public class PathParserTest {
	/** The class parser to test */
	private PathParser	pathParser;
	/** The field to set */
	private Path		field;
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
		pathParser = new PathParser();
		fieldReference = PathParserTest.class.getDeclaredField("field");
		fieldReference.setAccessible(true);
	}
	
	/**
	 * Test method for {@link AbstractParser#getFieldType()}.
	 */
	@Test
	public void testGetFieldType () {
		assertEquals(Path.class, pathParser.getFieldType());
	}
	
	/**
	 * Test method for {@link AbstractParser#parse(Object, Field, String)}.
	 */
	@Test
	public void testParse () {
		pathParser.parse(this, fieldReference, "src/main/config.xml");
		assertEquals(Paths.get("./src/main/config.xml").normalize(), field.normalize());
	}
	
}
