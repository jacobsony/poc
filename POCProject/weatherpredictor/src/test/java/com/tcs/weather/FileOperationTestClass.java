package com.tcs.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.tcs.weather.util.ApplicationConstant;
import com.tcs.weather.util.FileOperations;


/**
 * 
 * @author Jacob Sony,jacobsony.m@tcs.com
 * 
 * This the test class for FileOperations
 *
 */
public class FileOperationTestClass {

	@Test
	public void testReadPropertyFile() {
		
		FileOperations operations = new FileOperations();
		try {
			Properties prop = operations.readPropertyFile(ApplicationConstant.PROPERTY_FILE_NAME);
			Assert.assertNotNull(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFileExist () {
		
		FileOperations operations = new FileOperations();
		String data = "COK|28.7041,77.1025,0.0|2016-08-16T16:06:50Z|Warm|25.7|1013.25|85.1\n";
		try {
		operations.writeToFile(data,ApplicationConstant.WEATHER_DATA_FILE_NAME);
		File file = new File("weatherpredictor.txt");
		Assert.assertTrue( file.exists());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}
	
	@Test
	public void testWriteToFileWithData () {
		
		FileOperations operations = new FileOperations();
		String data = "COK|28.7041,77.1025,0.0|2016-08-16T16:06:50Z|Warm|25.7|1013.25|85.1";
		try {
			operations.writeToFile(data,ApplicationConstant.WEATHER_DATA_FILE_NAME);

			BufferedReader br = new BufferedReader(new FileReader("weatherpredictor.txt"));
			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				Assert.assertTrue(data.equals(currentLine));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
