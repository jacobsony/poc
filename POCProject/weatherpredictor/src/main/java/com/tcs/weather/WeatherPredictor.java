package com.tcs.weather;

import java.util.Date;
import java.util.Timer;

/**
 * 
 *  
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class will trigger WeatherPredictProcessor every 1 hour
 *
 */

public class WeatherPredictor {

	public static void main(String[] args) {
		
		System.out.println("Welcome to weather predictor..");
		Timer time = new Timer();
		long hourInMilliSecond = 3600000;
		WeatherPredictProcessor processor = new WeatherPredictProcessor(); 
		time.scheduleAtFixedRate(processor, new Date(), hourInMilliSecond);
		
	}
}
