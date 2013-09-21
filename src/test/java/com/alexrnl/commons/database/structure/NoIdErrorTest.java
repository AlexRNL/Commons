package com.alexrnl.commons.database.structure;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.database.structure.NoIdError;

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
	 * Test method for {@link NoIdError#getEntityClass()}.
	 */
	@Test
	public void testGetEntityClass () {
		assertEquals(Entity.class, unknownException.getEntityClass());
		assertEquals(Dummy.class, dummyException.getEntityClass());
	}
	
	/**
	 * Test method for {@link Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("Could not find id column in entity Entity", unknownException.getMessage());
		assertEquals("Could not find id column in entity Dummy", dummyException.getMessage());
	}
}
