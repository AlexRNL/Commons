package com.alexrnl.commons.arguments.parsers;

/**
 * Parser for the {@link Byte} field type.
 * @author Alex
 */
public class WByteParser extends AbstractParser<Byte> {
	
	/**
	 * Constructor #1.<br />
	 */
	public WByteParser () {
		super(Byte.class);
	}
	
	@Override
	public Byte getValue (final String parameter) {
		return Byte.parseByte(parameter);
	}
	
}
