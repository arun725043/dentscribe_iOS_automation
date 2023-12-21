package com.dentscribe.testCases_iOS;

import java.io.IOException;
import java.time.Month;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;

public class TestAllAppointmentStatuses extends iOSBase 
{
	/*
	 * This class verifying whether appointment statuses changing from Start to Continue to Review to Reviewed on performing the respective operations 
	 */
	
	String patientName = null;
	String appointmentDateString = readData("testData", "shortAppointmentsDate");
	
	@Test (priority = 1)
	public void goToCalendarPageAndSelectAppointmentsDate() throws InterruptedException, IOException 
	{
		loginPage.verifyIsApplicationLaunched();
		
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "sms page");
		
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue on sms verification page");
		
		// __________________________________Skip tour pages__________________________________________________________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

		// __________________________________Select date______________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		int[] date = calendarPage.getDateMonthYear(appointmentDateString);
		Month month = Month.of(date[1]);     

		month.toString();
 		click(driver, calendarPage.dropdownIconCalendar, "Calendar Dropdown");
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");

		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		
		click(driver, calendarPage.doneButtonCalendarPopup, "Done");
	}
		
	@Test (priority = 2, dependsOnMethods = { "goToCalendarPageAndSelectAppointmentsDate" })
	public void verifyIsPatientPopupAppearingOnStartButtonClick() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException
	{
		// __________________________________Click patient____________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickPatient("Start");
		calendarPage.verifyPatientDetailsPopup();
		patientName = getText(driver, calendarPage.patientNameOnPatientDetailsPopup);
		patientName = patientName.trim();
		ExtentManager.logInfoDetails("Selected patient name - <b>" + patientName + "</b> for recording on calendar view page");
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyIsPatientPopupAppearingOnStartButtonClick" })
	public void verifyStartRecordingButtonOnPatientDetailsPopupAndClick() throws InterruptedException
	{
		calendarPage.startRecordingButtonOnPatientDetailsPopup("verify");
		calendarPage.startRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyStartRecordingButtonOnPatientDetailsPopupAndClick" })
	public void verifyIsAppointmentStatusContinueAfterPauseRecording() throws InterruptedException
	{
		Thread.sleep(10000);
		// to click on Pause button on recording screen 
		recordingPage.clickPauseStopButton("pause");
		
		// Verify the continue button on calendar view page
		calendarPage.verifyClickContinueButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsAppointmentStatusContinueAfterPauseRecording" })
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
	
	@Test (priority = 6, dependsOnMethods = { "verifyIsPatientPopupAppearingOnContinueButtonClick" })
	public void verifyContinueRecordingButtonOnPatientDetailsPopupAndClick() throws InterruptedException
	{
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("verify");
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
	}
	
	
	@Test (priority = 7, dependsOnMethods = { "verifyContinueRecordingButtonOnPatientDetailsPopupAndClick" })
	public void verifyIsAppointmentStatusReviewAfterStopRecording() throws InterruptedException
	{
		Thread.sleep(15000);	//recording time 

		// ___________________________Stop Recording_______________________
		recordingPage.clickPauseStopButton("stop");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		
		//verify review button
		calendarPage.verifyClickReviewButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 8, dependsOnMethods = { "verifyIsAppointmentStatusReviewAfterStopRecording" })
	public void addSignatureInSoapReportAndSubmit() throws InterruptedException
	{
		// ____________click review button and go to soap report__________________
		calendarPage.verifyClickReviewButtonForAppointment("click", patientName);
		soapReportPage.validateSoapReportPage();
		
		//_____________Add signature and verified reviewed button________	
		soapReportPage.scrollToAdoptSignatureButton();
		click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signature on soap report page");
		click(driver, soapReportPage.buttonSubmitAdoptSignaturePopup, "Submit button on create signature popup");
		soapReportPage.scrollTillSubmitButton();
		click(driver, soapReportPage.reportSubmitButton, "Submit button on soap report page");
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 9, dependsOnMethods = { "addSignatureInSoapReportAndSubmit" })
	public void verifyIsAppointmentStatusReviewedAndSoapReportAfterClickIt() throws InterruptedException
	{
		// Verify the continue button on calendar view page
		calendarPage.verifyClickReviewedButtonForAppointment("verify", patientName);
	
		// ____________click reviewed button and go to soap report__________________
		calendarPage.verifyClickReviewedButtonForAppointment("click", patientName);
		soapReportPage.validateSoapReportPage();
	}
}
