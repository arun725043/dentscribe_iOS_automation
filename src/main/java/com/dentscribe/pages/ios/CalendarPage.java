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
	public By iconSearch = AppiumBy.accessibilityId("search-icon-button");
	public By iconSettingSearchPage = By.xpath("(//XCUIElementTypeOther[@name='setting-icon-button'])[1]/XCUIElementTypeOther[2]");
	public By iconSetting_calendar = By.xpath("");
	public By inputSearch = By.xpath("//XCUIElementTypeTextField[@name='search-input']");
	public By inputMonthYear = AppiumBy.accessibilityId("downarrow-icon-button");
	public By selectedMonth = By.xpath("//XCUIElementTypeStaticText[@name='month-text']");
	public By buttonWhileUsingThisApp = By.xpath("");
	public By doneButton = By.xpath("//XCUIElementTypeOther[@name='calendar-done-button']");
	public By dropdownCalendar = AppiumBy.accessibilityId("downarrow-icon-button");
	public By rightArrowIcon = AppiumBy.accessibilityId("rightarrow-icon-button");
	public By leftArrowIcon = AppiumBy.accessibilityId("leftarrow-icon-button");
	public By rightArrowIconCalendar = AppiumBy.accessibilityId("calendar-display.header.rightArrow");
	public By leftArrowIconCalendar = AppiumBy.accessibilityId("calendar-display.header.leftArrow");
	public By monthYearCalendar = AppiumBy.accessibilityId("calendar-display.header.title");
	public By patientNameOnRecording = By
			.xpath("//XCUIElementTypeStaticText[@name='Name:']//following-sibling::XCUIElementTypeStaticText");
	public By allowMicOk = By.xpath("//XCUIElementTypeButton[@name='OK']");
	public By resumeRecording = By.xpath("(//XCUIElementTypeOther[@name='OK'])[2]");
	public By errorPopupOk = By.xpath("(//XCUIElementTypeOther[@name=\"OK\"])[2]");
	public By allowNotificationsPopupBy = By.xpath("//XCUIElementTypeStaticText[@name='DentScribe” Would Like to Send You Notifications']");
	public By allowButtonNotificationPopupBy = By.xpath("//XCUIElementTypeButton[@name='Allow']");

	public By backButton = By.xpath(
			"(//XCUIElementTypeOther[@name='Welcome, Android Dev User! search-input'])[4]/XCUIElementTypeOther[1]/XCUIElementTypeOther");
	public By settingIconButton = AppiumBy.accessibilityId("setting-icon-button");
	public By continueRecordingButton = AppiumBy.accessibilityId("Continue-recording");

	// Verify whether Calendar page exists or not
    public void validateCalendarPage()
    {
    	clickAllowButtonOnNotificationsPopup();
    	if(IsElementPresent(driver, dropdownCalendar) && IsElementPresent(driver, rightArrowIcon))
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
			click(driver, allowMicOk,  "Ok");
			// click(driver, resumeRecording,  "OK");

		} catch (Exception e) {
			System.out.println("catch while using app");
		}
	}

	// _______________verify user is landing on search page or not______________
	public boolean verifySearchLandingPage() {
		click(driver, iconSearch,  "Search Icon");
		fetchingPatientLoader();
		return IsElementPresent(driver, inputSearch);
	}

	// ___________fetch patient loader_______
	public void fetchingPatientLoader() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'Fetching Patient')]")));
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//*[contains(@text,'Fetching Patient')]")));
		} catch (Exception e) {
			System.out.println("Loaded");
		}
	}

