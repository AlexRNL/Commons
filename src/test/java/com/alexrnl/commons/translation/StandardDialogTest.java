package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link StandardDialog} class.
 * @author Alex
 */
public class StandardDialogTest {
	/** The dialog to test */
	private StandardDialog	dialog;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		dialog = new StandardDialog("commons.dialogs.hello");
	}
	
	/**
	 * Test method for {@link StandardDialog#title()}.
	 */
	@Test
	public void testTitle () {
		assertEquals("commons.dialogs.hello.title", dialog.title());
	}
	
	/**
	 * Test method for {@link StandardDialog#message()}.
	 */
	@Test
	public void testMessage () {
		assertEquals("commons.dialogs.hello.message", dialog.message());
	}
	
	/**
	 * Test method for {@link StandardDialog#getTranslationKey()}.
	 */
	@Test
	public void testGetTranslationKey () {
		assertEquals("commons.dialogs.hello.message", dialog.getTranslationKey());
	}
	
	/**
	 * Test method for {@link StandardDialog#getParameters()}.
	 */
	@Test
	public void testGetParameters () {
		assertNotNull(dialog.getParameters());
		assertTrue(dialog.getParameters().isEmpty());
	}
}
