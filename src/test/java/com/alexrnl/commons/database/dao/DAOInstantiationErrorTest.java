package com.alexrnl.commons.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.alexrnl.commons.database.dao.DAOInstantiationError;

/**
 * Test suite for the {@link DAOInstantiationError} class.
 * @author Alex
 */
public class DAOInstantiationErrorTest {
	/** Exception with an unknown DAO */
	private final DAOInstantiationError unknownDAO = new DAOInstantiationError();
	/** Exception with a known DAO class */
	private final DAOInstantiationError knownDAO  = new DAOInstantiationError("com.alexrnl.commons.database.DummyDBDAO");
	
	/**
	 * Test method for {@link DAOInstantiationError#getDAOFactory()}.
	 */
	@Test
	public void testGetDAOFactory () {
		assertNull(unknownDAO.getDAOFactory());
		assertEquals("com.alexrnl.commons.database.DummyDBDAO", knownDAO.getDAOFactory());
	}
	
	/**
	 * Test method for {@link Throwable#getMessage()}.
	 */
	@Test
	public void testGetMessage () {
		assertEquals("The DAO factory class null is not implemented.", unknownDAO.getMessage());
		assertEquals("The DAO factory class com.alexrnl.commons.database.DummyDBDAO is not implemented.",
				knownDAO.getMessage());
	}
}
