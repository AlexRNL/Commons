package com.alexrnl.commons.error;

import static org.junit.Assert.assertEquals;

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
	 * Test method for {@link com.alexrnl.commons.error.ExceptionUtils#display(java.lang.Throwable)}.
	 */
	@Test
	public void testDisplayClassAndMessage () {
		assertEquals("null exception caught", ExceptionUtils.display(null));
		assertEquals("class java.lang.NullPointerException; test NPE", ExceptionUtils.display(exception));
	}
}
