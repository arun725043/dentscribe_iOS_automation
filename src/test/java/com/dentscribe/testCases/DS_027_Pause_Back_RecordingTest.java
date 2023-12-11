package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import java.time.Month;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;


import Api.GenerateOTP;
import io.appium.java_client.AppiumBy;

public class DS_027_Pause_Back_RecordingTest extends iOSBase {

	@Test
	public void recordingTest() {

		try {
			// __________________________________Login into Application__________________________________________________
			loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "valid");
			
			GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
			click(driver, loginPage.continueButtonSMSVerification, "Continue on sms verification page");
			
			// __________________________________Skip tour pages__________________________________________________________
			tourPages.skipTourPages();
			ExtentManager.logInfoDetails("User is on Calendar View Page");

			// __________________________________Select date______________________________________________________________
			
			waitUntilLoaderDisappear(driver);
			System.out.println("Loading Done");
			int[] date = calendarPage.getDateMonthYear(readData("testData", "calendarTestDate"));
			Month month = Month.of(date[1]);     

			month.toString();
     		click(driver, calendarPage.dropdownCalendar, "Calendar Dropdown");
			ExtentManager.logInfoDetails("Clicked on Calendar dropdown");

			calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
			ExtentManager.logInfoDetails("Day,Month and year is selected successfully");
			
			click(driver, calendarPage.doneButton, "Done");

			// __________________________________Click patient____________________________________________________________
			waitUntilLoaderDisappear(driver);
			calendarPage.clickPatient();
			String patientName = getText(driver, calendarPage.patientNameOnRecording);
			patientName = patientName.trim();
			ExtentManager.logInfoDetails("<b>"+patientName+"</b> Has Been selected");
			System.out.println(patientName);
			assertTrue(recordingPage.clickVerifyStartRecording("verify"));
			ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected");
			
			// __________________________________start Recording___________________________________________________________;
			recordingPage.clickVerifyStartRecording("click");
			waitUntilLoaderDisappear(driver);
			System.out.println("Loading Done");
			calendarPage.clickWhileUsingAppButton();
			
			// to verify the patient name appear on recording screen;
			verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName);

		     // to click on Pause button on recording screen 
			recordingPage.performClickPause();
			
			// Verify the continue button on calendar view page
			assertTrue(calendarPage.verifyContinueButton(patientName));
			ExtentManager.logInfoDetails(patientName+": Has Continue button ");
			
			// to click on continue button 
			calendarPage.performClickContinue(patientName);
			
			// to click on continue recording button
			calendarPage.peformClickContinueRecording();
			
			// to verify the patient name appear on recording screen;
			verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName);
			
			// to click on back icon button 
			recordingPage.perfromClickBackIconButton();
			
			// to verify that 'Do you want to pause the recording ?' popup appears 
			verifyText(getText(driver, recordingPage.doYouWantPausePopup), "Do you want to pause the recording ?", "Do you want to pause the recording ? popup on reocrding page");
			
			// to click on 'Cancel' button 'Do you want to pause the recording ?' popup
			recordingPage.performclick_OK_Cancel("cancel");
			
			// to verify the patient name appear on recording screen;
			verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName+": Appears as expected");

			// to click on back icon button 
			recordingPage.perfromClickBackIconButton();
			 	
			// to click on 'OK' button 'Do you want to pause the recording ?' popup
			recordingPage.performclick_OK_Cancel("ok");
			
			// Verify the continue button on calendar view page
			assertTrue(calendarPage.verifyContinueButton(patientName));
			ExtentManager.logInfoDetails(patientName+": Has Continue button ");
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
