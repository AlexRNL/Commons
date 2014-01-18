package com.alexrnl.commons.arguments.parsers;


/**
 * Parser for the {@link Character} field type.
 * @author Alex
 */
public class WCharParser extends AbstractParser<Character> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WCharParser () {
		super(Character.class);
	}

	@Override
	protected Character getValue (final String parameter) throws IllegalArgumentException {
		return parameter.charAt(0);
	}
	
}
