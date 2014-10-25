package com.alexrnl.commons.database.h2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

import org.h2.engine.Constants;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.alexrnl.commons.database.DataBaseConfigurationError;
import com.alexrnl.commons.database.Dummy;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.database.sql.DummySQLDAO;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Test suite for the {@link H2Utils} class.
 * @author Alex
 */
public class H2UtilsTest {
	/** Temporary folder for tests */
	@ClassRule
	public static TemporaryFolder	folder	= new TemporaryFolder();
	
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
		final DataSourceConfiguration dbInfosNoPrefix = new DataSourceConfiguration("jdbc:h2:data/backup", "", "", null);
		assertEquals(Paths.get("data/backup.h2.db"), H2Utils.getDBFile(dbInfosNoPrefix, true));
		assertEquals(Paths.get("data/backup"), H2Utils.getDBFile(dbInfosNoPrefix, false));
		final DataSourceConfiguration dbInfosWithParameters = new DataSourceConfiguration("jdbc:h2:file:test/data/backup;IFEXISTS=TRUE;TRACE_LEVEL_FILE=3", "", "", null);
		assertEquals(Paths.get("test/data/backup.h2.db"), H2Utils.getDBFile(dbInfosWithParameters, true));
		assertEquals(Paths.get("test/data/backup"), H2Utils.getDBFile(dbInfosWithParameters, false));
	}
	
	/**
	 * Test method for {@link H2Utils#initDatabase(DataSourceConfiguration)}.
	 * @throws URISyntaxException
	 *         if there is a problem while loading the file.
	 * @throws IOException
	 *         if there is an error while creating the file database.
	 */
	@Test
	public void testInitDatabase () throws URISyntaxException, IOException {
		final Path creationFile = Paths.get(getClass().getResource("/createDummyDB.sql").toURI());
		final Path tempDBFile = folder.newFolder().toPath().resolve("testDummy");
		final DataSourceConfiguration dbInfos = new DataSourceConfiguration(Constants.START_URL + tempDBFile, "aba", "ldr", creationFile);
		H2Utils.initDatabase(dbInfos);
		
		try (final Connection connection = DriverManager.getConnection(dbInfos.getUrl(), dbInfos.getUsername(), dbInfos.getPassword())) {
			final DummySQLDAO dummyDao = new DummySQLDAO(connection);
			final Set<Dummy> dummies = dummyDao.retrieveAll();
			assertEquals(2, dummies.size());
		} catch (final SQLException e) {
			fail(ExceptionUtils.display(e));
		}
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
				tempDBFile.toString().substring(0, tempDBFile.toString().length() - ".h2.db".length()), "aba", "ldr", Paths.get("/dummy"));
		H2Utils.initDatabase(dbInfos);
		
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
}
