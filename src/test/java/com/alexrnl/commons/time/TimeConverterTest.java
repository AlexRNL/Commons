package com.alexrnl.commons.time;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alexrnl.commons.time.TimeConverter.Unit;

/**
 * Test suite for the {@link TimeConverter} class.
 * @author Alex
 */
public class TimeConverterTest {
	/** The acceptable delta when comparing double in tests */
	private static final double DELTA_RESULT_COMPARISON = 0.0001;

	/**
	 * Test method for {@link TimeConverter#convert(double, Unit, Unit)}.
	 */
	@Test
	public void testConvertDouble () {
		assertEquals(10, TimeConverter.convert(10.0, Unit.SECONDS, Unit.SECONDS), DELTA_RESULT_COMPARISON);
		assertEquals(60, TimeConverter.convert(1.0, Unit.MINUTES, Unit.SECONDS), DELTA_RESULT_COMPARISON);
		assertEquals(86400, TimeConverter.convert(1.0, Unit.DAYS, Unit.SECONDS), DELTA_RESULT_COMPARISON);
		assertEquals(0.001, TimeConverter.convert(1.0, Unit.MILLISECONDS, Unit.SECONDS), DELTA_RESULT_COMPARISON);
		assertEquals(1000000000.0, TimeConverter.convert(1.0, Unit.SECONDS, Unit.NANOSECONDS), DELTA_RESULT_COMPARISON);
	}
	
	/**
	 * Test method for {@link TimeConverter#convert(double, Unit, Unit)}.
	 */
	@Test
	public void testConvertLong () {
		assertEquals(10, TimeConverter.convert(10, Unit.SECONDS, Unit.SECONDS));
		assertEquals(60, TimeConverter.convert(1, Unit.MINUTES, Unit.SECONDS));
		assertEquals(86400, TimeConverter.convert(1, Unit.DAYS, Unit.SECONDS));
		assertEquals(152, TimeConverter.convert(152000, Unit.NANOSECONDS, Unit.MICROSECONDS));
	}
}
