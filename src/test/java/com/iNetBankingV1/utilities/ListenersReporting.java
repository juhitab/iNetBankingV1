package com.iNetBankingV1.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.iNetBankingV1.testCases.BaseClass;

public class ListenersReporting extends TestListenerAdapter{

	ExtentHtmlReporter htmlreporter; //The ExtentHtmlReporter creates a rich standalone HTML file. It allows several configuration options via the config() method.
	ExtentReports extentRep; //ExtentReports itself does not build any reports, but allows reporters to access information, which in turn build the said reports
	ExtentTest test; //defines a test
	
	//Invoked before running all the test methods belonging to the classes inside the <test> tag and calling all their Configuration methods. 
	public void onStart(ITestContext context) { 
		
		//generate a unique reportname by timestamp - to create separate reports to keep the history of reports
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //new Date() creates current date and time
		String reportName = "Test-Report-" + timeStamp + ".html";
		
		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + reportName); //location of report to be generated
		htmlreporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		
		htmlreporter.config().setDocumentTitle("iNetBanking Test Project");
		htmlreporter.config().setReportName("Automation Test Report");
		htmlreporter.config().setTheme(Theme.DARK);
		
		extentRep = new ExtentReports();
		extentRep.attachReporter(htmlreporter);
		
		extentRep.setSystemInfo("Test Environment", "QA"); //can set these from config.properties
		extentRep.setSystemInfo("Hostname", "localhost"); 
		
	}
	public void onTestSuccess(ITestResult tr) {
		test = extentRep.createTest(tr.getName());
//		test.log(Status.PASS, tr.getName()+" passed");
		test.pass(MarkupHelper.createLabel(tr.getName() + "passed", ExtentColor.GREEN));
	}
	public void onTestFailure(ITestResult tr) {
		
		test = extentRep.createTest(tr.getName());
		test.log(Status.FAIL,MarkupHelper.createLabel(tr.getName() + "failed", ExtentColor.RED)); //Logs an event with Status and custom Markup such as: •Code block •Label •Table
		test.fail(tr.getThrowable().getMessage()); //also shows AssertionError, or any exception
		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + ".png";
		
		File ssFile = new File(screenshotPath);
		if(ssFile.exists()) { //checks if screenshot present inside Screenshot folder -- screenshot should be taken by testcase when validation fails
			try {
				test.addScreenCaptureFromPath(screenshotPath, "Validation Failure Screenshot");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		else { // if failure caused due to other reason, not validation failure
			 Object testobject = tr.getInstance();
			 WebDriver webDriver = ((BaseClass) testobject).getDriver();//get the driver allocated for the current Test Class object
			 ((BaseClass) testobject).captureScreen(webDriver, tr.getName());
			 try {
				test.addScreenCaptureFromPath(screenshotPath, "Exception Failure Screenshot");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	
	}
	public void onTestSkipped(ITestResult tr) {
		test = extentRep.createTest(tr.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
	}
	public void onFinish(ITestContext context) {
		extentRep.flush(); //Writes test information from the started reporters to their output view •extent-html-formatter: flush output to HTML file
	}
}
