package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.ios.IOSDriver;

public class PatientProfilePage extends iOSActions{	
	
	// Locator
	public By headerTextPatientProfilePage = By.xpath("//XCUIElementTypeStaticText[@name='Patient Profile']");
	public By headerTestPastReport = By.xpath("//XCUIElementTypeStaticText[@name='Past SOAP Report']");
	public By patientName = By.xpath("//XCUIElementTypeStaticText[@name=‘DOB: ‘]//parent::XCUIElementTypeOther//parent::XCUIElementTypeOther//preceding-sibling::XCUIElementTypeOther");
	public By iconBackPatientProfile = By.xpath("//XCUIElementTypeOther[@name='Patient Profile']/XCUIElementTypeOther");
	
	
	IOSDriver driver;

	public PatientProfilePage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// Verify whether Patient Profile page exists or not
    public void validatePatientProfilePage()
    {
    	if(IsElementPresent(driver, headerTextPatientProfilePage) && IsElementPresent(driver, headerTestPastReport))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Patient Profile page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Patient Profile page not exists or not opened or Past SOAP Report text not found. please check");
			Assert.fail();
		}
    }
	
	// To verify the patient name
	public boolean verifyPatientName(String name) {
		
		String n = name.split(" ")[0].trim();
		return IsElementPresent(driver,	By.xpath("//XCUIElementTypeStaticText[contains(@name, '"+n+"')]"));
	}
	
	// To verify that user navigated on patient search page on Back icon click
	public void clickBackIconPatientProfilePage() {
		click(driver, iconBackPatientProfile, "Click on Back Icon button on patient profile page");
	}
	
}
