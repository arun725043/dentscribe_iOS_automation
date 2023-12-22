package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.ios.IOSDriver;

public class FeedbackPage extends iOSActions{
	
	IOSDriver driver;
	
	public FeedbackPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// Feedback page locators
	public By headerTextFeedbackPageBy = By.xpath("//XCUIElementTypeStaticText[@name='Feedback']");
	public By iconBackFeedbackPageBy = By.xpath("(//XCUIElementTypeOther[@name=' New Feedback'])[1]/XCUIElementTypeOther[1]");
	public By iconNewFeedbackBy = By.xpath("(//XCUIElementTypeOther[@name=' New Feedback'])[2]");
	public By msgNoFeedbacksBy = By.xpath("//XCUIElementTypeStaticText[@name='No feedback found.']");
	
	// New feedback page locators
	public By headerTextNewFeedbackPageBy = By.xpath("//XCUIElementTypeStaticText[@name='New Feedback']");
	public By iconBackNewFeedbackPageBy = By.xpath("(//XCUIElementTypeOther[@name='New Feedback'])[3]/XCUIElementTypeOther/XCUIElementTypeOther[1]");
	public By buttonSubmitNewFeedbackPageBy = By.xpath("//XCUIElementTypeOther[@name='feedback-submit-button']");
	
	public By labelTitleBy = By.xpath("//XCUIElementTypeStaticText[@name='Title*']");
	public By inputTitleBy = By.xpath("//XCUIElementTypeTextField[@value='Title']");
	public By labelDescriptionBy = By.xpath("//XCUIElementTypeStaticText[@name='Description*']");
	public By inputDescriptionBy = By.xpath("//XCUIElementTypeTextView[@name='Add description here..']");
	public By linkAddAttachmentBy = By.xpath("//XCUIElementTypeOther[@name='Add attachment ( .jpg, .jpeg, .png, .gif, bmp)']");
	public By successMessageBy = By.xpath("//XCUIElementTypeOther[@name='Feedback has been successfully created']");
	
	// Verify whether Feedback page exists or not
    public void validateFeedbackPage()
    {
    	if(IsElementPresent(driver, headerTextFeedbackPageBy) && IsElementPresent(driver, iconNewFeedbackBy))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Feedback page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Feedback page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
    // Verify whether New Feedback page exists or not
    public void validateNewFeedbackPage()
    {
    	if(IsElementPresent(driver, headerTextNewFeedbackPageBy) && IsElementPresent(driver, linkAddAttachmentBy))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on New Feedback page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either New Feedback page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// to click on back icon 
	public void clickBackIconFeedbackPage() {
		click(driver, iconBackFeedbackPageBy, "Back icon on feedback page");
	}
	
	// to click on New Feedback icon 
	public void clickNewFeedbackIcon() {
		click(driver, iconNewFeedbackBy, "New Feedback icon on feedback page");
	}
	
	// to click on back icon 
	public void clickBackIconNewFeedbackPage() {
		click(driver, iconBackNewFeedbackPageBy, "Back icon on new feedback page");
	}
	
	// to click on submit button 
	public void clickSubmitButtonNewFeedbackPage() {
		click(driver, labelTitleBy);
		click(driver, buttonSubmitNewFeedbackPageBy, "Submit button on new feedback page");
	}
	
	// Fill feedback form
	public void fillFeedbackFormAndSubmit(String title, String description) throws InterruptedException
	{
		sendKeys(driver, inputTitleBy, title, "Title field textbox");
		sendKeys(driver, inputDescriptionBy, description, "Description field textbox");
		Thread.sleep(10000);
		clickSubmitButtonNewFeedbackPage();
	}
	
	// verify submitted feedback
	public void verifyNewlyAddedFeedback(String title, String description)
	{  
		String titleTextString = "(//XCUIElementTypeStaticText[@name='" + title +"'])";
		String descriptionTextString = "(//XCUIElementTypeStaticText[@name='" + description +"'])";
		if (IsElementPresent(driver, By.xpath(titleTextString)) && IsElementPresent(driver, By.xpath(descriptionTextString)))
		{
			ExtentManager.logInfoDetails("Both title and description found successfully in feedback list i.e. Feedback submitted successfully as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either title or description text not found in feedback list. please check");
			Assert.fail();
		}
	}
	
}
