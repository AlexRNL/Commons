package com.alexrnl.commons.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decorator for an input stream that should not be closed.<br />
 * For example, use on <code>System.in</code>:
 * 
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
	public int read (final byte[] b) throws IOException {
		return inputStream.read(b);
	}
	
	@Override
	public int read (final byte[] b, final int off, final int len) throws IOException {
		return inputStream.read(b, off, len);
	}
	
	@Override
	public long skip (final long n) throws IOException {
		return inputStream.skip(n);
	}
	
	@Override
	public int available () throws IOException {
		return inputStream.available();
	}
	
	@Override
	public synchronized void mark (final int readlimit) {
		inputStream.mark(readlimit);
	}
	
	@Override
	public synchronized void reset () throws IOException {
		inputStream.reset();
	}
	
	@Override
	public boolean markSupported () {
		return inputStream.markSupported();
	}
	
	@Override
	public void close () throws IOException {
		// Underlying method call blocked
	}
}
