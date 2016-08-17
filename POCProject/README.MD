	## ** Synopsis ** 
		WeatherPredictor predict current weather based on the last year data.
	    The out put of project will be logged in to weatherpredictor.txt file with weather details of 10 different base stations across the world.
	    The data contains following weather informations.
		1) IATA Code	
		2)Latitude
		3) Longitude
		4)Altitude
		5)LocalTime
		6)Condition	
		7)Temperature
		8)pressure
		9)humidity
		
	## **Prerequisites**		
		1)Jdk 1.7
		2)Maven 4.0
		
	## **Steps to run application**
				
	  This is a java application. Maven used to build application.
	  Build pom file with mvn clean install command.
	  This will package project in to executable jar weatherpredictor.jar
	  Run the jar with command java -jar weatherpredictor.jar
	  	
	
       ## **How WeatherPredictor works:**
 
     We have selected 10 base stations across world which have various weather conditions.We have collected
	 last year data of these stations and stored the values in property file.
	   
	   Historical data available in property files are
	   
	   1) Month wise minimum and maximum temperature (in Degree Celsius)
	   2) Month wise dew point.
	   
    ## Other informations stored in property files are	
    
	   1)IATA code
	   2)TimeZone
	   3)Latitude
	   4)Longitude
	   5)Altitude
	   6)List of base stations.
	   
	The application designed such that every hour it will execute produce report.
	
     ##  1)Temperature (in Degree Celsius) Calculation:
	
    Current month is calculated from the local time.And based on the current month 
	minimum and maximum temperature is fetched from property file;
	Assumption 1):The maximum temperature is at After noon 12 :00 PM and Minimum temperature is at 00:00
	Assumption 2): The temperature variation is uniformly distributed across every hour.
		
		1)Calculate difference between minimum and maximum temperature.
		2)Divide subtracted value by 12.(12 hours between noon and midnight) 
		3)This will give variation per hour.
		4)Get the hour difference from noon to current hour.
		5)Multiply this difference with variation per hour value.
		6)Subtracts this from maximum temperature.
		This will give current temperature.
		
		
    ## 2) Weather condition Calculation:
  
      Weather condition is calculated based on temperature and time.
	 
    	Temperature is less than 2  = Snow
		Temperature between 3 and 12 = Cold
		Temperature between 13 and 22 = Cool
		Temperature between 23 and 29 and Time is between 10 AM to 2 PM = Sunny
		Temperature between 23 and 29  = Warm
		Temperature between 30 and 38 and Time is between 10 AM to 2 PM = Sunny
		Temperature between 30 and 38 = Hot
		Temperature above 38 Time is between 10 AM to 2 PM = Sunny
		Temperature above 38 Time is between 10 AM to 2 PM = Hotter
		
       ## 3)Atmospheric pressure Calculation:
  
      Pressure is calculated based on information that average pressure variance is 12hpa/100 meters from sea level
	  
	  Sea level pressure is constant with value :1013.25
	  
	  Altitude fetched from property file ;
	  Divide altitude by 100;
	  Multiply the dividend with 12.
	  This will give atmospheric pressure hpa.
	  
      ## 4) Humidity Calculation:  
	
	 Relative humidity is represented percentage.
	   It is based on the equation below
	   
	   Relative Humidity = Vapor pressure(E)/Saturated vapor pressure(Es) *100	   
	   E = 6.11*10^((7.5+Td)/(237.3++Td)
	   Es = 6.11*10^((7.5+T)/(237.3++T)
	   T = Current temperature
	   Td = Dew point temperature.Month wise dew point temperature is stored in property file.
	   
	  
	  
	  
	 
	   
	   
	   
	   
	  
	   
	   
	   
	   
	  
	  
	  

     


	  
	
	   
	   
	
	   
  
