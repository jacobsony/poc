package com.tcs.weather;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.FileOperations;

public class WeatherDataProcessorTest {

	Properties prop =null;
	@Before
	public void initialize() throws IOException {
		FileOperations operations = new FileOperations();
		prop = operations.readReadPropertyFile();
		
	}
	
	
	@Test
	public void testAtmospherePressureCalculations() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		
		try {
			float actual = wdp.fetchAtmospherePressure("kochi", prop);
			float fraction = (0/100)*12;
			float actualPressure = ApplicationConstant.SEALEVEL_PRESSURE_KEY - fraction;
			Assert.assertEquals(actualPressure, actual,0.00f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testFetchTemperatureCalculations() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float actual = wdp.fetchTemperature("kochi", prop);
			Assert.assertEquals(1.3f, actual,0.0f);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	@Test
	public void testFetchIata() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			String actual = wdp.fetchIataCode("kochi", prop);
		   Assert.assertEquals("COK", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSnowCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 1;
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Snow", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testColdCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 6;
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Cold", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCoolCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 18;
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Cool", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWarmCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 25;
			String expected ="Warm";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHotCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 32;
			String expected ="Hot";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHotterCondition() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		try {
			float temperature = 39;
			String expected ="Hotter";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = wdp.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchValueFromPropertt() {
		WeatherDataProcessor wdp = new WeatherDataProcessor();
		
		String key = "kochi.longitude";
		float actual;
		try {
			actual = Float.valueOf(wdp.fetchValueFromProperty(prop, key));
			float expected =  77.1025f;
			Assert.assertEquals(expected, actual,0.0000f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
}
