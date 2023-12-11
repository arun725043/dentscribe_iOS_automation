package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.ios.IOSDriver;

public class HelpPage extends iOSActions{
	
	IOSDriver driver;
	
	public HelpPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public By headerTextHelpPageBy = By.xpath("//XCUIElementTypeStaticText[@name='Help']");
    public By textFaqsBy = By.xpath("//XCUIElementTypeStaticText[@name='FAQs']");
	public By backIconButton = By.xpath("(//XCUIElementTypeOther[@name='Help'])[4]/XCUIElementTypeOther");
	
	// Verify whether Help page exists or not
    public void validateHelpPage()
    {
    	if(IsElementPresent(driver, headerTextHelpPageBy) && IsElementPresent(driver, textFaqsBy))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Help page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either help page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// to click on back Icon button 
	public void performClickONBackIconButton() {
		click(driver, backIconButton, "Back Icon Button on Help page");
	}
			
	
			
}
