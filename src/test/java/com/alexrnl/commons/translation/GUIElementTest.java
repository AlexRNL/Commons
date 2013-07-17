package com.alexrnl.commons.translation;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link GUIElement} class.
 * @author Alex
 */
public class GUIElementTest {
	/** The component to test */
	private GUIElement	component;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		component = new GUIElement("commons.menu.open");
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.GUIElement#toString()}.
	 */
	@Test
	public void testToString () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.GUIElement#getText()}.
	 */
	@Test
	public void testGetText () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.GUIElement#getShortcut()}.
	 */
	@Test
	public void testGetShortcut () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.translation.GUIElement#getToolTip()}.
	 */
	@Test
	public void testGetToolTip () {
		fail("Not yet implemented"); // TODO
	}
}
