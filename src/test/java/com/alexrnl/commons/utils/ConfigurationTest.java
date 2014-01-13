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
	
	/** The good XML configuration for the tests. */
	private Configuration goodConfXML;
	
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
		goodConfXML = new Configuration(Paths.get(getClass().getResource("/configuration.xml").toURI()));
		Logger.getLogger(Configuration.class.getName()).setLevel(Level.FINE);
		goodConf = new Configuration(Paths.get(getClass().getResource("/configuration.properties").toURI()), false);
	}
	
	/**
	 * Test method for {@link Configuration#has(String)}.
	 */
	@Test
	public void testHas () {
		assertFalse(badConf.has("hello.world"));
		assertFalse(goodConfXML.has("aba.ldr"));
		assertTrue(goodConf.has("configuration.test"));
	}
	
	/**
	 * Test method for {@link Configuration#get(String)}.
	 */
	@Test
	public void testGet () {
		assertEquals(null, badConf.get("configuration.test"));
		assertEquals(null, badConf.get("configuration.service"));
		assertEquals("value", goodConfXML.get("configuration.test"));
		assertEquals("ace", goodConfXML.get("configuration.service"));
		assertEquals("value", goodConf.get("configuration.test"));
		assertEquals("ace", goodConf.get("configuration.service"));
	}
	
	/**
	 * Test method for {@link Configuration#size()}.
	 */
	@Test
	public void testSize () {
		assertEquals(0, badConf.size());
		assertEquals(6, goodConfXML.size());
		assertEquals(2, goodConf.size());
	}
	
	/**
	 * Test method for {@link Configuration#isLoaded()}.
	 */
	@Test
	public void testIsLoaded () {
		assertFalse(badConf.isLoaded());
		assertTrue(goodConfXML.isLoaded());
		assertTrue(goodConf.isLoaded());
	}
	
	/**
	 * Test method for the format detection in the constructor.
	 * @throws URISyntaxException
	 *         if the path to the configuration is badly formatted.
	 */
	@Test
	public void testAutoDetectFormat () throws URISyntaxException {
		final Configuration xmlConf = new Configuration(Paths.get(getClass().getResource("/configuration.xml").toURI()));
		final Configuration propConf = new Configuration(Paths.get(getClass().getResource("/configuration.properties").toURI()));
		
		assertEquals(6, xmlConf.size());
		assertEquals(2, propConf.size());
	}
}
