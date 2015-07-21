package com.alexrnl.commons.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link EditableInputStream} class.
 * @author Alex
 */
public class EditableInputStreamTest {
	/** The stream to test */
	private EditableInputStream stream;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		stream = new EditableInputStream("test");
	}
	
	/**
	 * Test method for {@link EditableInputStream#read()}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testRead () throws IOException {
		assertEquals(116, stream.read());
	}
	
	/**
	 * Test method for {@link EditableInputStream#read(byte[])}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testReadByteArray () throws IOException {
		final byte[] array = new byte[4];
		assertEquals(4, stream.read(array));
		assertArrayEquals(new byte[] {116, 101, 115, 116}, array);
	}
	
	/**
	 * Test method for {@link EditableInputStream#read(byte[], int, int)}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testReadByteArrayIntInt () throws IOException {
		final byte[] array = new byte[4];
		assertEquals(2, stream.read(array, 2, 2));
		assertArrayEquals(new byte[] {0, 0, 116, 101}, array);
	}
	
	/**
	 * Test method for {@link EditableInputStream#skip(long)}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testSkip () throws IOException {
		assertEquals(2, stream.skip(2));
		assertEquals(2, stream.skip(8));
	}
	
	/**
	 * Test method for {@link EditableInputStream#available()}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testAvailable () throws IOException {
		assertEquals(4, stream.available());
		stream.skip(2);
		assertEquals(2, stream.available());
	}
	
	/**
	 * Test method for {@link EditableInputStream#mark(int)} and {@link EditableInputStream#reset()}
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testMark () throws IOException {
		stream.mark(8);
		stream.skip(2);
		stream.reset();
		final byte[] array = new byte[4];
		assertEquals(4, stream.read(array));
		assertArrayEquals(new byte[] {116, 101, 115, 116}, array);
	}
	
	/**
	 * Test method for {@link EditableInputStream#markSupported()}.
	 */
	@Test
	public void testMarkSupported () {
		assertTrue(stream.markSupported());
	}
	
	/**
	 * Test method for {@link EditableInputStream#updateStream(String)}.
	 * @throws IOException
	 *         if there is an issue with the stream.
	 */
	@Test
	public void testUpdateStream () throws IOException {
		final byte[] array = new byte[2];
		assertEquals(2, stream.read(array));
		assertArrayEquals(new byte[] {116, 101}, array);
		stream.updateStream("lauAba");
		final byte[] arrayUpdated = new byte[6];
		assertEquals(6, stream.read(arrayUpdated));
		assertArrayEquals(new byte[] {108, 97, 117, 65, 98, 97}, arrayUpdated);
	}
}
