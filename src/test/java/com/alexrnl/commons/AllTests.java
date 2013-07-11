package com.alexrnl.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.database.DatabaseTests;
import com.alexrnl.commons.error.ErrorTests;
import com.alexrnl.commons.gui.GUITests;
import com.alexrnl.commons.mvc.MVCTests;
import com.alexrnl.commons.time.TimeTests;
import com.alexrnl.commons.translation.TranslationsTests;
import com.alexrnl.commons.utils.UtilsTests;

/**
 * All tests for the common library.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ DatabaseTests.class, ErrorTests.class, GUITests.class, MVCTests.class,
		TimeTests.class, TranslationsTests.class, UtilsTests.class })
public class AllTests {
}
