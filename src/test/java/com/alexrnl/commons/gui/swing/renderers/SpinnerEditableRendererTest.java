package com.alexrnl.commons.gui.swing.renderers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link SpinnerEditableRenderer} class.
 * @author Alex
 */
public class SpinnerEditableRendererTest {
	/** The renderer to test */
	private SpinnerEditableRenderer spinnerRenderer;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		spinnerRenderer = new SpinnerEditableRenderer(new JSpinner());
	}
	
	/**
	 * Test method for {@link SpinnerEditableRenderer#SpinnerEditableRenderer(javax.swing.JSpinner)}.
	 */
	@Test
	public void testSpinnerEditableRenderer () {
		assertEquals(SwingConstants.TRAILING, spinnerRenderer.getTextField().getHorizontalAlignment());
		assertTrue(spinnerRenderer.getTextField().isEditable());
	}
}
