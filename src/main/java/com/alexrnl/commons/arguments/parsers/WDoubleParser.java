package com.alexrnl.commons.arguments.parsers;


/**
 * Parser for the {@link Double} class.
 * @author Alex
 */
public class WDoubleParser extends AbstractParser<Double> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WDoubleParser () {
		super(Double.class);
	}
	
	@Override
	public Double getValue (final String parameter) {
		return Double.valueOf(parameter);
	}
}
