package com.alexrnl.commons.database;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.database.sql.SQLTests;

/**
 * Test suite for the database package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ AbstractDAOFactoryTest.class, ColumnTest.class, DataSourceConfigurationTest.class,
		NoIdErrorTest.class, DAOInstantiationErrorTest.class, QueryGeneratorTest.class,
		SQLTests.class })
public class DatabaseTests {
}
