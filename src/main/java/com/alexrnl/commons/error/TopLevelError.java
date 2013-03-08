package com.alexrnl.commons.error;

/**
 * Abstract class to use for declaring severe errors thrown.<br />
 * Will be caught at the higher level of the application.
 * @author Alex
 */
public abstract class TopLevelError extends Error {
	/** Serial Version UID */
	private static final long	serialVersionUID	= -551856567230335889L;
	
	/**
	 * Constructor #1.<br />
	 * Build an error with both an error message and a cause.
	 * @param message
	 *        the details of the error.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public TopLevelError (final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor, prefer other constructor when possible.
	 */
	public TopLevelError () {
		super();
	}
	
	/**
	 * Constructor #3.<br />
	 * Build an error with and error message.
	 * @param message
	 *        the details of the error.
	 */
	public TopLevelError (final String message) {
		super(message);
	}
	
	/**
	 * Constructor #4.<br />
	 * Build an error with a cause.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public TopLevelError (final Throwable cause) {
		super(cause);
	}
	
}