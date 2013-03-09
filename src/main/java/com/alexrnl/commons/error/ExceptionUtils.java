package com.alexrnl.commons.error;

import java.util.logging.Logger;

/**
 * Utility methods for exception handling.<br />
 * @author Alex
 */
public final class ExceptionUtils {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ExceptionUtils.class.getName());
	
	/** The separator used to separate the class from the message. */
	private static final String	CLASS_MESSAGE_SEPARATOR	= "; ";
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private ExceptionUtils () {
		super();
	}
	
	/**
	 * Print a nice message with the {@link Class} and the {@link Throwable#getMessage() message}
	 * of the exception.<br />
	 * @param e
	 *        the throwable object to display.
	 * @return a {@link String} containing the class and the message of the exception.
	 */
	public static String displayClassAndMessage (final Throwable e) {
		if (e == null) {
			lg.warning("Cannot display null exception.");
			return "null exception caught";
		}
		return e.getClass() + CLASS_MESSAGE_SEPARATOR + e.getMessage();
	}
}
