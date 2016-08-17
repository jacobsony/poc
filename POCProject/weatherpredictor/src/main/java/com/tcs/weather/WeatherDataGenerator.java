package com.tcs.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tcs.weather.bean.WeatherBean;
import com.tcs.weather.exception.WeatherPredictorException;
import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.FileOperations;

	/**
	 * 
	 * @author Jacob Sony , jacobsony.m@tcs.com
	 * 
	 *  This class handles the main operation of weather predictor. This will
	 *  fetch base station info from property file. Will pass this info to
	 *  WeatherDataProcessor class for weather calculations. After fetching
	 *  all information of all base station it pushes informations to
	 *  FileOperation class for to log
	 *
	 */

public class WeatherDataGenerator  {

	/**
	 * This method will read base station details from property file and push
	 * information to WeatherDataProcessor class for calculations. After
	 * fetching all information of all base station it pushes informations to
	 * FileOperation class for to log
	 * 
	 */
	
	public void generateData() {
		try {

			FileOperations operations = new FileOperations();
			
			/* Initialize  property  */
			Properties	properties = operations.readPropertyFile(ApplicationConstant.PROPERTY_FILE_NAME);

			WeatherDataProcessor dataProcessor = new WeatherDataProcessor();
				// fetching base station details stored in property file
			String[] arrayOfBaseStation = dataProcessor.fetchBaseStations(properties);

			List<WeatherBean> weatherDetails = new ArrayList<WeatherBean>();
			
			for (int count = 0; count < arrayOfBaseStation.length; count++)

			{
				System.out.println("Weather analysis for base station :" + arrayOfBaseStation[count] + "  starts");
				
				WeatherBean weather = new WeatherBean();
				weather.setIataCode(dataProcessor.fetchIataCode(arrayOfBaseStation[count].toLowerCase(), properties));
				
				dataProcessor.setCoordinates(arrayOfBaseStation[count].toLowerCase(), weather, properties);
				weather.setLocalTime(dataProcessor.fetchLocalTime(arrayOfBaseStation[count].toLowerCase(), properties));
				
				weather.setTemperature(dataProcessor.fetchTemperature(arrayOfBaseStation[count].toLowerCase(), properties));
				weather.setCondition(dataProcessor.fetchCondition(weather.getTemperature(),
						arrayOfBaseStation[count].toLowerCase(), properties));
				weather.setPressure(dataProcessor.fetchAtmosphericPressure(arrayOfBaseStation[count].toLowerCase(), properties));
				weather.setHumidity(dataProcessor.fetchHumidity(arrayOfBaseStation[count].toLowerCase(),
						weather.getTemperature(), properties));

				weatherDetails.add(weather);
				System.out.println("Weather analysis for basestation :" + arrayOfBaseStation[count] + " over");

			}

			String formatedData = populateFormatedData(weatherDetails);
			
			operations.writeToFile(formatedData,ApplicationConstant.WEATHER_DATA_FILE_NAME);

		}

		catch (WeatherPredictorException weatherExcp) {
			System.out.println("WeatherPredictorException :  " + weatherExcp.getMessage());
			weatherExcp.printStackTrace();
		}
		catch (IOException ioExcp) {
			System.out.println("IOException occured");
			ioExcp.printStackTrace();
		}		
		catch (Exception excp) {
			System.out.println("Exception occured" + excp.getClass().getCanonicalName());
			excp.printStackTrace();

		}

	}

	/**
	 * 
	 * This method conver weather informations to required format.
	 * 
	 * @param List      <WeatherBean>
	 * @return formatedString
	 */
	private String populateFormatedData(List<WeatherBean> weatherDetails) {
		StringBuilder builder = new StringBuilder();
		for (WeatherBean weather : weatherDetails) {
			builder.append(weather.formatData());
		}
		return builder.toString();

	}
	
}
