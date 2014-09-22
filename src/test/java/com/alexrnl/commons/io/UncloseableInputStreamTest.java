package com.alexrnl.commons.io;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Test suite for the {@link UncloseableInputStream} class.
 * @author Alex
 */
public class UncloseableInputStreamTest {
	/** The stream to test */
	private UncloseableInputStream	uncloseableStream;
	/** The underlying stream (mocked) */
	@Mock
	private InputStream				underlyingStream;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		initMocks(this);
		uncloseableStream = new UncloseableInputStream(underlyingStream);
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#read()}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testRead () throws IOException {
		uncloseableStream.read();
		verify(underlyingStream).read();
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#close()}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testClose () throws IOException {
		uncloseableStream.close();
		verify(underlyingStream, never()).read();
	}
}
