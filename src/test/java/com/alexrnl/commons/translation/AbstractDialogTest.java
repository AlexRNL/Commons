package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link AbstractDialog} class.
 * @author Alex
 */
public class AbstractDialogTest {
	/** The dialog to test */
	private HelloDialog	dialog;
	
	/**
	 * Basic class extending the abstract dialog.
	 * @author Alex
	 */
	private static class HelloDialog extends AbstractDialog {
		/** The key to the translation dialog */
		private final String	helloKey;
		
		/**
		 * Constructor #1.<br />
		 */
		public HelloDialog () {
			super();
			this.helloKey = "commons.dialogs.hello";
		}
		
		@Override
		public String getTranslationKey () {
			return helloKey;
		}
		
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		dialog = new HelloDialog();
	}
	
	/**
	 * Test method for {@link AbstractDialog#title()}.
	 */
	@Test
	public void testTitle () {
		assertEquals("commons.dialogs.hello.title", dialog.title());
	}
	
	/**
	 * Test method for {@link AbstractDialog#message()}.
	 */
	@Test
	public void testMessage () {
		assertEquals("commons.dialogs.hello.message", dialog.message());
	}
	
	/**
	 * Test method for {@link AbstractDialog#getTranslationKey()}.
	 */
	@Test
	public void testGetTranslationKey () {
		assertEquals("commons.dialogs.hello", dialog.getTranslationKey());
	}
	
	/**
	 * Test method for {@link AbstractDialog#getParameters()}.
	 */
	@Test
	public void testGetParameters () {
		assertNotNull(dialog.getParameters());
		assertEquals(0, dialog.getParameters().length);
	}
}
