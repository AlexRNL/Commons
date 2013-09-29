package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

/**
 * Test suite for the {@link StringUtils} class.
 * @author Alex
 */
public class StringUtilsTest {
	
	/**
	 * Test method for {@link StringUtils#isNewLine(char)}.
	 */
	@Test
	public void testIsNewLine () {
		assertFalse(StringUtils.isNewLine('a'));
		assertTrue(StringUtils.isNewLine('\n'));
		assertTrue(StringUtils.isNewLine('\r'));
	}
	
	/**
	 * Test method for {@link StringUtils#toCharList(String)}
	 */
	@Test
	public void testToCharList () {
		assertEquals(Arrays.asList(new Character[] { }), StringUtils.toCharList(""));
		assertEquals(Arrays.asList(new Character[] { 'l', 'd', 'r' }), StringUtils.toCharList("ldr"));
	}
	
	/**
	 * Test method for {@link StringUtils#nullOrEmpty(String)}.
	 */
	@Test
	public void testNullOrEmpty () {
		assertTrue(StringUtils.nullOrEmpty(null));
		assertTrue(StringUtils.nullOrEmpty(""));
		assertFalse(StringUtils.nullOrEmpty("camelCase"));
	}
	
	/**
	 * Test method for {@link StringUtils#neitherNullNorEmpty(String)}.
	 */
	@Test
	public void testNeitherNullNorEmpty () {
		assertFalse(StringUtils.neitherNullNorEmpty(null));
		assertFalse(StringUtils.neitherNullNorEmpty(""));
		assertTrue(StringUtils.neitherNullNorEmpty("camelCase"));
	}
	
	/**
	 * Test method for {@link StringUtils#replaceIfNull(String, String)}.
	 */
	@Test
	public void testReplaceIfNull () {
		assertEquals("test", StringUtils.replaceIfNull(null, "test"));
		assertEquals("test", StringUtils.replaceIfNull("test", "test2"));
	}
	
	/**
	 * Test method for {@link StringUtils#emptyIfNull(String)}.
	 */
	@Test
	public void testEmptyIfNull () {
		assertEquals("", StringUtils.emptyIfNull((String) null));
		assertEquals("test", StringUtils.emptyIfNull("test"));
	}
	
	/**
	 * Test method for {@link StringUtils#separateWith(String, Object...)}.
	 */
	@Test
	public void testSeparateWith () {
		assertEquals("1,2,3", StringUtils.separateWith(",", 1, 2, 3));
		assertEquals("1", StringUtils.separateWith(",", 1));
	}
	
	/**
	 * Test method for {@link StringUtils#getMD5(String)}.
	 */
	@Test
	public void testgetMD5String () {
		assertEquals("0cc175b9c0f1b6a831c399e269772661", StringUtils.getMD5("a"));
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", StringUtils.getMD5("abcdefghijklmnopqrstuvwxyz"));
	}
	
	/**
	 * Test method for {@link StringUtils#getMD5(String)}.
	 */
	@Test
	public void testgetMD5StringCharset () {
		assertEquals("0b687c31ee552ea09607eb2c2685f6eb", StringUtils.getMD5("abc123È^$ñ@!§", StandardCharsets.ISO_8859_1));
		assertEquals("65fd8f6818ff5304e5add7cf28ed3815", StringUtils.getMD5("abc123È^$ñ@!§", StandardCharsets.UTF_8));
	}

	/**
	 * Test method for {@link StringUtils#removeMultipleSpaces(String)}.
	 */
	@Test
	public void testRemoveMultipleSpaces () {
		assertEquals("", StringUtils.removeMultipleSpaces(""));
		assertEquals("", StringUtils.removeMultipleSpaces("   "));
		assertEquals("a", StringUtils.removeMultipleSpaces("a"));
		assertEquals("ABA LDR", StringUtils.removeMultipleSpaces("ABA LDR"));
		assertEquals("ABA LDR", StringUtils.removeMultipleSpaces("ABA LDR "));
		assertEquals("ABA LDR", StringUtils.removeMultipleSpaces("ABA   LDR "));
		assertEquals("ABA LDR", StringUtils.removeMultipleSpaces("  ABA   LDR "));
	}
	
	/**
	 * Test method for {@link StringUtils#removeMultipleSpaces(String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveMultipleSpacesNull () {
		StringUtils.removeMultipleSpaces(null);
	}
	
	/**
	 * Test method for {@link StringUtils#splitInLines(String, int)}.
	 */
	@Test
	public void testSplitInLines () {
		assertEquals("LDR", StringUtils.splitInLines("LDR", 4));
		assertEquals("LDR", StringUtils.splitInLines("  LDR   ", 3));
		assertEquals("L\nD\nR", StringUtils.splitInLines("LDR", 1));
		assertEquals("Hello, World!\nThis is a\ntest for a\njava method\nwhich split\nString in\nline of\nlimited length",
				StringUtils.splitInLines("Hello, World! This is a test for a java method which split String in line of limited length", 14));
		Logger.getLogger(StringUtils.class.getName()).setLevel(Level.FINE);
		assertEquals("abcd\nefgh", StringUtils.splitInLines("abcdefgh", 4));
	}
	
	/**
	 * Test method for {@link StringUtils#splitInLines(String, int)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testSplitInLinesNull () {
		StringUtils.splitInLines(null, 28);
	}
	
	/**
	 * Test method for {@link StringUtils#splitInLines(String, int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSplitInLinesIllegalArgument () {
		StringUtils.splitInLines("LDR", 0);
	}
	
	/**
	 * Test method for {@link StringUtils#splitInLinesHTML(String, int)}.
	 */
	@Test
	public void testSplitInLinesHTML () {
		assertEquals("<html>Hello, World!<br />This is a<br />test for a<br />java method<br />which split<br />String in<br />line of<br />limited length</html>",
				StringUtils.splitInLinesHTML("Hello, World! This is a test for a java method which split String in line of limited length", 14));
	}
}
