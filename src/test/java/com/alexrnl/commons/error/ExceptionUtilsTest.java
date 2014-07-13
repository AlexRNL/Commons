package com.alexrnl.commons.error;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ExceptionUtils} class.
 * @author Alex
 */
public class ExceptionUtilsTest {
	/** The exception for the test */
	private NullPointerException	exception;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		exception = new NullPointerException("test NPE");
	}
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = ExceptionUtils.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link ExceptionUtils#display(Throwable)}.
	 */
	@Test
	public void testDisplayClassAndMessage () {
		assertEquals("null exception caught", ExceptionUtils.display(null));
		assertEquals("class java.lang.NullPointerException; test NPE", ExceptionUtils.display(exception));
	}
}
