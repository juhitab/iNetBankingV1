package com.iNetBankingV1.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManagerDashboard {

	WebDriver driver;
	@FindBy(xpath="//a[text()=\"Log out\"]")
	@CacheLookup
	WebElement logout;
	
	public ManagerDashboard(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	public void clickLogout() {
		logout.click();
	}
}
