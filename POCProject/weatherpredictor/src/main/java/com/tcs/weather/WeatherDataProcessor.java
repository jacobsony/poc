package com.tcs.weather;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;

import com.tcs.weather.bean.WeatherBean;
import com.tcs.weather.exception.WeatherPredictorException;
import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.ApplicationUtil;


/**
 * 
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class contains the methods which process data to calculate various parameters of weather.
 * The data to calculate parameters stored in  basestations.properties file 
 * 
 * 
 *  
 */
public class WeatherDataProcessor {
	
	/**
	 *  This method returns IATA code for the base station.
	 *  It fetches necessary info from basestation.properties file by passing the key.
	 * @param stationName
	 * @param prop
	 * @return iata code
	 * @throws WeatherPredictorException 
	 */

	public  String fetchIataCode(final String stationName, final Properties prop) throws WeatherPredictorException {
		
		String key = stationName+"."+ApplicationConstant.IATA_KEY;
		return fetchValueFromProperty(prop,key);
	}
	
	
	
	
	/**
	 * This method set longitude,latitude and altitude.
	 *  
	 * @param stationName
	 * @param weather
	 * @param Properties
	 * @throws WeatherPredictorException 
	 */
	
	public void setCoordinates(final String stationName, WeatherBean weather,
			final Properties prop) throws 
			WeatherPredictorException {


		String key = stationName + "." + ApplicationConstant.LATITUDE_KEY;

		weather.setLatitude(Double.valueOf(prop.getProperty(key)));

		key = stationName + "." + ApplicationConstant.LONGITUDE_KEY;

		weather.setLongitude(Double.valueOf(fetchValueFromProperty(prop, key)));

		key = stationName + "." + ApplicationConstant.ALTITUDE_KEY;

		weather.setAltitude(Double.valueOf(fetchValueFromProperty(prop, key)));


	}
	
	
	
	  /**
     * 
     * This method returns local time based on time zone
     * @param stationName
     * @param Properties
     * @return local time
    * @throws WeatherPredictorException 
     */
	public String fetchLocalTime(final String stationName, final Properties prop)
			throws WeatherPredictorException {


		Date localTime = getLocalTime(stationName, prop);
		// date formated to required format.
		String formatedLocalTime = ApplicationUtil.formatDate(localTime, "yyyy-MM-dd'T'HH:mm:ss'Z'");

		return formatedLocalTime;

	}
	
	
	/**
	 * 
	 * This method calculate the temperature based on minimum and maximum temperature in a month.
	 * Minimum and maximum temperature for each month stored in property file
	 * Assumption : Max temperature is at 12:00 noon,And minimum temperature at midnight 00:00
	 * 
	 * @param stationName
	 * @param Properties
	 * @return atmosphericTemperature
	 * @throws WeatherPredictorException 
	 */
	public  double fetchTemperature(final String stationName, final Properties prop) throws WeatherPredictorException
		
	{
		// fetching current month to get maximum and minimum temperature
		String month = fetchMonth(stationName,prop);
		String key = stationName+"."+month+"."+ApplicationConstant.MINIMUM_TEMP_KEY;
		
		// fetching minimum temperature for the current month 
		double minTemperature = Double.valueOf(fetchValueFromProperty(prop,key));
		
		key = stationName+"."+month+"."+ApplicationConstant.MAXIMUM_TEMP_KEY;
		
		// fetching maximum temperature for the current month
		double maxTemperature    = Double.valueOf(fetchValueFromProperty(prop,key));
		
		// local time is calculated to get current hour of of day
		Date localTime = getLocalTime(stationName, prop);		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(localTime);
		
		WeatherCalculations weatherCalculations = new WeatherCalculations();
		
		double atmosphericTemperature	= weatherCalculations.calculateTemperature(maxTemperature, minTemperature,calendar.get(Calendar.HOUR_OF_DAY));
		
		return atmosphericTemperature;
	}
	
	
	
	
	/**
	 * This method returns current weather condition.
	 * The weather condition is calculated based on temperature and hour of day.
	 * @param stationName
	 * @param Properties
	 *  @return condition
	 * @throws WeatherPredictorException 
	 */

	public String fetchCondition(final double temperature,final String stationName, final Properties prop) throws WeatherPredictorException {

		String weatherCondition = null;

		if (temperature <= 2) {
			weatherCondition = "Snow";
		} else if ((temperature >= 3) && (12 >= temperature)) {
			weatherCondition = "Cold";
		}

		else if ((temperature >= 13) && (22 >= temperature)) {
			weatherCondition = "Cool";
		}

		else if ((temperature >= 23) && (29 >= temperature) && isSunny(stationName,prop)) {
			weatherCondition = "Sunny";
		}

		else if ((temperature >= 23) && (29 >= temperature)) {
			weatherCondition = "Warm";
		}

		else if ((temperature >= 30) && (38 >= temperature) && isSunny(stationName,prop)) {
			weatherCondition = "Sunny";
		}

		else if ((temperature >= 30) && (38 >= temperature)) {
			weatherCondition = "Hot";
		}

		else if (temperature >= 38 && isSunny(stationName,prop)) {
			weatherCondition = "Sunny";
		}

		else {
			weatherCondition = "Hotter";
		}

		return weatherCondition;
	}

	
	
