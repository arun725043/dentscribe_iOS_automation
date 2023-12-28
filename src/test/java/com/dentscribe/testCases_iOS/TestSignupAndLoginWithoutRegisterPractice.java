package com.dentscribe.testCases_iOS;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import Api.GenerateOTP;


public class TestSignupAndLoginWithoutRegisterPractice extends iOSBase 
{
	
	String emailString = "kapoor.arun+auto" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	String pass = "Pass@" + CommonMethods.GenerateRandomNumber(5);

	@Test (priority = 1)
	public void verifySignupWithNonSupportedPMS() throws InterruptedException 
	{
		// ___________Application launched_______________
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();

		// ________fill form and verify confirmation popup___________
		signUpPage.fillSignupForm(generateRandomFirstName(), genrateRandomLastName(), "9", readData("testData", "mobile"), emailString, 
				"title" + GenerateRandomNumber(3), String.valueOf(GenerateRandomNumber(6)), pass, pass, "Eaglesoft");
		signUpPage.verifyNonSupportedPmsPopupAndClose();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifySignupWithNonSupportedPMS" })
	public void verifySignupWithSupportedPMS()
	{
		signUpPage.selectPracticeManagementSoftware("dentrix");
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.verifyConfirmationPopup();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifySignupWithSupportedPMS" })
	public void verifyWishToContinuePopupBackButton()
	{		
		// _____________verify back button_______________
		signUpPage.clickConfirmationPopupButton("back");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyWishToContinuePopupBackButton" })
	public void verifyPracticeInfoMandatoryFields()
	{
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.clickConfirmationPopupButton("continue");
		// __________________validate OTP functionality_____________
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, signUpPage.smsVerificationContinueButton, "Continue");
		practiceInfoPage.validatePracticeInfoPage();
		// ____________verify mandatory fields on practice info form________
		practiceInfoPage.verifyMandatoryFields();
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyPracticeInfoMandatoryFields" })
	public void verifyLoginWithoutRegisterPractice()
	{
		// ________________verify user should not be logged in with these credentials______________
		click(driver, loginPage.loginButtonBy, "Login Button");
		loginPage.performLoginWithoutPractice(CommonVariables.email, CommonVariables.actualPass);
		verifyText(getAttribute_ios(loginPage.textPracticeIsNotAuthorized, "name"), CommonVariables.errorMsgWithoutRegisterPractice, "Expected error message");
		ExtentManager.logInfoDetails("User is still on login page as expected");
	}
}
