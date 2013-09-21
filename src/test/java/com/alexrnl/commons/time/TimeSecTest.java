package com.alexrnl.commons.time;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link TimeSec} class.
 * @author Alex
 */
public class TimeSecTest {
	/** The time at midnight */
	private TimeSec			timeMidnight;
	/** The time in the morning */
	private TimeSec			timeMorning;
	/** The time at noon */
	private TimeSec			timeNoon;
	/** The time in the afternoon */
	private TimeSec			timeAfterNoon;
	/** The time in the evening */
	private TimeSec			timeEvening;
	/** The time in the night */
	private TimeSec			timeNight;
	/** The list with all the times */
	private List<TimeSec>	times;
	
	/**
	 * Set up attributes.
	 */
	@Before
	public void setUp () {
		timeMidnight = new TimeSec();
		timeMorning = new TimeSec(6);
		timeNoon = new TimeSec(12, 04);
		timeAfterNoon = new TimeSec(14, 26, 88);
		timeEvening = new TimeSec(Time.get("19h21"), 48);
		timeNight = new TimeSec(Time.get("23:18"));
		
		times = new ArrayList<>(5);
		times.add(timeMidnight);
		times.add(timeMorning);
		times.add(timeNoon);
		times.add(timeAfterNoon);
		times.add(timeEvening);
		times.add(timeNight);
	}
	
	/**
	 * Test method for {@link TimeSec#hashCode()}.
	 */
	@Test
	public void testHashCode () {
		for (final TimeSec time : times) {
			// Checking other times
			for (final TimeSec other : times) {
				if (time != other) {
					assertThat(other.hashCode(), not(time.hashCode()));
				}
			}
			
			// Other checks
			final TimeSec otherS = new TimeSec(time.getHours(), time.getMinutes(), time.getSeconds() + 1);
			final TimeSec otherM = new TimeSec(time.getHours(), time.getMinutes() + 1, time.getSeconds());
			final TimeSec otherH = new TimeSec(time.getHours() + 1, time.getMinutes(), time.getSeconds());
			assertThat(otherS.hashCode(), not(time.hashCode()));
			assertThat(otherM.hashCode(), not(time.hashCode()));
			assertThat(otherH.hashCode(), not(time.hashCode()));
			assertEquals(time.hashCode(), new TimeSec(time.getHours(), time.getMinutes(), time.getSeconds()).hashCode());
		}
	}
	
