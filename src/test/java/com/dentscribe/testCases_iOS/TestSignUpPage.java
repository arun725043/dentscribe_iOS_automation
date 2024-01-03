package com.dentscribe.testCases_iOS;

import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonVariables;

public class TestSignUpPage extends iOSBase 
{
	
	@Test (priority = 1)
	public void verifyIsSignupPageExists()
	{
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();
		
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupWithoutMandatoryFields() {
		// To verify the sign up mandatory fields
		signUpPage.verifyMandatoryFields();
	}

	@Test(priority = 3, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupPageForInvalidEmail() {
		// To validate the invalid email id
		signUpPage.verifyInvalidEmail();
	}

	@Test(priority = 4, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupPageForWeakPassword() {
		// To validate the weak password
		signUpPage.verifyWeakPassword();
	}

	@Test(priority = 5, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupPageForConfirmPassowrd() {
		// To validate the confirm password match
		signUpPage.verifyConfirmPasswordMatch();
	}

	@Test(priority = 6, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifyExistingAndDuplicateEmailId() {
		// To validate the existing email id
		signUpPage.verifyExistingEmailId(signUpPage.getSignupDetail(), readData(CommonVariables.inputFileUserDetails, "username"), "Dentrix");
	}

	@Test(priority = 7, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupWithOnlyMandatoryFields() {
		// To validate that user is able to create the account only using mandatory fields
		signUpPage.verifyAccountCreateByMandatoryFields();
		signUpPage.verifyConfirmationPopup();
	}
	
	@Test(priority = 8, dependsOnMethods = { "verifyIsSignupPageExists" })
	public void verifySignupConfirmationPopupButtons()
	{
		// _____________verify back and continue button_______________
		signUpPage.clickConfirmationPopupButton("back");
		click(driver, signUpPage.continueButtonSignupPageBy, "Continue On signup page");
		signUpPage.clickConfirmationPopupButton("continue");
		verifyText(getText(driver, signUpPage.textSmsVerification), "SMS Verification", "SMS Verification on sms verification page");
	}
}