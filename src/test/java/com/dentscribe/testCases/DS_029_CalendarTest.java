package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.time.Month;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonLocators;
import Api.GenerateOTP;

public class DS_029_CalendarTest extends iOSBase {

	@Test
	public void verifyCalendarFunctionality() throws IOException, InterruptedException {
	
		
		// Application launched
		verifyText(getText(driver, CommonLocators.textWelcome_ios), "Welcome to Dentscribe!", "Welcome to Dentscribe!");
		ExtentManager.logInfoDetails("Application launched successfully");
		
		// Perform Login
		loginPage.loginApplication(readData("Config", "username"), readData("Config", "password"), "valid");
		
		// Fill the OTP and click on 'Continue' Button
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue");
		
		// Skip the tour page
		tourPages.skipTourPages();

		int[] date = calendarPage.getDateMonthYear(readData("testData", "calendarTestDate"));
		Month month = Month.of(date[1]);

		String monthName = month.toString();
		click(driver, calendarPage.dropdownCalendar, "Calendar Dropdown");
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");

		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day,Month and year is selected successfully");

		click(driver, calendarPage.doneButton, "Done"); 
		String monthYear = getAttribute_ios(calendarPage.selectedMonth, "label");
		System.out.println(monthYear);
		
		String[] data = monthYear.split(" ");
		System.out.println(monthName+" "+data[0]);
		assertEquals(monthName.toLowerCase(), data[0].toLowerCase());
		ExtentManager.logInfoDetails(readData("testData", "calendarTestDate") + " date is selected successfully");

	}

}
