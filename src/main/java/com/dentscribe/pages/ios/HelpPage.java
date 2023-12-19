package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonVariables;

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
	
	// FAQs question locators
	public static By question1By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question1 + "']");
	public static By question2By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question2 + "']");
	public static By question3By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question3 + "']");
	public static By question4By = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.question4 + "']");
	
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
			
	//verify whether all expected questions are available or not
	public void verifyFAQsQuestions()
	{
		scrollDownTillElementVisible(driver, question1By);
		IsElementPresent(driver, question1By, CommonVariables.question1);
		scrollDownTillElementVisible(driver, question2By);
		IsElementPresent(driver, question2By, CommonVariables.question2);
		scrollDownTillElementVisible(driver, question3By);
		IsElementPresent(driver, question3By, CommonVariables.question3);
		scrollDownTillElementVisible(driver, question4By);
		IsElementPresent(driver, question4By, CommonVariables.question4);
	}
			
}
