package com.alexrnl.commons.database.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.Dummy.DummyColumn;
import com.alexrnl.commons.database.structure.Column;
import com.alexrnl.commons.database.structure.Entity;

/**
 * Test suite for the {@link SQLDAO} class.
 * @author Alex
 */
public class SQLDAOTest {
	/** The test database connection */
	private static Connection	connection;
	/** The DAO for the {@link Dummy} class */
	private DummySQLDAO			dummyDAO;

	/**
	 * Create the embedded database and set-up the dummy table.
	 * @throws SQLException
	 *         if there was a problem while initializing the database.
	 */
	@BeforeClass
	public static void setUpBeforeClass () throws SQLException {
		connection = DriverManager.getConnection("jdbc:h2:mem:");
		final PreparedStatement createTable = connection.prepareStatement("CREATE TABLE dummy ("
				+ "id		INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "name		VARCHAR_IGNORECASE(60) NOT NULL);");
		createTable.executeUpdate();
	}
	
	/**
	 * Close the database connection, after all tests have been carried out.
	 * @throws SQLException if there was a problem while closing the database connection.
	 */
	@AfterClass
	public static void tearDownAfterClass () throws SQLException {
		connection.close();
		Logger.getLogger(SQLDAO.class.getName()).setLevel(Level.INFO);
	}
	
	/**
	 * Set up test attributes.
	 * @throws SQLException
	 *         if there was a problem while initializing the DAO.
	 */
	@Before
	public void setUp () throws SQLException {
		Logger.getLogger(SQLDAO.class.getName()).setLevel(Level.ALL);
		dummyDAO = new DummySQLDAO(connection);
	}
	
	/**
	 * Close the DAO after test use.<br />
	 * @throws IOException
	 *         if there was an issue while closing the DAO.
	 */
	@After
	public void tearDown () throws IOException {
		for (final Dummy dummy : dummyDAO.retrieveAll()) {
			assertTrue(dummyDAO.delete(dummy));
		}
		dummyDAO.close();
		
	}
	
	/**
	 * Test method for {@link SQLDAO#create(Entity)}.
	 */
	@Test
	public void testCreate () {
		assertEquals(0, dummyDAO.retrieveAll().size());
		final Dummy dummy = new Dummy("test");
		final Dummy newDummy = dummyDAO.create(dummy);
		
		assertNotNull(newDummy);
		assertEquals(1, dummyDAO.retrieveAll().size());
		// Objects are not equals because the id is not set on the first dummy
		assertNotEquals(dummy, newDummy);
		assertEquals("test", newDummy.getName());
		
		assertNull(dummyDAO.create(null));
	}
	
	/**
	 * Test method for {@link SQLDAO#find(int)}.
	 */
	@Test
	public void testFind () {
		assertEquals(0, dummyDAO.retrieveAll().size());
		final Dummy aba = dummyDAO.create(new Dummy("ABA"));
		final Dummy ldr = dummyDAO.create(new Dummy("LDR"));
		
		assertEquals(aba, dummyDAO.find(aba.getId()));
		assertEquals(ldr, dummyDAO.find(ldr.getId()));
	}
	
	/**
	 * Test method for {@link SQLDAO#update(Entity)}.
	 */
	@Test
	public void testUpdate () {
		assertEquals(0, dummyDAO.retrieveAll().size());
		final Dummy aba = dummyDAO.create(new Dummy("ABA"));
		aba.setName("aba");
		assertTrue(dummyDAO.update(aba));
		final Dummy updated = dummyDAO.find(aba.getId());
		assertEquals(aba, updated);
		assertEquals("aba", updated.getName());
		
		assertFalse(dummyDAO.update(null));
	}
	
	/**
	 * Test method for {@link SQLDAO#delete(Entity)}.
	 */
	@Test
	public void testDelete () {
		assertEquals(0, dummyDAO.retrieveAll().size());
		final Dummy aba = dummyDAO.create(new Dummy("ABA"));
		assertEquals(1, dummyDAO.retrieveAll().size());
		dummyDAO.delete(aba);
		assertEquals(0, dummyDAO.retrieveAll().size());
		assertNull(dummyDAO.find(aba.getId()));
		
		assertTrue(dummyDAO.delete(null));
	}
	
	/**
	 * Test method for {@link SQLDAO#retrieveAll()}.
	 */
	@Test
	public void testRetrieveAll () {
		assertEquals(0, dummyDAO.retrieveAll().size());
		dummyDAO.create(new Dummy("ABA"));
		dummyDAO.create(new Dummy("LDR"));
		
		final Set<Dummy> dummies = dummyDAO.retrieveAll();
		assertEquals(2, dummies.size());
		for (final Dummy dummy : dummies) {
			if (!dummy.getName().equals("ABA") && !dummy.getName().equals("LDR")) {
				fail("Unknwon dummy found: " + dummy);
			}
		}
	}
	
	/**
	 * Test method for {@link SQLDAO#search(Column, String)}.
	 */
	@Test
	public void testSearch () {
		// Populate table
		dummyDAO.create(new Dummy("ABA"));
		dummyDAO.create(new Dummy("LDR"));
		dummyDAO.create(new Dummy("123"));
		dummyDAO.create(new Dummy("A2E"));
		dummyDAO.create(new Dummy("AXE"));
		dummyDAO.create(new Dummy("BLO"));
		dummyDAO.create(new Dummy("LUD"));
		
		final Set<Dummy> allDummies = dummyDAO.retrieveAll();
		assertEquals(allDummies, dummyDAO.search(null, "test"));
		assertEquals(allDummies, dummyDAO.search(Dummy.getColumns().get(DummyColumn.NAME), null));

		assertEquals(3, dummyDAO.search(Dummy.getColumns().get(DummyColumn.NAME), "A%").size());
		assertEquals(1, dummyDAO.search(Dummy.getColumns().get(DummyColumn.NAME), "ABA").size());
		assertEquals(2, dummyDAO.search(Dummy.getColumns().get(DummyColumn.NAME), "%2%").size());
		
		// Test with column which is not defined
		assertTrue(dummyDAO.search(new Column(String.class, "notHere"), "MAN").isEmpty());
	}
	
	/**
	 * Test case when logs are disabled on the class.
	 */
	@Test
	public void testNoLog () {
		Logger.getLogger(SQLDAO.class.getName()).setLevel(Level.OFF);
		
		final Dummy lau = dummyDAO.create(new Dummy("LAU"));
		lau.setName("LAu");
		dummyDAO.update(lau);
		dummyDAO.find(lau.getId());
		dummyDAO.delete(lau);
		dummyDAO.retrieveAll();
		dummyDAO.search(Dummy.getColumns().get(DummyColumn.NAME), "x");
	}
}
