package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link Word} class.
 * @author Alex
 */
public class WordTest {
	/** The word to test */
	private Word	simpleWord;
	/** An empty word */
	private Word	emptyWord;
	/** A complex word */
	private Word	complexWord;
	/** The words to test */
	private List<Word> words;

	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		simpleWord = new Word("ldr", 2, 5);
		emptyWord = new Word("", 2, 2);
		complexWord = new Word("anti-gel", 8, 16);
		
		words = new LinkedList<>();
		words.add(simpleWord);
		words.add(emptyWord);
		words.add(complexWord);
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
	}/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final Word word : words) {
			for (final Word other : words) {
				if (word == other) {
					assertEquals(word.hashCode(), other.hashCode());
				} else {
					assertNotEquals(word.hashCode(), other.hashCode());
				}
			}
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#length()}.
	 */
	@Test
	public void testLength () {
		for (final Word word : words) {
			assertEquals(word.getWord().length(), word.length());
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#charAt(int)}.
	 */
	@Test
	public void testCharAt () {
		for (final Word word : words) {
			int i = 0;
			while (i < word.length()) {
				assertEquals(word.getWord().charAt(i), word.charAt(i));
				i++;
			}
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#subSequence(int, int)}.
	 */
	@Test
	public void testSubSequence () {
		assertEquals(complexWord.getWord().subSequence(0, 2), complexWord.subSequence(0, 2));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#compareTo(java.lang.String)}.
	 */
	@Test
	public void testCompareTo () {
		for (final Word word : words) {
			for (final Word other : words) {
				assertEquals(word.getWord().compareTo(other.getWord()), word.compareTo(other.getWord()));
			}
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#toString()}.
	 */
	@Test
	public void testToString () {
		for (final Word word : words) {
			assertEquals(word.getWord().toString(), word.toString());
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject () {
		for (final Word word : words) {
			for (final Word other : words) {
				if (word == other) {
					assertEquals(word, other);
				} else {
					assertNotEquals(word, other);
				}
			}
			assertFalse(word.equals(new Object()));
		}
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.Word#getNextWord(java.lang.String)}.
	 */
	@Test
	public void testGetNextWord () {
		assertEquals("ldr", Word.getNextWord("  ldr! de,dpo").getWord());
		assertEquals("jean-jacques", Word.getNextWord("jean-jacques xxx").getWord());
		assertEquals("", Word.getNextWord(".").getWord());
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
