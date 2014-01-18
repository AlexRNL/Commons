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
	protected Double getValue (final String parameter) throws IllegalArgumentException {
		return Double.valueOf(parameter);
	}
}
