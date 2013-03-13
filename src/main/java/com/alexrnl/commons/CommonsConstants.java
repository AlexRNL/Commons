package com.alexrnl.commons;

/**
 * Commons library constants.
 * @author Alex
 */
public final class CommonsConstants {
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private CommonsConstants () {
		super();
	}
	
	/** Default prime number to use for hash code computation */
	public static final int		PRIME_FOR_HASHCODE		= 31;
	
	/** The separator between hours and minutes */
	public static final String	TIME_SEPARATOR			= ":";
	
	/** The regex pattern that matches any non decimal character */
	public static final String	NON_DECIMAL_CHARACTER	= "[^0-9]";
}
