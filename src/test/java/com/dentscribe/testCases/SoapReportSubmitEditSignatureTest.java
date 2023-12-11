package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import java.time.Month;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;

public class SoapReportSubmitEditSignatureTest extends iOSBase {

	@Test
	public void editSignarueReviewed() {

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
			System.out.println("Loading Done");
			calendarPage.clickPatient(); 
			String patientName = getText(driver, calendarPage.patientNameOnRecording);
			patientName = patientName.trim();
			ExtentManager.logInfoDetails("<b>"+patientName+"</b> Has Been selected");
			System.out.println(patientName);
			assertTrue(recordingPage.clickVerifyStartRecording("verify"));
			ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected");

			// __________________________________start Recording___________________________________________________________
			recordingPage.clickVerifyStartRecording("click");
			waitUntilLoaderDisappear(driver);
			System.out.println("Loading Done");
			calendarPage.clickWhileUsingAppButton();
			calendarPage.runAudio(5);

			// ___________________________Stop Recording_______________________
			
			recordingPage.clickPauseStopButton("stop");
			waitUntilLoaderDisappear(driver);
			System.out.println("Loading Done");

			// ____________submit soap report__________________
			calendarPage.verifySoapReport(patientName, soapReportPage.textSoapReport);
			
			//_____________Update signature and verified reviewed button________
			click(driver, soapReportPage.editButton, "Edit Button on report page");
			assertTrue(IsElementPresent(driver, soapReportPage.saveButton));
			ExtentManager.logInfoDetails("Save Button appears on report page as expectes");
			click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signatur on report page");
			click(driver, soapReportPage.buttonSubmit, "Submit");
			click(driver, soapReportPage.saveButton, "Save Button on report page");
			click(driver, soapReportPage.soapReportBackButton, "Back Icon on report page");
			
			calendarPage.verifyReviewedButton(patientName);
			ExtentManager.logInfoDetails(patientName+": appointment has been reviewed");
					
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}