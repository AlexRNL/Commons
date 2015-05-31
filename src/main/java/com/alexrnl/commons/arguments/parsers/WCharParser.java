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
	public Character getValue (final String parameter) {
		return parameter.charAt(0);
	}
	
}
