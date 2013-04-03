package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Configuration} class.
 * @author Alex
 */
public class ConfigurationTest {
	/** The bad configuration for the tests. */
	private Configuration badConf;
	
	/** The good configuration for the tests. */
	private Configuration goodConf;
	
	/**
	 * Set up attributes.
	 * @throws URISyntaxException
	 *         if the path to the configuration is badly formatted.
	 */
	@Before
	public void setUp () throws URISyntaxException {
		badConf = new Configuration();
		goodConf = new Configuration(Paths.get(getClass().getResource("/configuration.xml").toURI()));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Configuration#setConfigurationFile(java.nio.file.Path)}.
	 * @throws URISyntaxException
	 *         if the path to the configuration is badly formatted.
	 */
	@Test
	public void testSetConfigurationFile () throws URISyntaxException {
		assertFalse(badConf.isLoaded());
		badConf.setConfigurationFile(Paths.get(getClass().getResource("/configuration.xml").toURI()));
		Logger.getLogger(Configuration.class.getName()).setLevel(Level.FINE);
		assertTrue(badConf.isLoaded());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Configuration#get(java.lang.String)}.
	 */
	@Test
	public void testGet () {
		assertEquals(null, badConf.get("configuration.test"));
		assertEquals(null, badConf.get("configuration.service"));
		assertEquals("value", goodConf.get("configuration.test"));
		assertEquals("ace", goodConf.get("configuration.service"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Configuration#size()}.
	 */
	@Test
	public void testSize () {
		assertEquals(0, badConf.size());
		assertEquals(2, goodConf.size());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Configuration#isLoaded()}.
	 */
	@Test
	public void testIsLoaded () {
		assertFalse(badConf.isLoaded());
		assertTrue(goodConf.isLoaded());
	}
}
