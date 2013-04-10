package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

/**
 * Test suite for the {@link NoIdError} class.
 * @author Alex
 */
public class NoIdErrorTest {
	
	/** Exception with no entity information */
	private final NoIdError	unknownException = new NoIdError();
	/** Exception with the entity that generated the problem */
	private final NoIdError	dummyException = new NoIdError(Dummy.class);
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.NoIdError#getEntityClass()}.
	 */
	@Test
	public void testGetEntityClass () {
		assertEquals(Entity.class, unknownException.getEntityClass());
		assertEquals(Dummy.class, dummyException.getEntityClass());
	}
	
	/**
	 * Test method for {@link java.lang.Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("Could not find id column in entity Entity", unknownException.getMessage());
		assertEquals("Could not find id column in entity Dummy", dummyException.getMessage());
	}
}
