package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link SimpleDialog} class.
 * @author Alex
 */
public class SimpleDialogTest {
	/** The dialog to test */
	private SimpleDialog	dialog;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		dialog = new SimpleDialog("title", "message");
	}
	
	/**
	 * Test method for {@link SimpleDialog#title()}.
	 */
	@Test
	public void testTitle () {
		assertEquals("title", dialog.title());
	}
	
	/**
	 * Test method for {@link SimpleDialog#message()}.
	 */
	@Test
	public void testMessage () {
		assertEquals("message", dialog.message());
	}
	
	/**
	 * Test method for {@link SimpleDialog#getParameters()}.
	 */
	@Test
	public void testGetParameters () {
		assertTrue(dialog.getParameters().isEmpty());
	}
	
	/**
	 * Test method for {@link SimpleDialog#getTranslationKey()}.
	 */
	@Test
	public void testGetTranslationKey () {
		assertEquals("message", dialog.getTranslationKey());
	}
}
