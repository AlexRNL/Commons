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
	private static Logger			lg							= Logger.getLogger(H2Utils.class.getName());
	
	/** The file prefix used in URIs */
	public static final String		FILE_URI_PREFIX				= "file:";
	/** The separator between the URL and the parameters in a H2 database file description */
	private static final Character	H2_URL_PARAMETER_SEPARATOR	= ';';
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private H2Utils () {
		super();
	}
	
	/**
	 * Create the path to the H2 database file using the specified dbInfos.<br />
	 * The suffix (file extension) will be added if requested.
	 * @param dbInfos
	 *        the database information to use.
	 * @param suffix
	 *        <code>true</code> if the suffix should be added to the path.
	 * @return the path of the database file.
	 */
	public static Path getDBFile (final DataSourceConfiguration dbInfos, final boolean suffix) {
		// Removing the jdbc:h2 part of the URL
		String file = dbInfos.getUrl().substring(Constants.START_URL.length());
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
		
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("filename: " + file);
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
		if (!Files.exists(getDBFile(dbInfos, true))) {
			try {
				if (dbInfos.getCreationFile() == null) {
					throw new DataBaseConfigurationError("No creation script defined in the data " +
							"source configuration, cannot initialize database.");
				}
				final Path dbFile = getDBFile(dbInfos, false);
				if (lg.isLoggable(Level.INFO)) {
					lg.info("URL connection: " + (Constants.START_URL + dbFile));
				}
				RunScript.execute(Constants.START_URL + dbFile, dbInfos.getUsername(), dbInfos.getPassword(),
						dbInfos.getCreationFile().toString(), null, true);
			} catch (final SQLException e) {
				lg.warning("Error while initilization of H2 database: " + ExceptionUtils.display(e));
				throw new DataBaseConfigurationError("Could not create H2 database", e);
			}
		}
	}
}
