package com.dentscribe.testCases_iOS;

import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;

public class TestSignUpPage extends iOSBase 
{
	
	@Test (priority = 1)
	public void DS_016_verifyIsSignupPageExists()
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();
		
	}
	
	@Test(priority = 2, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_017_verifySignupWithoutMandatoryFields() {
		// To verify the sign up mandatory fields
		signUpPage.verifyMandatoryFields();
	}

	@Test(priority = 3, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_018_verifySignupPageForInvalidEmail() {
		// To validate the invalid email id
		signUpPage.verifyInvalidEmail();
	}

	@Test(priority = 4, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_019_verifySignupPageForWeakPassword() {
		// To validate the weak password
		signUpPage.verifyWeakPassword();
	}

	@Test(priority = 5, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_020_verifySignupPageForConfirPassowrd() {
		// To validate the confirm password match
		signUpPage.verifyConfirmPasswordMatch();
	}

	@Test(priority = 6, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_021_verifyExistingAndDuplicateEmailId() {
		// To validate the existing email id
		signUpPage.verifyExistingEmailId(signUpPage.getSignupDetail(), readData("UserDetails", "username"), "Dentrix");
	}

	@Test(priority = 7, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_022_verifySignupWithOnlyMandatoryFields() {
		// To validate that user is able to create the account only using mandatory fields
		signUpPage.verifyAccountCreateByMandatoryFields();
		signUpPage.verifyConfirmationPopup();
	}
	
	@Test(priority = 8, dependsOnMethods = { "DS_016_verifyIsSignupPageExists" })
	public void DS_023_verifySignupConfirmationPopupButtons()
	{
		// _____________verify back and continue button_______________
		signUpPage.clickConfirmationPopupButton("back");
		click(driver, signUpPage.continueButtonSignupPageBy, "Continue On signup page");
		signUpPage.clickConfirmationPopupButton("continue");
		verifyText(getText(driver, signUpPage.textSmsVerification), "SMS Verification", "SMS Verification on sms verification page");
	}
}