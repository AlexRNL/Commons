package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the {@link ReflectionException} class.
 * @author Alex
 */
public class ReflectionExceptionTest {
	/** A blank error */
	private final ReflectionException	blank			= new ReflectionException();
	/** An error with a message */
	private final ReflectionException	message			= new ReflectionException("Error while comparing Integer");
	/** An error with a code */
	private final ReflectionException	cause			= new ReflectionException(new RuntimeException());
	/** An error with a message and a code */
	private final ReflectionException	messageAndCause	= new ReflectionException("Error while comparing Dummy", new NullPointerException());
	
	/**
	 * Test method for {@link Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertNull(blank.getMessage());
		assertEquals("Error while comparing Integer", message.getMessage());
		assertEquals(RuntimeException.class.getName(), cause.getMessage());
		assertEquals("Error while comparing Dummy", messageAndCause.getMessage());
	}
	
	/**
	 * Test method for {@link Throwable#getCause()}.
	 */
	@Test
	public void testGetCause () {
		assertNull(blank.getCause());
		assertNull(message.getCause());
		assertEquals(RuntimeException.class, cause.getCause().getClass());
		assertEquals(NullPointerException.class, messageAndCause.getCause().getClass());
	}
}
