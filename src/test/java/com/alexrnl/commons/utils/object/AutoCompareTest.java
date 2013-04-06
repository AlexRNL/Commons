package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link AutoCompare} class.
 * @author Alex
 */
public class AutoCompareTest {
	/**
	 * Simple class to test the auto comparator.
	 * @author Alex
	 */
	static class ComparedClass {
		/** A string */
		private final String	string;
		/** An Integer array */
		private final Integer[]	integers;
		/** A boolean */
		private final boolean	state;
		/** A double array */
		private final double[]	doubles;
		
		/**
		 * Constructor #1.<br />
		 * @param string
		 *        a string.
		 * @param integers
		 *        an array of integer.
		 * @param state
		 *        a boolean.
		 * @param doubles
		 *        an array of double.
		 */
		public ComparedClass (final String string, final Integer[] integers, final boolean state,
				final double[] doubles) {
			super();
			this.string = string;
			this.integers = integers;
			this.state = state;
			this.doubles = doubles;
		}
		
		/**
		 * Return the attribute string.
		 * @return the attribute string.
		 */
		@Field
		public String getString () {
			return string;
		}
		
		/**
		 * Return the attribute integers.
		 * @return the attribute integers.
		 */
		@Field
		public Integer[] getIntegers () {
			return integers;
		}
		
		/**
		 * Return the attribute state.
		 * @return the attribute state.
		 */
		public boolean isState () {
			return state;
		}
		
		/**
		 * Return the attribute doubles.
		 * @return the attribute doubles.
		 */
		@Field
		public double[] getDoubles () {
			return doubles;
		}
		
	}
	
	/** The first compared class */
	private ComparedClass one;
	/** The second compared class. */
	private ComparedClass two;
	/** The third compared class. */
	private ComparedClass three;
	/** The fourth compared class. */
	private ComparedClass four;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		one = new ComparedClass("cmp", new Integer[] { 0, 8 }, false, new double[] {});
		two = new ComparedClass("cmp", new Integer[] { 0, 8 }, true, new double[] {});
		three = new ComparedClass("aba", new Integer[] { 4, 6, 7, 8 }, true, new double[] { 1.0, -2.8 });
		four = new ComparedClass("aba", new Integer[] { 4, 6, 7, 8 }, false, new double[] { 1.0, -2.8 });
	}
	
	/**
	 * Test method for {@link com.alexrnl.commons.utils.object.AutoCompare#compare(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testCompare () {
		final AutoCompare comparator = AutoCompare.getInstance();
		assertTrue(comparator.compare(one, two));
		assertTrue(comparator.compare(three, four));
		assertTrue(comparator.compare(two, two));
		assertFalse(comparator.compare(one, null));
		assertFalse(comparator.compare(null, four));
		assertFalse(comparator.compare(one, three));
		assertFalse(comparator.compare(new Object(), three));
	}
	
}
