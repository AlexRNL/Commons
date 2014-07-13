package com.alexrnl.commons.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the {@link TopLevelError} class.
 * @author Alex
 */
public class TopLevelErrorTest {
	
	/** An error with no information */
	private final TopLevelError	blankError			= new TopLevelError();
	/** An error with all informations */
	private final TopLevelError	fullError			= new TopLevelError("test", new NullPointerException());
	/** An error with a message */
	private final TopLevelError	errorWithMessage	= new TopLevelError("hjo");
	/** An error with a cause */
	private final TopLevelError	errorWithCause		= new TopLevelError(new IllegalStateException());
	
	/**
	 * Test method for {@link Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertNull(blankError.getMessage());
		assertEquals("test", fullError.getMessage());
		assertEquals("hjo", errorWithMessage.getMessage());
		assertEquals("java.lang.IllegalStateException", errorWithCause.getMessage());
	}
	
	/**
	 * Test method for {@link Throwable#getCause()}.
	 */
	@Test
	public void testGetCause () {
		assertNull(blankError.getCause());
		assertEquals(NullPointerException.class, fullError.getCause().getClass());
		assertNull(errorWithMessage.getCause());
		assertEquals(IllegalStateException.class, errorWithCause.getCause().getClass());
	}
}
