package com.tcs.weather;

import java.text.DecimalFormat;

import com.tcs.weather.util.ApplicationConstant;


/**
 * 
 *@author Jacob Sony,jacobsony.m@tcs.com
 *
 *This class handles the calculations for differenr weather parameters.
 *Most of the parameters are calculated with certail mathematical equations.
 *Details of each approach described above corresponding methods
 *
 *
 */
public class WeatherCalculations {
	
	
	/**
	 * 
	 * This method calculate the temperature based on minimum and maximum temperature in a month.
	 * Minimum and maximum temperature for each month stored in property file
	 * Assumption : Max temperature is at 12:00 noon,And minimum temperature at midnight 00:00
	 * And temperature variation across hour assumed to be uniform
	 * ie.Temperature increases from midnight to noon and reaches maximum temperature at noon.
	 * From noon temperature starts decreasing and reaches minimum temperature at midnight.
	 * 
	 * @param maxTemperature
	 * @param minTemperature
	 * @param hourOfDay
	 * @return calculatedTemperature
	 */
	
	public  double calculateTemperature(final double maxTemperature ,final double minTemperature,int hourOfDay )
	{
		
		double calculatedTemperature;
		// calculating per hour variation.
		double perHourVariation = ((maxTemperature - minTemperature) / 12);

		// Hour of day represented in 24 hour time format
		int hourDifference = 12 - hourOfDay;

		// hour difference >= 0 for hour before noon.
		if (hourDifference >= 0) {
			calculatedTemperature = maxTemperature - (hourDifference * perHourVariation);
		} else {
			calculatedTemperature = maxTemperature - (hourDifference * perHourVariation * -1);
		}

		DecimalFormat df = new DecimalFormat("#.#");
		calculatedTemperature = Double.valueOf(df.format(calculatedTemperature));

		return calculatedTemperature;
	}

	
	  /**
	   	* This method calculates current atmospheric pressure
	   	* Pressure calculated based on the Altitude,
	   	* Altitude  is the height in meters relative to sea level
	   	* Pressure varies with altitude
	   	* ie Average pressure variance is 12hpa/100 meters from sea level
	   	* Sea level pressure is a constant value
	   	* 
	   	* @param altitude
	   	* @return calculatedPressure
	   */
	
	public double calculateAtmopshericPressure(final double altitude) {

		double seaLevelPressure = ApplicationConstant.SEALEVEL_PRESSURE;
		double fraction = (altitude / 100) * 12;
		double calculatedPressure = seaLevelPressure - fraction;

		return calculatedPressure;

	}
	
	
	/**
	 * 
	 * The humidity is calculated based on the equation
	 * Relative Humidity = Vapor pressure(E)/Saturated vapor pressure(Es) *100	   
	 *  E = 6.11*10^((7.5+Td)/(237.3++Td)
	 *  Es = 6.11*10^((7.5+T)/(237.3++T)
	 *  T = Current temperature
	 *  Td = Dew point temperature.Month wise dew point temperature is stored in property file
	 * 
	 * 
	 * @param dewpointTemperature
	 * @param currentTemperature
	 * @return humidity
	 */
	
	public  double calculateHumidity (final double  dewpointTemperature,final double currentTemperature )
	{
		
		double exponentValue = (7.5 * dewpointTemperature) / (237.3 + dewpointTemperature);

		double vaporPressure = 6.11 * Math.pow(10, exponentValue); // Vapor pressure(E)

		double exponentValue2 = (7.5 * currentTemperature) / (237.3 + currentTemperature);

		double saturatedVaporPressure = 6.11 * Math.pow(10, exponentValue2); // Saturated vapor pressure(Es)

		double humidity = (vaporPressure / saturatedVaporPressure) * 100;

		DecimalFormat df = new DecimalFormat("#.#");
		humidity = Double.valueOf(df.format(humidity));
		
		return humidity;
		
	}
	
}
