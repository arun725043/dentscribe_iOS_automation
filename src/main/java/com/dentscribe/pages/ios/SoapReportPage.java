package com.dentscribe.pages.ios;

import java.time.Duration;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;
import com.dentscribe.common.CommonVariables;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class SoapReportPage extends iOSActions{
	
	IOSDriver driver;
	ActionsUtiils gestures;
	
	public SoapReportPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

    // Soap report locators
	public By headerTextSoapReportPageBy = By.xpath("//XCUIElementTypeStaticText[@name='SOAP Report']");
	public By noteMsgSoapReportPageBy = By.xpath("//XCUIElementTypeStaticText[@name=' Review and sign the report.']");
	
	public By iconBackSoapReportPage = By.xpath("(//XCUIElementTypeOther[@name='SOAP Report'])[4]/XCUIElementTypeOther");
	public By iconEditSoapReportPage = By.xpath("(//XCUIElementTypeOther[@name='report_edit_button'])[2]");
	public By iconSaveSoapReportPage = By.xpath("//XCUIElementTypeStaticText[@name='report_save_button']");
	
	public By buttonAdoptSignature = AppiumBy.accessibilityId("adopt-signature-button");
	public By buttonSubmitAdoptSignaturePopup = AppiumBy.accessibilityId("signature-text-submit-button");
	public By reportSubmitButton = AppiumBy.accessibilityId("report-submit-button");
	public By textCreateYourSignature = By.xpath("");
	public By textInitials = By.xpath("");

	public By textSoapReport = By.xpath("//XCUIElementTypeStaticText[@name='SOAP Report']");
	public By labelSignature = By.xpath("//XCUIElementTypeStaticText[@name='Signature']");
	public By labelLicense = By.xpath("//XCUIElementTypeStaticText[@name='License']");
	public By signatureArea = By.xpath("");
	public By buttonRedraw = By.xpath("");
	public By inputName = By.xpath("//XCUIElementTypeTextField[@name='edit-name-input']");
	public By inputTitle = By.xpath("//XCUIElementTypeTextField[@name='edit-title-input']");
	public By inputLicense = By.xpath("//XCUIElementTypeTextField[@name='edit-license-input']");
	
	//________verify whether SOAP report page exists or not_________ 
	public void validateSoapReportPage()
	{
		if(IsElementPresent(driver, headerTextSoapReportPageBy) && IsElementPresent(driver, noteMsgSoapReportPageBy))
		{
			ExtentManager.logInfoDetails("<b>User is now on SOAP Report page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either SOAP Report not exists or opened or verifying note message is not available or as expected. please check");
			Assert.fail();
		}
	}

	public void scrollToAdoptSignatureButton() {
		
		ActionsUtiils gestures = new ActionsUtiils(driver);
		
		while(true)
		{
			try {
				new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.visibilityOfElementLocated(buttonAdoptSignature));
				break;
			} catch (Exception e) {
				gestures.scroll(ScrollDirection.DOWN, 0.70);
			}
		}
	}
	
	// To perform the back Icon button 
	 public void clickBackIconSoapReport() {
		 click(driver, iconBackSoapReportPage, "Back Icon Button on Soap report page");
	 }

	// To update the first name, title, license
	public void updateUserDetails() {
		performScroll(driver);
		clear_ios(inputName);
		CommonVariables.firstName = generateRandomFirstName();
		sendKeys(driver, inputName, CommonVariables.firstName, "Name on SOAP Report Page");
		
		clear_ios(inputTitle);
		CommonVariables.title = generateRandomFirstName();
		sendKeys(driver, inputTitle, CommonVariables.title, "Title on SOAP Report Page");
		
		hideKeyboard();
		performScroll(driver);
		
		clear_ios(inputLicense);
		CommonVariables.license = GenerateRandomNumber(6);
		sendKeys(driver, inputLicense, CommonVariables.license, "License on SOAP Report Page");
		
		hideKeyboard();
	}

	// To verify the updated data i.e: first name, title, license
	public void verifyUpdatedFields() {
		performScroll(driver);
		scrollDownTillElementVisible(driver, labelLicense);
		verifyText(getAttribute_ios(inputName, "value"), CommonVariables.firstName, CommonVariables.firstName + " name on soap report page");
		verifyText(getAttribute_ios(inputTitle, "value"), CommonVariables.title,  CommonVariables.title + " on soap report page");
		verifyText(getAttribute_ios(inputLicense, "value"), CommonVariables.license, CommonVariables.license + " on soap report page");
	}

	public void scrollTillSubmitButton() {
        ActionsUtiils gestures = new ActionsUtiils(driver);
		
		while(true)
		{
			try {
				new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.visibilityOfElementLocated(reportSubmitButton));
				break;
			} catch (Exception e) {
				gestures.scroll(ScrollDirection.DOWN, 0.70);
			}
		}
	}
}