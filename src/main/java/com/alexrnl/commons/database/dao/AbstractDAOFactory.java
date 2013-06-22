package com.alexrnl.commons.database.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.alexrnl.commons.database.structure.Entity;
import com.alexrnl.commons.error.ExceptionUtils;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * It manages the creation of the factory through the
 * {@link #buildFactory(String, DataSourceConfiguration, Class)} method.<br />
 * Concrete classes need to have a default constructor to be instantiated dynamically.<br />
 * The user of this class should extend it and add the required {@link DAO} methods needed for its
 * needs.
 * The {@link DAO} should be registered by using the {@link #addDAO(Class, DAO)} method. This will
 * allow to properly close the {@link DAO}s by the abstract factory.<br />
 * Finally, implementation should create their connection and DAOs in the {@link #init()} method,
 * to ensure proper access to the configuration file.
 * @author Alex
 */
public abstract class AbstractDAOFactory implements Closeable {
	/** Logger */
	private static Logger												lg	= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** The database configuration information */
	private DataSourceConfiguration										dataSourceConfig;
	
	/** Map containing all the DAOs which keys are the class they manage */
	private final Map<Class<? extends Entity>, DAO<? extends Entity>>	daos;
	
	/**
	 * Retrieve and create the appropriate factory using the name of the class.<br />
	 * Using this method will avoid dependencies to the concerte factory type and allow changing
	 * factories easily.
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
		T factory = null;
		try {
			factory = Class.forName(factoryClass).asSubclass(parentClass).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ClassCastException e) {
			lg.severe("Cannot instantiate DAO factory class (" + factoryClass + "). " + ExceptionUtils.display(e));
			throw new DAOInstantiationError(factoryClass, e);
		}
		factory.setDataSourceConfiguration(dataSourceConfig);
		factory.init();
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
	 */
	public AbstractDAOFactory () {
		super();
		daos = new HashMap<>();
	}
	
	/**
	 * Method which will initialize the connection (if any) and creates the DAOs.<br/>
	 * Attempting to access the data source configuration file will fail if done in the constructor.
	 */
	protected abstract void init ();

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
	 * @param entityClass
	 *        the entity class to get the DAO from.
	 * @return the DAO associated to the class.
	 */
	public <T extends Entity> DAO<T> getDAO (final Class<T> entityClass) {
		return (DAO<T>) daos.get(entityClass);
	}
	
	/**
	 * Add the DAO to the map.
	 * @param entityClass
	 *        the class manage by the DAO.
	 * @param dao
	 *        the {@link DAO} of the class.
	 */
	protected <T extends Entity> void addDAO (final Class<T> entityClass, final DAO<T> dao) {
		daos.put(entityClass, dao);
	}
	
	/**
	 * Return the data source configuration to be used by the implementation.
	 * @return the configuration information.
	 */
	protected DataSourceConfiguration getDataSourceConfiguration () {
		return dataSourceConfig;
	}
	
	/**
	 * Set the configuration of the data source to use for this factory.
	 * @param dataSourceConfig
	 *        the configuration to use.
	 */
	protected void setDataSourceConfiguration (final DataSourceConfiguration dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}
	
	@Override
	public void close () throws IOException {
		for (final DAO<? extends Entity> dao : daos.values()) {
			dao.close();
		}
	}
	
}
