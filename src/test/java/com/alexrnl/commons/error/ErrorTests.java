package com.alexrnl.commons.error;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the error package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ExceptionUtilsTest.class, TopLevelErrorTest.class })
public class ErrorTests {
}
