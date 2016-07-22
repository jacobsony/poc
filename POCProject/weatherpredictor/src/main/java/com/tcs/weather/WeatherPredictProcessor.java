package com.tcs.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import com.tcs.weather.bean.WeatherBean;
import com.tcs.weather.exception.WeatherPredictorException;
import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.FileOperations;

/**
 * 
 * @author Jacob Sony , jacobsony.m@tcs.com
 * 
 *         This class handles the main operation of weather predictor. This will
 *         fetch base station info from property file. Will pass this info to
 *         WeatherDataProcessor class for weather calculations. After fetching
 *         all information of all base station it pushes informations to
 *         FileOperation class for to log
 *
 */

public class WeatherPredictProcessor extends TimerTask {

	/**
	 * This method will read base station details from property file and push
	 * information to WeatherDataProcessor class for calculations. After
	 * fetching all information of all base station it pushes informations to
	 * FileOperation class for to log
	 * 
	 */
	@Override
	public void run() {
		try {

			FileOperations operations = new FileOperations();
			Properties prop = new Properties();
			prop = operations.readReadPropertyFile();
			
			
			WeatherDataProcessor dataProcessor = new WeatherDataProcessor();
			String baseStations = dataProcessor.fetchValueFromProperty(prop,
					ApplicationConstant.BASE_STATION_KEY);

			String[] baseStationsList = baseStations.split(","); // Base station
																	// names
																	// stored in
																	// property
																	// file
																	// seprated
																	// by comma

			List<WeatherBean> weatherDetails = new ArrayList<WeatherBean>();

			for (int count = 0; count < baseStationsList.length; count++)

			{
				System.out.println("Weather analysis for base station :"
						+ baseStationsList[count] + "  starts");
				WeatherBean weather = new WeatherBean();
				weather.setIata(dataProcessor.fetchIataCode(
						baseStationsList[count].toLowerCase(), prop));
				dataProcessor.setCordinates(
						baseStationsList[count].toLowerCase(), weather, prop);
				weather.setLocalTime(dataProcessor.fetchLocalTime(
						baseStationsList[count].toLowerCase(), prop));
				weather.setTemperature(dataProcessor.fetchTemperature(
						baseStationsList[count].toLowerCase(), prop));
				weather.setCondition(dataProcessor.fetchCondition(
						weather.getTemperature(),
						baseStationsList[count].toLowerCase(), prop));
				weather.setPressure(dataProcessor.fetchAtmospherePressure(
						baseStationsList[count].toLowerCase(), prop));
				weather.setHumidity(dataProcessor.fetchHumidity(
						baseStationsList[count].toLowerCase(),
						weather.getTemperature(), prop));

				weatherDetails.add(weather);
				System.out.println("Weather analysis for base station :"
						+ baseStationsList[count] + " over");

			}

			String formatedData = populateFormatedData(weatherDetails);
			operations.writeToFile(formatedData);

		}

		catch (WeatherPredictorException weatherExcp) {
			System.out
					.println("WeatherPredictorException : Reason missiong key : "
							+ weatherExcp.getMessage());
		}

		catch (IOException ioExcp) {
			System.out.println("IOException occured");
		}

		catch (Exception excp) {
			System.out.println("Exception occured"
					+ excp.getClass().getCanonicalName());

		}

	}

	/**
	 * 
	 * This method format the data in required format.
	 * 
	 * @param List
	 *            <WeatherBean>
	 * @return formatedString
	 */
	private String populateFormatedData(List<WeatherBean> weatherDetails) {
		StringBuilder builder = new StringBuilder();
		for (WeatherBean weather : weatherDetails) {
			builder.append(weather.getIata());
			builder.append("|");
			builder.append(weather.getLatitude());
			builder.append(",");
			builder.append(weather.getLongitude());
			builder.append(",");
			builder.append(weather.getAltitude());
			builder.append("|");
			builder.append(weather.getLocalTime());
			builder.append("|");
			builder.append(weather.getCondition());
			builder.append("|");
			builder.append(weather.getTemperature());
			builder.append("|");
			builder.append(weather.getPressure());
			builder.append("|");
			builder.append(weather.getHumidity());
			builder.append("\n");
		}

		return builder.toString();

	}

}
