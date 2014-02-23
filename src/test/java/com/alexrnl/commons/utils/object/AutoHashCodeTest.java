package com.alexrnl.commons.utils.object;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.utils.object.AutoEqualsTest.ComparedClass;

/**
 * Test suite for the {@link AutoHashCode} class.
 * @author Alex
 */
public class AutoHashCodeTest {
	/** The test object for the auto hash code method. */
	private ComparedClass testObject;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		testObject = new ComparedClass("test", new Integer[] { 2, 8 }, false, new double[] { 0.314159 });
	}
	
	/**
	 * Test method for {@link AutoHashCode#hashCode(Object)}.
	 */
	@Test
	public void testHashCodeObject () {
		Logger.getLogger(AutoHashCode.class.getName()).setLevel(Level.FINE);
		AutoHashCode.getInstance().hashCode(testObject);
	}
}
