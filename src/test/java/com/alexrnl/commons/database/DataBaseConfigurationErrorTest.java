package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the {@link DataBaseConfigurationError} class.
 * @author Alex
 */
public class DataBaseConfigurationErrorTest {
	
	/** An error with no information */
	private final DataBaseConfigurationError	blankError			= new DataBaseConfigurationError();
	/** An error with all informations */
	private final DataBaseConfigurationError	fullError			= new DataBaseConfigurationError("test", new NullPointerException());
	/** An error with a message */
	private final DataBaseConfigurationError	errorWithMessage	= new DataBaseConfigurationError("ldr");
	/** An error with a cause */
	private final DataBaseConfigurationError	errorWithCause		= new DataBaseConfigurationError(new IllegalStateException());
	
	/**
	 * Test method for {@link Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertNull(blankError.getMessage());
		assertEquals("test", fullError.getMessage());
		assertEquals("ldr", errorWithMessage.getMessage());
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
