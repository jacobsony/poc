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

import com.tcs.weather.bean.WeatherBean;
import com.tcs.weather.exception.WeatherPredictorException;
import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.FileOperations;


/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This the test class for WeatherDataProcessor
 *
 */
public class WeatherDataProcessorTest {

	Properties prop =null;
	WeatherDataProcessor dataProcessor = null;
	
	@Before
	public void initialize() throws IOException {
		FileOperations operations = new FileOperations();
		prop = operations.readPropertyFile(ApplicationConstant.PROPERTY_FILE_NAME);
		dataProcessor = new WeatherDataProcessor();
		
	}
	
	
	
	@Test
	public void testFetchIataCode() {
		try {
			String actual = dataProcessor.fetchIataCode("kochi", prop);
		   Assert.assertEquals("COK", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void tesSetCoordinatesAltitude() {
		WeatherBean weatherBean = new WeatherBean();
		
		try {
			 dataProcessor.setCoordinates("johannesburg", weatherBean,prop);
			double actual = weatherBean.getAltitude();
			double expected = 1753;
		   Assert.assertEquals(expected, actual,0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void tesSetCoordinatesLongitude() {
		WeatherBean weatherBean = new WeatherBean();
		
		try {
			 dataProcessor.setCoordinates("johannesburg", weatherBean,prop);
			double actual = weatherBean.getLongitude();
			double expected = 28.0473;
		   Assert.assertEquals(expected, actual,0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void tesSetCoordinatesLatitude() {
		WeatherBean weatherBean = new WeatherBean();
		
		try {
			 dataProcessor.setCoordinates("johannesburg", weatherBean,prop); 
			double actual = weatherBean.getLatitude();
			double expected = -26.2041;
		   Assert.assertEquals(expected, actual,0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testfetchAtmosphericPressure() {
		
			double expected = 802.89;
			double actual = 0;
			try {
				actual = dataProcessor.fetchAtmosphericPressure("johannesburg", prop);
			} catch (WeatherPredictorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Assert.assertEquals(expected, actual,0.00);
		
	}
	
	
	@Test
	public void testfetchHumidity() {
		
			double expected = 34.9;
			double temperature = 12;
			double actual = 0;
			try {
				actual = dataProcessor.fetchHumidity("johannesburg", temperature ,prop);
			} catch (WeatherPredictorException e) {
				e.printStackTrace();
			}
			
			Assert.assertEquals(expected, actual,0.0);
		
	}
	
	
	@Test
	public void testfetchBaseStations() {
		
			
		String [] expected ={"Kochi","Tokyo","Sydney","Moscow","Paris","Johannesburg","Delhi","Dubai","NewYork","Santiago"};
		
		String [] actual = null;
			try {
				actual = dataProcessor.fetchBaseStations(prop);
			} catch (WeatherPredictorException e) {
				e.printStackTrace();
			}
			
			Assert.assertArrayEquals(expected, actual);
		
	}
	

	
	
	@Test
	public void testSnowCondition() {
		try {
			double temperature = 1;
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Snow", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testColdCondition() {
		try {
			double temperature = 6;
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Cold", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCoolCondition() {
		try {
			double temperature = 18;
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals("Cool", actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWarmCondition() {
		try {
			double temperature = 25;
			String expected ="Warm";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHotCondition() {
		try {
			double temperature = 32;
			String expected ="Hot";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHotterCondition() {
		try {
			double temperature = 39;
			String expected ="Hotter";
			TimeZone.setDefault(TimeZone.getTimeZone(prop.getProperty(("kochi."+ApplicationConstant.TIME_ZONE_KEY))));
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			if (calendar.get(Calendar.HOUR_OF_DAY) >= 11
					&& calendar.get(Calendar.HOUR_OF_DAY) <= 14) {
				expected ="Sunny";
			}
			String actual = dataProcessor.fetchCondition(temperature,"kochi", prop);
		   Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testFetchTemperatureCalculations() {
		try {
			double expected = 25.2;
			double actual = dataProcessor.fetchTemperature("kochi", prop);
			Assert.assertEquals(expected, actual,0.0);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
