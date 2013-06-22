package com.alexrnl.commons.database.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the dao package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ AbstractDAOFactoryTest.class, DAOInstantiationErrorTest.class,
		DataSourceConfigurationTest.class })
public class DAOTests {
}
