package com.iNetBankingV1.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
/*
 * The Properties class (java.util.Properties) represents a persistent set of properties.
 * The Properties can be saved to a stream or loaded from a stream.
 * Each key and its corresponding value in the property list is a string
 */
public class ReadConfig {

	Properties prop; //will read the properties file
	//constructor-(As soon as an object of this class is created, it loads/reads the config.properties file
	public ReadConfig() {
		File configfile = new File("./Configuration/config.properties");
		try {
			FileInputStream fis = new FileInputStream(configfile);
			prop = new Properties();
			prop.load(fis); //loads/Reads a property list (key and element pairs) from the input byte stream/FileInputStream. throws IOException, hence try catch
		}catch(Exception e) {
			System.out.println("Exception is : " + e.getMessage());
		}
	}
	
	//methods to return the value for each key in the property object loaded with key=value pairs from the config.properties file
	public String getApplicationURL() {
		return prop.getProperty("baseURL"); //Searches for the property with the specified key in this property list
	}
	public String getUsername() {
		return prop.getProperty("username"); 
	}
	public String getPassword() {
		return prop.getProperty("password"); 
	}
	public String getChromePath() {
		return prop.getProperty("chromePath"); 
	}
}
