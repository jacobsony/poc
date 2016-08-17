package com.tcs.weather.bean;

/**
 * 
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This is the bean class used to hold the values of weather parameters
 *
 */

public class WeatherBean {
	
	
	private String iataCode;
	
	private double latitude;
	
	private double longitude;
	
	private double altitude;
	
	private String localTime;
	
	private String condition;
	
	private double temperature;
	
	private double pressure;
	
	private double humidity;
	
	
	
	/**
	 * @return the iataCode
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * @param iataCode the iataCode to set
	 */
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}



	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}



	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}





	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}





	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}





	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}





	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}





	/**
	 * @return the localTime
	 */
	public String getLocalTime() {
		return localTime;
	}





	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}





	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}





	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}





	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}





	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}





	/**
	 * @return the pressure
	 */
	public double getPressure() {
		return pressure;
	}





	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}





	/**
	 * @return the humidity
	 */
	public double getHumidity() {
		return humidity;
	}





	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}



  /**
   * 
   * This method format the the weather information to required format.
   * @return formated string
   */

	public String formatData() {
		StringBuilder builder = new StringBuilder();
	
			builder.append(this.iataCode);
			builder.append("|");
			builder.append(this.latitude);
			builder.append(",");
			builder.append(this.longitude);
			builder.append(",");
			builder.append(this.altitude);
			builder.append("|");
			builder.append(this.localTime);
			builder.append("|");
			builder.append(this.condition);
			builder.append("|");
			builder.append(this.temperature);
			builder.append("|");
			builder.append(this.pressure);
			builder.append("|");
			builder.append(this.humidity);
			builder.append("\n");
		

		return builder.toString();

	}
	

}
