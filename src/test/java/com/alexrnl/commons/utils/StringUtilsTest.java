package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test suite for the {@link StringUtils} class.
 * @author Alex
 */
public class StringUtilsTest {
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#nullOrEmpty(java.lang.String)}.
	 */
	@Test
	public void testNullOrEmpty () {
		assertTrue(StringUtils.nullOrEmpty(null));
		assertTrue(StringUtils.nullOrEmpty(""));
		assertFalse(StringUtils.nullOrEmpty("camelCase"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#neitherNullNorEmpty(java.lang.String)}.
	 */
	@Test
	public void testNeitherNullNorEmpty () {
		assertFalse(StringUtils.neitherNullNorEmpty(null));
		assertFalse(StringUtils.neitherNullNorEmpty(""));
		assertTrue(StringUtils.neitherNullNorEmpty("camelCase"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#replaceIfNull(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReplaceIfNull () {
		assertEquals("test", StringUtils.replaceIfNull(null, "test"));
		assertEquals("test", StringUtils.replaceIfNull("test", "test2"));
	}
	
}
