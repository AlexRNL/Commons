package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * Test suite for the {@link AbstractDAOFactory} class.
 * @author Alex
 */
public class AbstractDAOFactoryTest {
	/**
	 * Dummy factory, for test purposes.
	 * @author Alex
	 */
	private static class DummyFactory extends AbstractDAOFactory {
		/**
		 * Constructor #1.<br />
		 */
		public DummyFactory () {
			super();
		}
		
		@Override
		public void close () throws IOException {}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getImplementation()}.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetImplementation () {
		try {
			AbstractDAOFactory.createFactory((Class<? extends AbstractDAOFactory>) null);
		} catch (final DAOInstantiationError e) {}
		AbstractDAOFactory.getImplementation();
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.Class)}.
	 */
	@Test
	public void testCreateFactoryClassOfQextendsAbstractDAOFactory () {
		AbstractDAOFactory.createFactory(DummyFactory.class);
		assertEquals(DummyFactory.class, AbstractDAOFactory.getImplementation().getClass());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.String)}.
	 */
	@Test
	public void testCreateFactoryString () {
		AbstractDAOFactory.createFactory(DummyFactory.class.getName());
		assertEquals(DummyFactory.class, AbstractDAOFactory.getImplementation().getClass());
	}
}
