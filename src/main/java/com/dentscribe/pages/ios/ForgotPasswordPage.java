package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class ForgotPasswordPage extends iOSActions{

	IOSDriver driver;
	
	public ForgotPasswordPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	//Locator 
	public By headerTextForgotPassword = By.xpath("//XCUIElementTypeStaticText[@name='Forgot Password']");
	public By noteMsgForgotPasswordPage = By.xpath("//XCUIElementTypeStaticText[@name='Please enter your registered email with us']");
	public By inputEmail = AppiumBy.iOSClassChain("**/XCUIElementTypeTextField[`name == \"username-input\"`]");
	public By continueButton = AppiumBy.accessibilityId("forgot-password-continue-button");
	public By sentResetEmailPopup = AppiumBy.accessibilityId("Reset password link has been sent to your email");
	public By validationMsgUserNameRequiredBy = By.xpath("//XCUIElementTypeStaticText[@name='Username is required.']");
	public By validationMsgWrongUsernameBy = By.xpath("//XCUIElementTypeStaticText[@name='Please enter valid email.']");
	public By errorMsgNoRecordFoundBy = AppiumBy.accessibilityId("No record found");
	public By iconBackForgotPassword = By.xpath("(//XCUIElementTypeOther[@name='Forgot Password'])[4]/XCUIElementTypeOther");
	
	// Verify whether Forgot Password page exists or not
    public void validateForgotPasswordPage()
    {
    	if(IsElementPresent(driver, headerTextForgotPassword) && IsElementPresent(driver, noteMsgForgotPasswordPage))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Forgot Password page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Forgot Password page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// to sent the email 
	public void sendResetPasswordEmail(String email) throws InterruptedException {
		
	    sendKeys(driver, inputEmail, email, "Email on Forgot Password Page");
		hideKeyboard();
		click(driver, continueButton, "Continue button on Forgot Password Page");
		verifyText(getAttribute_ios(sentResetEmailPopup, "name"), "Reset password link has been sent to your email", "Reset password link has been sent to your email on Forgot Password page");
	}

	// to verify the 'User name is required.' appears 
	public void verifyUserNameIsRequired() {
		click(driver, continueButton, "Continue button on Forgot Password Page");
		verifyText(getText(driver, validationMsgUserNameRequiredBy), "Username is required.", "Mandatory username validation message");
	}
	
	// to verify the 'Please enter valid email.' appears 
	public void verifyWrongUserName(String email) {
		sendKeys(driver, inputEmail, email, "Email on Forgot Password Page");
		click(driver, continueButton, "Continue button on Forgot Password Page");
		verifyText(getText(driver, validationMsgWrongUsernameBy), "Please enter valid email.", "Wrong email format validation message");
	}
	
	// to verify the 'No record found' appears 
	public void verifyInvalidUserName(String email) {
		sendKeys(driver, inputEmail, email, "Email on Forgot Password Page");
		click(driver, continueButton, "Continue button on Forgot Password Page");
		scrollDownTillElementVisible(driver, errorMsgNoRecordFoundBy);
		verifyText(getText(driver, errorMsgNoRecordFoundBy), "No record found", "Wrong email format validation message");
	}
	
	// To verify that user navigated on patient search page on Back icon click
	public void clickBackIconForgotPassword() {
		click(driver, iconBackForgotPassword, "Back icon on forgot password page");
	}
}
