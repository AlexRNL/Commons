package com.alexrnl.commons.arguments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Parameter} class.
 * @author Alex
 */
public class ParameterTest {
	/** The parameter to test */
	private Parameter parameter;
	/** The parameter loaded reflectively */
	private Parameter reflectParam;
	/** The parameter to load reflectively */
	@Param(names = {"-name"}, required = false, description = "the name", order = 1)
	private String myParam;
	
	/**
	 * Set up test attributes.
	 * @throws SecurityException
	 *         if the field to test cannot be retrieved.
	 * @throws NoSuchFieldException
	 *         if the field to test cannot be found.
	 */
	@Before
	public void setUp () throws NoSuchFieldException, SecurityException {
		parameter = new Parameter(null, Arrays.asList(new String[] {"-n", "--number"}), true, "my description", 0);
		final Field field = ParameterTest.class.getDeclaredField("myParam");
		reflectParam = new Parameter(field, field.getAnnotation(Param.class));
		
	}
	
	/**
	 * Test method for {@link Parameter#getField()}.
	 * @throws SecurityException
	 *         if the field to test cannot be retrieved.
	 * @throws NoSuchFieldException
	 *         if the field to test cannot be found.
	 */
	@Test
	public void testGetField () throws NoSuchFieldException, SecurityException {
		assertNull(parameter.getField());
		assertEquals(ParameterTest.class.getDeclaredField("myParam"), reflectParam.getField());
	}
	
	/**
	 * Test method for {@link Parameter#getNames()}.
	 */
	@Test
	public void testGetNames () {
		assertEquals(new HashSet<>(Arrays.asList("-n", "--number")), parameter.getNames());
		assertEquals(new HashSet<>(Arrays.asList("-name")), reflectParam.getNames());
	}
	
	/**
	 * Test method for {@link Parameter#isRequired()}.
	 */
	@Test
	public void testIsRequired () {
		assertTrue(parameter.isRequired());
		assertFalse(reflectParam.isRequired());
	}
	
	/**
	 * Test method for {@link Parameter#getDescription()}.
	 */
	@Test
	public void testGetDescription () {
		assertEquals("my description", parameter.getDescription());
		assertEquals("the name", reflectParam.getDescription());
	}
	
	/**
	 * Test method for {@link Parameter#getOrder()}.
	 */
	@Test
	public void testGetOrder () {
		assertEquals(0, parameter.getOrder());
		assertEquals(1, reflectParam.getOrder());
	}
	
	/**
	 * Test method for {@link Parameter#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		assertNotEquals(parameter.hashCode(), reflectParam.hashCode());
	}

	/**
	 * Test method for {@link Parameter#equals(Object)}.
	 */
	@Test
	public void testEqualsObject () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link Parameter#compareTo(Parameter)}.
	 */
	@Test
	public void testCompareTo () {
		fail("Not yet implemented"); // TODO
	}
}
