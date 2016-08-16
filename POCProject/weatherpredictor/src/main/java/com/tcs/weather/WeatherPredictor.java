package com.tcs.weather;

/**
 * 
 *  
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class will trigger WeatherDataGenerator 
 *
 */

public class WeatherPredictor {

	public static void main(String[] args) {
		
		System.out.println("Welcome to weather predictor..");
		WeatherDataGenerator dataGenerator = new WeatherDataGenerator(); 
		dataGenerator.generateData();
	}
}
