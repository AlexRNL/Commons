package com.alexrnl.commons.gui.swing.renderers;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;
import javax.swing.UIManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ListCellTextHighLighterRenderer} class.
 * @author Alex
 */
public class ListCellTextHighLighterRendererTest {
	/** The values to highlight */
	private Collection<String>				valuesToHighlight;
	/** The renderer */
	private ListCellTextHighLighterRenderer highlighter;
	
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		valuesToHighlight = Arrays.asList("ABA", "ldr", "to my second WW");
		highlighter = new ListCellTextHighLighterRenderer(valuesToHighlight, Color.CYAN);
	}

	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.renderers.ListCellTextHighLighterRenderer#ListCellTextHighLighterRenderer(java.util.Collection, java.awt.Color)}.
	 */
	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void testListCellTextHighLighterRendererNullCollection () {
		new ListCellTextHighLighterRenderer(null, Color.BLACK);
	}

	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.renderers.ListCellTextHighLighterRenderer#ListCellTextHighLighterRenderer(java.util.Collection, java.awt.Color)}.
	 */
	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void testListCellTextHighLighterRendererNullColor () {
		new ListCellTextHighLighterRenderer(new ArrayList<String>(), null);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.renderers.ListCellTextHighLighterRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.String, int, boolean, boolean)}.
	 */
	@Test
	public void testGetListCellRendererComponentJListOfQextendsStringStringIntBooleanBoolean () {
		final JList<String> list = new JList<>();
		final Component notHighlighNotSelected = highlighter.getListCellRendererComponent(list, "test", 2, false, false);
		assertEquals(UIManager.getDefaults().getColor("List.background"), notHighlighNotSelected.getBackground());
		final Component notHighlightSelected = highlighter.getListCellRendererComponent(list, "Styris", 0, true, false);
		assertEquals(UIManager.getDefaults().getColor("List.selectionBackground"), notHighlightSelected.getBackground());
		final Component highlightNotSelected = highlighter.getListCellRendererComponent(list, "ABA", 0, false, false);
		assertEquals(Color.CYAN, highlightNotSelected.getBackground());
		final Component highlightSelected = highlighter.getListCellRendererComponent(list, "ldr", 0, true, false);
		assertEquals(UIManager.getDefaults().getColor("List.selectionBackground"), highlightSelected.getBackground());
		
		Logger.getLogger(ListCellTextHighLighterRenderer.class.getName()).setLevel(Level.FINE);
		highlighter.getListCellRendererComponent(list, "ABA", 0, false, false);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.gui.swing.renderers.ListCellTextHighLighterRenderer#getText(java.lang.String)}.
	 */
	@Test
	public void testGetTextString () {
		assertEquals("", highlighter.getText(""));
		assertEquals("ABA", highlighter.getText("ABA"));
		assertEquals("ldr", highlighter.getText("ldr"));
	}
}
