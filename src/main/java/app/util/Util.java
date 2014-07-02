package app.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Util {
	
	/**
	 * Method that returns the current time on the server.
	 * 
	 * @return {@link Date}
	 */
	public static Date getCurrentTime() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Method that transforms a {@link Date} object into a {@link Timestamp} object.
	 * 
	 * @param date
	 * @return {@link Timestamp}
	 */
	public static Timestamp getTimestamp(final Date date) {
		return new Timestamp(date.getTime());
	}
	
}
