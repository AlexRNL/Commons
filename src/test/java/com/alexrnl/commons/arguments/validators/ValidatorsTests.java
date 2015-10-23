package com.alexrnl.commons.arguments.validators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for the validators package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ExistingPathValidatorTest.class, NonEmptyStringValidatorTest.class,
		PositiveNumberValidatorTest.class })
public class ValidatorsTests {

}
