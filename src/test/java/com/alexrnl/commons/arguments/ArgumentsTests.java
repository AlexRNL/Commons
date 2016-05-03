package com.alexrnl.commons.arguments;

import java.lang.reflect.Field;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.alexrnl.commons.arguments.parsers.ParsersTests;
import com.alexrnl.commons.arguments.validators.ValidatorsTests;
import com.alexrnl.commons.utils.object.ReflectUtils;

/**
 * Test suite for the arguments package.
 * @author Alex
 */
@RunWith(Suite.class)
@SuiteClasses({ ArgumentsTest.class, CollectionFieldSetterTest.class,
		DefaultParameterValueSetterFactoryTest.class, GenericFieldSetterTest.class,
		ParameterTest.class, ParserNotFoundTest.class, ParsersTests.class,
		ParsingParametersTest.class, ParsingResultsTest.class, UndefinedItemClassTest.class,
		ValidatorsTests.class })
public class ArgumentsTests {
	
	/**
	 * Get the parameter for the specified field.
	 * @param objectClass
	 *        the object class.
	 * @param name
	 *        the name of the field.
	 * @return the {@link Parameter} for this field.
	 * @throws IllegalArgumentException
	 *         if the field could not be found.
	 */
	static Parameter getParameterForField (final Class<?> objectClass, final String name) {
		for (final Field field : ReflectUtils.retrieveFields(objectClass, Param.class)) {
			field.setAccessible(true);
			if (!field.getName().equals(name)) {
				continue;
			}
			return new Parameter(field, field.getAnnotation(Param.class));
		}
		throw new IllegalArgumentException("Could not find field " + name + " in class " + objectClass);
	}
}
