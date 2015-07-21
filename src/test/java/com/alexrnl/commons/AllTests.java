package com.alexrnl.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.arguments.ArgumentsTests;
import com.alexrnl.commons.database.DatabaseTests;
import com.alexrnl.commons.error.ErrorTests;
import com.alexrnl.commons.gui.GUITests;
import com.alexrnl.commons.io.IOTests;
import com.alexrnl.commons.mvc.MVCTests;
import com.alexrnl.commons.time.TimeTests;
import com.alexrnl.commons.translation.TranslationTests;
import com.alexrnl.commons.utils.UtilsTests;

/**
 * All tests for the common library.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ArgumentsTests.class, DatabaseTests.class, ErrorTests.class, GUITests.class,
		IOTests.class, MVCTests.class, TimeTests.class, TranslationTests.class, UtilsTests.class })
public class AllTests {
}
