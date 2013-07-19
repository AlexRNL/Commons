package com.alexrnl.commons.gui.swing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.Test;

/**
 * Test suite for the {@link SwingUtils} class.
 * @author Alex
 */
public class SwingUtilsTest {
	
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
}
