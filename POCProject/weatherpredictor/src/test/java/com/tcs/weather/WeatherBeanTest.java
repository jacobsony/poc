package com.tcs.weather;

import org.junit.Test;

import com.tcs.weather.bean.WeatherBean;

import org.junit.Assert;

/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This the test class for WeatherBean
 *
 */
public class WeatherBeanTest {

	@Test
	public void testFormatData() {
		WeatherBean  weatherBean = new WeatherBean();
		weatherBean.setIataCode("COK");
		weatherBean.setLatitude(28.7041);
		weatherBean.setLongitude (77.1025);
		weatherBean.setAltitude(0.0);
		weatherBean.setLocalTime("2016-08-16T16:06:50Z");
		weatherBean.setCondition("Warm");
		weatherBean.setTemperature(25.7);
		weatherBean.setPressure(1013.25);
		weatherBean.setHumidity(85.1);
		
		String actuals = weatherBean.formatData();
		String expected = "COK|28.7041,77.1025,0.0|2016-08-16T16:06:50Z|Warm|25.7|1013.25|85.1\n";
		Assert.assertTrue(expected.equals(actuals));
		
		
		
	}

}
