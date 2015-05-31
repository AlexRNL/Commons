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
	public Integer getValue (final String parameter) {
		return Integer.parseInt(parameter);
	}
	
}
