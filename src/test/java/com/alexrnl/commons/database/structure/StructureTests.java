package com.alexrnl.commons.database.structure;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the structure package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ColumnTest.class, NoIdErrorTest.class })
public class StructureTests {
}
