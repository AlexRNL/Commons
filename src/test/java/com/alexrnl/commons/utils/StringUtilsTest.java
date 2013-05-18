package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

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
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#separateWith(String, Object...)}.
	 */
	@Test
	public void testSeparateWith () {
		assertEquals("1,2,3", StringUtils.separateWith(",", 1, 2, 3));
		assertEquals("1", StringUtils.separateWith(",", 1));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#getMD5(String)}.
	 */
	@Test
	public void testgetMD5String () {
		assertEquals("0cc175b9c0f1b6a831c399e269772661", StringUtils.getMD5("a"));
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", StringUtils.getMD5("abcdefghijklmnopqrstuvwxyz"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.StringUtils#getMD5(String)}.
	 */
	@Test
	public void testgetMD5StringCharset () {
		assertEquals("0b687c31ee552ea09607eb2c2685f6eb", StringUtils.getMD5("abc123È^$ñ@!§", StandardCharsets.ISO_8859_1));
		assertEquals("65fd8f6818ff5304e5add7cf28ed3815", StringUtils.getMD5("abc123È^$ñ@!§", StandardCharsets.UTF_8));
	}
}
