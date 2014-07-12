package com.alexrnl.commons.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link SpinnerTimeModel} class.<br />
 * @author Alex
 */
public class SpinnerTimeModelTest {
	/** Spinner for morning times */
	private SpinnerTimeModel	spinnerMorning;
	/** Spinner for evening times */
	private SpinnerTimeModel	spinnerAfternoon;
	/** Spinner with <code>null</code> for bounds */
	private SpinnerTimeModel	spinnerNullBounds;
	
	/**
	 * Set up test attributes.
	 */
	@Before
	public void setUp () {
		spinnerMorning = new SpinnerTimeModel(new Time(9), Time.get("8:00"), Time.get("12:00"));
		spinnerAfternoon = new SpinnerTimeModel(new Time(16), Time.get("14:00"), Time.get("18:00"), Time.get("0:5"));
		spinnerNullBounds = new SpinnerTimeModel(new Time(), null, null);
	}
	
	/**
	 * Restore default logging level.
	 */
	@After
	public void tearDown () {
		Logger.getLogger(SpinnerTimeModel.class.getName()).setLevel(Level.INFO);
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#SpinnerTimeModel(Time, Time, Time)}.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void constructorBeforeMin () {
		new SpinnerTimeModel(new Time(), Time.get("8:00"), Time.get("12:00"));
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#SpinnerTimeModel(Time, Time, Time)}.
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void constructorAfterMax () {
		new SpinnerTimeModel(new Time(14), Time.get("8:00"), Time.get("12:00"));
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#getValue()}.
	 */
	@Test
	public void testGetValue () {
		assertEquals(new Time(9), spinnerMorning.getValue());
		assertEquals(new Time(16), spinnerAfternoon.getValue());
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#setValue(Object)}.
	 */
	@Test
	public void testSetValue () {
		spinnerMorning.setValue(Time.get("14:00"));
		Logger.getLogger(SpinnerTimeModel.class.getName()).setLevel(Level.WARNING);
		spinnerMorning.setValue(Time.get("14:00"));
		Logger.getLogger(SpinnerTimeModel.class.getName()).setLevel(Level.INFO);
		spinnerAfternoon.setValue("14:00");
		spinnerNullBounds.setValue(new Time());
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#setValue(Object)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetValueNull () {
		spinnerMorning.setValue(null);
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#setValue(Object)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetValueBadClass () {
		spinnerMorning.setValue(new Object());
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#getNextValue()}.
	 */
	@Test
	public void testGetNextValue () {
		assertEquals(Time.get("9:01"), spinnerMorning.getNextValue());
		Logger.getLogger(SpinnerTimeModel.class.getName()).setLevel(Level.FINE);
		assertEquals(Time.get("16:05"), spinnerAfternoon.getNextValue());
		
		spinnerMorning.setValue(Time.get("12:00"));
		assertNull(spinnerMorning.getNextValue());
	}
	
	/**
	 * Test method for {@link SpinnerTimeModel#getPreviousValue()}.
	 */
	@Test
	public void testGetPreviousValue () {
		assertEquals(Time.get("8:59"), spinnerMorning.getPreviousValue());
		Logger.getLogger(SpinnerTimeModel.class.getName()).setLevel(Level.FINE);
		assertEquals(Time.get("15:55"), spinnerAfternoon.getPreviousValue());
		
		spinnerAfternoon.setValue(Time.get("14:03"));
		assertNull(spinnerAfternoon.getPreviousValue());
	}
}
