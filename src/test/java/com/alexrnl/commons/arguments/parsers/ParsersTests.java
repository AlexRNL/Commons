package com.alexrnl.commons.arguments.parsers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the parsers package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ByteParserTest.class, ClassParserTest.class, CharParserTest.class,
		DoubleParserTest.class, FloatParserTest.class, IntParserTest.class, LongParserTest.class,
		ShortParserTest.class, StringParserTest.class, WByteParserTest.class })
public class ParsersTests {
	
}
