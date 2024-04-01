package com.iNetBankingV1.testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.iNetBankingV1.pageObjects.LoginPage;// have to import LoginPage class from pageObjects package since it's a different package

public class LoginTest extends BaseClass{

	
	@Test
	public void TC_LoginTest_001() throws Exception {
		LoginPage login = new LoginPage(driver);
		try {
			login.setUserId(username); //now getting username from BaseClass->getting from ReadConfig.java->reading from config.properties file
			login.setPassword(password);
			
			logger.info("Trying to log in...");
			login.clickLogin();
		
			String actualURL = driver.getCurrentUrl(); 
			
			if(actualURL.contains("Manager")) {
				Assert.assertTrue(true);
				logger.info("Logged in successfully");
			}
			else {
				
				captureScreen(driver, "TC_LoginTest_001");
				logger.assertLog(false, "Login failed"); //ERROR [main] (LoginTest.java:29) Login failed
				Assert.assertTrue(false, "Login failed");
				
			}
		}catch(Exception e) {
			logger.error(e.getMessage()); //exceptions like NoSuchElementException, StaleElementException etc
			throw e;//exception re thrown to fail the test case in report, and show execption message in report
		}
	}
	
}
