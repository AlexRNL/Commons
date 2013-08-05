package com.alexrnl.commons.utils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent a word in a {@link String}.<br />
 * @author Alex
 */
public final class Word {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(Word.class.getName());
	
	/** The word it self */
	private final String	word;
	/** The index of the beginning of the word */
	private final int		begin;
	/** The index of the end of the word */
	private final int		end;
	
	/**
	 * Constructor #1.<br />
	 * @param word
	 *        the word represented (cannot be null).
	 * @param begin
	 *        the index of the beginning of the word.
	 * @param end
	 *        the index of the end of the word.
	 */
	public Word (final String word, final int begin, final int end) {
		super();
		Objects.requireNonNull(word);
		if (word.length() != end - begin) {
			throw new IllegalArgumentException("Word " + word + " does not fit between "
					+ begin + " and " + end);
		}
		this.word = word;
		this.begin = begin;
		this.end = end;
	}

	/**
	 * Return the attribute word.
	 * @return the attribute word.
	 */
	public String getWord () {
		return word;
	}

	/**
	 * Return the attribute begin.
	 * @return the attribute begin.
	 */
	public int getBegin () {
		return begin;
	}

	/**
	 * Return the attribute end.
	 * @return the attribute end.
	 */
	public int getEnd () {
		return end;
	}
	
	/**
	 * Return the next word in the {@link String}.<br />
	 * @param string
	 *        the string to parse.
	 * @return the next word.
	 */
	public static Word getNextWord (final String string) {
		Objects.requireNonNull(string);
		
		int begin = 0;
		int end = string.length();
		
		boolean beginFound = false;
		int currentIndex = begin;
		while (currentIndex < end) {
			if (!beginFound && allowedInWord(string.charAt(currentIndex))) {
				begin = currentIndex;
				beginFound = true;
			} else if (beginFound && !allowedInWord(string.charAt(currentIndex))) {
				end = currentIndex;
			}
			currentIndex++;
		}
		
		final String word = string.substring(begin, end);
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("The next word in '" + string + "' is '" + word + "'");
		}
		return new Word(word, begin, end);
	}
	
	/**
	 * Check if a {@link Character} is allowed in a word.<br />
	 * <code>true</code> if this is a letter from the alphabet or a dash <code>-</code>.
	 * @param c
	 *        the character to check.
	 * @return <code>true</code> if the character can be found in a word.
	 */
	public static boolean allowedInWord (final char c) {
		return Character.isAlphabetic(c) || c == '-';
	}
}
