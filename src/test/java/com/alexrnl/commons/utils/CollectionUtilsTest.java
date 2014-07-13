package com.alexrnl.commons.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

/**
 * Test suite for the {@link CollectionUtils} class.
 * @author Alex
 */
public class CollectionUtilsTest {
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = CollectionUtils.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link CollectionUtils#isSorted(Iterable)}.
	 */
	@Test
	public void testIsSorted () {
		assertTrue(CollectionUtils.isSorted(new LinkedList<Integer>()));
		assertTrue(CollectionUtils.isSorted(Arrays.asList(new Integer[] {1, 1, 2, 3, 8, 20, 28})));
		assertFalse(CollectionUtils.isSorted(Arrays.asList(new Integer[] {1, 0, 2, 3, 8, 20})));
		assertFalse(CollectionUtils.isSorted(Arrays.asList(new Integer[] {0, 2, 1, 3, 8, 20})));
		assertFalse(CollectionUtils.isSorted(Arrays.asList(new Integer[] {0, 2, 1})));
	}
	
	/**
	 * Test method for {@link CollectionUtils#isSorted(Iterable)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testIsSortedNullPointer () {
		CollectionUtils.isSorted(null);
	}
	
	/**
	 * Test method for {@link CollectionUtils#convertPropertiesToMap(java.util.Properties)}
	 * @throws IOException
	 *         if the property file to test could not be read.
	 */
	@Test
	public void testConvertPropertiesToMap () throws IOException {
		final Properties properties = new Properties();
		properties.load(CollectionUtilsTest.class.getResourceAsStream("/configuration.properties"));
		final Map<String, String> map = CollectionUtils.convertPropertiesToMap(properties);
		
		assertEquals(properties.size(), map.size());
		for (final Entry<Object, Object> property : properties.entrySet()) {
			assertTrue(map.containsKey(property.getKey()));
			assertEquals(property.getValue(), map.get(property.getKey()));
		}
	}
	
	/**
	 * Test method for {@link CollectionUtils#convertPropertiesToMap(java.util.Properties)}
	 */
	@Test(expected = NullPointerException.class)
	public void testConvertPropertiesToMapNullPointer () {
		CollectionUtils.convertPropertiesToMap(null);
	}
}
