package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;


public class TestSmsVerificationPageAfterLogin extends iOSBase 
{	
	@Test (priority = 1)
	public void verifyIsSmsVerificationPageExistsAfterLogin() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		// login with valid credentials
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), 
				readData(CommonVariables.inputFileUserDetails, "password"), "sms page");
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsSmsVerificationPageExistsAfterLogin" })
	public void verifySmsVerificationWithBlankOtp()
	{
		// _____________validate OTP with empty_____________
		signUpPage.validateSmsVerificationPage();
		signUpPage.verifyEmptyOTP();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifySmsVerificationWithBlankOtp" })
	public void verifySmsVerificationWithWrongOtp()
	{
		// ____________validate otp with wrong value_____________
		GenerateOTP.fillOtp(driver, signUpPage.inputWrongOtp());
		signUpPage.verifyWrongOtp();
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifySmsVerificationWithWrongOtp" })
	public void verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate ReSend code link_______________
		signUpPage.verifyResendCodeLInk();
	}
	
	@Test (priority = 5)
	public void verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage()
	{
		// ________________validate back button_____________
		signUpPage.verifyBackIconSmsVerificationPage();
		loginPage.validateLoginPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage" })
	public void verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), readData(CommonVariables.inputFileUserDetails, "password"), "sms page");
		// To fill the otp
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify that tour page appears as expected
		verifyText(getText(driver, loginPage.txtCalenerScheduleView), "Calendar Schedule View", "Calendar Schedule View");
	}
}
