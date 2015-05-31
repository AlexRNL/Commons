package com.alexrnl.commons.arguments.parsers;


/**
 * Parser for the {@link Float} class.
 * @author Alex
 */
public class WFloatParser extends AbstractParser<Float> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WFloatParser () {
		super(Float.class);
	}
	
	@Override
	public Float getValue (final String parameter) {
		return Float.valueOf(parameter);
	}
}
