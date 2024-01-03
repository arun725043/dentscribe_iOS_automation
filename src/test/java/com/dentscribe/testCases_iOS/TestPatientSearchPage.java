package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;

public class TestPatientSearchPage extends iOSBase {

	@Test (priority = 1)
	public void verifySkipLinkTourPages() throws IOException, InterruptedException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Perform Login
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), 
				readData(CommonVariables.inputFileUserDetails, "password"), "valid");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		tourPages.validateTourPageCalendarScheduleView();

		//skip tour pages and go to calendar view page
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifySkipLinkTourPages" } )
	public void verifyIsPatientSearchPageExists()
	{
		// search result should be display after searching
		calendarPage.clickSearchIconCalendarPage();
		patientSearchPage.validatePatientSearchPage();
	}
	@Test (priority = 3, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByInvalidData()
	{
		// to verify the in valid user
		patientSearchPage.verifyNoMatchesFound();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void DS_042_verifyPatientSearchByPatientName()
	{
		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + readData(CommonVariables.inputFileTestData, "patientName") + "</b>");
		patientSearchPage.searchPatient(readData(CommonVariables.inputFileTestData, "patientName"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name='"+readData(CommonVariables.inputFileTestData, "patientName")+"'])[1]")));
		assertTrue(patientSearchPage.verifySearchedPatient(readData(CommonVariables.inputFileTestData, "patientName")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientPhoneNumber()
	{
		// Search By patientMobile and verify
		ExtentManager.logInfoDetails("Searching By mobile : <b> " + readData(CommonVariables.inputFileTestData, "patientPhone") + "</b>");
		patientSearchPage.searchPatient(readData(CommonVariables.inputFileTestData, "patientPhone"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData(CommonVariables.inputFileTestData, "patientPhone")+"'])[1]")));   	

		String no = driver.findElements(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData(CommonVariables.inputFileTestData, "patientPhone")+"'])[1]")).get(0).getText();
		assertEquals(no.trim(), readData(CommonVariables.inputFileTestData, "patientPhone"));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientDOB()
	{
		// Search By dob and verify
		ExtentManager.logInfoDetails("Searching By DOB : <b> " + readData(CommonVariables.inputFileTestData, "dob") + "</b>");
		patientSearchPage.searchPatient(readData(CommonVariables.inputFileTestData, "dob"));
		
		assertTrue(patientSearchPage.verifySearchedPatient(readData(CommonVariables.inputFileTestData, "dob")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}

	@Test (priority = 7, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientInsurance()
	{
		// Search By insurance and verify
		ExtentManager.logInfoDetails("Searching By insurance : <b> " + readData(CommonVariables.inputFileTestData, "insurance") + "</b>");
		patientSearchPage.searchPatient(readData(CommonVariables.inputFileTestData, "insurance"));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData(CommonVariables.inputFileTestData, "insurance")+" '])[1]")));
		assertTrue(IsElementPresent(driver, By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData(CommonVariables.inputFileTestData, "insurance")+" '])[1]")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
}
