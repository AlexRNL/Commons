package com.alexrnl.commons.time;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the Time package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ SpinnerTimeModelTest.class, TimeTest.class, TimeSecTest.class })
public class TimeTests {
}
