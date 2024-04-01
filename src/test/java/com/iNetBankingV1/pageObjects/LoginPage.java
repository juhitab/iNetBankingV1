package com.iNetBankingV1.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
 * User ID :	mngr561735, Password :	mEhEnEn -- coming from testng.xml file <parameters> or config.properties
 */
public class LoginPage {

	@FindBy(name="uid")
	@CacheLookup
	WebElement username;
	
	@FindBy(name="password")
	@CacheLookup
	WebElement password;
	
	@FindBy(name="btnLogin")
	@CacheLookup
	WebElement loginBtn;
	
	@FindBy(name="btnReset")
	@CacheLookup
	WebElement ResetBtn; // may add later only if needed by any testcase
	

	WebDriver driver;
	
	//constructor
	public LoginPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this); //used to initialize the web elements declared above in this page object 
	}
	
	//page operations
	public void setUserId(String usrname) {
		username.sendKeys(usrname);
	}
	public void setPassword(String pwd) {
		password.sendKeys(pwd);
	}
	public void clickLogin() {
		loginBtn.click();
	}
}
