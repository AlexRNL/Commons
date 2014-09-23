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
 * TODO
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

	
	/**
	 * Test method for {@link UncloseableInputStream#read(byte[])}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testReadByteArray () throws IOException {
		final byte[] byteArray = new byte[8];
		uncloseableStream.read(byteArray);
		verify(underlyingStream).read(byteArray);
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#read(byte[], int, int)}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testReadByteArrayIntInt () throws IOException {
		final byte[] byteArray = new byte[8];
		uncloseableStream.read(byteArray, 8, 28);
		verify(underlyingStream).read(byteArray, 8, 28);
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#skip(long)}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testSkip () throws IOException {
		uncloseableStream.skip(88);
		verify(underlyingStream).skip(88);
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#available()}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testAvailable () throws IOException {
		uncloseableStream.available();
		verify(underlyingStream).available();
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#mark(int)}.
	 */
	@Test
	public void testMark () {
		uncloseableStream.mark(48);
		verify(underlyingStream).mark(48);
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#reset()}.
	 * @throws IOException
	 *         if there was an I/O error.
	 */
	@Test
	public void testReset () throws IOException {
		uncloseableStream.reset();
		verify(underlyingStream).reset();
	}
	
	/**
	 * Test method for {@link UncloseableInputStream#markSupported()}.
	 */
	@Test
	public void testMarkSupported () {
		uncloseableStream.markSupported();
		verify(underlyingStream).markSupported();
	}
}
