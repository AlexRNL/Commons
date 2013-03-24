package com.alexrnl.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.time.TimeTests;
import com.alexrnl.commons.utils.UtilsTests;

/**
 * All tests for the common library.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({TimeTests.class, UtilsTests.class})
public class AllTests {
}
