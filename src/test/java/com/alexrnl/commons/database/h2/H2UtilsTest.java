package com.alexrnl.commons.database.h2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.engine.Constants;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.alexrnl.commons.database.DataBaseConfigurationError;
import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.database.sql.DummySQLDAO;

/**
 * Test suite for the {@link H2Utils} class.
 * @author Alex
 */
public class H2UtilsTest {
	/** Temporary folder for tests */
	@ClassRule
	public static TemporaryFolder	folder	= new TemporaryFolder();
	/** The creation file for the test database */
	private Path					creationFile;
	
	/**
	 * Set up test attributes.
	 * @throws URISyntaxException
	 *         if there is a problem while loading the craetion file.
	 */
	@Before
	public void setUp () throws URISyntaxException {
		creationFile = Paths.get(getClass().getResource("/createDummyDB.sql").toURI());
	}
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = H2Utils.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link H2Utils#getDBFile(DataSourceConfiguration, boolean)}.
	 */
	@Test
	public void testGetDBFile () {
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration("jdbc:h2:file:test/data/backup", "", "", null);
		assertEquals(Paths.get("test/data/backup.h2.db"), H2Utils.getDBFile(dbInfos, true));
		assertEquals(Paths.get("test/data/backup"), H2Utils.getDBFile(dbInfos, false));
		Logger.getLogger(H2Utils.class.getName()).setLevel(Level.FINE);
		final DataSourceConfiguration dbInfosNoPrefix = new DataSourceConfiguration("jdbc:h2:data/backup", "", "", null);
		assertEquals(Paths.get("data/backup.h2.db"), H2Utils.getDBFile(dbInfosNoPrefix, true));
		assertEquals(Paths.get("data/backup"), H2Utils.getDBFile(dbInfosNoPrefix, false));
		Logger.getLogger(H2Utils.class.getName()).setLevel(Level.INFO);
		final DataSourceConfiguration dbInfosWithParameters = new DataSourceConfiguration("jdbc:h2:file:test/data/backup;IFEXISTS=TRUE;TRACE_LEVEL_FILE=3", "", "", null);
		assertEquals(Paths.get("test/data/backup.h2.db"), H2Utils.getDBFile(dbInfosWithParameters, true));
		assertEquals(Paths.get("test/data/backup"), H2Utils.getDBFile(dbInfosWithParameters, false));
		final DataSourceConfiguration dbInfosInMemory = new DataSourceConfiguration("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "", null);
		assertNull(H2Utils.getDBFile(dbInfosInMemory, true));
		assertNull(H2Utils.getDBFile(dbInfosInMemory, false));
	}
	
	/**
	 * Validate the creation of the database
	 * @param dbInfos
	 *        the database information to use to connect to the database.
	 * @throws SQLException
	 *         if there was an SQL error while validating the database.
	 * @throws IOException
	 *         if an exception occur while closing the DAO.
	 */
	private static void validateDatabase (final DataSourceConfiguration dbInfos) throws SQLException, IOException {
		try (final Connection connection = DriverManager.getConnection(dbInfos.getUrl(), dbInfos.getUsername(), dbInfos.getPassword())) {
			final DummySQLDAO dummyDao = new DummySQLDAO(connection);
			final Set<Dummy> dummies = dummyDao.retrieveAll();
			assertEquals(2, dummies.size());
			dummyDao.close();
		} catch (final SQLException | IOException e) {
			throw e;
		}
	}

	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)}.
	 * @throws IOException
	 *         if there is an error while creating the file database.
	 * @throws SQLException
	 *         if there was an SQL error while validating the database.
	 */
	@Test
	public void testInitDatabase () throws IOException, SQLException {
		final Path tempDBFile = folder.newFolder().toPath().resolve("testDummy");
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(Constants.START_URL + tempDBFile, "aba", "ldr", creationFile);
		H2Utils.initDatabase(dbInfos);
		
		validateDatabase(dbInfos);
	}
	
	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)} with in-memory database.
	 * @throws SQLException
	 *         if there was an SQL error while validating the database.
	 * @throws IOException
	 *         if an exception occur while closing the DAO.
	 */
	@Test
	public void testInitDatabaseInMemory () throws IOException, SQLException {
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration("jdbc:h2:mem:testInMemory;DB_CLOSE_DELAY=-1", "aba", "ldr", creationFile);
		Logger.getLogger(H2Utils.class.getName()).setLevel(Level.WARNING);
		H2Utils.initDatabase(dbInfos);
		Logger.getLogger(H2Utils.class.getName()).setLevel(Level.INFO);
		validateDatabase(dbInfos);
	}

	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)}.
	 * @throws IOException
	 *         if there is an error while creating the file database.
	 */
	@Test
	public void testInitDatabaseAlreadyCreated () throws IOException {
		final Path tempDBFile = folder.newFile("testDB.h2.db").toPath();
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(Constants.START_URL +
				tempDBFile.toString().substring(0, tempDBFile.toString().length() - Constants.SUFFIX_PAGE_FILE.length()), "aba", "ldr", Paths.get("/dummy"));
		H2Utils.initDatabase(dbInfos);
		
		// Check that file is still empty
		assertEquals(0, Files.size(tempDBFile));
	}
	
	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)}.
	 * @throws IOException
	 *         if there is an error while creating the file database.
	 */
	@Test(expected = DataBaseConfigurationError.class)
	public void testInitDatabaseNoCreationScript () throws IOException {
		final Path tempDBFile = folder.newFolder().toPath().resolve("testDummy");
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(Constants.START_URL + tempDBFile, "aba", "ldr", null);
		H2Utils.initDatabase(dbInfos);
	}
	
	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)}.
	 * @throws URISyntaxException
	 *         if there is a problem while loading the creation file.
	 */
	@Test(expected = DataBaseConfigurationError.class)
	public void testBadSQLScript () throws URISyntaxException {
		final Path badCreationFile = Paths.get(getClass().getResource("/badScript.sql").toURI());
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration("jdbc:h2:mem:testBadScript", "aba", "ldr", badCreationFile);
		H2Utils.initDatabase(dbInfos);
	}
}
