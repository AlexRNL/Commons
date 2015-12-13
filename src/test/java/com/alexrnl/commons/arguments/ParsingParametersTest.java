package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ParsingParameters} class.
 * @author Alex
 */
public class ParsingParametersTest {
	/** The parsing parameters to test */
	private ParsingParameters	parsingParameters;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		parsingParameters = new ParsingParameters(this, "88.2", "-xa");
	}
	
	/**
	 * Test method for {@link ParsingParameters#ParsingParameters(Object, String, String)} with
	 * a <code>null</code> target.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testNullTarget() {
		new ParsingParameters(null, "88.2", "-xa");
	}
	
	/**
	 * Test method for {@link ParsingParameters#ParsingParameters(Object, String, String)} with
	 * a <code>null</code> value.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testNullValue() {
		new ParsingParameters(this, null, "-xa");
	}
	
	/**
	 * Test method for {@link ParsingParameters#ParsingParameters(Object, String, String)} with
	 * a <code>null</code> argument.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testNullArgument() {
		new ParsingParameters(this, "88.2", null);
	}
	
	/**
	 * Test method for {@link ParsingParameters#ParsingParameters(Object, String, String)} with
	 * an empty argument.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArgument() {
		new ParsingParameters(this, "88.2", "");
	}
	
	/**
	 * Test method for {@link ParsingParameters#getTarget()}.
	 */
	@Test
	public void testGetTarget () {
		assertEquals(this, parsingParameters.getTarget());
	}
	
	/**
	 * Test method for {@link ParsingParameters#getValue()}.
	 */
	@Test
	public void testGetValue () {
		assertEquals("88.2", parsingParameters.getValue());
	}
	
	/**
	 * Test method for {@link ParsingParameters#getArgument()}.
	 */
	@Test
	public void testGetArgument () {
		assertEquals("-xa", parsingParameters.getArgument());
	}
}
