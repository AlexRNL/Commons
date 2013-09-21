package com.alexrnl.commons.database.dao;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link DataSourceConfiguration} class.
 * @author Alex
 */
public class DataSourceConfigurationTest {
	/** Configuration with a creation file */
	private DataSourceConfiguration confWithCreationFile;
	/** Configuration without a creation file */
	private DataSourceConfiguration confWithoutCreationFile;
	/** All the configuration to test */
	private List<DataSourceConfiguration> dsConfigs;
	
	/**
	 * Set-up test attributes.
	 */
	@Before
	public void setUp () {
		confWithCreationFile = new DataSourceConfiguration("localhost:80/db", "aba", "ldr", null);
		confWithoutCreationFile = new DataSourceConfiguration("192.168.8.28:8128/sql", "barf", "yte", Paths.get("conf/create.sql"));
		
		dsConfigs = new LinkedList<>();
		dsConfigs.add(confWithCreationFile);
		dsConfigs.add(confWithoutCreationFile);
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#getUrl()}.
	 */
	@Test
	public void testGetUrl () {
		assertEquals("localhost:80/db", confWithCreationFile.getUrl());
		assertEquals("192.168.8.28:8128/sql", confWithoutCreationFile.getUrl());
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#getUsername()}.
	 */
	@Test
	public void testGetUsername () {
		assertEquals("aba", confWithCreationFile.getUsername());
		assertEquals("barf", confWithoutCreationFile.getUsername());
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#getPassword()}.
	 */
	@Test
	public void testGetPassword () {
		assertEquals("ldr", confWithCreationFile.getPassword());
		assertEquals("yte", confWithoutCreationFile.getPassword());
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#getCreationFile()}.
	 */
	@Test
	public void testGetCreationFile () {
		assertNull(confWithCreationFile.getCreationFile());
		assertEquals(Paths.get("conf/create.sql").toAbsolutePath(), confWithoutCreationFile.getCreationFile().toAbsolutePath());
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final DataSourceConfiguration conf : dsConfigs) {
			final DataSourceConfiguration newConf = new DataSourceConfiguration(conf.getUrl(), conf.getUsername(), conf.getPassword(), conf.getCreationFile());
			assertNotSame(conf, newConf);
			assertEquals(conf.hashCode(), newConf.hashCode());

			assertThat(conf.hashCode(), not(new DataSourceConfiguration(conf.getUrl() + "/sql", conf.getUsername(),
					conf.getPassword(), conf.getCreationFile()).hashCode()));
			assertThat(conf.hashCode(), not(new DataSourceConfiguration(conf.getUrl(), conf.getUsername() + "123",
					conf.getPassword(), conf.getCreationFile()).hashCode()));
			assertThat(conf.hashCode(), not(new DataSourceConfiguration(conf.getUrl(), conf.getUsername(),
					conf.getPassword() + "XXX", conf.getCreationFile()).hashCode()));
			assertThat(conf.hashCode(), not(new DataSourceConfiguration(conf.getUrl(), conf.getUsername(),
					conf.getPassword(), Paths.get("/dev/null/")).hashCode()));
		}
	}

	/**
	 * Test method for {@link DataSourceConfiguration#equals(Object)}.
	 */
	@Test
	public void testEqualsObject () {
		for (final DataSourceConfiguration conf : dsConfigs) {
			final DataSourceConfiguration newConf = new DataSourceConfiguration(conf.getUrl(), conf.getUsername(), conf.getPassword(), conf.getCreationFile());
			assertNotSame(conf, newConf);
			assertEquals(conf.hashCode(), newConf.hashCode());

			// Basic cases
			assertFalse(conf.equals(null));
			assertTrue(conf.equals(conf));
			assertFalse(conf.equals(new Object()));
			assertEquals(conf, conf);
			
			assertNotEquals(conf, new DataSourceConfiguration(conf.getUrl() + "/sql", conf.getUsername(),
					conf.getPassword(), conf.getCreationFile()));
			assertNotEquals(conf, new DataSourceConfiguration(conf.getUrl(), conf.getUsername() + "123",
					conf.getPassword(), conf.getCreationFile()));
			assertNotEquals(conf, new DataSourceConfiguration(conf.getUrl(), conf.getUsername(),
					conf.getPassword() + "XXX", conf.getCreationFile()));
			assertNotEquals(conf, new DataSourceConfiguration(conf.getUrl(), conf.getUsername(),
					conf.getPassword(), Paths.get("/dev/null/")));
		}
	}
	
	/**
	 * Test method for {@link DataSourceConfiguration#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("DataSource connection info [url=localhost:80/db; username=aba; password=ldr; creationFile=null]", confWithCreationFile.toString());
		assertEquals("DataSource connection info [url=192.168.8.28:8128/sql; username=barf; password=yte; creationFile=conf" + File.separator + "create.sql]", confWithoutCreationFile.toString());
	}
}
