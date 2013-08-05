package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Word} class.
 * @author Alex
 */
public class WordTest {
	/** The word to test */
	private Word	simpleWord;

	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		simpleWord = new Word("ldr", 2, 5);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#Word(String, int, int)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testWordNullPointerException () {
		new Word(null, 1, 2);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#Word(String, int, int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWordIllegalArgumentException () {
		new Word("nh", 4, 2);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#getWord()}.
	 */
	@Test
	public void testGetWord () {
		assertEquals("ldr", simpleWord.getWord());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#getBegin()}.
	 */
	@Test
	public void testGetBegin () {
		assertEquals(2, simpleWord.getBegin());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#getEnd()}.
	 */
	@Test
	public void testGetEnd () {
		assertEquals(5, simpleWord.getEnd());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#getNextWord(java.lang.String)}.
	 */
	@Test
	public void testGetNextWord () {
		assertEquals("ldr", Word.getNextWord("  ldr! de,dpo").getWord());
		assertEquals("jean-jacques", Word.getNextWord("jean-jacques xxx").getWord());
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#allowedInWord(char)}.
	 */
	@Test
	public void testAllowedInWord () {
		assertTrue(Word.allowedInWord('b'));
		assertTrue(Word.allowedInWord('-'));
		assertFalse(Word.allowedInWord(' '));
	}
}
