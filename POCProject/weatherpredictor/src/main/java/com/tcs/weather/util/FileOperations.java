package com.tcs.weather.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This class handles file operation of application
 * 
 * 
 *
 */
public class FileOperations {
	
	
	/**
	 * This methods writes data in to file with file name mentioned as parameter
	 * @param data
	 * @param fileName
	 * @throws IOException
	 */
	public void writeToFile(final String data, final String fileName) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fileWritter = new FileWriter(file.getName());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(data);
		bufferWritter.close();

		System.out.println("Data logged successfuly");
	}

	
	/**
	 * This method loads properties from file with name mentioned in parameters
	 * @param propertyFileName
	 * @return Properties
	 * @throws IOException
	 */
	public Properties readPropertyFile(final String propertyFileName) throws IOException {

		Properties prop = new Properties();
		InputStream input = FileOperations.class.getClassLoader().getResourceAsStream(propertyFileName);
		prop.load(input);

		return prop;

	}
	
	
}
