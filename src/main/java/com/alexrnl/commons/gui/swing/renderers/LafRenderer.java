package com.alexrnl.commons.gui.swing.renderers;

import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Renderer for Look and Feel combo box.<br />
 * @author Alex
 */
public class LafRenderer extends ListCellTextRenderer<LookAndFeelInfo> {
	
	@Override
	protected String getText (final LookAndFeelInfo value) {
		return value.getName();
	}
	
}
