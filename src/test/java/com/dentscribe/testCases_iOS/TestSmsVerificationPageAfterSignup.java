package com.dentscribe.testCases_iOS;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;

public class TestSmsVerificationPageAfterSignup extends iOSBase {
	
	String emailId = CommonMethods.generateRandomFirstName() + "@gmail.com";
	String pass = "Pass@" + CommonMethods.GenerateRandomNumber(5);
	
	@Test (priority = 1)
	public void verifyIsSmsVerificationPageExistsAfterSignup() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();
		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(generateRandomFirstName(), genrateRandomLastName(), "9", readData(CommonVariables.inputFileTestData, "mobile"), 
				emailId, "title" + GenerateRandomNumber(3), 
				String.valueOf(GenerateRandomNumber(6)), pass, pass, "Dentrix");
		signUpPage.verifyConfirmationPopup();
		
		//___________Click on Confirmation popup button______________
		signUpPage.clickConfirmationPopupButton("continue");
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterSignup" })
	public void verifySmsVerificationWithBlankOtp()
	{
		// _____________validate OTP with empty_____________
		signUpPage.validateSmsVerificationPage();
		signUpPage.verifyEmptyOTP();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterSignup" })
	public void verifySmsVerificationWithWrongOtp()
	{
		// ____________validate otp with wrong value_____________
		GenerateOTP.fillOtp(driver, signUpPage.inputWrongOtp());
		signUpPage.verifyWrongOtp();
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterSignup" })
	public void verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate ReSend code link_______________
		signUpPage.verifyResendCodeLInk();
	}
	
	@Test (priority = 5)
	public void verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage()
	{
		// ________________validate back button_____________
		signUpPage.verifyBackIconSmsVerificationPage();
		IsElementPresent(driver, signUpPage.selectYourPracticeManagement);
		ExtentManager.logInfoDetails("<b>User is now back on Signup page as expected");
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyWhetherSmsVerificationPageBackIconRedirectToSignupPage" })
	public void verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// Go to sms verification page again
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.clickConfirmationPopupButton("continue");
		// To fill the otp
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();
	}
}
