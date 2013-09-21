package com.alexrnl.commons.utils.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alexrnl.commons.utils.object.AttributeComparator.Attributes;

/**
 * Test suite for the {@link AttributeComparator} class.
 * @author Alex
 */
public class AttributeComparatorTest {
	/** List of left attributes to be compared */
	private final List<Object> left = new ArrayList<>();
	/** List of right attributes to be compared */
	private final List<Object> right = new ArrayList<>();
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		left.add(true);
		right.add(true);
		left.add(8);
		right.add(8);
		left.add(null);
		right.add(null);
		left.add(new int[] {2, 1});
		right.add(new int[] {2, 1});;
		left.add(new Double[] {8.4, 86.42});
		right.add(new Double[] {8.4, 86.42});
	}
	
	/**
	 * Test method for {@link AttributeComparator#add(java.lang.Object, Object)}.
	 */
	@Test
	public void testAddTT () {
		final AttributeComparator ac = new AttributeComparator();
		assertEquals(0, ac.getAttributeNumber());
		ac.add(left, right);
		assertEquals(1, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link AttributeComparator#add(AttributeComparator.Attributes)}.
	 */
	@Test
	public void testAddAttributesOfT () {
		final AttributeComparator ac = new AttributeComparator();
		assertEquals(0, ac.getAttributeNumber());
		ac.add(new Attributes<>(left, right));
		assertEquals(1, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link AttributeComparator#clear()}.
	 */
	@Test
	public void testClear () {
		final AttributeComparator ac = new AttributeComparator();
		ac.add(new Attributes<>(left, right));
		assertEquals(1, ac.getAttributeNumber());
		ac.clear();
		assertEquals(0, ac.getAttributeNumber());
	}
	
	/**
	 * Test method for {@link AttributeComparator#areEquals()}.
	 */
	@Test
	public void testAreEquals () {
		final AttributeComparator attributeComparator = new AttributeComparator();
		for (int index = 0; index < left.size(); ++index) {
			attributeComparator.add(left.get(index), right.get(index));
		}
		assertTrue(attributeComparator.areEquals());
		
		attributeComparator.add(null, false);
		assertFalse(attributeComparator.areEquals());
		
		attributeComparator.clear();
		attributeComparator.add(true, false);
		assertFalse(attributeComparator.areEquals());
	}
	
	/**
	 * Test method for {@link AttributeComparator#areEquals(java.lang.Object, Object)}.
	 */
	@Test
	public void testAreEqualsTT () {
		for (int leftIndex = 0; leftIndex < left.size(); ++leftIndex) {
			assertTrue(AttributeComparator.areEquals(left.get(leftIndex), right.get(leftIndex)));
			for (int rightIndex = 0; rightIndex < right.size(); ++rightIndex) {
				if (leftIndex != rightIndex) {
					assertFalse(AttributeComparator.areEquals(left.get(leftIndex), right.get(rightIndex)));
				}
			}
		}
	}
}
