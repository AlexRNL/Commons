package com.alexrnl.commons.utils.object;


/**
 * An exception occurred while getting value from objects with reflection.
 * @author Alex
 */
public class ReflectionException extends RuntimeException {
	/** Serial version UID */
	private static final long	serialVersionUID	= -2461613595268720759L;
	
	/**
	 * Constructor #1.<br />
	 * @param message
	 *        the message of the exception.
	 * @param cause
	 *        the exception which caused the error.
	 */
	public ReflectionException (final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor #2.<br />
	 * Default constructor.
	 */
	public ReflectionException () {
		super();
	}
	
	/**
	 * Constructor #3.<br />
	 * @param message
	 *        the message of the exception.
	 */
	public ReflectionException (final String message) {
		super(message);
	}
	
	/**
	 * Constructor #4.<br />
	 * @param cause
	 *        the exception which caused the error.
	 */
	public ReflectionException (final Throwable cause) {
		super(cause);
	}
}
