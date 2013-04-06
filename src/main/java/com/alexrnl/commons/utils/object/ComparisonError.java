package com.alexrnl.commons.utils.object;

import com.alexrnl.commons.error.TopLevelError;

/**
 * An error occurred while comparing objects with {@link AutoCompare}.
 * @author Alex
 */
public class ComparisonError extends TopLevelError {
	/** Serial version UID */
	private static final long	serialVersionUID	= -2461613595268720759L;
	
	/**
	 * Constructor #1.<br />
	 * @param message
	 *        the message of the exception.
	 * @param cause
	 *        the exception which caused the error.
	 */
	public ComparisonError (final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor #2.<br />
	 */
	public ComparisonError () {
		super();
	}
	
	/**
	 * Constructor #3.<br />
	 * @param message
	 *        the message of the exception.
	 */
	public ComparisonError (final String message) {
		super(message);
	}
	
	/**
	 * Constructor #4.<br />
	 * @param cause
	 *        the exception which caused the error.
	 */
	public ComparisonError (final Throwable cause) {
		super(cause);
	}
}
