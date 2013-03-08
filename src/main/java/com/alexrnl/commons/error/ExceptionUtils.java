package com.alexrnl.commons.error;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * TODO
 * @author Alex
 */
public final class ExceptionUtils {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(ExceptionUtils.class.getName());
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private ExceptionUtils () {
		super();
	}
	
	/**
	 * 
	 * @param e
	 *        the throwable object to display.
	 * @return a {@link String} containing the class and the message of the exception.
	 * @throws NullPointerException
	 *         if the {@link Throwable} is <code>null</code>.
	 */
	public static String displayClassAndMessage (final Throwable e) throws NullPointerException {
		Objects.requireNonNull(e);
		return e.getClass() + "; " + e.getMessage();
	}
}
