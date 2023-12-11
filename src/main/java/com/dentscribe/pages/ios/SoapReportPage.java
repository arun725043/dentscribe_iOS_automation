package com.dentscribe.pages.ios;

import java.time.Duration;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		public By buttonAdoptSignature = AppiumBy.accessibilityId("adopt-signature-button");
		public By editButton = By.xpath("//XCUIElementTypeOther[@name=' Review and sign the report.']/XCUIElementTypeOther");
		public By buttonSubmit = AppiumBy.accessibilityId("signature-text-submit-button");
		public By reportSubmitButton = AppiumBy.accessibilityId("report-submit-button");
		public By textCreateYourSignature = By.xpath("");
		public By textInitials = By.xpath("");

		public By textSoapReport = By.xpath("//XCUIElementTypeStaticText[@name='SOAP Report']");
		public By signatureArea = By.xpath("");
		public By buttonRedraw = By.xpath("");
		public By soapReportBackButton = By.xpath("(//XCUIElementTypeOther[@name='SOAP Report'])[4]/XCUIElementTypeOther");
		public By saveButton = By.xpath("//XCUIElementTypeOther[@name='']");
		public By inputName = By.xpath("//XCUIElementTypeTextField[@name='edit-name-input']");
		public By inputTitle = By.xpath("//XCUIElementTypeTextField[@name='edit-title-input']");
		public By inputLicense = By.xpath("//XCUIElementTypeTextField[@name='edit-license-input']");
				

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
			CommonVariables.license = generateRandomFirstName();
			sendKeys(driver, inputLicense, CommonVariables.license, "License on SOAP Report Page");
			
			hideKeyboard();
			
		}

		// To verify the updated data i.e: first name, title, license
		public void verifyUpdatedFields() {
			performScroll(driver);
			
			verifyText(getAttribute_ios(inputName, "value"), CommonVariables.firstName, CommonVariables.firstName+" name on soap report page");
			verifyText(getAttribute_ios(inputTitle, "value"), CommonVariables.title,  CommonVariables.title +" on soap report page");
			verifyText(getAttribute_ios(inputLicense, "value"), CommonVariables.license, CommonVariables.license+" on soap report page");
			
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