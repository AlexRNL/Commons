package com.alexrnl.commons.arguments.parsers;

/**
 * Parser for the {@link Short} field type.
 * @author Alex
 */
public class WShortParser extends AbstractParser<Short> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WShortParser () {
		super(Short.class);
	}
	
	@Override
	protected Short getValue (final String parameter) throws IllegalArgumentException {
		return Short.parseShort(parameter);
	}
	
}
