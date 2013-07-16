package com.alexrnl.commons.translation;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link AbstractDialog} class.
 * @author Alex
 */
public class AbstractDialogTest {
	
	/**
	 * Basic class extending the abstract dialog.
	 * @author Alex
	 */
	private static class HelloDialog extends AbstractDialog {
		/** The key to the translation dialog */
		private final String	helloKey;
		/** Parameters of the dialog */
		private final Object[]	parameters;
		
		/**
		 * Constructor #1.<br />
		 * @param name
		 *        the name to say hello to.
		 */
		public HelloDialog (final String name) {
			super();
			this.helloKey = "commons.dialogs.hello";
			this.parameters = new Object[1];
			parameters[0] = name;
		}
		
		@Override
		public String toString () {
			return helloKey;
		}
		
		@Override
		public Object[] getParameters () {
			return parameters;
		}
		
	}
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.AbstractDialog#title()}.
	 */
	@Test
	public void testTitle () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.AbstractDialog#message()}.
	 */
	@Test
	public void testMessage () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.AbstractDialog#toString()}.
	 */
	@Test
	public void testToString () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.AbstractDialog#getParameters()}.
	 */
	@Test
	public void testGetParameters () {
		fail("Not yet implemented"); // TODO
	}
}
