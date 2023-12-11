package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertFalse;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;


public class TestSmsVerificationPageAfterLogin extends iOSBase 
{	
	@Test (priority = 1)
	public void DS_010_verifyIsSmsVerificationPageExistsAfterLogin() throws IOException, InterruptedException
	{
		loginPage.verifyIsApplicationLaunched();
		// login with valid credentials
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "sms page");
	}
	
	@Test (priority = 2, dependsOnMethods = { "DS_010_verifyIsSmsVerificationPageExistsAfterLogin" })
	public void DS_011_verifySmsVerificationWithBlankOtp()
	{
		// _____________validate OTP with empty_____________
		signUpPage.validateSmsVerificationPage();
		signUpPage.verifyEmptyOTP();
	}
	
	@Test(priority = 3, dependsOnMethods = { "DS_011_verifySmsVerificationWithBlankOtp" })
	public void DS_012_verifySmsVerificationWithWrongOtp()
	{
		// ____________validate otp with wrong value_____________
		GenerateOTP.fillOtp(driver, signUpPage.inputWrongOtp());
		signUpPage.verifyWrongOtp();
	}
	
	@Test(priority = 4, dependsOnMethods = { "DS_012_verifySmsVerificationWithWrongOtp" })
	public void DS_013_verifySmsVerificationResendCodeLink() throws InterruptedException
	{
		// ___________validate resend link_______________
		click(driver, signUpPage.linkResendCode);
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");
	}
	
	@Test (priority = 5)
	public void DS_014_verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage()
	{
		// ________________validate back button_____________
		signUpPage.verifyBackIconButton();
		loginPage.validateLoginPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_014_verifyWhetherSmsVerificationPageBackIconRedirectToLoginPage" })
	public void DS_015_verifySmsVerificationWithValidOtp() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "sms page");
		// To fill the otp
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify that tour page appears as expected
		verifyText(getText(driver, loginPage.txtCalenerScheduleView), "Calendar Schedule View", "Calendar Schedule View");
	}
}
