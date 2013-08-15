package com.alexrnl.commons.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link IOUtils} class.
 * @author Alex
 */
public class IOUtilsTest {
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.io.IOUtils#readLine(java.io.BufferedReader)}.
	 * @throws URISyntaxException
	 *         if the path to the test file is not correct.
	 * @throws IOException
	 *         if there was an issue while reading the file.
	 */
	@Test
	public void testReadLine () throws IOException, URISyntaxException {
		final BufferedReader reader = Files.newBufferedReader(
				Paths.get(getClass().getResource("/configuration.properties").toURI()),
				StandardCharsets.UTF_8);
		assertNotNull(IOUtils.readLine(reader));
		assertNotNull(IOUtils.readLine(reader));
		boolean eof = false;
		try {
			IOUtils.readLine(reader);
		} catch (final EOFException e) {
			eof = true;
		} finally {
			assertTrue(eof);
		}
	}

	/**
	 * Test method for {@link com.alexrnl.commons.io.IOUtils#readLine(java.io.BufferedReader)}.
	 * @throws IOException
	 *         if there was an issue while reading the file.
	 */
	@Test(expected = NullPointerException.class)
	public void testReadLineNullPointerException () throws IOException {
		IOUtils.readLine(null);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.io.IOUtils#getFilename(java.nio.file.Path)}.
	 */
	@Test
	public void testGetFilename () {
		assertEquals("hello", IOUtils.getFilename(Paths.get("serv", "io", "hello.txt")));
		assertEquals("hello", IOUtils.getFilename(Paths.get("io", "hello")));
		assertEquals("hel.lo", IOUtils.getFilename(Paths.get("serv", "hel.lo.txt")));
		assertEquals(".classpath", IOUtils.getFilename(Paths.get(".classpath")));
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.io.IOUtils#getFilename(java.nio.file.Path)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetFilenameNullPointerException () {
		IOUtils.getFilename(null);
	}
	
}
