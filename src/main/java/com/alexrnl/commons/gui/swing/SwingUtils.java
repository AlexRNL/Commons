package com.alexrnl.commons.gui.swing;

import java.awt.Frame;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * TODO
 * @author Alex
 */
public final class SwingUtils {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(SwingUtils.class.getName());
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private SwingUtils () {
		super();
	}
	
	/**
	 * Sets the look and feel of the application.<br />
	 * Update all frames and pack them to update
	 * @param lookAndFeelName
	 *        the name of the look and feel to set.
	 * @return <code>true</code> if the new look and feel was successfully set.
	 */
	public static boolean setLookAndFeel (final String lookAndFeelName) {
		boolean lookAndFeelApplied = false;
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			if (lg.isLoggable(Level.FINE)) {
				lg.fine(laf.getName());
			}
			if (laf.getName().equals(lookAndFeelName)) {
				try {
					UIManager.setLookAndFeel(laf.getClassName());
					if (lg.isLoggable(Level.FINE)) {
						lg.fine("Look and feel properly setted (" + laf.getName() + ").");
					}
					lookAndFeelApplied = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					lg.warning("Could not set the look and feel " + laf.getName() + ": "
						+ ExceptionUtils.display(e));
					lookAndFeelApplied = false;
				}
			}
		}
		// Applying changes to application
		if (lookAndFeelApplied) {
			for (final Frame frame : Frame.getFrames()) {
				SwingUtilities.updateComponentTreeUI(frame);
			}
		} else {
			lg.warning("Could not find (or set) the look and feel " + lookAndFeelName
					+ ". Using default look and feel.");
		}
		return lookAndFeelApplied;
	}
}
