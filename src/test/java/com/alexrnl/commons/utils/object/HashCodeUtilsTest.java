package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link HashCodeUtils} class.
 * @author Alex
 */
public class HashCodeUtilsTest {
	/**
	 * Dummy class for tests.
	 * @author Alex
	 */
	private static class Dummy {
		/** A number */
		private final int number;
		/** A digit */
		private final int digit;
		
		/**
		 * Constructor #1.<br />
		 * @param number the number.
		 * @param digit the digit.
		 */
		public Dummy (final int number, final int digit) {
			super();
			this.number = number;
			this.digit = digit;
		}
	}
	
	/** The first dummy object */
	private Dummy		first;
	/** The second dummy object */
	private Dummy		second;
	/** The third dummy object */
	private Dummy		third;
	/** The fourth dummy object */
	private Dummy		fourth;
	/** The fifth dummy object */
	private Dummy		fifth;
	/** List with the dummy object, for iterations */
	private List<Dummy>	dummies;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		first = new Dummy(0, 0);
		second = new Dummy(28, 1);
		third = new Dummy(88, 2);
		fourth = new Dummy(128, 4);
		fifth = new Dummy(888, 8);
		
		dummies = new ArrayList<>();
		dummies.add(first);
		dummies.add(second);
		dummies.add(third);
		dummies.add(fourth);
		dummies.add(fifth);
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(int, int, java.lang.Iterable)}.
	 */
	@Test
	public void testHashCodeIntIntIterableOfObject () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(int, int, java.lang.Object[])}.
	 */
	@Test
	public void testHashCodeIntIntObjectArray () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(int, java.lang.Iterable)}.
	 */
	@Test
	public void testHashCodeIntIterableOfObject () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(int, java.lang.Object[])}.
	 */
	@Test
	public void testHashCodeIntObjectArray () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(java.lang.Iterable)}.
	 */
	@Test
	public void testHashCodeIterableOfObject () {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.HashCodeUtils#hashCode(java.lang.Object[])}.
	 */
	@Test
	public void testHashCodeObjectArray () {
		for (final Dummy dummy : dummies) {
			for (final Dummy other : dummies) {
				if (other == dummy) {
					assertEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
							HashCodeUtils.hashCode(new Object[] { other.number, other.digit }));
				} else {
					assertNotEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
							HashCodeUtils.hashCode(new Object[] { other.number, other.digit }));
				}
			}
			
			// Check for clone
			final Dummy clone = new Dummy(dummy.number, dummy.digit);
			assertEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
					HashCodeUtils.hashCode(new Object[] { clone.number, clone.digit }));
			
			// Check for order
			assertEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
					HashCodeUtils.hashCode(new Object[] { dummy.digit, dummy.number }));
		}
	}
}
