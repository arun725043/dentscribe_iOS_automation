package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class AddPaymentMethodPage extends iOSActions {

	IOSDriver driver;
	
	public AddPaymentMethodPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	//Locator 
	public By headerAddPaymentPage = By.xpath("//XCUIElementTypeStaticText[@name='Add Payment Method']");
	public By headerCardDetails = By.xpath("//XCUIElementTypeStaticText[@name='Card Details ']");
	public By inputCardHolderName = By.xpath("//XCUIElementTypeTextField[@name='cardholder-name-input']");
	public By inputCardNumber = AppiumBy.accessibilityId("Card number");
	public By inputExpiryDate = AppiumBy.accessibilityId("expiration date");
	public By inputCVC = AppiumBy.accessibilityId("CVC");
	public By inputZipCode = AppiumBy.accessibilityId("Postal Code");
	public By inputAddressLine2 = By.xpath("//XCUIElementTypeTextField[@name='address-line-2-input']");
	public By continueButton = AppiumBy.accessibilityId("billing-continue-button");
	public By cardInfoLabel = By.xpath("//XCUIElementTypeStaticText[@name='Card Info*']"); 
	public By textCardHolderNameIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='Cardholder name is required.']");
	public By cardHolderLabel = By.xpath("//XCUIElementTypeStaticText[@name='Cardholder Name*']");
	public By carddetailsnotcomplete = AppiumBy.accessibilityId("Card details not complete");
	public By cityLabel = By.xpath("//XCUIElementTypeStaticText[@name='City*']");
	public By textInvalidCard = AppiumBy.accessibilityId("Your card number is invalid.");
	public By iconBackBy = By.xpath("(//XCUIElementTypeOther[@name='Add Payment Method'])[4]/XCUIElementTypeOther");
			
	//________verify whether Add Payment Method page exists or not_________ 
	public void validateAddPaymentMethodPage()
	{
		if(IsElementPresent(driver, headerAddPaymentPage) && IsElementPresent(driver, headerCardDetails))
		{
			ExtentManager.logInfoDetails("<b>User is now on Add Payment Method page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either Add Payment Method page not exists or opened or verifying header is not available or as expected. please check");
			Assert.fail();
		}
	}
	
	// To fill the card details
	public void fillCardDetails(String[] deatils) {
		sendKeys(driver, inputCardHolderName, deatils[0], "Card Holder Name");
		hideKeyboard();
		sendKeys(driver, inputCardNumber, deatils[1], "Card Number");
		hideNumericKeys(cardInfoLabel, driver);
		sendKeys(driver, inputExpiryDate, deatils[2], "Expiry Date");
		hideNumericKeys(cardInfoLabel, driver);
		sendKeys(driver, inputCVC, deatils[3], "CVC");
		sendKeys(driver, inputZipCode, deatils[4], "Zip");
		hideNumericKeys(cardInfoLabel, driver);
	}
	
	//to fill the billing details
	public void fillBillingDetails(String address2) {
		performScroll(driver);
		sendKeys(driver, inputAddressLine2, address2, "Address Line 2");
		hideKeyboard();
	}
	
	// To perform click on continue button
	public void clickContinueButtonPaymentMethodPage() {
		click(driver, continueButton, "Continue on add payment method page");
	}
	
	// To verify the 'card holder name is required'
	public void verifyCardHolderNameIsRequired() {	
		
	  performScroll(driver);
	  click(driver, continueButton, "Continue on add payment method page");
	  scrollUpTillElementVisible(driver, cardHolderLabel);
	  //verifyText(driver.findElement(textCardHolderNameIsRequired).getText(), "Cardholder name is required.", "Cardholder name is required. on add payment method page");
		
	}

	public void verifyCarddetailsnotcompletedAlert() {
		
		sendKeys(driver, inputCardHolderName, "Tesing", "Cardholder name");
		hideKeyboard();
		performScroll(driver);
		click(driver, continueButton, "Continue on add payment method page");
		verifyText(driver.findElement(carddetailsnotcomplete).getAttribute("name"), "Card details not complete", "Card details not complete");
	}

	public void verifyInvalidCard() {
		
		scrollUpTillElementVisible(driver, cardHolderLabel);
		sendKeys(driver, inputCardNumber, "112233445566", "Card Number");
		hideNumericKeys(cardInfoLabel, driver);
		verifyText(getText(driver, textInvalidCard), "Your card number is invalid.", "Your card number is invalid.");
	}
	
	// To perform click on continue button
	public void clickBackIconPaymentMethodPage() {
		click(driver, iconBackBy, "Back icon on add payment method page");
	}
}
