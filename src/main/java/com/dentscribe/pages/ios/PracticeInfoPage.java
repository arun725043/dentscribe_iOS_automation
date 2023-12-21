package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class PracticeInfoPage extends iOSActions{
	
	IOSDriver driver;

	ActionsUtiils actionsUtiils;
	public PracticeInfoPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________________________________practice info locators_______________________
	public By headerTextPracticeInfoPage = By.xpath("//XCUIElementTypeStaticText[@name='Practice Info ']");
	public By inputName = By.xpath("//XCUIElementTypeTextField[@name='name-input']");
	public By inputAddress1 = By.xpath("//XCUIElementTypeTextField[@name='address-line-1-input']"); 				
	public By inputAddress2 = By.xpath("//XCUIElementTypeTextField[@name='address-line-2-input']");     	
	public By inputCity = By.xpath("//XCUIElementTypeTextField[@name='buildings-input']");    	
	public By dropdownState = By.xpath("(//XCUIElementTypeOther[@name='State'])[2]");  	
	public By inputZip = By.xpath("//XCUIElementTypeTextField[@name='zipcode-input']");   	
	public By dropdownCountry = By.xpath("(//XCUIElementTypeOther[@name='Country'])[2]"); 	
	public By officeNoCountryCode = By.xpath("//XCUIElementTypeOther[@name='United States Telephone input']");
	public By inputOfficeMobile = By.xpath("//XCUIElementTypeTextField[@name='phone-input']");
	public By textIndia = By.xpath("");
	public By inputOfficeId = By.xpath("");
	public By alabama = By.xpath("(//XCUIElementTypeOther[@name='Alabama'])[2]");
	public By country = By.xpath("(//XCUIElementTypeOther[@name='United States'])[3]");
	public By officePhoneNumLabel = By.xpath("//XCUIElementTypeStaticText[@name='Office Phone Number']");
	public By continueButton = AppiumBy.accessibilityId("Continue");
	
	public By nameLabelBy = By.xpath("//XCUIElementTypeStaticText[@name='Name']");
	public By errorMsgNameRequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='Name is required.']");
	public By errorMsgAddressLine1RequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='Address line 1 is required.']");
	public By errorMsgCityRequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='City is required.']");
	public By errorMsgStateRequiredBy = By.xpath("////XCUIElementTypeOther[@name='State is required.']");
	public By errorMsgZipCodeRequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='Zip code is required.']");
	public By errorMsgCountryRequiredBy = By.xpath("//XCUIElementTypeOther[@name='Country is required.']");
	public By errorMsgOfficePhoneNumberRequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='Phone number is required.']");
	
	//________verify whether Practice Info page exists or not_________ 
	public void validatePracticeInfoPage()
	{
		if(IsElementPresent(driver, headerTextPracticeInfoPage))
		{
			ExtentManager.logInfoDetails("<b>User is now on Practice Info page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either Practice Info page not exists or opened or verifying header is not available or as expected. please check");
			Assert.fail();
		}
	}
		
	// ______________fill practice info_______________
	public void fillPracticeInfo(String state, String country) throws InterruptedException {
		sendKeys(driver, inputName, CommonMethods.generateRandomFirstName(), "Name");
		hideKeyboard();
		sendKeys(driver, inputAddress1, "AddressOne" + CommonMethods.GenerateRandomNumber(5), "Address line 1");
		hideKeyboard();
		sendKeys(driver, inputAddress2, "AddressTwo" + CommonMethods.GenerateRandomNumber(5), "Address line 2");
		hideKeyboard();
		performScroll(driver);
		sendKeys(driver, inputCity, CommonMethods.getAlphaNumericString(5), "City");
		click(driver, dropdownState, "State Drop Down");
		click(driver, alabama, "Alabama");
		sendKeys(driver, inputZip, CommonMethods.GenerateRandomNumber(6), "zip");
		click(driver, dropdownCountry, "Country Dropdown");
		click(driver, this.country, "Country");
		sendKeys(driver, officeNoCountryCode, "9", "Office Phone Number Country Code");
		sendKeys(driver, inputOfficeMobile, readData("testData", "mobile"), "Office Phone Number");
		click(driver, officePhoneNumLabel, "Office Phone Number Label");
	}
	
	public void clickContinueButtonPracticeInfo()
	{
		click(driver, continueButton, "Continue button on Practice Info page");
	}
	
	// to verify the mandatory fields on sign up page
	public void verifyMandatoryFields() {

		scrollDownTillElementVisible(driver, CommonLocators.continueButtonBy);
		click(driver, CommonLocators.continueButtonBy, "Continue on Practice Info page");
		
		scrollUpTillElementVisible(driver, headerTextPracticeInfoPage);
		verifyText(getText(driver, errorMsgNameRequiredBy), "Name is required.", "Name validation message");
		verifyText(getText(driver, errorMsgAddressLine1RequiredBy), "Address line 1 is required.", "Address Line 1 validation message");
		verifyText(getText(driver, errorMsgCityRequiredBy), "City is required.", "City validation message");
		
		scrollDownTillElementVisible(driver, CommonLocators.continueButtonBy);
		verifyText(getText(driver, errorMsgStateRequiredBy), "State is required.", "State validation message");
		verifyText(getText(driver, errorMsgZipCodeRequiredBy), "Zip code is required.", "Zip Code validation message");
		verifyText(getText(driver, errorMsgCountryRequiredBy), "Country is required.", "Country validation message");
		verifyText(getText(driver, errorMsgOfficePhoneNumberRequiredBy), "Phone number is required.", "Office Phone Number validation message");
	}
	
}
