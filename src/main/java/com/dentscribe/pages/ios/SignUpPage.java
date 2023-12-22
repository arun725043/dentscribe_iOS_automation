package com.dentscribe.pages.ios;

import static org.testng.Assert.assertFalse;

import java.time.Duration;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class SignUpPage extends iOSActions {
	public boolean flag;
	IOSDriver driver;

	// Locators

	public By signupButton = By.xpath("//XCUIElementTypeOther[@name='Sign Up']");
	public By noteSignupPage = By.xpath("//XCUIElementTypeStaticText[@name='" + CommonVariables.noteMessageSignupPageString + "']");
	public By inputFirstName = By.xpath("//XCUIElementTypeTextField[@name='firstname-input']");
	public By inputLastName = By.xpath(" //XCUIElementTypeTextField[@name='lastname-input']");
	public By countryCodeBy = By.xpath("//XCUIElementTypeOther[@name='United States Telephone input']");
	public By firstNameLabel = By.xpath("//XCUIElementTypeStaticText[@name='First Name']");
	public By inputPhoneNumber = By.xpath("//XCUIElementTypeTextField[@name='phone-input']");
	public By inputEmail = By.xpath("//XCUIElementTypeTextField[@name='email-input']");
	public By inputTitle = By.xpath("//XCUIElementTypeTextField[@name='title-input']");
	public By phoneNumLabel = By.xpath("//XCUIElementTypeStaticText[@name=\"Phone Number\"]");
	public By emailLabel = By.xpath("//XCUIElementTypeStaticText[@name='Email']");
	public By titleLabel = By.xpath("//XCUIElementTypeStaticText[@name='Title']");
	public By licenseNumber = By.xpath("//XCUIElementTypeTextField[@name='license-input']");
	public By licenseNumberLabel = By.xpath("//XCUIElementTypeStaticText[@name='License Number']");
	public By passwordLabel = By.xpath("//XCUIElementTypeStaticText[@name='Password']");
	public By pmsDropDown = By.xpath("(//XCUIElementTypeOther[@name=''])[2]");
	public By optionEagleSoft = By.xpath("(//XCUIElementTypeOther[@name='select-pms'])[2]");
	public By optionDentrix = By.xpath("(//XCUIElementTypeOther[@name='select-pms'])[4]");
	public By optionOpenDental = By.xpath("(//XCUIElementTypeOther[@name='select-pms'])[6]");
	public By continueButtonSignupPageBy = By.xpath("//XCUIElementTypeOther[@name='signup-continue-button']");
	public By buttonBack = By.xpath("(//XCUIElementTypeOther[@name='Back'])[2]");
	public By buttonContinue = By.xpath("//XCUIElementTypeOther[@name='Continue']");// Continue button on confirmation
																					// popup
	public By textSmsVerification = By.xpath("//XCUIElementTypeStaticText[@name='SMS Verification']");
	public By smsVerificationContinueButton = By.xpath("//XCUIElementTypeOther[@name='sms-verification-button']");
	public By textEnterValidCode = By.xpath("//XCUIElementTypeStaticText[@name='Please enter the valid code.']");
	public By linkResendCode = By.xpath("//XCUIElementTypeOther[@name='resend-code-button']");
	public By iconBackSmsVerificationPage = By.xpath("(//XCUIElementTypeOther[@name='SMS Verification'])[4]/XCUIElementTypeOther");
	public By eyeIconPassword = AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == ''`][1]");
	public By eyeIconConfirmPassword = By.xpath("(//XCUIElementTypeStaticText[@name=\"\"])[2]");
	public By doYouWishToContinue = AppiumBy.accessibilityId("Do you wish to continue?");
	public By textNonSupportPmsPopUp = AppiumBy.accessibilityId("" + CommonVariables.messageNonSupportPmsPopup + "");
	public By buttonOkNonSupportPmsPopUp = By.xpath("(//XCUIElementTypeOther[@name='Okay'])[2]");
	public By inputPassword = By.xpath("//XCUIElementTypeTextField[@name='password-input']");
	public By inputConfirmPassword = By.xpath("//XCUIElementTypeTextField[@name='confirmpassword-input']");
	public By selectYourPracticeManagement = By
			.xpath("//XCUIElementTypeStaticText[@name='Select Your Practice Management Software']");
	public By textFistNameIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='First name is required.']");
	public By textPhoneNumberIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='Phone number is required.']");
	public By textUserNameIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='Username is required.']");
	public By textLicenseNumberIsRequired = AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == \"License number is required.\"`]");
	public By textPasswordIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='Password is required.']");
	public By textConfirmPasswordIsRequired = By.xpath("//XCUIElementTypeStaticText[@name='Confirm password is required.']");
	public By textEnterValidEmail = By.xpath("//XCUIElementTypeStaticText[@name='Please enter valid email.']");
	public By textConfirmPasswordMatch = By
			.xpath("//XCUIElementTypeOther[@name='confirm password does not match with password.']");
	public By textAlreadyExistingEmailId = By
			.xpath("//XCUIElementTypeStaticText[@name='User with email id already exists.']");
	public By textWeakPasssword = By.xpath("//XCUIElementTypeStaticText[@name='Password is too weak,it should have a digit and a special character, password must be longer than or equal to 8 characters.']");

	public SignUpPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	//________verify whether Sign up page exists or not_________ 
	public void validateSignUpPage()
	{
		click(driver, signupButton, "Sign Up button");
		if(IsElementPresent(driver, noteSignupPage))
		{
			ExtentManager.logInfoDetails("<b>User is now on Sign Up page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either Sign Up not exists or opened or verifying note message is not available or as expected. please check");
			Assert.fail();
		}
	}
		
	//________verify whether SMS verification page exists or not_________ 
	public void validateSmsVerificationPage()
	{
		if(IsElementPresent(driver, textSmsVerification) && IsElementPresent(driver, linkResendCode))
		{
			ExtentManager.logInfoDetails("<b>User is now on SMS Verification page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either SMS Verification page not exists or opened or Resend code link not found to verify. please check");
			Assert.fail();
		}
	}
		
	// _____________verify confirmation popup button____________
	public void verifyConfirmationPopup() 
	{
		if (IsElementPresent(driver, doYouWishToContinue))
		{
			ExtentManager.logInfoDetails("PMS confirmation popup opened as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either 'Do you wish to continue' popup not exists or not opened. please check");
			Assert.fail();
		}
	}
	
	// _____________verify confirmation popup button____________
	public void verifyNonSupportedPmsPopupAndClose() 
	{
		if (IsElementPresent(driver, textNonSupportPmsPopUp))
		{
			ExtentManager.logInfoDetails("Non supported PMS popup opened as expected");
			click(driver, buttonOkNonSupportPmsPopUp, "Okay button on non supported pms popup");
		}
		else {
			ExtentManager.logFailureDetails("Either selected PMS is supported by app or expected popup not opened/found. please check");
			Assert.fail();
		}
	}

	// ___________To verify back and continue button functionality on confirmation PopUp__________
	public void clickConfirmationPopupButton(String buttonName) {
		new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.visibilityOfElementLocated(buttonBack));
		switch (buttonName) {
		case "back":
			click(driver, buttonBack, "Back On Confirmation popup");
			verifyText(getText(driver, selectYourPracticeManagement), "Select Your Practice Management Software",
					"Sign Up page");
			break;
		case "continue":
			click(driver, buttonContinue, "Continue On Confirmation popup");
			verifyText(getText(driver, textSmsVerification), "SMS Verification", "SMS Verification on Sign Up page");
			break;
		default:
			break;
		}
	}

	// To validate the back button on SMS verification screen
	public void verifyBackIconSmsVerificationPage() {
		click(driver, iconBackSmsVerificationPage, "Back icon on SMS Verfication page");
	}

	// To validation with valid Otp
	public void verifyWrongOtp() {
		click(driver, smsVerificationContinueButton, "Continue");
		verifyText(getText(driver, textEnterValidCode), "Please enter the valid code.", "Please enter the valid code.");
	}

	// To verify the SMS verification screen appear
	public void verifyEmptyOTP() {
		click(driver, smsVerificationContinueButton, "Continue on sms verification page");
		verifyText(getText(driver, textEnterValidCode), "Please enter the valid code.", "Please enter the valid code.");
	}
	
	// _______________verify resend code link________________
	public void verifyResendCodeLInk() throws InterruptedException
	{
		click(driver, linkResendCode, "Resend Code link");
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");
	}

	// _________getting random fname,lname,licenseno,password, mobile number_______________
	public String[] getSignupDetail() {
		String fname = generateRandomFirstName();
		String lname = genrateRandomLastName();
		String licenseNo = String.valueOf(GenerateRandomNumber(6));
		String mobileNumber = readData("testData", "mobile");
		String pass = "Pass@" + CommonMethods.GenerateRandomNumber(5);
		return new String[] { fname, lname, mobileNumber, licenseNo, pass };
	}

	// __________________fill signup form_____________________
	public void fillSignupForm(String fname, String lname, String countryCode, String mobileNo, String email, String title, 
			String licenseNo, String password, String confirmPassword, String pms) throws InterruptedException {
		sendKeys(driver, inputFirstName, fname, "First Name");
		sendKeys(driver, inputLastName, lname, "Last Name");
		hideKeyboard();
		sendKeys(driver, countryCodeBy, countryCode, "Country Code");
		click(driver, phoneNumLabel, "Phone Label");
		sendKeys(driver, inputPhoneNumber, mobileNo, "Phone");
		click(driver, phoneNumLabel, "Phone Label");
	
		sendKeys(driver, inputEmail, email, "Email");
		CommonVariables.email = email;
		hideKeyboard();
		sendKeys(driver, inputTitle, title, "Title");
		hideKeyboard();
		performScroll(driver);
		sendKeys(driver, licenseNumber, licenseNo, "License Number");
		try {
			click(driver, licenseNumberLabel, "License Number Label");
		} catch (Exception e) {
			click(driver, titleLabel, "Title");
		}
		performScroll(driver);
		click(driver, eyeIconPassword, "Eye Icon - Password");
		sendKeys(driver, inputPassword, password, "Password");
		CommonVariables.actualPass = password;
		hideKeyboard();
		sendKeys(driver, CommonLocators.confirmPassowrd_ios, confirmPassword, "Confirm Password");
		hideKeyboard();
		selectPracticeManagementSoftware(pms.toLowerCase());
		clickContinueButtonSignupPage();
	}
	
	// To verify the existing email id
	public void verifyExistingEmailId(String[] userDetail, String email, String pms) {
		scrollUpTillElementVisible(driver, firstNameLabel);
		sendKeys(driver, inputFirstName, userDetail[0], "First Name");
		sendKeys(driver, inputLastName, userDetail[1], "Last Name");
		hideKeyboard();
		sendKeys(driver, countryCodeBy, "9", "Country Code");
		click(driver, phoneNumLabel, "Phone Label");
		sendKeys(driver, inputPhoneNumber, userDetail[2], "Phone");
		click(driver, phoneNumLabel, "Phone Label");
	
		sendKeys(driver, inputEmail, email, "Email");
		hideKeyboard();
		performScroll(driver);
		sendKeys(driver, licenseNumber, userDetail[3], "License Number");
		try {
			click(driver, licenseNumberLabel, "License Number Label");
		} catch (Exception e) {
			click(driver, titleLabel, "Title");
		}
		performScroll(driver);
		sendKeys(driver, inputPassword, userDetail[4], "Password");
		hideKeyboard();
		sendKeys(driver, CommonLocators.confirmPassowrd_ios, userDetail[4], "Confirm Password");
		hideKeyboard();
		performScroll(driver);
		click(driver, continueButtonSignupPageBy, "Continue on Sign Up page");
		scrollUpTillElementVisible(driver, emailLabel);
		verifyText(getText(driver, textAlreadyExistingEmailId), "User with email id already exists.", "User with email id already exists. on sign up page");
		clear_ios(inputEmail);
	}
	
	// to select the practice management software
	public void selectPracticeManagementSoftware(String softWareType) 
	{
		click(driver, pmsDropDown, "PMS dropdown");
		
		switch (softWareType) {
		case "eaglesoft":
			click(driver, optionEagleSoft, "Eaglesoft");
			break;
		case "dentrix": 
			click(driver, optionDentrix, "Dentrix");
			break;
		case "opendental": 
			click(driver, optionOpenDental, "OpenDental");
			break;
		default: 
			System.out.println("Please enter the valid value");
		}
	}

	public void clickContinueButtonSignupPage()
	{
		click(driver, continueButtonSignupPageBy, "Continue button on Sign Up page");
	}
	
	// To enter the wrong OTP
	public char[] inputWrongOtp() {
		char[] ch = { '1', '2', '3', '4', '5', '6' };
		;
		return ch;
	}

	// to verify the mandatory fields on sign up page
	public void verifyMandatoryFields() {

		scrollDownTillElementVisible(driver, continueButtonSignupPageBy);

		click(driver, continueButtonSignupPageBy, "Continue on Sign Up page");
		
		scrollUpTillElementVisible(driver, firstNameLabel);
		
		verifyText(getText(driver, textFistNameIsRequired), "First name is required.", "First name is required. on signup page");
		verifyText(getText(driver, textPhoneNumberIsRequired), "Phone number is required.", "Phone number is required.. on signup page");
		verifyText(getText(driver,textUserNameIsRequired ), "Username is required.", "Username is required. on signup page");
		
		scrollDownTillElementVisible(driver, continueButtonSignupPageBy);
		
		//verifyText(driver.findElement(textLicenseNumberIsRequired).getText(), "License number is required.", "License number is required. on signup page");
		verifyText(getText(driver, textPasswordIsRequired), "Password is required.", "Password is required. on signup page");
		verifyText(getText(driver,textConfirmPasswordIsRequired ), "Confirm password is required.", "Confirm password is requred. on signup page");
	}

	// to verify the invalid email id message in sign up page
	public void verifyInvalidEmail() {
		
		scrollUpTillElementVisible(driver, emailLabel);
		sendKeys(driver, inputEmail, "Test.com", "Email");
		verifyText(getText(driver, textEnterValidEmail), "Please enter valid email.", "Please enter valid email. on sigun page");
		clear_ios(inputEmail);
		hideKeyboard();
	}

	// to verify the weak password in sign up page
	public void verifyWeakPassword() {
		
		scrollDownTillElementVisible(driver, continueButtonSignupPageBy);
		click(driver, eyeIconPassword, "Eye Icon - Password");
		sendKeys(driver, inputPassword, "12345", "Password");
		verifyText(getText(driver, textWeakPasssword), "Password is too weak,it should have a digit and a special character, password must be longer than or equal to 8 characters.", "Password is too weak,it should have a digit and a special character, password must be longer than or equal to 8 characters. on sign up page");
		clear_ios(inputPassword);
	}
	
	//to verify the confirm password match 
	public void verifyConfirmPasswordMatch() {
		sendKeys(driver, inputPassword, "Test@123", "Password");
		hideKeyboard();
		sendKeys(driver, CommonLocators.confirmPassowrd_ios, "Test@1234", "Confirm Password");
		verifyText(getText(driver, textConfirmPasswordMatch), "confirm password does not match with password.", "confirm password does not match with password. on sign up page");
		clear_ios(inputPassword);
		clear_ios(CommonLocators.confirmPassowrd_ios);
		hideKeyboard();
	}
	
	// To verify that user is able to create account by only mandatory fields
	public void verifyAccountCreateByMandatoryFields() {
		sendKeys(driver, inputEmail, "Test"+GenerateRandomNumber(4)+"@gmail.com", "Email");
		hideKeyboard();
		scrollDownTillElementVisible(driver, continueButtonSignupPageBy);
		selectPracticeManagementSoftware("dentrix");
		click(driver, continueButtonSignupPageBy, "Continue on Sign Up page");
	}

}