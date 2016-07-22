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
 * This class handles file operation of weather predictor
 * 
 *
 */
public class FileOperations {
	
	public  void writeToFile(String data)  throws IOException
    {	
    		File file =new File("weatherpredictor.txt");
    		
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(data);
    	        bufferWritter.close();
    	    
	        System.out.println("Data logged successfuly");
    }

	
	public Properties readReadPropertyFile() throws IOException
    {	
		
		Properties prop = new Properties();
		String propertyFile = "basestations.properties";
		InputStream input = FileOperations.class.getClassLoader().getResourceAsStream(
				propertyFile);
		prop.load(input);
		
		return prop;
    	    
    }
	
	
}
