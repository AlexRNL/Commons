package com.alexrnl.commons.time;


/**
 * Utility class used to convert dates from a specific unit into an other one.
 * @author Alex
 */
public final class TimeConverter {
	
	/**
	 * Enumeration for a time unit.
	 * @author Alex
	 */
	public enum Unit {
		/** Nanoseconds */
		NANOSECONDS (null, 0),
		/** Microseconds */
		MICROSECONDS (NANOSECONDS, 1000),
		/** Milliseconds */
		MILLISECONDS (MICROSECONDS, 1000),
		/** Seconds */
		SECONDS (MILLISECONDS, 1000),
		/** Minutes */
		MINUTES (SECONDS, 60),
		/** Hours */
		HOURS (MINUTES, 60),
		/** Days */
		DAYS (HOURS, 24),
		/** Weeks */
		WEEKS (DAYS, 7),
		/** Months (30 days) */
		MONTHS (DAYS, 30),
		/** Years (365 days) */
		YEARS (DAYS, 365),
		/** Centuries */
		CENTURIES (YEARS, 100),
		/** Millennium */
		MILLENNIUMS (CENTURIES, 10);
		
		/** The reference of the unit */
		private final Unit	reference;
		/** The conversion factor to go to the reference */
		private final int	conversionFactor;

		/**
		 * Constructor #1.<br />
		 * @param reference
		 *        the reference unit for the conversion.
		 * @param conversionFactor
		 *        the conversion factor between the current unit and the reference given.
		 */
		private Unit (final Unit reference, final int conversionFactor) {
			this.reference = reference;
			this.conversionFactor = conversionFactor;
		}

		/**
		 * Return the attribute reference.
		 * @return the attribute reference.
		 */
		public Unit getReference () {
			return reference;
		}

		/**
		 * Return the attribute conversionFactor.
		 * @return the attribute conversionFactor.
		 */
		public int getConversionFactor () {
			return conversionFactor;
		}
		
	}
	
	/**
	 * Constructor #1.<br />
	 * Default private constructor.
	 */
	private TimeConverter () {
		super();
	}
	
	/**
	 * Convert the provided time from the given {@link Unit} into the specified destination.
	 * @param time
	 *        the original time.
	 * @param from
	 *        the unit of the original time.
	 * @param to
	 *        the unit required.
	 * @return the time in the unit required.
	 */
	public static double convert (final double time, final Unit from, final Unit to) {
		return time * getConversionFactor(from, to);
	}
	
	/**
	 * Convert the provided time from the given {@link Unit} into the specified destination.
	 * @param time
	 *        the original time.
	 * @param from
	 *        the unit of the original time.
	 * @param to
	 *        the unit required.
	 * @return the time in the unit required.
	 * @see #convert(double, Unit, Unit)
	 */
	public static long convert (final long time, final Unit from, final Unit to) {
		return Double.valueOf(convert(Long.valueOf(time).doubleValue(), from, to)).longValue();
	}
	
	/**
	 * Get the conversion factor between the unit specified.
	 * @param from
	 *        the unit to start from.
	 * @param to
	 *        the unit to reach.
	 * @return the conversion factor.
	 */
	public static double getConversionFactor (final Unit from, final Unit to) {
		if (from.equals(to)) {
			// Avoid useless computation if unit is the same
			return 1.0;
		}
		
		double conversionFactorFrom = 1.0;
		Unit workingUnit = from;
		while (workingUnit.getReference() != null) {
			conversionFactorFrom *= workingUnit.getConversionFactor();
			workingUnit = workingUnit.getReference();
		}
		double conversionFactorTo = 1.0;
		workingUnit = to;
		while (workingUnit.getReference() != null) {
			conversionFactorTo *= workingUnit.getConversionFactor();
			workingUnit = workingUnit.getReference();
		}
		
		return conversionFactorFrom / conversionFactorTo;
	}
}
