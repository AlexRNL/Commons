package com.alexrnl.commons.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.utils.object.ObjectTests;

/**
 * Test suite for the utils package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ConfigurationTest.class, ObjectTests.class, StringUtilsTest.class })
public class UtilsTests {
}
