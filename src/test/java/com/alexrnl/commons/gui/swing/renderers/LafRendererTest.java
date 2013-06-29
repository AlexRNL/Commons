package com.alexrnl.commons.gui.swing.renderers;

import static org.junit.Assert.assertEquals;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link LafRenderer} class.
 * @author Alex
 */
public class LafRendererTest {
	/** The renderer to be tested */
	private LafRenderer lafRenderer;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		lafRenderer = new LafRenderer();
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.renderers.LafRenderer#getText(javax.swing.UIManager.LookAndFeelInfo)}.
	 */
	@Test
	public void testGetTextLookAndFeelInfo () {
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			assertEquals(laf.getName(), lafRenderer.getText(laf));
		}
	}
}
