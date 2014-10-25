package com.alexrnl.commons.database.dao;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * It manages the creation of the factory through the
 * {@link #buildFactory(String, DataSourceConfiguration, Class)} method.<br />
 * Concrete classes need to have a constructor with the {@link DataSourceConfiguration} as a single parameter
 * to be instantiated dynamically.<br />
 * The user of this class should extend it and add the required {@link DAO} methods needed for its
 * needs.
 * The {@link DAO} should be registered by using the {@link #addDAO(Class, DAO)} method. This will
 * allow to automatically close the {@link DAO}s by the abstract factory.<br />
 * @author Alex
 */
public abstract class AbstractDAOFactory implements Closeable {
	/** Logger */
	private static final Logger											LG	= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** The database configuration information */
	private final DataSourceConfiguration								dataSourceConfig;
	
	/** Map containing all the DAOs which keys are the class they manage */
	private final Map<Class<? extends Entity>, DAO<? extends Entity>>	daos;
	
	/**
	 * Retrieve and create the appropriate factory using the name of the class.<br />
	 * Using this method will avoid dependencies to the concrete factory type and allow changing
	 * factories easily (via external configuration for example).
	 * @param <T>
	 *        The concrete factory class.
	 * @param factoryClass
	 *        the type of DAO required.
	 * @param dataSourceConfig
	 *        the configuration of the database.
	 * @param parentClass
	 *        the parent class of the factory, useful for casting to an intermediate type
	 * @return the instance of the factory to use.
	 * @see AbstractDAOFactory#buildFactory(String, DataSourceConfiguration)
	 */
	public static <T extends AbstractDAOFactory> T buildFactory (final String factoryClass,
			final DataSourceConfiguration dataSourceConfig, final Class<T> parentClass) {
		T factory;
		try {
			factory = Class.forName(factoryClass).asSubclass(parentClass)
					.getConstructor(DataSourceConfiguration.class).newInstance(dataSourceConfig);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| ClassCastException | NoSuchMethodException | SecurityException |
				IllegalArgumentException | InvocationTargetException e) {
			LG.severe("Cannot instantiate DAO factory class (" + factoryClass + "). " + ExceptionUtils.display(e));
			throw new DAOInstantiationError(factoryClass, e);
		}
		return factory;
	}
	
	/**
	 * Retrieve and create the appropriate factory using the name of the class.
	 * @param factoryClass
	 *        the type of DAO required.
	 * @param dataSourceConfig
	 *        the configuration of the database.
	 * @return the instance of the factory to use.
	 */
	public static AbstractDAOFactory buildFactory (final String factoryClass, final DataSourceConfiguration dataSourceConfig) {
		return buildFactory(factoryClass, dataSourceConfig, AbstractDAOFactory.class);
	}
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 * @param dataSourceConfig
	 *        the configuration of the data source.
	 */
	public AbstractDAOFactory (final DataSourceConfiguration dataSourceConfig) {
		super();
		daos = new HashMap<>();
		this.dataSourceConfig = dataSourceConfig;
	}
	
	/**
	 * Return an {@link Collections#unmodifiableCollection(Collection) unmodifiable collection} of
	 * the DAOs build with the factory.
	 * @return the {@link DAO}s.
	 */
	public Collection<DAO<? extends Entity>> getDAOs () {
		return Collections.unmodifiableCollection(daos.values());
	}
	
	/**
	 * Get the DAO for the specified class.<br />
	 * @param <T>
	 *        the type of object manipulated by the DAO.
	 * @param entityClass
	 *        the entity class to get the DAO from.
	 * @return the DAO associated to the class.
	 */
	public <T extends Entity> DAO<T> getDAO (final Class<T> entityClass) {
		return (DAO<T>) daos.get(entityClass);
	}
	
	/**
	 * Add the DAO to the map.
	 * @param <T>
	 *        the type of object manipulated by the DAO.
	 * @param entityClass
	 *        the class manage by the DAO.
	 * @param dao
	 *        the {@link DAO} of the class.
	 */
	protected <T extends Entity> void addDAO (final Class<T> entityClass, final DAO<T> dao) {
		if (daos.containsKey(entityClass)) {
			try {
				if (LG.isLoggable(Level.INFO)) {
					LG.info("Closing previously installed DAO on class " + entityClass);
				}
				final DAO<? extends Entity> oldDAO = daos.remove(entityClass);
				if (oldDAO != null) {
					oldDAO.close();
				}
			} catch (final IOException e) {
				LG.warning("Error while closing DAO: " + ExceptionUtils.display(e));
			}
		}
		daos.put(entityClass, dao);
	}
	
	/**
	 * Return the data source configuration to be used by the implementation.
	 * @return the configuration information.
	 */
	protected DataSourceConfiguration getDataSourceConfiguration () {
		return dataSourceConfig;
	}
	
	@Override
	public void close () throws IOException {
		for (final DAO<? extends Entity> dao : daos.values()) {
			dao.close();
		}
	}
	
}
