package com.tcs.weather.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class has the methods which can be re-used across application.
 *
 * 
 */

import com.tcs.weather.exception.WeatherPredictorException;

public class ApplicationUtil {
	
	/**
	 * 
	 * This method returns the date in required format.
	 * Required format is accepted as parameter
	 * @param date
	 * @param dateFormat
	 * @return 
	 */
	
	public static String formatDate(final Date date, final String dateFormat) {
		SimpleDateFormat sm = new SimpleDateFormat(dateFormat);
		String formatedDate = sm.format(date);
		return formatedDate;

	}
	
	/**
	 * 
	 * This method returns month value for corresponding integer value of month
	 *
	 * @param integerValueOfMonth
	 * @return month
	 * @throws WeatherPredictorException
	 */
	
	public static String fetchMonth(int integerValueOfMonth) throws WeatherPredictorException {

		String month = null;

		switch (integerValueOfMonth) {
		case 0:
			month = "january";
			break;

		case 1:
			month = "february";
			break;

		case 2:
			month = "march";
			break;

		case 3:
			month = "april";
			break;

		case 4:
			month = "may";
			break;

		case 5:
			month = "june";
			break;

		case 6:
			month = "july";
			break;

		case 7:
			month = "august";
			break;

		case 8:
			month = "september";
			break;

		case 9:
			month = "october";
			break;

		case 10:
			month = "november";
			break;

		case 11:
			month = "december";
			break;

		default:
			throw new WeatherPredictorException("Invalid month value");
		}

		return month;

	}

}
