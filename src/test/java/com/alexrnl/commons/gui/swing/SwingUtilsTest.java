package com.alexrnl.commons.gui.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.translation.Translator;

/**
 * Test suite for the {@link SwingUtils} class.
 * @author Alex
 */
public class SwingUtilsTest {
	/** Translator to use */
	private Translator translator;
	
	/**
	 * Set up test attributes.
	 * @throws URISyntaxException
	 *         if there is a problem with the path of the translation file.
	 */
	@Before
	public void setUp () throws URISyntaxException {
		translator = new Translator(Paths.get(getClass().getResource("/locale.xml").toURI()));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.SwingUtils#setLookAndFeel(java.lang.String)}.
	 */
	@Test
	public void testSetLookAndFeel () {
		assertFalse(SwingUtils.setLookAndFeel(null));
		assertFalse(SwingUtils.setLookAndFeel(""));
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			assertTrue(SwingUtils.setLookAndFeel(laf.getName()));
			Logger.getLogger(SwingUtils.class.getName()).setLevel(Level.FINE);
		}
		UIManager.installLookAndFeel(new LookAndFeelInfo("LDR", "com.alexrnl.commons.gui.swing.ldr"));
		assertFalse(SwingUtils.setLookAndFeel("LDR"));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.SwingUtils#getMenuItem(Translator, com.alexrnl.commons.translation.GUIElement, java.awt.event.ActionListener)}.
	 */
	@Test
	public void testGetMenuItem () {
		fail("Implement test method");
		// TODO implements test method
	}
		
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.SwingUtils#getMenu(com.alexrnl.commons.translation.Translator, String)}.
	 */
	@Test
	public void testGetMenu () {
		final JMenu open = SwingUtils.getMenu(translator, "commons.menu.file.open");
		assertEquals("Open a file", open.getText());
		assertEquals('O', open.getMnemonic());

		final JMenu close = SwingUtils.getMenu(translator, "commons.menu.file.close");
		assertEquals("Close a file", close.getText());
		assertEquals('\0', close.getMnemonic());

		final JMenu quit = SwingUtils.getMenu(translator, "commons.menu.file.quit");
		assertEquals("Quit the application", quit.getText());
		assertEquals('\0', quit.getMnemonic());
	}
}
