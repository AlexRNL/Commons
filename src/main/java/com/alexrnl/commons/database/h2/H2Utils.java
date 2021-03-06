package com.alexrnl.commons.database.h2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.engine.Constants;
import org.h2.tools.RunScript;

import com.alexrnl.commons.database.DataBaseConfigurationError;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Utility methods for H2 database connections.<br />
 * @author Alex
 */
public final class H2Utils {
	/** Logger */
	private static final Logger		LG							= Logger.getLogger(H2Utils.class.getName());
	
	/** The file prefix used in URIs */
	public static final String		FILE_URI_PREFIX				= "file:";
	/** The mem prefix used in URIs */
	public static final String		IN_MEMORY_PREFIX			= "mem:";
	/** The separator between the URL and the parameters in a H2 database file description */
	private static final Character	H2_URL_PARAMETER_SEPARATOR	= ';';
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private H2Utils () {
		super();
		throw new InstantiationError("Instantiation of class " + H2Utils.class + " is forbidden");
	}
	
	/**
	 * Create the path to the H2 database file using the specified dbInfos.<br />
	 * The suffix (file extension) will be added if requested.<br />
	 * Example: <code>jdbc:h2:file:data/dummy</code> will return a {@link Path} leading to
	 * <ul>
	 * <li><code>./data/dummy</code> with suffix set to <code>false</code>.</li>
	 * <li><code>./data/dummy.h2.db</code> with suffix set to <code>true</code>.</li>
	 * </ul>
	 * @param dbInfos
	 *        the database information to use.
	 * @param suffix
	 *        <code>true</code> if the suffix should be added to the path.
	 * @return the path of the database file, or <code>null</code> if the configuration denotes an
	 *         in-memory database.
	 */
	public static Path getDBFile (final DataSourceConfiguration dbInfos, final boolean suffix) {
		// Removing the jdbc:h2 part of the URL
		String file = dbInfos.getUrl().substring(Constants.START_URL.length());
		
		if (file.startsWith(IN_MEMORY_PREFIX)) {
			// The configuration of the database is for a in-memory database.
			return null;
		}
		// Removing the file: part of the URL, if present
		if (file.startsWith(FILE_URI_PREFIX)) {
			file = file.substring(FILE_URI_PREFIX.length());
		}
		// Removing parameters after the URL
		final int delimiter = file.indexOf(H2_URL_PARAMETER_SEPARATOR);
		if (delimiter >= 0) {
			file = file.substring(0, delimiter);
		}
		
		// Adding the extension if required
		if (suffix) {
			file += Constants.SUFFIX_PAGE_FILE;
		}
		
		if (LG.isLoggable(Level.FINE)) {
			LG.fine("filename: " + file);
		}
		return Paths.get(file);
	}
	
	/**
	 * Initialise the database with the script specified in the dbInfos if it the targeted file does
	 * not exists.<br />
	 * This will fails if the dbInfos has a <code>null</code> creation file.
	 * @param dbInfos
	 *        the database information to use.
	 */
	public static void initDatabase (final DataSourceConfiguration dbInfos) {
		if (dbInfos.getCreationFile() == null) {
			throw new DataBaseConfigurationError("No creation script defined in the data " +
					"source configuration, cannot initialize database.");
		}
		if (LG.isLoggable(Level.INFO)) {
			LG.info("Database file for connection " + dbInfos.getUrl() + " does not exists, " +
					"initializing database with script " + dbInfos.getCreationFile());
		}
		
		final Path dbFile = getDBFile(dbInfos, true);
		try {
			String dbUrl = null;
			
			if (dbFile == null) {
				// in-memory database
				dbUrl = dbInfos.getUrl();
			} else if (!Files.exists(dbFile)) {
				// un-existing database
				dbUrl = Constants.START_URL + getDBFile(dbInfos, false);
			}
			
			if (dbUrl != null) {
				RunScript.execute(dbUrl, dbInfos.getUsername(), dbInfos.getPassword(),
						dbInfos.getCreationFile().toString(), null, false);
			}
		} catch (final SQLException e) {
			LG.warning("Error while initilization of H2 database: " + ExceptionUtils.display(e));
			throw new DataBaseConfigurationError("Could not create H2 database", e);
		}
	}
}
