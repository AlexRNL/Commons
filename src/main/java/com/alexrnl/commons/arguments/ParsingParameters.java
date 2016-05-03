package com.alexrnl.commons.arguments;

/**
 * Class which contain the parameters when parsing an argument.
 * @author Alex
 */
public class ParsingParameters {
	/** The target object to assign the parameter value */
	private final Object	target;
	/** The value to assign to the parameter */
	private final String	value;
	/** The name of the argument matching the parameter */
	private final String	argument;
	
	/**
	 * Constructor #1.<br />
	 * @param target
	 *        the target object to assign the parameter value.
	 * @param value
	 *        the value to assign to the parameter.
	 * @param argument
	 *        the name of the argument matching the parameter.
	 */
	public ParsingParameters (final Object target, final String value, final String argument) {
		super();
		if (target == null) {
			throw new IllegalArgumentException("Target object cannot be null");
		}
		if (value == null) {
			throw new IllegalArgumentException("Parameter value cannot be null");
		}
		if (argument == null || argument.isEmpty()) {
			throw new IllegalArgumentException("Argument name cannot be null or empty");
		}
		
		this.target = target;
		this.value = value;
		this.argument = argument;
	}
	
	/**
	 * Return the attribute target.
	 * @return the attribute target.
	 */
	public Object getTarget () {
		return target;
	}
	
	/**
	 * Return the attribute value.
	 * @return the attribute value.
	 */
	public String getValue () {
		return value;
	}
	
	/**
	 * Return the attribute argument.
	 * @return the attribute argument.
	 */
	public String getArgument () {
		return argument;
	}
	
}
