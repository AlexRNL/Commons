package com.alexrnl.commons.database.dao;

import java.io.Serializable;
import java.nio.file.Path;

import com.alexrnl.commons.utils.object.AutoCompare;
import com.alexrnl.commons.utils.object.AutoHashCode;
import com.alexrnl.commons.utils.object.Field;

/**
 * Class holding the data source configuration information.<br />
 * @author Alex
 */
public class DataSourceConfiguration implements Serializable {
	/** Serial version UID */
	private static final long	serialVersionUID	= -3772713943627646577L;
	
	/** The URL of the connection */
	private final String		url;
	/** The user name to use */
	private final String		username;
	/** The password associated to the user name */
	private final String		password;
	/** The path to the creation file, which allow (if set) to create the database if it does not exists */
	private final Path			creationFile;

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
		this.creationFile = creationFile;
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
		return creationFile;
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
		return AutoCompare.getInstance().compare(this, (DataSourceConfiguration) obj);
	}

	@Override
	public String toString () {
		return "DataSource connection info [url=" + url + "; username=" + username
				+ "; password=" + password + "; creationFile=" + creationFile + "]";
	}
	
	
}
