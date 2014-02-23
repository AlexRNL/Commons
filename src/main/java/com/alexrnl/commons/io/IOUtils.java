package com.alexrnl.commons.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility methods for IO stuff.<br />
 * @author Alex
 */
public final class IOUtils {
	/** Logger */
	private static Logger			lg						= Logger.getLogger(IOUtils.class.getName());
	
	/** The byte order mark used at the beginning of unicode files */
	public static final Character	UNICODE_BYTE_ORDER_MARK	= '\ufeff';
	/** The character used to separate the filename from its extension */
	public static final Character	FILE_EXTENSION_SEPARATOR	= '.';
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private IOUtils () {
		super();
	}
	
	/**
	 * Read the next line on the buffered reader provided.<br />
	 * @param reader
	 *        the stream to read.
	 * @return the next line in the stream.
	 * @throws IOException
	 *         if there was an issue when reading the stream.
	 * @throws EOFException
	 *         if the line returned is <code>null</code>.
	 */
	public static String readLine (final BufferedReader reader) throws IOException, EOFException {
		Objects.requireNonNull(reader);
		final String line = reader.readLine();
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Read line: " + line);
		}
		if (line == null) {
			throw new EOFException("End of stream reached");
		}
		return line;
	}
	
	/**
	 * Return the raw filename of the specified path.<br />
	 * Strip all the parent folders and the extension of the file to just return the filename.
	 * <strong>Note that for Unix hidden files (starting with '.'), this method return the full
	 * filename.</strong>
	 * @param path
	 *        the file, it must not be a directory.
	 * @return the filename, without the folders and the extension.
	 */
	public static String getFilename (final Path path) {
		Objects.requireNonNull(path);
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException("Cannot get name of a directory");
		}
		final String filename = path.getFileName().toString();
		final int extensionSeparatorIndex = filename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if (extensionSeparatorIndex <= 0 || extensionSeparatorIndex == filename.length() - 1) {
			return filename;
		}
		return filename.substring(0, extensionSeparatorIndex);
	}
	
	/**
	 * Return the extension of the file.<br />
	 * Strip all the parent folders and the name of the file to return just the file extension.
	 * <strong>Note that for Unix hidden files (starting with '.'), this method return an empty string.</strong>
	 * @param path
	 *        the file, it must not be a directory.
	 * @return the file extension.
	 */
	public static String getFileExtension (final Path path) {
		Objects.requireNonNull(path);
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException("Cannot get extension of a directory");
		}
		final String filename = path.getFileName().toString();
		final int extensionSeparatorIndex = filename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if (extensionSeparatorIndex <= 0) {
			return "";
		}
		return filename.substring(extensionSeparatorIndex + 1);
	}

	/**
	 * Build an input stream from the specified string in the encoding specified.
	 * @param string
	 *        the string to use.
	 * @param charset
	 *        the charset to use.
	 * @return the input stream.
	 */
	public static InputStream toInputStream (final String string, final Charset charset) {
		return new ByteArrayInputStream(string.getBytes(charset));
	}
	
	/**
	 * Build an input stream from the specified string in the default encoding of the JVM.
	 * @param string
	 *        the string to use.
	 * @return the input stream.
	 */
	public static InputStream toInputStream (final String string) {
		return toInputStream(string, Charset.defaultCharset());
	}
}
