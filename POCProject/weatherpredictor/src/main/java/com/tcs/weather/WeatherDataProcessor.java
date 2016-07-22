package com.tcs.weather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import com.tcs.weather.bean.WeatherBean;
import com.tcs.weather.exception.WeatherPredictorException;
import com.tcs.weather.util.ApplicationConstant;


/**
 * 
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class contains the methods to process data to calculate various parameters of weather
 * 
 *  */
public class WeatherDataProcessor {
	
	private  static  Map<Integer,String> monthMap = new HashMap<Integer, String>();
	
	
	//Static method will load month information to a map
	
	static{
		monthMap.put(0, "january");
		monthMap.put(1, "february");
		monthMap.put(2, "march");
		monthMap.put(3, "april");
		monthMap.put(4, "may");
		monthMap.put(5, "june");
		monthMap.put(6, "july");
		monthMap.put(7, "august");
		monthMap.put(8, "september");
		monthMap.put(9, "october");
		monthMap.put(10,"november");
		monthMap.put(11,"december");
	}
	
	/**
	 * 
	 * This method calculate the temperature based on minimum and maximum temperature in a month.
	 * Minimum and maximum temperature for each month stored in property file
	 * Assumption : Max temperature is at 12:00 noon,And minimum temperature at midnight 00:00
	 * And assumed uniform variation across the hours.
	 * 
	 * @param stationName
	 * @param Properties
	 * @return temperature
	 * @throws WeatherPredictorException 
	 * @throws NumberFormatException 
	 */
	public  float fetchTemperature(String stationName, Properties prop) throws NumberFormatException, WeatherPredictorException
		
	{
		
		String month = fetchMonth(stationName,prop);
		String key = stationName+"."+month+"."+ApplicationConstant.MINIMUM_TEMP_KEY;
		
		float minTemperature = Float.valueOf(fetchValueFromProperty(prop,key));
		
		key = stationName+"."+month+"."+ApplicationConstant.MAXIMUM_TEMP_KEY;
		
		float maxTemperature    = Float.valueOf(fetchValueFromProperty(prop,key));
		
		float calculatedTemperature;
		float perHourVariation =  ((maxTemperature - minTemperature)/12); // calculating per hour variation.Assumption: Uniform variation of temperature.
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime( new Date());
		int hourDifference =  12 - calendar.get(Calendar.HOUR_OF_DAY) ;
		
		// hour difference  >= 0 for hour before noon.
		if (hourDifference >= 0) {
			calculatedTemperature = maxTemperature - (hourDifference * perHourVariation);
		} else {
			calculatedTemperature = maxTemperature
					- (hourDifference * perHourVariation * -1);
		}
		
		DecimalFormat df = new DecimalFormat("#.#");  
		calculatedTemperature = Float.valueOf(df.format(calculatedTemperature));
		return calculatedTemperature;
	}
	
	
	
	/**
	 * 
	 * 
	 * Pressure calculated base on the information :Average pressure variance is 12hpa/100 meters from sea level
	 * @param stationName
	 * @param Properties
	* @return AtmospherePressure
	 * @throws WeatherPredictorException 
	 * @throws NumberFormatException 
	 */
	
		
	public  float fetchAtmospherePressure(String stationName, Properties prop) throws NumberFormatException, WeatherPredictorException
	 
	{
		String key = stationName+"."+ApplicationConstant.ALTITUDE_KEY;
		float altitude = Float.valueOf(fetchValueFromProperty(prop,key));
		float seaLevelPressure = ApplicationConstant.SEALEVEL_PRESSURE_KEY;
		float fraction = (altitude/100)*12;
		float calculatedPressure = seaLevelPressure - fraction;
		return calculatedPressure; 
	}
	
     /**
      * 
      * This method returns local time based on time stamp
      * @param stationName
      * @param Properties
      * @return local time
     * @throws WeatherPredictorException 
      */
	public String fetchLocalTime(String stationName, Properties prop)
			throws WeatherPredictorException {


		String key = stationName + "." + ApplicationConstant.TIME_ZONE_KEY;

		TimeZone.setDefault(TimeZone.getTimeZone(fetchValueFromProperty(prop,
				key)));

		String formatedDate = formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'");


		return formatedDate;

	}
	
	/**
	 *  This method returns IATA code
	 * @param stationName
	 * @param prop
	 * @return iata code
	 * @throws WeatherPredictorException 
	 */

