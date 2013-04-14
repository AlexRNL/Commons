package com.alexrnl.commons.database;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.alexrnl.commons.error.ExceptionUtils;

/**
 * The abstract factory for the {@link DAO}s.<br />
 * It manages the creation of the factory through the {@link #createFactory(String)} method.<br />
 * The user of this class should extend it and add the required {@link DAO} methods needed for its
 * needs.
 * The {@link DAO} should be registered by using the {@link #addDAO(Class, DAO)} method. This will
 * allow to properly close the {@link DAO}s by the abstract factory.
 * @author Alex
 */
public abstract class AbstractDAOFactory implements Closeable {
	/** Logger */
	private static Logger												lg	= Logger.getLogger(AbstractDAOFactory.class.getName());
	
	/** Implementation of the factory to be used */
	private static AbstractDAOFactory									implementation;
	
	/** Map containing all the DAOs which keys are the class they manage */
	private final Map<Class<? extends Entity>, DAO<? extends Entity>>	daos;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor.
	 */
	public AbstractDAOFactory () {
		super();
		daos = new HashMap<>();
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
	public <T extends Entity> void addDAO (final Class<T> entityClass, final DAO<T> dao) {
		daos.put(entityClass, dao);
	}
	
	/**
	 * Return the implementation of the factory to be used.
	 * @return the concrete implementation
	 */
	public static AbstractDAOFactory getImplementation () {
		Objects.requireNonNull(implementation, "DAO Factory implementation is null");
		return implementation;
	}
	
	@Override
	public final void close () throws IOException {
		for (final DAO<? extends Entity> dao : daos.values()) {
			dao.close();
		}
	}
	
	/**
	 * Create the factory from the class specified.
	 * @param factoryClass
	 *        the type of DAO required.
	 */
	public static synchronized void createFactory (final Class<? extends AbstractDAOFactory> factoryClass) {
		try {
			if (implementation != null) {
				try {
					implementation.close();
				} catch (final IOException e) {
					lg.warning("Could not close previous implementation (" + e.getMessage() + ")");
				}
			}
			implementation = factoryClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			implementation = null;
			lg.severe("Cannot instantiate DAO factory class (" + factoryClass + "). " + ExceptionUtils.display(e));
			throw new DAOInstantiationError(factoryClass.getName(), e);
		}
	}
	
	/**
	 * Retrieve and create the appropriate factory.
	 * @param factoryClass
	 *        the type of DAO required.
	 */
	public static void createFactory (final String factoryClass) {
		try {
			createFactory(Class.forName(factoryClass).asSubclass(AbstractDAOFactory.class));
		} catch (ClassNotFoundException | ClassCastException e) {
			implementation = null;
			lg.severe("Cannot instantiate DAO factory class (" + factoryClass + "). " + ExceptionUtils.display(e));
			throw new DAOInstantiationError(factoryClass, e);
		}
	}
	
}