	/**
	 * Test method for {@link TimeSec#compareTo(Time)}.
	 */
	@Test
	public void testCompareTo () {
		for (final TimeSec time : times) {
			assertEquals(0, time.compareTo(time));
			assertTrue(time.compareTo(null) > 0);
		}
		
		// The list is ordered so testing that is possible
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index + 1).compareTo(times.get(index)) > 0);
			assertTrue(times.get(index).compareTo(times.get(index + 1)) < 0);
		}
		assertTrue(TimeSec.get("3h01m58s").compareTo(TimeSec.get("3h01m59s")) < 0);
		assertTrue(TimeSec.get("21h03m28s").compareTo(TimeSec.get("21h03m27s")) > 0);
		assertTrue(TimeSec.get("3h01m58s").compareTo(Time.get("3h02")) < 0);
		assertTrue(TimeSec.get("21h03m28s").compareTo(Time.get("21h03")) > 0);
	}
	
	/**
	 * Test method for {@link TimeSec#after(Time)}.
	 */
	@Test
	public void testAfter () {
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index + 1).after(times.get(index)));
			assertFalse(times.get(index).after(times.get(index + 1)));
		}
		assertTrue(TimeSec.get("16h1m28").after(TimeSec.get("4h02m8")));
		assertTrue(TimeSec.get("16h1m28").after(Time.get("4h02")));
	}
	
	/**
	 * Test method for {@link TimeSec#before(Time)}.
	 */
	@Test
	public void testBefore () {
		for (int index = 0; index < times.size() - 1; ++index) {
			assertTrue(times.get(index).before(times.get(index + 1)));
			assertFalse(times.get(index + 1).before(times.get(index)));
		}
		assertTrue(TimeSec.get("2h08m40").before(TimeSec.get("4h02m18")));
		assertTrue(TimeSec.get("2h08m40").before(Time.get("2h09")));
	}
	
	/**
	 * Test method for {@link TimeSec#toString()}.
	 */
	@Test
	public void testToString () {
		assertEquals("00:00:00", timeMidnight.toString());
		assertEquals("06:00:00", timeMorning.toString());
		assertEquals("12:04:00", timeNoon.toString());
		assertEquals("14:27:28", timeAfterNoon.toString());
		assertEquals("19:21:48", timeEvening.toString());
		assertEquals("23:18:00", timeNight.toString());
	}
	
	/**
	 * Test method for {@link TimeSec#equals(Object)}.
	 */
	@Test
	public void testEqualsObject () {
		for (final TimeSec time : times) {
			// Check all other times are different
			for (final TimeSec other : times) {
				if (time != other) {
					assertThat(time, not(other));
				}
			}
			
			// Other checks
			assertThat(new TimeSec(time.getHours(), time.getMinutes(), time.getSeconds() + 1), not(time));
			assertThat(new TimeSec(time.getHours(), time.getMinutes() + 1, time.getSeconds()), not(time));
			assertThat(new TimeSec(time.getHours() + 1, time.getMinutes(), time.getSeconds()), not(time));
			assertEquals(time, new TimeSec(time.getHours(), time.getMinutes(), time.getSeconds()));
			assertEquals(time, time);
			assertFalse(time.equals(null));
			assertFalse(time.equals(new Object()));
		}
	}
	
	/**
	 * Test method for {@link TimeSec#get(String)}.
	 */
	@Test
	public void testGetString () {
		assertEquals(timeMidnight, TimeSec.get(""));
		assertEquals(timeMorning, TimeSec.get("6:0"));
		assertEquals(timeNoon, TimeSec.get("12:04"));
		assertEquals(timeAfterNoon, TimeSec.get("14h27m28s"));
		assertEquals(timeEvening, TimeSec.get("19 21 48"));
		assertEquals(timeNight, TimeSec.get("23.18.00"));
	}
	
	/**
	 * Test method for {@link TimeSec#getCurrent()}.
	 */
	@Test
	public void testGetCurrent () {
		final TimeSec time = TimeSec.getCurrent();
		final Calendar cal = Calendar.getInstance(Locale.getDefault());
		assertEquals(cal.get(Calendar.SECOND), time.getSeconds());
		assertEquals(cal.get(Calendar.MINUTE), time.getMinutes());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), time.getHours());
		Logger.getLogger(TimeSecTest.class.getName()).info("This should display the current time: " + time);
	}
	
	/**
	 * Test method for {@link TimeSec#getSeconds()}.
	 */
	@Test
	public void testGetSeconds () {
		assertEquals(0, timeMidnight.getSeconds());
		assertEquals(0, timeMorning.getSeconds());
		assertEquals(0, timeNoon.getSeconds());
		assertEquals(28, timeAfterNoon.getSeconds());
		assertEquals(48, timeEvening.getSeconds());
		assertEquals(0, timeNight.getSeconds());
	}
	
	/**
	 * Test method for {@link TimeSec#getTime()}.
	 */
	@Test
	public void testGetTime () {
		for (final TimeSec time : times) {
			final Time other = new Time(time.getHours(), time.getMinutes());
			assertEquals(other, time.getTime());
		}
	}
	
	/**
	 * Test method for {@link TimeSec#add(TimeSec)}.
	 */
	@Test
	public void testAddTimeSec () {
		// Basic check for midnight time
		for (final TimeSec time : times) {
			assertEquals(time, timeMidnight.add(time));
		}
		assertEquals(timeAfterNoon, timeMorning.add(TimeSec.get("8h27m28s")));
		assertEquals(timeNight, timeNoon.add(new TimeSec(11, 14, 00)));
	}
	
	/**
	 * Test method for {@link TimeSec#sub(TimeSec)}.
	 */
	@Test
	public void testSubTimeSec () {
		// Basic check for midnight time
		for (final TimeSec time : times) {
			assertEquals(time, time.sub(timeMidnight));
		}
		assertEquals(timeMorning, timeNight.sub(TimeSec.get("17h18m0")));
		assertEquals(timeNoon, timeAfterNoon.sub(new TimeSec(2, 23, 28)));
	}
	
	/**
	 * Test method for {@link TimeSec#clone()}.
	 */
	@Test
	public void testClone () {
		try {
			for (final TimeSec time : times) {
				final TimeSec clone = time.clone();
				final TimeSec copy = new TimeSec(time);
				assertEquals(time, clone);
				assertTrue(time != clone);
				assertEquals(time, copy);
				assertTrue(time != copy);
			}
		} catch (final CloneNotSupportedException e) {
			fail(e.getMessage());
		}
	}
}