	public  String fetchIataCode(String stationName, Properties prop) throws WeatherPredictorException {
		
		String key = stationName+"."+ApplicationConstant.IATA_KEY;
		return fetchValueFromProperty(prop,key);
	}

	
	/**
	 * This method set longitude,latitude and altitude
	 * 
	 * @param stationName
	 * @param weather
	 * @param Properties
	 * @throws WeatherPredictorException 
	 * @throws NumberFormatException 
	 */
	
	public void setCordinates(String stationName, WeatherBean weather,
			Properties prop) throws NumberFormatException,
			WeatherPredictorException {


		String key = stationName + "." + ApplicationConstant.LATITUDE_KEY;

		weather.setLatitude(Float.valueOf(prop.getProperty(key)));

		key = stationName + "." + ApplicationConstant.LONGITUDE_KEY;

		weather.setLongitude(Float.valueOf(fetchValueFromProperty(prop, key)));

		key = stationName + "." + ApplicationConstant.ALTITUDE_KEY;

		weather.setAltitude(Float.valueOf(fetchValueFromProperty(prop, key)));


	}

	/**
	 * This method set fetch weather condition for the month
	 * @param stationName
	 * @param Properties
	 *  @return condition
	 * @throws WeatherPredictorException 
	 */

	public String fetchCondition(float temperature,String stationName, Properties prop) throws WeatherPredictorException {

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
	 * This method check weather its time between 10 AM to 3PM
	 * Assumption:This time can be considered for weather condition Sunny
	 * @return boolean
	 * @throws WeatherPredictorException 
	 */
	private boolean isSunny(String stationName, Properties prop) throws WeatherPredictorException {
		String key = stationName + "." + ApplicationConstant.TIME_ZONE_KEY;
		TimeZone.setDefault(TimeZone.getTimeZone(fetchValueFromProperty(prop,
				key)));
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());

		if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
				&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
			return true;
		}
		return false;
	}

    
	private String fetchMonth(String stationName, Properties prop)
			throws WeatherPredictorException {

		String key = stationName + "." + ApplicationConstant.TIME_ZONE_KEY;
		TimeZone.setDefault(TimeZone.getTimeZone(fetchValueFromProperty(prop,
				key)));
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(new Date());
		return monthMap.get(calendar.get(Calendar.MONTH));
	}
	
	/**
	 * 
	 * The humidity is calculated based on the equation
	 *Relative Humidity = Vapor pressure(E)/Saturated vapor pressure(Es) *100	   
	 *  E = 6.11*10^((7.5+Td)/(237.3++Td)
	 *  Es = 6.11*10^((7.5+T)/(237.3++T)
	 *  T = Current temperature
	 *  Td = Dew point temperature.Month wise dew point temperature is stored in property file
	 * @param stationName
	 * @param temperature
	 * @param Properties
	 * @return humidity
	 * @throws WeatherPredictorException 
	 * @throws NumberFormatException 
	 */

	public double fetchHumidity(String stationName, double temperature,Properties prop) throws NumberFormatException, WeatherPredictorException {
		
		
		String month = fetchMonth(stationName,prop);
		
		String key = stationName+"."+month+"."+ApplicationConstant.DEWPOINT_KEY;
		
		double dewpointTemperature = Double.valueOf(fetchValueFromProperty(prop,key));
		
		double exponentValue = (7.5*dewpointTemperature)/(237.3+dewpointTemperature) ;
		
		double vaporPressure = 6.11*Math.pow(10, exponentValue);
		
		double exponentValue2	 = (7.5*temperature)/(237.3+temperature) ;
		
		double saturatedVaporPressure = 6.11*Math.pow(10, exponentValue2);
		
		double humidity  = (vaporPressure/saturatedVaporPressure)*100 ;
		
		DecimalFormat df = new DecimalFormat("#.#");  
		humidity = Double.valueOf(df.format(humidity));
		return humidity;
	}
	
	  /**
	   * 
	   * This method will fetch value from property file based on key
	   * 
	   * @param prop
	   * @param key
	   * @return Value
	   * @throws WeatherPredictorException
	   */
	public String fetchValueFromProperty(Properties prop, String key)
			throws WeatherPredictorException {
		String value = prop.getProperty(key);
		if (value == null)
			throw new WeatherPredictorException(key);

		return value;

	}
	
	private static String formatDate(Date date, String format) {
		SimpleDateFormat sm = new SimpleDateFormat(format);
		String strDate = sm.format(date);
		return strDate;

	}


}
