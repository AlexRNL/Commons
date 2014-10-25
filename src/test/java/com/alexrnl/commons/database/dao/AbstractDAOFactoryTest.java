package com.alexrnl.commons.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.alexrnl.commons.database.DAOAdaptater;
import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.DummyFactory;
import com.alexrnl.commons.error.ExceptionUtils;

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
	 */
	@Test
	public void testBuildFactoryStringDataSourceConfigurationClassOfT () {
		try (final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null)) {
			assertEquals(DummyFactory.class, factory.getClass());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}

	/**
	 * Test method for {@link AbstractDAOFactory#buildFactory(String, DataSourceConfiguration)}.
	 */
	@Test
	public void testBuildFactoryStringDataSourceConfiguration () {
		try (final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null)) {
			assertEquals(DummyFactory.class, factory.getClass());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
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
		try (final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null)) {
			assertEquals(1, factory.getDAOs().size());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#getDAO(Class)}.
	 */
	@Test
	public void testGetDAO () {
		try (final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), null)) {
			assertNotNull(factory.getDAO(Dummy.class));
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}

	/**
	 * Test method for {@link AbstractDAOFactory#getDataSourceConfiguration()}.
	 */
	@Test
	public void testGetDataSourceConfiguration() {
		final DataSourceConfiguration dataSourceConfig = new DataSourceConfiguration("localhost:8080/db", "aba", "ldr", null);
		try (final AbstractDAOFactory factory = AbstractDAOFactory.buildFactory(DummyFactory.class.getName(), dataSourceConfig)) {
			assertEquals(dataSourceConfig, factory.getDataSourceConfiguration());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#addDAO(Class, DAO)}.
	 * When adding multiple DAO to the same class.
	 */
	@Test
	public void testAddMultipleDAOs () {
		try (final AbstractDAOFactory factory = new DummyFactory(null)) {
			assertEquals(1, factory.getDAOs().size());
			final DAO<Dummy> daoMock = mock(DAO.class);
			factory.addDAO(Dummy.class, daoMock);
			assertEquals(1, factory.getDAOs().size());
			factory.addDAO(Dummy.class, new DAOAdaptater<Dummy>());
			verify(daoMock).close();
			assertEquals(1, factory.getDAOs().size());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test method for {@link AbstractDAOFactory#addDAO(Class, DAO)}.
	 * When overriding a null DAO.
	 */
	@Test
	public void testAddNullDAO () {
		try (final AbstractDAOFactory factory = new DummyFactory(null)) {
			assertEquals(1, factory.getDAOs().size());
			factory.addDAO(Dummy.class, null);
			assertEquals(1, factory.getDAOs().size());
			factory.addDAO(Dummy.class, new DAOAdaptater<Dummy>());
			assertEquals(1, factory.getDAOs().size());
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test that an exception on closing a dao does not break anything.
	 */
	@Test
	public void testExceptionWhenClosingDAO () {
		Logger.getLogger(AbstractDAOFactory.class.getName()).setLevel(Level.WARNING);
		try (final AbstractDAOFactory factory = new DummyFactory(null)) {
			assertEquals(1, factory.getDAOs().size());
			final DAO<Dummy> daoMock = mock(DAO.class);
			factory.addDAO(Dummy.class, daoMock);
			doThrow(IOException.class).when(daoMock).close();
			factory.addDAO(Dummy.class, new DAOAdaptater<Dummy>());
			verify(daoMock).close();
		} catch (final IOException e) {
			fail(ExceptionUtils.display(e));
		}
		Logger.getLogger(AbstractDAOFactory.class.getName()).setLevel(Level.INFO);
	}
}
