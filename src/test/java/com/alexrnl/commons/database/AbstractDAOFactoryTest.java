package com.alexrnl.commons.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test suite for the {@link AbstractDAOFactory} class.
 * @author Alex
 */
public class AbstractDAOFactoryTest {
	/**
	 * Dummy private factory, for test purposes.
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
			AbstractDAOFactory.createFactory((Class<? extends AbstractDAOFactory>) null, null);
		} catch (final DAOInstantiationError e) {}
		AbstractDAOFactory.getImplementation();
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getDataSourceConfiguration()}.
	 */
	@Test()
	public void testGetDataSourceConfiguration() {
		final DataSourceConfiguration dataSourceConfig = new DataSourceConfiguration("localhost:8080/db", "aba", "ldr", null);
		AbstractDAOFactory.createFactory(DummyFactory.class, dataSourceConfig);
		assertEquals(dataSourceConfig, AbstractDAOFactory.getDataSourceConfiguration());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(Class, DataSourceConfiguration)}.
	 */
	@Test
	public void testCreateFactoryClassOfQextendsAbstractDAOFactory () {
		AbstractDAOFactory.createFactory(DummyFactory.class, null);
		assertEquals(DummyFactory.class, AbstractDAOFactory.getImplementation().getClass());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.String, DataSourceConfiguration)}.
	 */
	@Test
	public void testCreateFactoryString () {
		AbstractDAOFactory.createFactory(DummyFactory.class.getName(), null);
		assertEquals(DummyFactory.class, AbstractDAOFactory.getImplementation().getClass());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.Class, DataSourceConfiguration)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryClassOfQextendsAbstractDAOFactoryDAOInstantiationError () {
		AbstractDAOFactory.createFactory(DummyFactoryPrivate.class, null);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#createFactory(java.lang.String, DataSourceConfiguration)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testCreateFactoryStringDAOInstantiationError () {
		AbstractDAOFactory.createFactory(Object.class.getName(), null);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getDAOs()}.
	 */
	@Test
	public void testGetDAOs () {
		AbstractDAOFactory.createFactory(DummyFactory.class, null);
		final AbstractDAOFactory daos = AbstractDAOFactory.getImplementation();
		assertEquals(1, daos.getDAOs().size());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.database.AbstractDAOFactory#getDAO(Class)}.
	 */
	@Test
	public void testGetDAO () {
		AbstractDAOFactory.createFactory(DummyFactory.class, null);
		final AbstractDAOFactory daos = AbstractDAOFactory.getImplementation();
		assertNotNull(daos.getDAO(Dummy.class));
	}
	
}
