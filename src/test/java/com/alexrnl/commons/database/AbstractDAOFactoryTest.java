package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Set;

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
			addDAO(Dummy.class, new DAO<Dummy>() {
				
				@Override
				public void close () throws IOException {}
				
				@Override
				public Dummy create (final Dummy obj) {
					return null;
				}
				
				@Override
				public Dummy find (final int id) {
					return null;
				}
				
				@Override
				public boolean update (final Dummy obj) {
					return false;
				}
				
				@Override
				public boolean delete (final Dummy obj) {
					return false;
				}
				
				@Override
				public Set<Dummy> retrieveAll () {
					return null;
				}
				
				@Override
				public Set<Dummy> search (final Column field, final String value) {
					return null;
				}});
		}
		
	}
	/**
	 * Dummy factory, for test purposes.
	 * @author Alex
	 */
	private static class DummyFactoryPrivate extends AbstractDAOFactory {
		/**
		 * Constructor #1.<br />
		 */
		private DummyFactoryPrivate () {
			super();
		}
		
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
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.Class)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryClassOfQextendsAbstractDAOFactoryDAOInstantiationError () {
		AbstractDAOFactory.createFactory(DummyFactoryPrivate.class);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.String)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryStringDAOInstantiationError () {
		AbstractDAOFactory.createFactory(Object.class.getName());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getDAOs()}.
	 */
	@Test
	public void testGetDAOs () {
		AbstractDAOFactory.createFactory(DummyFactory.class);
		final AbstractDAOFactory daos = AbstractDAOFactory.getImplementation();
		assertEquals(1, daos.getDAOs().size());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getDAO(Class)}.
	 */
	@Test
	public void testGetDAO () {
		AbstractDAOFactory.createFactory(DummyFactory.class);
		final AbstractDAOFactory daos = AbstractDAOFactory.getImplementation();
		assertNotNull(daos.getDAO(Dummy.class));
	}
	
}
