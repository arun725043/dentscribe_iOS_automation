package com.dentscribe.pages.ios;

import java.io.IOException;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class LoginPage extends iOSActions{
	
	IOSDriver driver;

	public LoginPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public boolean flag = false;   
	public By continueButton = By.xpath("//XCUIElementTypeOther[@name='login-continue-button']");
	public By linkForgotPassword = By.xpath("//XCUIElementTypeOther[@label='forgotpassword-click']");
	public By linkBiometricBy = By.xpath("//XCUIElementTypeOther[@name='setup-biometrics-click']");
	public By txtCalenerScheduleView = AppiumBy.iOSNsPredicateString("name == 'Calendar Schedule View'");
	public By textCalendarSchecule = By.xpath("");
	public By signupButton = By.xpath("//XCUIElementTypeOther[@name='Sign Up']");
	public By loginButtonBy = By.xpath("//XCUIElementTypeOther[@name='Login']");
	public By noteLoginPage = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.noteMessageLoginPageString + "']");
	public By buttonSkip = By.xpath("");
	public By buttonEnable = By.xpath("");
	public By textSmsVerification = By.xpath("//XCUIElementTypeStaticText[@name='SMS Verification']");
	public By continueButtonSMSVerification = By.xpath("//XCUIElementTypeOther[@name='sms-verification-button']");
	public By userNameLabel = By.xpath("//XCUIElementTypeStaticText[@name='Username']");
	public By noUserNamePass = By.xpath("//XCUIElementTypeOther[@name='No record found for this user']");
	public By invalidUserNamePass = By.xpath("//XCUIElementTypeOther[@name='Invalid username or password']");
	public By textPracticeIsNotAuthorized = AppiumBy.accessibilityId("Currently the practice is not authorized. Please contact support.");
	public By spuPopupText = By.xpath("//XCUIElementTypeStaticText[@name='Please install SPU and refresh the data']");
	public By spuPopupOkButton = By.xpath("(//XCUIElementTypeOther[@name='Ok'])[2]");
	public By blockMessage15Min = By.xpath("//*[@label = 'Your account is locked due to invalid login attempt. You can login your account after 15 minutes']");
	public By immediateBlockMessage = By.xpath("//*[@label = 'You have attempted 3 times. Your account is locked for next 15 minutes.']");
	
	// verify whether application launched or not
	public void verifyIsApplicationLaunched()
	{
		if (IsElementPresent(driver, CommonLocators.textWelcome_ios)) 
		{
			ExtentManager.logInfoDetails("Application launched successfully");
		}
		else {
			ExtentManager.logFailureDetails("Either application not opened or Welcome text not found. please check");
			Assert.fail();
		}
	}
	
	//________verify whether Login 
	public void validateLoginPage()
	{
		if(IsElementPresent(driver, noteLoginPage) && IsElementPresent(driver, linkForgotPassword))
		{
			ExtentManager.logInfoDetails("<b>User is now on Login page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either login page not exists or opened or forgot password link not found to verify. please check");
			Assert.fail();
		}
	}

	// ________verify home page_______
	public void verifyHomePageElement() 
	{
		IsElementPresent(driver, loginButtonBy, "Login tab");
		IsElementPresent(driver, signupButton, "Signup tab");
		IsElementPresent(driver, linkBiometricBy, "Set up Biometric link");
		IsElementPresent(driver, linkForgotPassword, "Forgot Password link");
	}
		
	// login application
	public void loginApplication(String username, String pass, String expectedResult) throws IOException, InterruptedException {
		iOSBase.wait.until(ExpectedConditions.visibilityOfElementLocated(noteLoginPage));
		sendKeys(driver, CommonLocators.txtUsername_ios, username, "Username");
		hideKeyboard();
		sendKeys(driver, CommonLocators.passoword_ios, pass, "Password");
		hideKeyboard();
		click(driver, continueButton, "Continue on Login page");
		switch (expectedResult) {
		case "sms page":
			verifyText(getText(driver, textSmsVerification), "SMS Verification", "SMS Verification page");
			ExtentManager.logInfoDetails("User is logged in successfully as expected");
			break;
		case "no record error":
			verifyText(getAttribute_ios(noUserNamePass, "label"), "No record found for this user", "No record found for this user on login page");
			break;
		case "invalid error":
			verifyText(getAttribute_ios(invalidUserNamePass, "label"), "Invalid username or password", "Invalid username or password");
			break;
		case "spu popup":
			verifyCloseSpuInstallPopup();
			break;
		default:
			System.out.println("please provide valid name");
		}
	}
	
	// ____________verify username and password is required should display after clicking on continue_________
	public void verifyLoginMandatoryField() {
		click(driver, continueButton, "Continue on Login page");
		verifyText(getText(driver, CommonLocators.msgUsernameIsRequired_ios), "Username is required.", "Username is required.");
		verifyText(getText(driver, CommonLocators.msgPasswordIsRequired_ios), "Password is required.", "Password is required.");
	}
	
	// To perform click on forgot password link
	public void performClickOnForgotPasswordLink() {
		click(driver, linkForgotPassword, "Forgot Password link on login page");
	}
	
   // To perform the login for without filing the practice form 
	public void performLoginWithoutPractice(String username, String password) {
		sendKeys(driver, CommonLocators.txtUsername_ios, username, "Username");
		hideKeyboard();
		sendKeys(driver, CommonLocators.passoword_ios, password, "Password");
		hideKeyboard();
		click(driver, continueButton, "Continue on Login page");
	}
	
	// ________verify SPU install PopUp and close it_______
	public void verifyCloseSpuInstallPopup() {
		
		verifyText(getText(driver, spuPopupText),"Please install SPU and refresh the data", "SPU Install Popup");
		click(driver, spuPopupOkButton, "SPU Install Popup OK button");
	}
	
	// To verify the text after block the user 
	public void verifyBlockedUser(String username, String pass) {
		sendKeys(driver, CommonLocators.txtUsername_ios, username, "Username");
		hideKeyboard();
		sendKeys(driver, CommonLocators.passoword_ios, pass, "Password");
		hideKeyboard();
		click(driver, continueButton, "Continue on Login page");
		click(driver, continueButton, "Continue on Login page");
		click(driver, continueButton, "Continue on Login page");
		verifyText(getAttribute_ios(immediateBlockMessage, "label"), "You have attempted 3 times. Your account is locked for next 15 minutes.", "You have attempted 3 times. Your account is locked for next 15 minutes.");
		click(driver, continueButton, "Continue on Login page");
		verifyText(getAttribute_ios(blockMessage15Min, "label"), "Your account is locked due to invalid login attempt. You can login your account after 15 minutes", "Your account is locked due to invalid login attempt. You can login your account after 15 minutes");
	}
	
	// To unblock the user
	public void unblockUser(String actualPass) {
		sendKeys(driver, CommonLocators.passoword_ios, actualPass, "Password");
		hideKeyboard();
		click(driver, continueButton, "Continue on Login page");
		verifyText(getText(driver, textSmsVerification), "SMS Verification", "SMS Verification page");	
	}
	
}
