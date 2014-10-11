package com.alexrnl.commons.translation;

import static org.junit.Assert.assertEquals;

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
	 * Test method for {@link GUIElement#getTranslationKey()}.
	 */
	@Test
	public void testGetTranslationKey () {
		assertEquals("commons.menu.open", component.getTranslationKey());
	}
	
	/**
	 * Test method for {@link GUIElement#getText()}.
	 */
	@Test
	public void testGetText () {
		assertEquals("commons.menu.open", component.getText());
	}
	
	/**
	 * Test method for {@link GUIElement#getShortcut()}.
	 */
	@Test
	public void testGetShortcut () {
		assertEquals("commons.menu.open.shortcut", component.getShortcut());
	}
	
	/**
	 * Test method for {@link GUIElement#getToolTip()}.
	 */
	@Test
	public void testGetToolTip () {
		assertEquals("commons.menu.open.tooltip", component.getToolTip());
	}
}
