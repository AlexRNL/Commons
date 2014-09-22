package com.alexrnl.commons.arguments.parsers;

/**
 * Parser for the {@link Integer} field type.
 * @author Alex
 */
public class WIntegerParser extends AbstractParser<Integer> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WIntegerParser () {
		super(Integer.class);
	}
	
	@Override
	public Integer getValue (final String parameter) throws IllegalArgumentException {
		return Integer.parseInt(parameter);
	}
	
}
