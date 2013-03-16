package com.alexrnl.commons.time;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexrnl.commons.CommonsConstants;

/**
 * TODO
 * @author Alex
 */
public class TimeSec extends Time {
	/** Logger */
	private static Logger	lg	= Logger.getLogger(TimeSec.class.getName());
	
	/** Serial version UID */
	private static final long	serialVersionUID	= -5220683648807102121L;
	
	/** The number of seconds */
	private final int seconds;
	
	/**
	 * Constructor #1.<br />
	 * Default constructor, set time to midnight.
	 */
	public TimeSec () {
		this(0);
	}
	
	/**
	 * Constructor #2.<br />
	 * The number of minutes and seconds will be set to 0.
	 * @param hours
	 *        the number of hours.
	 */
	public TimeSec (final int hours) {
		this(hours, 0);
	}
	
	/**
	 * Constructor #3.<br />
	 * The number of seconds will be set to 0.
	 * @param hours
	 *        the number of hours.
	 * @param minutes
	 *        the number of minutes.
	 */
	public TimeSec (final int hours, final int minutes) {
		this(hours, minutes, 0);
	}
	
	/**
	 * Constructor #4.<br />
	 * @param hours
	 *        the number of hours.
	 * @param minutes
	 *        the number of minutes.
	 * @param seconds
	 *        the number of seconds.
	 */
	public TimeSec (final int hours, final int minutes, final int seconds) {
		super(hours, minutes + seconds / CommonsConstants.SECONDS_PER_MINUTES);
		this.seconds = seconds % CommonsConstants.SECONDS_PER_MINUTES;
	}
	
	/**
	 * Constructor #5.<br />
	 * @param timeStamp
	 *        the number of seconds since January 1st, 1970.
	 */
	public TimeSec (final long timeStamp) {
		this(new Date(timeStamp));
	}
	
	/**
	 * Constructor #6.<br />
	 * Build the time from the date.
	 * @param date
	 *        the date to use.
	 */
	public TimeSec (final Date date) {
		super(date);
		final Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(date);
		seconds = cal.get(Calendar.SECOND);
	}
	
	/**
	 * Constructor #7.<br />
	 * Build the object from the {@link Time} given.
	 * @param time
	 *        the time.
	 */
	public TimeSec (final Time time) {
		this(time, 0);
	}
	
	/**
	 * Constructor #8.<br />
	 * Build the object from the {@link Time} and seconds given.
	 * @param time
	 *        the time.
	 * @param seconds the number of seconds.
	 */
	public TimeSec (final Time time, final int seconds) {
		super(time);
		this.seconds = seconds;
	}
	
	/**
	 * Build a time based on a string.<br />
	 * The time format must be hours minutes (in that order) separated using any
	 * non-numerical character.<br />
	 * @param time
	 *        the time set.
	 * @return the time matching the string.
	 */
	public static TimeSec get (final String time) {
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("Parsing time " + time);
		}
		final String[] hm = time.split(CommonsConstants.NON_DECIMAL_CHARACTER);
		Integer hours = null;
		Integer minutes = null;
		Integer seconds = null;
		for (final String s : hm) {
			if (s.isEmpty()) {
				continue;
			}
			if (hours == null) {
				hours = Integer.parseInt(s);
				continue;
			}
			if (minutes == null) {
				minutes = Integer.parseInt(s);
				continue;
			}
			seconds = Integer.parseInt(s);
			break;
		}
		return new TimeSec(hours == null ? 0 : hours, minutes == null ? 0 : minutes,
				seconds == null ? 0 : seconds);
	}
	
	/**
	 * Return the current time.
	 * @return a {@link Time} object matching the current time.
	 */
	public static TimeSec getCurrent () {
		return new TimeSec(System.currentTimeMillis());
	}
	
	/**
	 * Return the attribute seconds.
	 * @return the attribute seconds.
	 */
	public int getSeconds () {
		return seconds;
	}
	
	/**
	 * Return a Time object build from this TimeSec properties.
	 * @return the new Time object.
	 */
	public Time getTime () {
		return new Time(getHours(), getMinutes());
	}
	
}
