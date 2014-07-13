package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
	private Dummy			first;
	/** The second dummy object */
	private Dummy			second;
	/** The third dummy object */
	private Dummy			third;
	/** The fourth dummy object */
	private Dummy			fourth;
	/** The fifth dummy object */
	private Dummy			fifth;
	/** List with the dummy object, for iterations */
	private List<Dummy>		dummies;
	/** List of prime numbers */
	private List<Integer>	primes;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		first = new Dummy(8, 0);
		second = new Dummy(28, 1);
		third = new Dummy(88, 2);
		fourth = new Dummy(128, 4);
		fifth = new Dummy(888, 8);
		
		dummies = new ArrayList<>(5);
		dummies.add(first);
		dummies.add(second);
		dummies.add(third);
		dummies.add(fourth);
		dummies.add(fifth);
		
		primes = new ArrayList<>(11);
		primes.add(2);
		primes.add(3);
		primes.add(5);
		primes.add(7);
		primes.add(11);
		primes.add(13);
		primes.add(17);
		primes.add(19);
		primes.add(23);
		primes.add(31);
		primes.add(37);
	}
	
	/**
	 * Test method for {@link HashCodeUtils#PRIME_FOR_HASHCODE}.
	 */
	@Test
	public void testDefaultPrime () {
		// FIXME update when implementing maths utils
		assertTrue(BigInteger.valueOf(HashCodeUtils.PRIME_FOR_HASHCODE).isProbablePrime(128));
	}
	
	/**
	 * Check that no instance can be created.
	 * @throws Exception
	 *         if there is a reflection exception thrown.
	 */
	@Test(expected = InvocationTargetException.class)
	public void testForbiddenInstance () throws Exception {
		final Constructor<?> defaultConstructor = HashCodeUtils.class.getDeclaredConstructors()[0];
		defaultConstructor.setAccessible(true);
		defaultConstructor.newInstance();
	}
	
	/**
	 * Test method for {@link HashCodeUtils#hashCode(int, Iterable)}.
	 */
	@Test
	public void testHashCodeIntIterableOfObject () {
		for (final Integer prime : primes) {
			for (final Dummy dummy : dummies) {
				for (final Dummy other : dummies) {
					if (other == dummy) {
						assertEquals(HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.number, dummy.digit })),
								HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { other.number, other.digit })));
					} else {
						assertNotEquals(HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.number, dummy.digit })),
								HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { other.number, other.digit })));
					}
				}
				
				// Check for clone
				final Dummy clone = new Dummy(dummy.number, dummy.digit);
				assertEquals(HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.number, dummy.digit })),
						HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { clone.number, clone.digit })));
				
				// Check for order
				assertNotEquals(HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.number, dummy.digit })),
						HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.digit, dummy.number })));
				
				// Check for small differences
				final List<Dummy> others = new ArrayList<>(4);
				others.add(new Dummy(dummy.number + 1, dummy.digit));
				others.add(new Dummy(dummy.number, dummy.digit + 1 ));
				others.add(new Dummy(dummy.number + 1, dummy.digit + 1));
				others.add(new Dummy(dummy.number - 1, dummy.digit));
				others.add(new Dummy(dummy.number, dummy.digit - 1));
				others.add(new Dummy(dummy.number - 1, dummy.digit - 1));
				for (final Dummy other : others) {
					assertNotEquals(HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { dummy.number, dummy.digit })),
							HashCodeUtils.hashCode(prime, Arrays.asList(new Object[] { other.number, other.digit })));
				}
			}
		}
	}
	
	/**
	 * Test method for {@link HashCodeUtils#hashCode(int, Object[])}.
	 */
	@Test
	public void testHashCodeIntObjectArray () {
		for (final Integer prime : primes) {
			for (final Dummy dummy : dummies) {
				for (final Dummy other : dummies) {
					if (other == dummy) {
						assertEquals(HashCodeUtils.hashCode(prime, new Object[] { dummy.number, dummy.digit }),
								HashCodeUtils.hashCode(prime, new Object[] { other.number, other.digit }));
					} else {
						assertNotEquals(HashCodeUtils.hashCode(prime, new Object[] { dummy.number, dummy.digit }),
								HashCodeUtils.hashCode(prime, new Object[] { other.number, other.digit }));
					}
				}
				
				// Check for clone
				final Dummy clone = new Dummy(dummy.number, dummy.digit);
				assertEquals(HashCodeUtils.hashCode(prime, new Object[] { dummy.number, dummy.digit }),
						HashCodeUtils.hashCode(prime, new Object[] { clone.number, clone.digit }));
				
				// Check for order
				assertNotEquals(HashCodeUtils.hashCode(prime, new Object[] { dummy.number, dummy.digit }),
						HashCodeUtils.hashCode(prime, new Object[] { dummy.digit, dummy.number }));
				
				// Check for small differences
				final List<Dummy> others = new ArrayList<>(4);
				others.add(new Dummy(dummy.number + 1, dummy.digit));
				others.add(new Dummy(dummy.number, dummy.digit + 1 ));
				others.add(new Dummy(dummy.number + 1, dummy.digit + 1));
				others.add(new Dummy(dummy.number - 1, dummy.digit));
				others.add(new Dummy(dummy.number, dummy.digit - 1));
				others.add(new Dummy(dummy.number - 1, dummy.digit - 1));
				for (final Dummy other : others) {
					assertNotEquals(HashCodeUtils.hashCode(prime, new Object[] { dummy.number, dummy.digit }),
							HashCodeUtils.hashCode(prime, new Object[] { other.number, other.digit }));
				}
			}
		}
	}
	
	/**
	 * Test method for {@link HashCodeUtils#hashCode(Iterable)}.
	 */
	@Test
	public void testHashCodeIterableOfObject () {
		for (final Dummy dummy : dummies) {
			for (final Dummy other : dummies) {
				if (other == dummy) {
					assertEquals(
							HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.number, dummy.digit })),
							HashCodeUtils.hashCode(Arrays.asList(new Object[] { other.number, other.digit })));
				} else {
					assertNotEquals(HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.number, dummy.digit })),
							HashCodeUtils.hashCode(Arrays.asList(new Object[] { other.number, other.digit })));
				}
			}
			
			// Check for clone
			final Dummy clone = new Dummy(dummy.number, dummy.digit);
			assertEquals(HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.number, dummy.digit })),
					HashCodeUtils.hashCode(Arrays.asList(new Object[] { clone.number, clone.digit })));
			
			// Check for order
			assertNotEquals(HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.number, dummy.digit })),
					HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.digit, dummy.number })));
			
			// Check for small differences
			final List<Dummy> others = new ArrayList<>(4);
			others.add(new Dummy(dummy.number + 1, dummy.digit));
			others.add(new Dummy(dummy.number, dummy.digit + 1 ));
			others.add(new Dummy(dummy.number + 1, dummy.digit + 1));
			others.add(new Dummy(dummy.number - 1, dummy.digit));
			others.add(new Dummy(dummy.number, dummy.digit - 1));
			others.add(new Dummy(dummy.number - 1, dummy.digit - 1));
			for (final Dummy other : others) {
				assertNotEquals(HashCodeUtils.hashCode(Arrays.asList(new Object[] { dummy.number, dummy.digit })),
						HashCodeUtils.hashCode(Arrays.asList(new Object[] { other.number, other.digit })));
			}
		}
	}
	
	/**
	 * Test method for {@link HashCodeUtils#hashCode(Object[])}.
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
			assertNotEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
					HashCodeUtils.hashCode(new Object[] { dummy.digit, dummy.number }));
			
			// Check for small differences
			final List<Dummy> others = new ArrayList<>(4);
			others.add(new Dummy(dummy.number + 1, dummy.digit));
			others.add(new Dummy(dummy.number, dummy.digit + 1 ));
			others.add(new Dummy(dummy.number + 1, dummy.digit + 1));
			others.add(new Dummy(dummy.number - 1, dummy.digit));
			others.add(new Dummy(dummy.number, dummy.digit - 1));
			others.add(new Dummy(dummy.number - 1, dummy.digit - 1));
			for (final Dummy other : others) {
				assertNotEquals(HashCodeUtils.hashCode(new Object[] { dummy.number, dummy.digit }),
						HashCodeUtils.hashCode(new Object[] { other.number, other.digit }));
			}
		}
	}
}
