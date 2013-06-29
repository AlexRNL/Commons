package com.alexrnl.commons.gui.swing.renderers;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Generic abstract class for simple text list cell renderer.<br />
 * A {@link JLabel} is renderer using a {@link DefaultListCellRenderer} (to avoid combo boxes to
 * shrink vertically).
 * @author Alex
 * @param <T> The type of data being rendered.
 */
public abstract class ListCellTextRenderer<T> implements ListCellRenderer<T> {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ListCellTextRenderer.class.getName());
	
	/** The first renderer to use (avoid shrinking) */
	private final DefaultListCellRenderer renderer;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public ListCellTextRenderer () {
		super();
		renderer = new DefaultListCellRenderer();
	}
	
	@Override
	public Component getListCellRendererComponent (final JList<? extends T> list, final T value,
			final int index, final boolean isSelected, final boolean cellHasFocus) {
		final JLabel component = (JLabel) renderer.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		
		component.setText(getText(value));
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Rendering text " + component.getText());
		}
		
		return component;
	}
	
	/**
	 * The text to be rendered.<br />
	 * @param value
	 *        the value being rendered.
	 * @return a String representation of the value specified.
	 */
	protected abstract String getText (T value);
}
