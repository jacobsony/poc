package com.tcs.weather;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tcs.weather.util.ApplicationConstant;


/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This the test class for WeatherCalculations
 *
 */
public class WeatherCalculationsTest {
	
	
	WeatherCalculations  calculations =null;
	@Before
	public void initialize() throws IOException {
		calculations = new WeatherCalculations();
	}
	

	@Test
	public void testCalculateTemperatureBeforeNoon() {
		
		double maximumTemp = 28;
		double minimumTemp = 22;
		int hourOfDay = 3;
		double expectedValue = 23.5;
	 double actualValue = calculations.calculateTemperature(maximumTemp, minimumTemp, hourOfDay);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
	
		
		
	}
	
	@Test
	public void testCalculateTemperatureAfterNoon() {
		
		double maximumTemp = 28;
		double minimumTemp = 22;
		int hourOfDay = 13;
		double expectedValue = 27.5;
	 double actualValue = calculations.calculateTemperature(maximumTemp, minimumTemp, hourOfDay);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}
	
	@Test
	public void testCalculateTemperatureMidnight() {
		
		double maximumTemp = 28;
		double minimumTemp = 22;
		int hourOfDay = 0;
		double expectedValue = minimumTemp;
	 double actualValue = calculations.calculateTemperature(maximumTemp, minimumTemp, hourOfDay);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}
	
	@Test
	public void testCalculateTemperatureNoon() {
		
		double maximumTemp = 28;
		double minimumTemp = 22;
		int hourOfDay = 12;
		double expectedValue = maximumTemp;
	 double actualValue = calculations.calculateTemperature(maximumTemp, minimumTemp, hourOfDay);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}
	
	
	@Test
	public void testCalculatePressure() {
		
		double altitude = 500;
		double expectedValue = 953.25;
		double actualValue = calculations.calculateAtmopshericPressure(altitude);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}
	
	@Test
	public void testCalculatePressureSealevel() {
		
		double altitude = 0;
		double expectedValue = ApplicationConstant.SEALEVEL_PRESSURE;
		double actualValue = calculations.calculateAtmopshericPressure(altitude);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}

	
	@Test
	public void testCalculateHumidity() {
		
		double dewpointTemperature =22;
		double currentTemperature =32;
		double expectedValue = 55.6;
		double actualValue = calculations.calculateHumidity(dewpointTemperature, currentTemperature);
		Assert.assertEquals(expectedValue, actualValue, 0.0);
		
	}

	
}
