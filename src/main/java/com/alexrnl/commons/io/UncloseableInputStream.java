package com.alexrnl.commons.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decorator for an input stream that should not be closed.<br />
 * For example, use on <code>System.in</code>:
 * <pre>
 * System.setIn(new UncloseableInputStream(System.in));
 * </pre>
 * @author Alex
 */
public class UncloseableInputStream extends InputStream {
	/** The underlying input stream. */
	private final InputStream	inputStream;
	
	/**
	 * Constructor #1.<br />
	 * @param inputStream
	 *        the actual input stream.
	 */
	public UncloseableInputStream (final InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}
	
	@Override
	public int read () throws IOException {
		return inputStream.read();
	}
	
	@Override
	public void close () throws IOException {
		// Underlying method call blocked
	}
}
