package com.alexrnl.commons.database;

import com.alexrnl.commons.error.TopLevelError;

/**
 * TODO
 * @author Alex
 */
public class DataBaseConfigurationError extends TopLevelError {
	/** Serial version UID */
	private static final long	serialVersionUID	= -5009724854819625902L;

	/**
	 * Constructor #1.<br />
	 * Build an database configuration error with both an error message and a cause.
	 * @param message
	 *        the details of the error.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public DataBaseConfigurationError (final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor, prefer other constructors when possible.
	 */
	public DataBaseConfigurationError () {
		super();
	}
	
	/**
	 * Constructor #3.<br />
	 * Build an error with and error message.
	 * @param message
	 *        the details of the error.
	 */
	public DataBaseConfigurationError (final String message) {
		super(message);
	}
	
	/**
	 * Constructor #4.<br />
	 * Build an error with a cause.
	 * @param cause
	 *        the exception that caused the error.
	 */
	public DataBaseConfigurationError (final Throwable cause) {
		super(cause);
	}
}
