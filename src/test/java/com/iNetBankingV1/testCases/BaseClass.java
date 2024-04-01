package com.iNetBankingV1.testCases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.iNetBankingV1.utilities.ReadConfig;

public class BaseClass { //will be extended by all test case classes--will get all the fields and methods defined in this class

	//these variables can be accessed by every Test class that extends BaseClass
	public WebDriver driver; 
	public Logger logger;
	
	ReadConfig readconfig = new ReadConfig();
	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	
	@BeforeClass
	@Parameters({"browser"})
	public void setup(@Optional("chrome") String browser) {
		
		logger = Logger.getLogger("eBanking"); //gets the logger with the given name, if it doesn't exist, creates new
		PropertyConfigurator.configure("log4j.properties"); //Read configuration options from the log4j.properties file
		   
		if(browser.equalsIgnoreCase("chrome")) {
//			System.setProperty("webdriver.chrome.driver", readconfig.getChromePath()); -if lower than Selenium 4
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
			driver = new FirefoxDriver();
		else if(browser.equalsIgnoreCase("ie"))
			driver = new InternetExplorerDriver();
		else if(browser.equalsIgnoreCase("edge"))
			driver = new EdgeDriver();
		
	}
	@Test(priority=-1)	
	public void openUrl(){
 
		logger.info("Opening site...");
		driver.get(baseURL);
		
		String actualURL = driver.getCurrentUrl(); 
		if(baseURL.equals(actualURL)) {
			Assert.assertTrue(true);
			logger.info("Site opened");
		}
		else {
			captureScreen(driver, "openUrl");
			logger.assertLog(false, "Site could not open"); //If assertion parameter is false, then logs msg as an error statement--ERROR [main] (BaseClass.java:39) Site could not open
			Assert.assertTrue(false, "Site could not open");//test case failed, below lines will not execute			
		}

	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		logger.info("Browser closed");
	}
	
	public void captureScreen(WebDriver driver, String testcaseName) {
		
		
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src=ts.getScreenshotAs(OutputType.FILE);
			String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + testcaseName + ".png";
			File dest = new File(screenshotPath);
			FileUtils.copyFile(src, dest);
			logger.info("Screenshot taken");
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		
	}
	public WebDriver getDriver() {
		return driver;
	}
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		}catch(NoAlertPresentException e) {
			return false;
		}
	}
}
