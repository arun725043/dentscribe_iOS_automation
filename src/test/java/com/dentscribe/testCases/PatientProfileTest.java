package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonLocators;

import Api.GenerateOTP;

public class PatientProfileTest extends iOSBase{
	
	@Test
	public void verifyPatientProfile() throws IOException, InterruptedException {
		
		//__________________Application Launched_____________________
		verifyText(getText(driver, CommonLocators.textWelcome_ios), "Welcome to Dentscribe!", "Welcome to Dentscribe!");
		ExtentManager.logInfoDetails("Application launched successfully");
		
		// Perform Login
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "valid");
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Contiune on sms verification screen");

		//skip tour pages
		tourPages.skipTourPages();
		
		// search result should be display after searching
		assertTrue(calendarPage.verifySearchLandingPage());
		ExtentManager.logInfoDetails("Patient search page opened successfully");

		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + readData("testData", "patientName") + "</b>");
		searchPage.searchPatient(readData("testData", "patientName"));
		waitUntilLoaderDisappear(driver);
		
		// To click on patient name 
		searchPage.performClickONPatient();
		
		// To verify the 'Patient Profile' text 
		patientProfilePage.verifyPatientProfileText();
		
		// To verify the patient name 
		assertTrue(patientProfilePage.verifyPatientName(readData("testData", "patientName")));
		ExtentManager.logInfoDetails(readData("testData", "patientName")+": Profile Opened as expected");
		
		
	}

}
