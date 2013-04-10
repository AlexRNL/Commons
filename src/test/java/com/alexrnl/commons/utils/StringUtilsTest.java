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
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#emptyIfNull(java.lang.String)}.
	 */
	@Test
	public void testEmptyIfNull () {
		assertEquals("", StringUtils.emptyIfNull((String) null));
		assertEquals("test", StringUtils.emptyIfNull("test"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#replaceIfNull(java.lang.Object, java.lang.String)}.
	 */
	@Test
	public void testReplaceIfNullObject () {
		assertEquals("test", StringUtils.replaceIfNull((Object) null, "test"));
		assertEquals("test", StringUtils.replaceIfNull(new StringBuilder("test"), "test2"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#emptyIfNull(java.lang.Object)}.
	 */
	@Test
	public void testEmptyIfNullObject () {
		assertEquals("", StringUtils.emptyIfNull((Object) null));
		assertEquals("test", StringUtils.emptyIfNull(new StringBuilder("test")));
	}
	
}
