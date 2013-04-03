package com.alexrnl.commons.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * Class access the application configuration.<br />
 * To retrieve a property, the method {@link #get(String)} should be used.
 * The default configuration file used will be <code>conf/configuration.xml</code>, it may be
 * changed using the {@link #setConfigurationFile(Path)} method.<br />
 * The {@link Configuration} is thread-safe as it is based on the {@link Properties} class, itself
 * extending a {@link Hashtable}.
 * Load the specified file using the {@link #load()} method. If the object is not loaded, it will be
 * loaded on the first call to the {@link #get(String)} method (which, thus may take a bit of time).
 * @author Alex
 */
public final class Configuration {
	/** Logger */
	private static Logger		lg							= Logger.getLogger(Configuration.class.getName());
	
	/** The default configuration file to load. */
	private static final Path	DEFAULT_CONFIGURATION_FILE	= Paths.get("conf/configuration.xml");
	
	/** The configuration file to load */
	private Path				configurationFile;
	/** The configuration properties */
	private final Properties	configuration;
	/** The state of the configuration, <code>true</code> if loaded */
	private boolean				loaded;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public Configuration () {
		this(DEFAULT_CONFIGURATION_FILE);
	}
	
	/**
	 * Constructor #2.<br />
	 * @param configurationFile
	 *        the configuration file to load.
	 */
	public Configuration (final Path configurationFile) {
		super();
		this.configurationFile = configurationFile;
		configuration = new Properties();
		loaded = false;
		load();
	}
	
	/**
	 * Set the configuration file to load.
	 * @param configurationFile
	 *        the path to the configuration file to load.
	 */
	public void setConfigurationFile (final Path configurationFile) {
		this.configurationFile = configurationFile;
		loaded = false;
		load();
	}
	
	/**
	 * Load the properties from the configuration file.
	 */
	public void load () {
		try {
			configuration.clear();
			configuration.loadFromXML(Files.newInputStream(configurationFile));
			loaded = true;
			if (lg.isLoggable(Level.INFO)) {
				lg.info(size() + " properties loaded from file: " + configurationFile);
			}
			if (lg.isLoggable(Level.FINE)) {
				lg.fine("Properties successfully loaded:");
				for (final Entry<Object, Object> currentProp : configuration.entrySet()) {
					lg.fine("\t" + currentProp.getKey() + " = " + currentProp.getValue());
				}
			}
		} catch (final IOException e) {
			lg.warning("Exception while loading configuration (" + ExceptionUtils.display(e) + ")");
		}
	}
	
	/**
	 * Get the property matching to the property name specified.
	 * @param propertyName
	 *        the name of the property to retrieve.
	 * @return the value of the property.
	 */
	public String get (final String propertyName) {
		if (!loaded) {
			load();
		}
		final String propertyValue = configuration.getProperty(propertyName);
		if (propertyValue == null) {
			lg.warning("No property with name " + propertyName);
			return null;
		}
		return propertyValue;
	}
	
	/**
	 * Check if the configuration file is loaded.
	 * @return <code>true</code> if the configuration is loaded.
	 */
	public boolean isLoaded () {
		return loaded;
	}
	
	/**
	 * Return the number of properties held by the configuration file.
	 * @return the number of properties.
	 */
	public int size () {
		return configuration.size();
	}
	
}