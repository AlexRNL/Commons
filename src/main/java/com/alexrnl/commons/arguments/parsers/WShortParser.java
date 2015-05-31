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
	public Short getValue (final String parameter) {
		return Short.parseShort(parameter);
	}
	
}
