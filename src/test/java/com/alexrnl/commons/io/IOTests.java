package com.alexrnl.commons.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the IO package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ EditableInputStreamTest.class, IOUtilsTest.class, UncloseableInputStreamTest.class })
public class IOTests {
	
}
