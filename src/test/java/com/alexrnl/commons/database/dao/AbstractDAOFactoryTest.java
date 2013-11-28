package com.alexrnl.commons.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.DummyFactory;

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
			super(null);
		}

	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#buildFactory(java.lang.String, DataSourceConfiguration, Class)}.
	 * @throws IOException
	 *         if there was an error while closing the DAOs.
	 */
	@Test
	public void testBuildFactoryStringDataSourceConfigurationClassOfT () throws IOException {
		final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null);
		assertEquals(DummyFactory.class, factory.getClass());
		factory.close();
	}

	/**
	 * Test method for {@link AbstractDAOFactory#buildFactory(String, DataSourceConfiguration)}.
	 */
	@Test
	public void testBuildFactoryStringDataSourceConfiguration () {
		final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null);
		assertEquals(DummyFactory.class, factory.getClass());
	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#buildFactory(java.lang.String, DataSourceConfiguration, Class)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testBuildFactoryStringDataSourceConfigurationClassOfTPrivateClassError () {
		AbstractDAOFactory.buildFactory(DummyFactoryPrivate.class.getName(), null);
	}

	/**
	 * Test method for {@link AbstractDAOFactory#buildFactory(String, DataSourceConfiguration)}.
	 */
	@Test(expected=DAOInstantiationError.class)
	public void testBuildFactoryStringDataSourceConfigurationNotADAOError () {
		AbstractDAOFactory.buildFactory(Object.class.getName(), null);
	}

	/**
	 * Test method for {@link AbstractDAOFactory#getDAOs()}.
	 */
	@Test
	public void testGetDAOs () {
		final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null);
		assertEquals(1, factory.getDAOs().size());
	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#getDAO(Class)}.
	 */
	@Test
	public void testGetDAO () {
		final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null);
		assertNotNull(factory.getDAO(Dummy.class));
	}

	/**
	 * Test method for {@link AbstractDAOFactory#getDataSourceConfiguration()}.
	 */
	@Test()
	public void testGetDataSourceConfiguration() {
		final DataSourceConfiguration dataSourceConfig = new DataSourceConfiguration("localhost:8080/db", "aba", "ldr", null);
		final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), dataSourceConfig);
		assertEquals(dataSourceConfig, factory.getDataSourceConfiguration());
	}
	
}
