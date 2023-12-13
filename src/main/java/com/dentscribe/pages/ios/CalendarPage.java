package com.dentscribe.pages.ios;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Month;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class CalendarPage extends iOSActions {

	IOSDriver driver;

	public CalendarPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public boolean flag = false;
	public String patientName = null;
	
	public By iconSearchCalendarPage = AppiumBy.accessibilityId("search-icon-button");
	public By iconSettingCalendarPage = AppiumBy.accessibilityId("setting-icon-button");
	public By inputMonthYear = AppiumBy.accessibilityId("downarrow-icon-button");
	public By selectedMonth = By.xpath("//XCUIElementTypeStaticText[@name='month-text']");
	public By buttonWhileUsingThisApp = By.xpath("");
	public By doneButtonCalendarPopup = By.xpath("//XCUIElementTypeOther[@name='calendar-done-button']");
	public By dropdownIconCalendar = AppiumBy.accessibilityId("downarrow-icon-button");
	public By rightArrowIcon = AppiumBy.accessibilityId("rightarrow-icon-button");
	public By leftArrowIcon = AppiumBy.accessibilityId("leftarrow-icon-button");
	public By rightArrowIconCalendar = AppiumBy.accessibilityId("calendar-display.header.rightArrow");
	public By leftArrowIconCalendar = AppiumBy.accessibilityId("calendar-display.header.leftArrow");
	public By monthYearCalendar = AppiumBy.accessibilityId("calendar-display.header.title");
	public By patientNameOnPatientDetailsPopup = By
			.xpath("//XCUIElementTypeStaticText[@name='Name:']//following-sibling::XCUIElementTypeStaticText");
	public By allowMicOk = By.xpath("//XCUIElementTypeButton[@name='OK']");
	public By resumeRecording = By.xpath("(//XCUIElementTypeOther[@name='OK'])[2]");
	public By errorPopupOk = By.xpath("(//XCUIElementTypeOther[@name=\"OK\"])[2]");
	public By allowNotificationsPopupBy = By.xpath("//XCUIElementTypeStaticText[@name='DentScribe” Would Like to Send You Notifications']");
	public By allowButtonNotificationPopupBy = By.xpath("//XCUIElementTypeButton[@name='Allow']");
	public By continueRecordingButton = AppiumBy.accessibilityId("Continue-recording");
	
	public By popupPatientDetails = By.xpath("//XCUIElementTypeStaticText[@name='Patient Details']");
	public By buttonStartRecording = By.xpath("//XCUIElementTypeOther[@name='Start-recording']");
	public By buttonContinueRecording = By.xpath("(//XCUIElementTypeOther[@name='Continue-recording'])");
	public By patientNameBy = By.xpath("//XCUIElementTypeStaticText[@name=' 5:00']");

	// Verify whether Calendar page exists or not
    public void validateCalendarPage()
    {
    	clickAllowButtonOnNotificationsPopup();
    	if(IsElementPresent(driver, dropdownIconCalendar) && IsElementPresent(driver, rightArrowIcon))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Calendar page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either calendar page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
    // ____________click while using app button_________
 	public void clickAllowButtonOnNotificationsPopup() {

 		try {
 			explicitWait(driver, allowButtonNotificationPopupBy, 10);
 			click(driver, allowButtonNotificationPopupBy,  "Allow button on notification confirmation popup");
 			// click(driver, resumeRecording,  "OK");

 		} catch (Exception e) {
 			System.out.println("catch while using app");
 		}
 	}
    
	// ____________click while using app button_________
	public void clickWhileUsingAppButton() {

		try {
			explicitWait(driver, allowMicOk, 10);
			click(driver, allowMicOk, "Ok button on allow access to microphone alert");

		} catch (Exception e) {
			System.out.println("catch while using app");
		}
	}

	// _______________verify user is landing on search page or not______________
	public void clickSearchIconCalendarPage() {
		click(driver, iconSearchCalendarPage,  "Search Icon");
	}

	// ___________select month and year_____________
	public void selectMonthYear(int day, int month, int year) throws InterruptedException {
		click(driver, rightArrowIcon,  "Right Arrow Icon Button");
		Thread.sleep(3000);
		String monthYear = getAttribute_ios(inputMonthYear, "label");
		System.out.println(monthYear);
		String[] data = monthYear.split(" ");
		System.out.println(year + " " + Integer.parseInt(data[1]));
		while (year != Integer.parseInt(data[1])) {
			if (year > Integer.parseInt(data[1])) {
				click(driver, rightArrowIcon,  "Right Arrow Icon Button");
			} else {
				click(driver, leftArrowIcon,  "Left Arrow Icon Button");
			}
			data[1] = getAttribute_ios(inputMonthYear, "label").split(" ")[1];
		}
	}

	public int[] getDateMonthYear(String fullDate) {
		String[] data = fullDate.split("-");
		int day = Integer.parseInt(data[0]);
		int month = Integer.parseInt(data[1]);
		int year = Integer.parseInt(data[2]);
		return new int[] { day, month, year };

	}

	public void selectMonthYearCalendar(int day, int month, int year) throws InterruptedException {
		Thread.sleep(2000);
		click(driver, rightArrowIconCalendar,  "Right Arrow Icon Button Calander");
		Thread.sleep(3000);
		String monthYear = getAttribute_ios(monthYearCalendar, "label");
		String[] data = monthYear.split(" ");
		while (year != Integer.parseInt(data[1])) {
			if (year > Integer.parseInt(data[1])) {
				click(driver, rightArrowIconCalendar,  "Right Arrow Icon Button Calendar");
			} else {
				click(driver, leftArrowIconCalendar,  "Left Arrow Icon Button Calendar");
			}
			data[1] = getAttribute_ios(monthYearCalendar, "label").split(" ")[1];

		}
		
		String monthName = getAttribute_ios(monthYearCalendar, "label").split(" ")[0];
		int actualMonth = Month.valueOf(monthName.toUpperCase()).getValue();
		while (actualMonth != month - 1) {
			if (actualMonth < month - 1) {
				click(driver, rightArrowIconCalendar,  "Right Arrow Icon Button Calendar");
				Thread.sleep(1500);
			} else {
				click(driver, leftArrowIconCalendar,  "Left Arrow Icon Button Calendar");
				Thread.sleep(1500);
			}
			monthName = getAttribute_ios(monthYearCalendar, "label").split(" ")[0];
			actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
		}
		if (day <= 9 && month <= 9) {
			click(driver, By.xpath(
					"//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-0" + month + "-0" + day + "']"),
					 "day");
		} else if (day <= 9 && month > 9) {
			driver.findElement(By.xpath("//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-" + month + "-0" + day + "']")).click();
		} else if (day > 9 && month <= 9) {
			click(driver, By.xpath(
					"//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-0" + month + "-" + day + "']"),
					 "day");
		} else {
			driver.findElement(By.xpath("//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-" + month + "-" + day + "']")).click();
		}
	}

	// ___________click patient which has start button______________
	public void clickPatient(String buttonName) 
	{
		String buttonNameString = null;
		switch (buttonName) {
		case "Start":
			buttonNameString = "Start-button";
			break;
		case "Continue":
			buttonNameString = "Continue-button";
			break;

		default:
			ExtentManager.logFailureDetails("Button name could be only Start/Continue");
			Assert.fail();
//			break;
		}

		int i = 1;
		while (i <= 25) {
			if (IsElementPresent(driver, By.xpath("(//XCUIElementTypeOther[@name='" + buttonNameString + "'])[" + i + "]"))) {
				try {
					new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.elementToBeClickable(By.xpath("(//XCUIElementTypeOther[@name='" + buttonNameString + "'])[" + i + "]//parent::XCUIElementTypeOther"))).click();
					ExtentManager.logInfoDetails("Clicked on appointment : <b> " + buttonName + " </b> button");
					break;
				} catch (Exception e) {
					i++;
					scrollDownTillElementVisible(driver, patientNameBy);
				}

			} else {
				i++;
			}
		}
		if (i == 25) {
			ExtentManager.logFailureDetails("No Appointment is visible");
			Assert.fail();
		}
	}
	
	//verify that Is patient details popup opened for same patient
	public void verifyPatientDetailsPopup()
	{
    	if(IsElementPresent(driver, popupPatientDetails))
    	{
    		ExtentManager.logInfoDetails("<b>Patient Details popup opened as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either patient details popup not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
	}
	
	// ______________click start recording button___________
	public void startRecordingButtonOnPatientDetailsPopup(String operation) {
		if (operation == "click") 
		{
			click(driver, buttonStartRecording, "Start Recording button on Patient Details popup");
		} else if (operation == "verify") {
			if(IsElementPresent(driver, buttonStartRecording))
			{
				ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed on Patient details popup as expected");
			}
			else {
				ExtentManager.logFailureDetails("Start Recording button not found on Patient Details popup");
				Assert.fail();
			}
		}
	}
	
	// ______________click continue recording button___________
	public void continueRecordingButtonOnPatientDetailsPopup(String operation) {
		
		if (operation == "click") 
		{
			click(driver, buttonContinueRecording, "Continue Recording button on Patient Details popup");
		} else if (operation == "verify") 
		{
			if(IsElementPresent(driver, buttonContinueRecording))
			{
				ExtentManager.logInfoDetails("<b>Continue Recording</b> button is displayed on Patient details popup as expected");
			}
			else {
				ExtentManager.logFailureDetails("Continue Recording button not found on Patient Details popup");
				Assert.fail();
			}
		}
	}

	// _____________To start and stop the audio_____________
	public void runAudio(long time)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File file = new File("//Users//mcl-0048//Desktop//dentscribe//audio_files//35_seconds_audio.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		String response = "P";
		while (!response.equals("Q")) {
			response = "p";
			response = response.toUpperCase();
			switch (response) {
			case ("P"):
				clip.start();
				Thread.sleep(time);
				clip.close();
				response = "Q";
				break;
			case ("S"):
				clip.stop();
				break;
			case ("R"):
				clip.setMicrosecondPosition(0);
				break;
			case ("Q"):
				clip.close();
				break;
			default:
				System.out.println("Not a valid response");
			}

		}
		System.out.println("Byeeee!");
	}

	// verify patient button name
	public boolean verifyReviewButton(String patient, String buttonName) {

		explicitWait(driver, AppiumBy.accessibilityId(patient + " Review-button"), 120);
		return IsElementPresent(driver, AppiumBy.accessibilityId(patient + " Review-button"));
	}

	public void verifyClickReviewButtonForAppointment(String operation, String patientName) throws InterruptedException 
	{
		if (operation == "verify")
		{
			new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
			waitUntilLoaderDisappear(driver);
			System.out.println("Loading Done");
	
			for (int i = 1; i <= 25; i++) {
				try {
					driver.findElement(AppiumBy.accessibilityId(patientName + " Review-button"));
					ExtentManager.logInfoDetails("<b>Appointment status is now Review button as expected for patient - " + patientName);
					break;
				} catch (Exception e) {
					Thread.sleep(10000); // Using static waits to till the soap report is generating
					new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
					waitUntilLoaderDisappear(driver);
					System.out.println("Loading Done");
					scrollDownTillElementVisible(driver, AppiumBy.accessibilityId(patientName + " Review-button"));
				}
			}
		}
		else if (operation == "click")
		{
			assertTrue(verifyReviewButton(patientName, "Review"));
			click(driver, AppiumBy.accessibilityId(patientName + " Review-button"),  "Review button on calendar page");
		}
		else {
			ExtentManager.logFailureDetails("Valid operations for Review button are verify or click. please check");
			Assert.fail();
		}
	}
	

	// To verify appointment status Reviewed or click in Reviewed button
	public void verifyClickReviewedButtonForAppointment(String operation, String patientName) 
	{
		waitUntilLoaderDisappear(driver);
		if (operation == "verify")
		{
			assertTrue(IsElementPresent(driver, AppiumBy.accessibilityId(patientName + " Reviewed-button")));
			ExtentManager.logInfoDetails(patientName + ": appointment status is changed to Reviewed as expected");
		}
		else if (operation == "click")
		{
			click(driver, AppiumBy.accessibilityId(patientName + " Reviewed-button"),  "<b>" + patientName + "<b> : Reviewed Button");
		}
		else {
			ExtentManager.logFailureDetails("Valid operations for Reviewed button are verify or click. please check");
			Assert.fail();
		}
	}
	
	// To verify appointment status Continue or click in Reviewed button
	public void verifyClickContinueButtonForAppointment(String operation, String patientName) 
	{
		waitUntilLoaderDisappear(driver);
		if (operation == "verify")
		{
			assertTrue(IsElementPresent(driver, AppiumBy.accessibilityId(patientName + " Continue-button")));
			ExtentManager.logInfoDetails(patientName + ": appointment status is changed to Continue as expected");
		}
		else if (operation == "click")
		{
			click(driver, AppiumBy.accessibilityId(patientName + " Continue-button"),  "Continue button of "+patientName+" on calendar view page");
		}
		else {
			ExtentManager.logFailureDetails("Valid operations for Continue button are verify or click. please check");
			Assert.fail();
		}
	}
	
	// To click on setting icon button 
	public void clickSettingIconButton() {
		waitUntilLoaderDisappear(driver);
		click(driver, iconSettingCalendarPage,  "Setting Icon on Calender Page");
	}
	
	// To click on Continue Recording button
	public void peformClickContinueRecording() {
		click(driver, continueRecordingButton,  "Continue Recording Button on Calendar view page");
	}
}