//	// ___________select month and year_____________
//	public void selectMonthYear(String month, String year, String day) throws InterruptedException {
//		clickUsingResourceId("rightarrow-icon-button");
//		Thread.sleep(3000);
//		String monthYear = getText(inputMonthYear);
//		String[] data = monthYear.split(" ");
//		System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
//		while (Integer.parseInt(year) != Integer.parseInt(data[1])) {
//			if (Integer.parseInt(year) > Integer.parseInt(data[1])) {
//				clickUsingResourceId("rightarrow-icon-button");
//			} else {
//				clickUsingResourceId("leftarrow-icon-button");
//			}
//			data[1] = getText(inputMonthYear).split(" ")[1];
//
//		}
//
//		int expectedMonth = Month.valueOf(month.toUpperCase()).getValue() - 1;
//		String monthName = getText(inputMonthYear).split(" ")[0];
//		int actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
//		while (actualMonth != expectedMonth) {
//			if (actualMonth < expectedMonth) {
//				clickUsingResourceId("rightarrow-icon-button");
//				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
//			} else {
//				clickUsingResourceId("leftarrow-icon-button");
//				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
//			}
//			monthName = getText(inputMonthYear).split(" ")[0];
//			actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
//		}
//		selectDate(day);
//	}

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

//			int expectedMonth = Month.valueOf(month.toUpperCase()).getValue() - 1;
//		String monthName = getText(inputMonthYear).split(" ")[0];
//		int actualMonth = Month.valueOf(monthName.toUpperCase()).getValue();
//		while (actualMonth != month - 1) {
//			if (actualMonth < month - 1) {
//				// clickUsingResourceId("rightarrow-icon-button");
//				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
//			} else {
//				// clickUsingResourceId("leftarrow-icon-button");
//				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
//			}
//			monthName = getText(inputMonthYear).split(" ")[0];
//			actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
//		}
//		// selectDate(day);
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
//			System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
		while (year != Integer.parseInt(data[1])) {
			if (year > Integer.parseInt(data[1])) {
				click(driver, rightArrowIconCalendar,  "Right Arrow Icon Button Calendar");
			} else {
				click(driver, leftArrowIconCalendar,  "Left Arrow Icon Button Calendar");
			}
			data[1] = getAttribute_ios(monthYearCalendar, "label").split(" ")[1];

		}
		// int expectedMonth = Month.valueOf(month.toUpperCase()).getValue() - 1;
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

			// XCUIElementTypeButton[@name="calendar-display.day_2024-08-06"]
			click(driver, By.xpath(
					"//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-0" + month + "-0" + day + "']"),
					 "day");
		} else if (day <= 9 && month > 9) {
			
			driver.findElement(By.xpath("//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-" + month + "-0" + day + "']")).click();
			
//			click(driver, By.xpath(
//					"//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-" + month + "-0" + day + "']"),
//					 "day");
		} else if (day > 9 && month <= 9) {
			click(driver, By.xpath(
					"//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-0" + month + "-" + day + "']"),
					 "day");
		} else {
			// XCUIElementTypeButton[@name="calendar-display.day_2023-11-14"]
			driver.findElement(By.xpath("//XCUIElementTypeButton[@name='calendar-display.day_" + year + "-" + month + "-" + day + "']")).click();
			// click(driver, By.xpath("//XCUIElementTypeButton[@name='calendar-display.day_"+year+"-"+month+"-"+day+"']"),
			//  "day");
		}
	}

	// ___________click patient which has start button______________
	public void clickPatient() {

		int i = 1;
		while (i <= 25) {
			if (IsElementPresent(driver, By.xpath("(//XCUIElementTypeOther[@name='Start-button'])[" + i + "]"))) {
				try {
					new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.elementToBeClickable(By.xpath("(//XCUIElementTypeOther[@name='Start-button'])[" + i + "]//parent::XCUIElementTypeOther"))).click();
					ExtentManager.logInfoDetails("Clicked on : <b> Start </b> button");
					break;
				} catch (Exception e) {
					i++;
				}

			} else {
				i++;
			}
		}
		if (i == 25) {
			ExtentManager.logFailureDetails("No Appointment is visible");
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
	public boolean verifyPatientButton(String patient, String buttonName) {

		explicitWait(driver, AppiumBy.accessibilityId(patient + " Review-button"), 120);
		return IsElementPresent(driver, AppiumBy.accessibilityId(patient + " Review-button"));
	}

	public void verifySoapReport(String patientName, By textSoapReport) throws InterruptedException {
		new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");

//		if(IsElementPresent(driver, AppiumBy.accessibilityId(patientName+" Review-button"))) {
//			for(int i = 1; i<=12; i++) {
//				try {
//					driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+patientName+" Review-button'])[2]"));
//					
//				} catch (Exception e) {
//					Thread.sleep(10000);  // Using static waits to till the soap report is generating
//					new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
//					waitUntilLoaderDisappear(driver);
//					System.out.println("Loading Done");
//				}
//			}
//			
//			assertTrue(verifyPatientButton(patientName, "Review"));
//			click(driver, By.xpath("(//XCUIElementTypeOther[@name='"+patientName+" Review-button'])[2]"),  "Review");
//			verifyText(getText(textSoapReport), "SOAP Report", "Soap Report Page");
//		}
//		else {
//			for(int i = 1; i<=12; i++) {
//				try {
//					driver.findElement(AppiumBy.accessibilityId(patientName+" Review-button"));
//				} catch (Exception e) {
//					Thread.sleep(10000);  // Using static waits to till the soap report is generating
//					new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
//					waitUntilLoaderDisappear(driver);
//					System.out.println("Loading Done");
//				}
//			}
//			
//			assertTrue(verifyPatientButton(patientName, "Review"));
//			click(driver, AppiumBy.accessibilityId(patientName+" Review-button"),  "Review");
//			verifyText(getText(textSoapReport), "SOAP Report", "Soap Report Page");
//		}

		for (int i = 1; i <= 25; i++) {
			try {
				driver.findElement(AppiumBy.accessibilityId(patientName + " Review-button"));
			} catch (Exception e) {
				Thread.sleep(10000); // Using static waits to till the soap report is generating
				new ActionsUtiils(driver).pullToRefres(ScrollDirection.UP, 0.15);
				waitUntilLoaderDisappear(driver);
				System.out.println("Loading Done");
			}
		}

		assertTrue(verifyPatientButton(patientName, "Review"));
		click(driver, AppiumBy.accessibilityId(patientName + " Review-button"),  "Review");
		verifyText(getText(driver, textSoapReport), "SOAP Report", "Soap Report Page");

	}
	

	// To update the signature in soap report and verify the review button
	public boolean verifyReviewedButton(String patientName) {
		waitUntilLoaderDisappear(driver);
		return IsElementPresent(driver, AppiumBy.accessibilityId(patientName + " Reviewed-button"));

	}

	// To click on Reviewed Button
	public void clickReviewedButton(String patientName) {
		waitUntilLoaderDisappear(driver);
		click(driver, AppiumBy.accessibilityId(patientName + " Reviewed-button"),  patientName + ": Reviewed Button");
	}
	
	// To click on setting icon button 
	public void clickSettingIconButton() {
		waitUntilLoaderDisappear(driver);
		click(driver, settingIconButton,  "Setting Icon Button on Calender Page");
	}

	// To verify the Continue button 
	public boolean verifyContinueButton(String patientName) {
		waitUntilLoaderDisappear(driver);
		return IsElementPresent(driver, AppiumBy.accessibilityId(patientName + " Continue-button"));
	}

	// To click on Continue button
	public void performClickContinue(String patientName) {
		waitUntilLoaderDisappear(driver);
		click(driver, AppiumBy.accessibilityId(patientName + " Continue-button"),  "Continue button of "+patientName+" on calendar view page");
		
	}
	
	// To click on Continue Recording button
	public void peformClickContinueRecording() {
		click(driver, continueRecordingButton,  "Continue Recording Button on Calendar view page");
	}
}
