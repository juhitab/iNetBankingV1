package com.iNetBankingV1.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.iNetBankingV1.pageObjects.LoginPage;
import com.iNetBankingV1.pageObjects.ManagerDashboard;
import com.iNetBankingV1.utilities.XLUtils;

public class DataDrivenLoginTest extends BaseClass{
	
	@Test(dataProvider = "LoginData")
	public void TC_DDTLogin(String uname, String passwd) throws Exception {
		try {
			LoginPage loginPg = new LoginPage(driver);
			System.out.println(uname + "    " + passwd);
			loginPg.setUserId(uname);
			loginPg.setPassword(passwd);
			loginPg.clickLogin();
			
			//for invalid data, login fails, shows alert--User is not valid
			if(isAlertPresent()) {
				//test case failed
				driver.switchTo().alert().accept();
				driver.switchTo().defaultContent(); //driver focused on top window/first frame. Selects either the first frame on the page, or the main document when a page contains iframes. 
				
				captureScreen(driver, "TC_DDTLogin");
				logger.warn("Login failed-Invalid credentials");
				Assert.assertTrue(false, "Login failed-Invalid credentials");
			}
			else {
		//alert not present, credentials are valid--check if login successful as before, then logout to login with new data- since browser will be closed @AfterClass
				
				String actualURL = driver.getCurrentUrl(); 
				if(actualURL.contains("Manager")) {
					Assert.assertTrue(true);
					logger.info(uname + "Logged in successfully");
					ManagerDashboard mngr = new ManagerDashboard(driver);
					mngr.clickLogout();
					logger.info(uname + " Logged out successfully");
					
					//Alert comes - You Have Successfully Logged Out!!
					if(isAlertPresent()) {
						driver.switchTo().alert().accept(); //back to login page
						driver.switchTo().defaultContent();
					}
					
				}
				else {
					
					captureScreen(driver, "TC_DDTLogin");
					logger.assertLog(false, "Login failed");
					Assert.assertTrue(false, "Login failed");
					
				}
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage()); //exceptions like NoSuchElementException, StaleElementException etc
			throw e;//exception re thrown to fail the test case in report, and show execption message in report
		}
	}
		
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
		String path = System.getProperty("user.dir")+ "/src/test/java/com/iNetBankingV1/testData/loginData.xlsx";
		XLUtils xl = new XLUtils(path, "UsersCreds");
		
		int rowcount = xl.getRowCount();
		int colcount = xl.getCellCount(1);
		String[][] data = new String[rowcount][colcount];
		
		for(int i=1;i<=rowcount;i++) {
			//int colcount = xl.getCellCount(i);
			for(int j=0;j<colcount;j++) {
				data[i-1][j] = xl.getCellData(i, j);
			}
		}
		xl.close();
		return data;
	}
	
}
