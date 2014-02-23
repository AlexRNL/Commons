package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link MutablePair} class.
 * @author Alex
 */
public class MutablePairTest {
	/** The pair to test */
	private MutablePair<String, Integer>	pair;
	/** The empty pair to test */
	private MutablePair<String, Integer>	emptyPair;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		pair = new MutablePair<>("aba", 28);
		emptyPair = new MutablePair<>();
	}
	
	/**
	 * Test method for {@link MutablePair#getLeft()}.
	 */
	@Test
	public void testGetLeft () {
		assertEquals("aba", pair.getLeft());
		assertNull(emptyPair.getLeft());
	}
	
	/**
	 * Test method for {@link MutablePair#getRight()}.
	 */
	@Test
	public void testGetRight () {
		assertEquals(Integer.valueOf(28), pair.getRight());
		assertNull(emptyPair.getRight());
	}
	
	/**
	 * Test method for {@link MutablePair#setLeft(Object)}.
	 */
	@Test
	public void testSetLeft () {
		assertEquals("aba", pair.getLeft());
		pair.setLeft("lau");
		assertEquals("lau", pair.getLeft());
		
		assertNull(emptyPair.getLeft());
		emptyPair.setLeft("");
		assertEquals("", emptyPair.getLeft());
	}
	
	/**
	 * Test method for {@link MutablePair#setRight(Object)}.
	 */
	@Test
	public void testSetRight () {
		assertEquals(Integer.valueOf(28), pair.getRight());
		pair.setRight(88);
		assertEquals(Integer.valueOf(88), pair.getRight());

		assertNull(emptyPair.getRight());
		emptyPair.setRight(-8);
		assertEquals(Integer.valueOf(-8), emptyPair.getRight());
	}
	
	/**
	 * Test method for {@link Pair#getKey()}.
	 */
	@Test
	public void testGetKey () {
		assertEquals("aba", pair.getKey());
		assertNull(emptyPair.getKey());
	}

	/**
	 * Test method for {@link Pair#getValue()}.
	 */
	@Test
	public void testGetValue () {
		assertEquals(Integer.valueOf(28), pair.getValue());
		assertNull(emptyPair.getValue());
	}

	/**
	 * Test method for {@link MutablePair#setValue(Object)}.
	 */
	@Test
	public void testSetValue () {
		assertEquals(Integer.valueOf(28), pair.getValue());
		pair.setValue(48);
		assertEquals(Integer.valueOf(48), pair.getValue());

		assertNull(emptyPair.getValue());
		emptyPair.setValue(-28);
		assertEquals(Integer.valueOf(-28), emptyPair.getValue());
	}
	
	/**
	 * Test method for {@link Pair#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		final int originalHashCode = pair.hashCode();
		assertEquals(originalHashCode, pair.hashCode());
		assertEquals(originalHashCode, new MutablePair<>("aba", 28).hashCode());
		assertNotEquals(originalHashCode, emptyPair.hashCode());
		pair.setLeft("man");
		assertNotEquals(originalHashCode, pair.hashCode());
		pair.setLeft("aba");
		assertEquals(originalHashCode, pair.hashCode());
		pair.setRight(4);
		assertNotEquals(originalHashCode, pair.hashCode());
	}
	
	/**
	 * Test method for {@link Pair#equals(Object)}.
	 */
	@Test
	public void testEqualsObject () {
		assertFalse(pair.equals(null));
		assertFalse(pair.equals(new Object()));
		assertFalse(emptyPair.equals(pair));
		
		final MutablePair<String, Integer> otherPair = new MutablePair<>("aba", 28);
		otherPair.setLeft("man");
		assertNotEquals(otherPair, pair);
		otherPair.setLeft("aba");
		assertEquals(pair, otherPair);
		otherPair.setRight(4);
		assertNotEquals(otherPair, pair);
	}
	
	/**
	 * Test method for {@link Pair#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("(aba, 28)", pair.toString());
		assertEquals("(null, null)", emptyPair.toString());
	}
}
