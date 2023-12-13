package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;


public class TestLoginPage extends iOSBase {

	@Test (priority = 1)
	public void DS_004_verifyIsLoginPageExistsAndItsFields() throws IOException, InterruptedException 
	{
		loginPage.verifyIsApplicationLaunched();
		loginPage.validateLoginPage();
		// ______________verify home page_____________________
		loginPage.verifyHomePageElement();
	}

	@Test (priority = 2, dependsOnMethods = { "DS_004_verifyIsLoginPageExistsAndItsFields" })
	public void DS_005_verifyLoginWithoutMandatoryFields()
	{
		// _______________verify mandatory fields_____________
		loginPage.verifyLoginMandatoryField();
	}
	
	@Test (priority = 3, dependsOnMethods = { "DS_004_verifyIsLoginPageExistsAndItsFields" })
	public void DS_006_verifyLoginWithNonExistingUser() throws IOException, InterruptedException
	{
		// try to login with invalid credentials
	    loginPage.loginApplication("arunr@gmail.com", "kapoor", "no record error");
		ExtentManager.logInfoDetails("User is not logged in as expected");
	}
	
	@Test (priority = 4, dependsOnMethods = { "DS_004_verifyIsLoginPageExistsAndItsFields" })
	public void DS_007_verifyLoginWithInvalidPassword() throws IOException, InterruptedException
	{	
		loginPage.loginApplication(readData("Config", "username"), "kapoor", "invalid error");
		ExtentManager.logInfoDetails("User is not logged in as expected");
	}
	
	@Test (priority = 5, dependsOnMethods = { "DS_004_verifyIsLoginPageExistsAndItsFields" })
	public void DS_008_verifyLoginWithThreeInvalidAttempts() throws IOException, InterruptedException
	{	
		// verify the blocked user
		loginPage.verifyBlockedUser(readData("Config", "username"), "Test@123");
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_004_verifyIsLoginPageExistsAndItsFields" })
	public void DS_009_verifyLoginWithValidCredentials() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "sms page");
	}
}
