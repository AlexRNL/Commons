package com.alexrnl.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Input stream whose content can be edited.<br />
 * @author Alex
 */
public class EditableInputStream extends InputStream {
	/** Logger */
	private static final Logger	LG	= Logger.getLogger(EditableInputStream.class.getName());
	
	/** The input stream used */
	private InputStream	inputStream;
	/** The charset used for the stream */
	private final Charset		charset;
	
	/**
	 * Constructor #1.<br />
	 * @param initialContent
	 *        the initial content of the stream.
	 * @param charset
	 *        the charset of the stream.
	 */
	public EditableInputStream (final String initialContent, final Charset charset) {
		super();
		this.charset = charset;
		inputStream = IOUtils.toInputStream(initialContent, charset);
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Building input stream with '" + initialContent + "'");
		}
	}
	
	/**
	 * Constructor #2.<br />
	 * Uses the default charset of the JVM.
	 * @param initialContent
	 *        the initial content of the stream.
	 */
	public EditableInputStream (final String initialContent) {
		this(initialContent, Charset.defaultCharset());
	}
	
	/**
	 * Update the content of the stream with the following string.<br />
	 * The previous stream is closed.
	 * @param content
	 *        the content to set.
	 * @throws IOException
	 *         if there is an issue while {@link #close() closing} the stream.
	 */
	public void updateStream (final String content) throws IOException {
		close();
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Changing input stream content to '" + content + "'");
		}
		inputStream = IOUtils.toInputStream(content, charset);
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
	public void close () throws IOException {
		inputStream.close();
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
	
}
