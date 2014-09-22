package com.alexrnl.commons.arguments.parsers;


/**
 * Parser for the {@link String} field type.
 * @author Alex
 */
public class StringParser extends AbstractParser<String> {
	
	/**
	 * Constructor #1.<br />
	 */
	public StringParser () {
		super(String.class);
	}
	
	@Override
	public String getValue (final String parameter) throws IllegalArgumentException {
		return parameter;
	}
}
