package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;


public class TestLoginPage extends iOSBase {

	@Test (priority = 1)
	public void verifyIsLoginPageExistsAndItsFields() throws IOException, InterruptedException 
	{
		loginPage.verifyIsApplicationLaunched();
		loginPage.validateLoginPage();
		// ______________verify home page_____________________
		loginPage.verifyHomePageElement();
	}

	@Test (priority = 2, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void verifyLoginWithoutMandatoryFields()
	{
		// _______________verify mandatory fields_____________
		loginPage.verifyLoginMandatoryField();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void DS_006_verifyLoginWithNonExistingUser() throws IOException, InterruptedException
	{
		// try to login with invalid credentials
	    loginPage.loginApplication("arunr@gmail.com", "kapoor", "no record error");
		ExtentManager.logInfoDetails("User is not logged in as expected");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void verifyLoginWithInvalidPassword() throws IOException, InterruptedException
	{	
		loginPage.loginApplication(readData("UserDetails", "username"), "kapoor", "invalid error");
		ExtentManager.logInfoDetails("User is not logged in as expected");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void verifyLoginWithThreeInvalidAttempts() throws IOException, InterruptedException
	{	
		// verify the blocked user
		loginPage.verifyBlockedUser(readData("UserDetails", "username"), "Test@123");
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyIsLoginPageExistsAndItsFields" })
	public void verifyLoginWithValidCredentials() throws IOException, InterruptedException
	{
		// login with valid credentials
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "sms page");
	}
}
