package com.alexrnl.commons.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Test;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Test suite for the {@link IOUtils} class.
 * @author Alex
 */
public class IOUtilsTest {
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = IOUtils.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link IOUtils#readLine(BufferedReader)}.
	 */
	@Test
	public void testReadLine () {
		try (final BufferedReader reader = Files.newBufferedReader(
				Paths.get(getClass().getResource("/configuration.properties").toURI()),
				StandardCharsets.UTF_8)) {
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
		} catch (final Exception e) {
			fail(ExceptionUtils.display(e));
		}
	}

	/**
	 * Test method for {@link IOUtils#readLine(BufferedReader)}.
	 * @throws IOException
	 *         if there was an issue while reading the file.
	 */
	@Test(expected = NullPointerException.class)
	public void testReadLineNullPointerException () throws IOException {
		IOUtils.readLine(null);
	}
	
	/**
	 * Test method for {@link IOUtils#getFilename(Path)}.
	 */
	@Test
	public void testGetFilename () {
		assertEquals("hello", IOUtils.getFilename(Paths.get("serv", "io", "hello.txt")));
		assertEquals("hello", IOUtils.getFilename(Paths.get("io", "hello")));
		assertEquals("hello.", IOUtils.getFilename(Paths.get("io", "hello.")));
		assertEquals("hel.lo", IOUtils.getFilename(Paths.get("serv", "hel.lo.txt")));
		assertEquals(".classpath", IOUtils.getFilename(Paths.get(".classpath")));
	}
	
	/**
	 * Test method for {@link IOUtils#getFilename(Path)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetFilenameNullPointerException () {
		IOUtils.getFilename(null);
	}
	
	/**
	 * Test method for {@link IOUtils#getFilename(Path)}.
	 * @throws IOException
	 *         if a temporary directory cannot be created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFilenameDirectory () throws IOException {
		final Path dir = Files.createTempDirectory("forFilename");
		dir.toFile().deleteOnExit();
		IOUtils.getFilename(dir);
	}
	
	/**
	 * Test method for {@link IOUtils#getFileExtension(Path)}.
	 */
	@Test
	public void testGetFileExtension () {
		assertEquals("txt", IOUtils.getFileExtension(Paths.get("src", "main", "hello.txt")));
		assertEquals("", IOUtils.getFileExtension(Paths.get("io", "hello")));
		assertEquals("", IOUtils.getFileExtension(Paths.get("etc", "hello.")));
		assertEquals("txt", IOUtils.getFileExtension(Paths.get("serv", "hel.lo.txt")));
		assertEquals("", IOUtils.getFileExtension(Paths.get(".classpath")));
	}
	
	/**
	 * Test method for {@link IOUtils#getFileExtension(Path)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetFileExtensionNullPointerException () {
		IOUtils.getFileExtension(null);
	}
	
	/**
	 * Test method for {@link IOUtils#getFileExtension(Path)}.
	 * @throws IOException
	 *         if a temporary directory cannot be created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFileExtensionDirectory () throws IOException {
		final Path dir = Files.createTempDirectory("forFileExtension");
		dir.toFile().deleteOnExit();
		IOUtils.getFileExtension(dir);
	}
	
	/**
	 * Test method for the {@link IOUtils#toInputStream(String)}.
	 */
	@Test
	public void testToInputStream () {
		try (final Scanner stream = new Scanner(IOUtils.toInputStream("lauManAba#À\n@#1289%*€"))) {
			assertEquals("lauManAba#À", stream.nextLine());
			assertEquals("@#1289%*€", stream.nextLine());
		} catch (final Exception e) {
			fail(ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test method for the {@link IOUtils#toInputStream(String)}.
	 */
	@Test
	public void testToInputStreamCharset () {
		try (final Scanner stream = new Scanner(IOUtils.toInputStream("lauManAba#À\n@#1289%*$",
				StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1.name())) {
			assertEquals("lauManAba#À", stream.nextLine());
			assertEquals("@#1289%*$", stream.nextLine());
		} catch (final Exception e) {
			fail(ExceptionUtils.display(e));
		}
	}
}