	/**
	 * 
	 * This method calculates current atmospheric pressure
	 * Pressure calculated base on the information,
	 * Pressure varies with altitude
	 * ie Average pressure variance is 12hpa/100 meters from sea level
	 * Sea level pressure is a constant value
	 * @param stationName
	 * @param Properties
	 * @return atmosphericPressure
	 * @throws WeatherPredictorException 
	 */
		
	public  double fetchAtmosphericPressure(final String stationName,  final Properties prop) throws  WeatherPredictorException
	 
	{
		String key = stationName+"."+ApplicationConstant.ALTITUDE_KEY;
		double altitude = Double.valueOf(fetchValueFromProperty(prop,key));
		WeatherCalculations weatherCalculations = new WeatherCalculations();		
		double atmosphericPressure = weatherCalculations.calculateAtmopshericPressure(altitude);
		return atmosphericPressure; 
	}
	

	/**
	 * This method calculate atmospheric humidity
	 * The humidity is calculated on the dew point temperature and current temperature 
	 * Average dew point temperature for each month is stored in properties file
	 * @param stationName
	 * @param temperature
	 * @param Properties
	 * @return humidity
	 * @throws WeatherPredictorException 
	 */

	public double fetchHumidity(final String stationName, final double temperature,final Properties prop) throws  WeatherPredictorException {
		
		// fectching current month to get dewpoint temperture of the month 
		String month = fetchMonth(stationName,prop);
		
		String key = stationName+"."+month+"."+ApplicationConstant.DEWPOINT_KEY;
		
		// dew point for the month is fetched from properties file
		double dewpointTemperature = Double.valueOf(fetchValueFromProperty(prop,key));
		WeatherCalculations weatherCalculations = new WeatherCalculations();
		
		double humidity = weatherCalculations.calculateHumidity(dewpointTemperature, temperature);
		
		return humidity;
	}
	
	
	/**
	 * 
	 * This method fetch base station details from property file
	 * 
	 * @param prop
	 * @return arrayOfBaseStation
	 * @throws WeatherPredictorException
	 */
	
	
	public String[] fetchBaseStations( final Properties properties)
			throws WeatherPredictorException {
		String baseStations = fetchValueFromProperty(properties,
				ApplicationConstant.BASE_STATION_KEY);
		//if none of the base stations defined it considers as exceptional scenario
		if (baseStations.isEmpty()) {
			throw new WeatherPredictorException("Base stations details are empty");
		}
		 // Base station names stored in property file separated by comma
		String[] arrayOfBaseStation = baseStations.split(",");
		return arrayOfBaseStation;

	}
	
	
	  /**
	   * 
	   * This method fetches value from property file for key.
	   * If key is missing it will throw WeatherPredictorException.
	   * 
	   * @param Properties
	   * @param key
	   * @return Value
	   * @throws WeatherPredictorException
	   */
	private String fetchValueFromProperty(final Properties properties, final String key)
			throws WeatherPredictorException {
		String value = properties.getProperty(key);
		// if value is null a WeatherPredictorException is thrown
		if (value == null)
			throw new WeatherPredictorException(" missing key :"+key);

		return value;

	}
	

	
	
	/**
	 * This method check weather its time between 10 AM to 3PM
	 * Assumption:This time can be considered for weather condition Sunny
	 *@param stationName
	 * @param properties
	 * @return boolean
	 * @throws WeatherPredictorException 
	 */
	
	private boolean isSunny(final String stationName, final Properties properties) throws WeatherPredictorException {
		Date localTime = getLocalTime(stationName, properties);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(localTime);

		if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
				&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param stationName
	 * @param prop
	 * @return month
	 * @throws WeatherPredictorException
	 */
	
	private String fetchMonth(final String stationName, final Properties prop)
			throws WeatherPredictorException {

		Date localTime = getLocalTime(stationName, prop);
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(localTime);
		return ApplicationUtil.fetchMonth(calendar.get(Calendar.MONTH));
	}
	
	
	private Date getLocalTime(final String stationName, final Properties prop)
			throws WeatherPredictorException {

		String key = stationName + "." + ApplicationConstant.TIME_ZONE_KEY;

		TimeZone.setDefault(TimeZone.getTimeZone(fetchValueFromProperty(prop,
				key)));

		return new Date();
	}
	
	
	

		
}
