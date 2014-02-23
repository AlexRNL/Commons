package com.alexrnl.commons.database.dao;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.alexrnl.commons.utils.Configuration;
import com.alexrnl.commons.utils.object.AutoEquals;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Class holding the data source configuration information.<br />
 * This class is immutable and serializable.
 * @author Alex
 */
public class DataSourceConfiguration implements Serializable {
	/** Serial version UID */
	private static final long	serialVersionUID	= -3772713943627646577L;

	/** The default key for the URL of the database */
	public static final String	URL_KEY				= "url";
	/** The default key for the user name of the database */
	public static final String	USERNAME_KEY		= "username";
	/** The default key for the password of the database */
	public static final String	PASSWORD_KEY		= "password";
	/** The default key for the creation file of the database */
	public static final String	CREATION_FILE_KEY	= "creationFile";
	
	/** The URL of the connection */
	private final String		url;
	/** The user name to use */
	private final String		username;
	/** The password associated to the user name */
	private final String		password;
	/** The path to the creation file, which allow (if set) to create the database if it does not exists */
	private final URI			creationFile;

	/**
	 * Constructor #1.<br />
	 * @param url
	 *        the URL of the database.
	 * @param username
	 *        the user name to use.
	 * @param password
	 *        the password associated to the user name.
	 * @param creationFile
	 *        The path to the creation file, which allow (if set) to create the database if it does
	 *        not exists
	 */
	public DataSourceConfiguration (final String url, final String username, final String password, final Path creationFile) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.creationFile = creationFile == null ? null : creationFile.toUri();
	}
	
	/**
	 * Constructor #2.<br />
	 * Uses the default keys for loading the data source configuration.
	 * @param configuration
	 *        the configuration object to use for loading the data source properties.
	 * @param dataSourceRootKey
	 *        the root key of the data source configuration.
	 */
	public DataSourceConfiguration (final Configuration configuration, final String dataSourceRootKey) {
		this(configuration.get(dataSourceRootKey + "." + URL_KEY),
				configuration.get(dataSourceRootKey + "." + USERNAME_KEY),
				configuration.get(dataSourceRootKey + "." + PASSWORD_KEY),
				Paths.get(configuration.get(dataSourceRootKey + "." + CREATION_FILE_KEY)));
	}
	
	/**
	 * Return the URL.
	 * @return the URL.
	 */
	@Field
	public String getUrl () {
		return url;
	}
	
	/**
	 * Return the attribute user name.
	 * @return the attribute user name.
	 */
	@Field
	public String getUsername () {
		return username;
	}
	
	/**
	 * Return the attribute password.
	 * @return the attribute password.
	 */
	@Field
	public String getPassword () {
		return password;
	}

	/**
	 * Return the attribute creationFile.
	 * @return the attribute creationFile.
	 */
	@Field
	public Path getCreationFile () {
		return creationFile == null ? null : Paths.get(creationFile);
	}

	@Override
	public int hashCode () {
		return AutoHashCode.getInstance().hashCode(this);
	}

	@Override
	public boolean equals (final Object obj) {
		if (!(obj instanceof DataSourceConfiguration)) {
			return false;
		}
		return AutoEquals.getInstance().compare(this, (DataSourceConfiguration) obj);
	}

	@Override
	public String toString () {
		return "DataSource connection info [url=" + url + "; username=" + username + "; password="
				+ password + "; creationFile="
				+ (creationFile == null ? "null" : Paths.get("").toAbsolutePath().relativize(getCreationFile())) + "]";
	}
	
}
