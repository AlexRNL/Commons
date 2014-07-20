package com.alexrnl.commons.arguments.parsers;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Parser for the {@link Path} class.
 * @author Alex
 */
public class PathParser extends AbstractParser<Path> {
	
	/**
	 * Constructor #1.<br />
	 */
	public PathParser () {
		super(Path.class);
	}
	
	@Override
	public Path getValue (final String parameter) throws IllegalArgumentException {
		return Paths.get(parameter);
	}
	
}
