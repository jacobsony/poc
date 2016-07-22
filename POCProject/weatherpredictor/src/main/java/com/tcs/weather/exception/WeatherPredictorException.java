package com.tcs.weather.exception;


/**
 *@author Jacob Sony,jacobsony.m@tcs.com
 * Custom exception class for WeatherPredictor
 */
public class WeatherPredictorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	
	public WeatherPredictorException() {
	}

	public WeatherPredictorException(String message) {
		super(message);
	}

}
