package com.alexrnl.commons.database;

import com.alexrnl.commons.database.dao.AbstractDAOFactory;
import com.alexrnl.commons.database.dao.DataSourceConfiguration;

/**
 * Dummy factory, for test purposes.
 * @author Alex
 */
public class DummyFactory extends AbstractDAOFactory {
	/**
	 * Constructor #1.<br />
	 * @param dataSourceConfiguration
	 *        the data source configuration.
	 */
	public DummyFactory (final DataSourceConfiguration dataSourceConfiguration) {
		super(dataSourceConfiguration);
		addDAO(Dummy.class, new DAOAdaptater<Dummy>());
	}

}