package com.alexrnl.commons.gui.swing.renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JList;

/**
 * This renderer highlight a defined set of strings.<br />
 * @author Alex
 */
public class ListCellTextHighLighterRenderer extends ListCellTextRenderer<String> {
	/** Logger */
	private static final Logger			LG	= Logger.getLogger(ListCellTextHighLighterRenderer.class.getName());
	
	/** The collection of strings to highlight */
	private final Collection<String>	toHighlight;
	/** The color to use for the background of the strings to highlight */
	private final Color					highlightColor;
	
	/**
	 * Constructor #1.<br />
	 * @param toHighlight
	 *        the collection of strings to highlight.
	 * @param highlightColor
	 *        the color to use for painting the specified {@link String}s' background.
	 */
	public ListCellTextHighLighterRenderer (final Collection<String> toHighlight, final Color highlightColor) {
		super();
		Objects.requireNonNull(toHighlight, "Collection of strings to highlight cannot be null");
		Objects.requireNonNull(highlightColor, "Color for highlighting cannot be null");
		this.toHighlight = toHighlight;
		this.highlightColor = highlightColor;
	}
	
	@Override
	public Component getListCellRendererComponent (final JList<? extends String> list,
			final String value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		
		if (toHighlight.contains(component.getText()) && !isSelected) {
			component.setBackground(highlightColor);
			if (LG.isLoggable(Level.FINE)) {
				LG.fine("Highlighting " + component.getText() + " with " + highlightColor
						+ "; " + component.getBackground());
			}
		}
		
		return component;
	}
	
	@Override
	protected String getText (final String value) {
		return value;
	}
}
