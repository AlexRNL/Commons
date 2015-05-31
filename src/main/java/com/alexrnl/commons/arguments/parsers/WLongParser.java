package com.alexrnl.commons.arguments.parsers;

/**
 * Parser for the {@link Long} field type.
 * @author Alex
 */
public class WLongParser extends AbstractParser<Long> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WLongParser () {
		super(Long.class);
	}
	
	@Override
	public Long getValue (final String parameter) {
		return Long.parseLong(parameter);
	}
	
}
