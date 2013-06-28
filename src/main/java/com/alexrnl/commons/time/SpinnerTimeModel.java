package com.alexrnl.commons.time;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractSpinnerModel;

/**
 * Model for a JSpinner on {@link Time}.<br />
 * Allow to set a value using a {@link Time} or a {@link String} which can be formatted using
 * {@link Time#get(String)}.
 * @author Alex
 */
public class SpinnerTimeModel extends AbstractSpinnerModel {
	/** Logger */
	private static Logger		lg					= Logger.getLogger(SpinnerTimeModel.class.getName());
	
	/** Serial version UID */
	private static final long	serialVersionUID	= 5142588648167228978L;
	
	// Times
	/** The value of the model */
	private Time				value;
	/** The step between two values */
	private final Time			step;
	/** The maximum value reachable */
	private final Time			maxValue;
	/** The minimum value reachable */
	private final Time			minValue;
	
	/**
	 * Constructor #1.<br />
	 * @param value
	 *        the value of the spinner.
	 * @param step
	 *        the step.
	 * @param maxValue
	 *        the maximum time reachable.
	 * @param minValue
	 *        the minimum time reachable.
	 */
	public SpinnerTimeModel (final Time value, final Time step, final Time maxValue, final Time minValue) {
		super();
		this.value = value;
		this.step = step;
		this.maxValue = maxValue;
		this.minValue = minValue;
	}
	
	/**
	 * Constructor #2.<br />
	 * @param value
	 *        the value of the spinner.
	 * @param maxValue
	 *        the maximum time reachable.
	 * @param minValue
	 *        the minimum time reachable.
	 */
	public SpinnerTimeModel (final Time value, final Time maxValue, final Time minValue) {
		this(value, new Time(0, 1), maxValue, minValue);
	}
	
	@Override
	public Object getValue () {
		return value;
	}
	
	@Override
	public void setValue (final Object value) {
		if ((value == null) || (!(value instanceof Time) && !(value instanceof String))) {
			throw new IllegalArgumentException("Illegal value for time " + value);
		}
		if (!value.equals(this.value)) {
			if (value instanceof Time) {
				this.value = (Time) value;
			} else if (value instanceof String) {
				this.value = Time.get((String) value);
			} else {
				lg.warning("Should not arrived at this point, severe class casting problem with " +
						value + " of class " + value.getClass());
				throw new IllegalArgumentException("Should not arrive here: value is not Time or " +
						"String but is" + value.getClass());
			}
			fireStateChanged();
		}
	}
	
	@Override
	public Object getNextValue () {
		return incrTime(1);
	}
	
	@Override
	public Object getPreviousValue () {
		return incrTime(-1);
	}
	
	/**
	 * Increment the current Time by one step in the direction specified.
	 * @param direction
	 *        direction of the step.
	 * @return the new time to be set.
	 */
	private Time incrTime (final int direction) {
		Time newValue = null;
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("incrTime in dir " + direction + " (value: " + value + ")");
		}
		
		// Changing value
		if (direction > 0) {
			newValue = value.add(step);
		} else if (direction < 0) {
			newValue = value.sub(step);
		} else {
			throw new IllegalArgumentException("Direction value " + direction + " is not valid.");
		}
		if (lg.isLoggable(Level.FINE)) {
			lg.fine("newValue: " + newValue);
		}
		
		// Bound checking
		if ((maxValue != null) && (maxValue.compareTo(newValue) < 0)) {
			return null;
		}
		if ((minValue != null) && (minValue.compareTo(newValue) > 0)) {
			return null;
		}
		return newValue;
	}
	
}