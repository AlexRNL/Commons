package com.alexrnl.commons.arguments;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.arguments.parsers.ParsersTests;

/**
 * Test suite for the arguments package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ArgumentsTest.class, ParameterTest.class, ParsersTests.class,
		ParsingResultsTest.class })
public class ArgumentsTests {

}
