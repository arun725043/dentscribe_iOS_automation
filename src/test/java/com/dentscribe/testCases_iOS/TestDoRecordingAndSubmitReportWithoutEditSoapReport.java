package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Month;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;
import io.appium.java_client.AppiumBy;

public class TestDoRecordingAndSubmitReportWithoutEditSoapReport extends iOSBase 
{
	String patientName = null;
	
	@Test (priority = 1)
	public void verifyContinueButtonAfterPauseRecordingThroughPauseButton() throws InterruptedException, IOException 
	{
		loginPage.verifyIsApplicationLaunched();
		
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "sms page");
		
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue on sms verification page");
		
		// __________________________________Skip tour pages__________________________________________________________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

		// __________________________________Select date______________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		int[] date = calendarPage.getDateMonthYear(readData("testData", "calendarTestDate"));
		Month month = Month.of(date[1]);    
		month.toString();
 		click(driver, calendarPage.dropdownCalendar, "Calendar Dropdown");
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");
		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day, Month and year is selected successfully");
		click(driver, calendarPage.doneButton, "Done");

		// __________________________________Click patient____________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickPatient("Start");
		calendarPage.verifyPatientDetailsPopup();
		patientName = getText(driver, calendarPage.patientNameOnPatientDetailsPopup);
		patientName = patientName.trim();
		ExtentManager.logInfoDetails("Selected patient name - <b>" + patientName + "</b> for recording on calendar view page");
		
		calendarPage.startRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
		
		// to verify the patient name appear on recording screen;
		verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName);

	    // to click on Pause button on recording screen 
		recordingPage.clickPauseStopButton("pause");
		
		// Verify the continue button on calendar view page
		calendarPage.verifyClickContinueButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyContinueButtonAfterPauseRecordingThroughPauseButton" })
	public void verifyIsPatientPopupAppearingOnContinueButtonClick()
	{
		// __________________________________Click patient____________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickPatient("Continue");
		calendarPage.verifyPatientDetailsPopup();
		patientName = getText(driver, calendarPage.patientNameOnPatientDetailsPopup);
		patientName = patientName.trim();
		ExtentManager.logInfoDetails("Selected patient name - <b>" + patientName + "</b> for recording on calendar view page");
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyIsPatientPopupAppearingOnContinueButtonClick" })
	public void verifyContinueRecordingButtonAndClick() throws InterruptedException
	{
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("verify");
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyContinueRecordingButtonAndClick" })
	public void verifyContinueButtonAfterPauseRecordingThroughBackIcon() throws InterruptedException
	{	
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
		calendarPage.verifyClickContinueButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyContinueButtonAfterPauseRecordingThroughBackIcon" })
	public void verifyStopRecordingAfterContinueButtonClick() throws InterruptedException
	{
		calendarPage.clickPatient("Continue");
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
		
		// ___________________________Stop Recording_______________________
		recordingPage.clickPauseStopButton("stop");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		
		//verify review button
		calendarPage.verifyClickReviewButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyStopRecordingAfterContinueButtonClick" })
	public void verifyReviewedButtonForAppointmentWithoutEditSoapReport() throws InterruptedException
	{
		// ____________click review button and go to soap report__________________
		calendarPage.verifyClickReviewButtonForAppointment("click", patientName);
		soapReportPage.validateSoapReportPage();
		
		//_____________Update signature and verified reviewed button________	
		soapReportPage.scrollToAdoptSignatureButton();
		click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signature on soap report page");
		click(driver, soapReportPage.buttonSubmitAdoptSignaturePopup, "Submit");
		soapReportPage.scrollTillSubmitButton();
		click(driver, soapReportPage.reportSubmitButton, "Submit button on soap report page");
		calendarPage.verifyClickReviewedButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 9, dependsOnMethods = { "verifySoapReportAfterReviewButtonClick" })
	public void verifyEditIconSoapReport()
	{
		//_____________verify edit icon soap report________
		click(driver, soapReportPage.editButton, "Edit icon on soap report page");
		assertTrue(IsElementPresent(driver, soapReportPage.saveButton));
		ExtentManager.logInfoDetails("Save icon visible on Edit icon click as expected");
	}
	
	@Test (priority = 10, dependsOnMethods = { "verifyEditIconSoapReport" })
	public void verifyReviewedButtonAfterUpdateSignatureInSoapReport()
	{
		//_____________Update signature and verified reviewed button________
		click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signature on soap report page");
		click(driver, soapReportPage.buttonSubmitAdoptSignaturePopup, "Submit");
		click(driver, soapReportPage.saveButton, "Save Button on soap report page");
		click(driver, soapReportPage.soapReportBackButton, "Back Icon on report page");
		
		calendarPage.verifyClickReviewedButtonForAppointment("verify", patientName);
	}
}
