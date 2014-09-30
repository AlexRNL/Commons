package com.alexrnl.commons.error;

import java.util.logging.Logger;

/**
 * Utility methods for exception handling.<br />
 * @author Alex
 */
public final class ExceptionUtils {
	/** Logger */
	private static final Logger	LG	= Logger.getLogger(ExceptionUtils.class.getName());
	
	/** The separator used to separate the class from the message. */
	private static final String	CLASS_MESSAGE_SEPARATOR	= "; ";
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private ExceptionUtils () {
		super();
		throw new InstantiationError("Instantiation of class " + ExceptionUtils.class + " is forbidden");
	}
	
	/**
	 * Print a nice message with the {@link Class} and the {@link Throwable#getMessage() message}
	 * of the exception.<br />
	 * @param e
	 *        the throwable object to display.
	 * @return a {@link String} containing the class and the message of the exception.
	 */
	public static String display (final Throwable e) {
		if (e == null) {
			LG.warning("Cannot display null exception.");
			return "null exception caught";
		}
		return e.getClass() + CLASS_MESSAGE_SEPARATOR + e.getMessage();
	}
}
