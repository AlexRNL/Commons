package com.alexrnl.commons.database;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the database package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ AbstractDAOFactoryTest.class, ColumnTest.class, NoIdErrorTest.class,
	DAOInstantiationErrorTest.class })
public class DatabaseTests {
}
