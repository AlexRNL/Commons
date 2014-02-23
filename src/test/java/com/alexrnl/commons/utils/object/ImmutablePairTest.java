package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link ImmutablePair} class.
 * @author Alex
 */
public class ImmutablePairTest {
	/** The pair to test */
	private ImmutablePair<String, Integer>	pair;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		pair = new ImmutablePair<>("aba", 28);
	}
	
	/**
	 * Test method for {@link ImmutablePair#getLeft()}.
	 */
	@Test
	public void testGetLeft () {
		assertEquals("aba", pair.getLeft());
	}
	
	/**
	 * Test method for {@link ImmutablePair#getRight()}.
	 */
	@Test
	public void testGetRight () {
		assertEquals(Integer.valueOf(28), pair.getRight());
	}
	
	/**
	 * Test method for {@link Pair#getKey()}.
	 */
	@Test
	public void testGetKey () {
		assertEquals("aba", pair.getKey());
	}

	/**
	 * Test method for {@link Pair#getValue()}.
	 */
	@Test
	public void testGetValue () {
		assertEquals(Integer.valueOf(28), pair.getValue());
	}

	/**
	 * Test method for {@link ImmutablePair#setValue(Object)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testSetValue () {
		pair.setValue(48);
	}
	
	/**
	 * Test method for {@link Pair#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		final int originalHashCode = pair.hashCode();
		assertEquals(originalHashCode, pair.hashCode());
		assertEquals(originalHashCode, new ImmutablePair<>("aba", 28).hashCode());
		assertNotEquals(originalHashCode, new ImmutablePair<>("man", 28).hashCode());
		assertNotEquals(originalHashCode, new ImmutablePair<>("aba", 4).hashCode());
	}
	
	/**
	 * Test method for {@link Pair#equals(Object)}.
	 */
	@Test
	public void testEqualsObject () {
		assertFalse(pair.equals(null));
		assertFalse(pair.equals(new Object()));
		
		assertNotEquals(new ImmutablePair<>("man", 28), pair);
		assertNotEquals(new ImmutablePair<>("aba", 4), pair);
	}
	
	/**
	 * Test method for {@link Pair#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("(aba, 28)", pair.toString());
	}
}
