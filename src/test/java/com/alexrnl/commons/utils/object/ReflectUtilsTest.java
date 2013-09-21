package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.error.ExceptionUtils;
import com.alexrnl.commons.utils.object.AutoCompareTest.ComparedClass;

/**
 * Test suite for the {@link ReflectUtils} class.
 * @author Alex
 */
public class ReflectUtilsTest {
	/** The first compared class */
	private ComparedClass one;
	/** The second compared class. */
	private ComparedClass two;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		one = new ComparedClass("test", new Integer[] { 1, 2 }, false, new double[] { 2.8 });
		two = new ComparedClass("track", new Integer[] { 4, 8 }, true, new double[] { -8.8 });
	}
	
	/**
	 * Test method for {@link ReflectUtils#retrieveMethods(java.lang.Class, Class)}.
	 */
	@Test
	public void testRetrieveMethods () {
		assertEquals(3, ReflectUtils.retrieveMethods(ComparedClass.class, Field.class).size());
		Logger.getLogger(ReflectUtils.class.getName()).setLevel(Level.FINE);
		assertEquals(4 + Object.class.getMethods().length, ReflectUtils.retrieveMethods(ComparedClass.class, null).size());
	}
	
	/**
	 * Test method for {@link ReflectUtils#invokeMethods(java.lang.Object, List)}.
	 */
	@Test
	public void testInvokeMethods () {
		final List<Method> comparedClassMethods = new ArrayList<>(ReflectUtils.retrieveMethods(ComparedClass.class, Field.class));
		Collections.sort(comparedClassMethods, new Comparator<Method>() {
			@Override
			public int compare (final Method m1, final Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		try {
			final List<Object> ones = ReflectUtils.invokeMethods(one, comparedClassMethods);
			final List<Object> twos = ReflectUtils.invokeMethods(two, comparedClassMethods);
			assertArrayEquals(new double[] { 2.8 }, (double[]) ones.get(0), 0.01);
			assertArrayEquals(new double[] { -8.8 }, (double[])  twos.get(0), 0.01);
			assertArrayEquals(new Integer[] { 1, 2 }, (Integer[]) ones.get(1));
			assertArrayEquals(new Integer[] { 4, 8 }, (Integer[]) twos.get(1));
			assertEquals("test", ones.get(2));
			assertEquals("track", twos.get(2));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("Error while invoking a method: " + ExceptionUtils.display(e));
		}
	}
	
	/**
	 * Test method for {@link ReflectUtils#getAllInterfaces(Class)}.
	 */
	@Test
	public void testGetAllInterfaces () {
		final Set<Class<?>> objectInterfaces = new HashSet<>(1);
		objectInterfaces.add(Object.class);
		assertEquals(objectInterfaces, ReflectUtils.getAllInterfaces(Object.class));
		
		final Set<Class<?>> comparedClassInterfaces = new HashSet<>(2);
		comparedClassInterfaces.add(ComparedClass.class);
		comparedClassInterfaces.add(Object.class);
		assertEquals(comparedClassInterfaces, ReflectUtils.getAllInterfaces(ComparedClass.class));
		
		final Set<Class<?>> hashMapInterfaces = new HashSet<>(6);
		hashMapInterfaces.add(Serializable.class);
		hashMapInterfaces.add(Cloneable.class);
		hashMapInterfaces.add(Map.class);
		hashMapInterfaces.add(HashMap.class);
		hashMapInterfaces.add(AbstractMap.class);
		hashMapInterfaces.add(Object.class);
		assertEquals(hashMapInterfaces, ReflectUtils.getAllInterfaces(HashMap.class));
	}
	
	/**
	 * Test method for {@link ReflectUtils#getAllInterfaces(Class)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetAllInterfacesNullPointerException () {
		ReflectUtils.getAllInterfaces(null);
	}
}
