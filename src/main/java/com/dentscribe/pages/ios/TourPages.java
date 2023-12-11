package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;


public class TourPages extends iOSActions {
	public TourPages(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	IOSDriver driver;

	// _________Objects_________
	LoginPage login = new LoginPage(driver);
	public boolean flag;
	public By textCalendarSchedule = By.xpath("//XCUIElementTypeStaticText[@name='Calendar Schedule View']");
	public By textParentListView = AppiumBy.accessibilityId("Patient List View");
	public By textPatientProfile = By.xpath("//XCUIElementTypeStaticText[@name='Patient Profile']");
	public By textRecording = By.xpath("//XCUIElementTypeStaticText[@name='Recording']");
	public By textSoapReport = By.xpath("//XCUIElementTypeStaticText[@name='SOAP Report']");
	public By textPatientDatabase = AppiumBy.accessibilityId("Patient Database Integration");  	
	public By op = By.xpath("//*[contains(@text='OP')]");
	public By buttonNext = By.xpath("//XCUIElementTypeOther[@name='tour-next-button']");
	public By buttonBack = By.xpath("//XCUIElementTypeOther[@name='tour-back-button']");
	public By linkSkip = By.xpath("(//XCUIElementTypeOther[@name='tour-skip-button'])[2]");

	// _________verify whether tour page exists or not
	public void validateTourPageCalendarScheduleView()
	{
		if (IsElementPresent(driver, textCalendarSchedule))
		{
			ExtentManager.logInfoDetails("<b>User is on expected Calendar Schedule View tour page");
		}
		else {
			ExtentManager.logFailureDetails("Either tour page not found/opened or expected Calendar Schedule View not found. Please checkssssss");
			Assert.fail();
		}
	}
	
	// _________skip tour slides______
	public void skipTourPages() {
		click(driver, linkSkip, "Skip");
	}

	
	// To verify the next button functionality
	public void verifyNextButtonFunctionality(String expectedCalendarslide) {
		
		verifyText(getText(driver, textCalendarSchedule), "Calendar Schedule View", "Calendar Schedule View slide");
		click(driver, buttonNext, "Next");

		verifyText(getText(driver, textParentListView), "Patient List View", "Patient List View slide");
		click(driver, buttonNext, "Next");
		
		verifyText(getText(driver, textPatientProfile), "Patient Profile", "Patient Profile slide");
		click(driver, buttonNext, "Next");
		
		verifyText(getText(driver, textRecording), "Recording", "Recording slide");
		click(driver, buttonNext, "Next");
		
		verifyText(getText(driver, textSoapReport), "SOAP Report", "SOAP Report  slide");
		click(driver, buttonNext, "Next");
		
		verifyText(getText(driver, textPatientDatabase), "Patient Database Integration", "Patient Database Integration slide");
		if (expectedCalendarslide == "yes")
		{
			click(driver, buttonNext, "Next");
		}
	}
	
	// To verify the back button functionality 
	public void verifyBackButtonFunctionality() {
		
		verifyText(getText(driver, textPatientDatabase), "Patient Database Integration", "Patient Database Integration slide");		
		
		click(driver, buttonBack, "Back");
		verifyText(getText(driver, textSoapReport), "SOAP Report", "SOAP Report  slide");
		
		click(driver, buttonBack, "Back");
		verifyText(getText(driver, textRecording), "Recording", "Recording slide");
		
		click(driver, buttonBack, "Back");
		verifyText(getText(driver, textPatientProfile), "Patient Profile", "Patient Profile slide");
		
		click(driver, buttonBack, "Back");
		verifyText(getText(driver, textParentListView), "Patient List View", "Patient List View slide");
		
		click(driver, buttonBack, "Back");
		verifyText(getText(driver, textCalendarSchedule), "Calendar Schedule View", "Calendar Schedule View slide");
	}
	
	// for swipe right functionality
	public void verifySwipeRightFunctionality() {
		
		verifyText(getText(driver, textCalendarSchedule), "Calendar Schedule View", "Calendar Schedule View slide");
		
		swipeRight();
		verifyText(getText(driver, textParentListView), "Patient List View", "Patient List View slide");

		swipeRight();
		verifyText(getText(driver, textPatientProfile), "Patient Profile", "Patient Profile slide");

		swipeRight();
		verifyText(getText(driver, textRecording), "Recording", "Recording slide");
		
		swipeRight();
		verifyText(getText(driver, textSoapReport), "SOAP Report", "SOAP Report  slide");
		
		swipeRight();
		verifyText(getText(driver, textPatientDatabase), "Patient Database Integration", "Patient Database Integration slide");			
	}
	
	// for swipe left functionality
	public void verifySwipeLeftFunctionality() {
		
		verifyText(getText(driver, textPatientDatabase), "Patient Database Integration", "Patient Database Integration slide");
		
		swipeLeft();
		verifyText(getText(driver, textSoapReport), "SOAP Report", "SOAP Report  slide");
		
		swipeLeft();
		verifyText(getText(driver, textRecording), "Recording", "Recording slide");
		
		swipeLeft();
		verifyText(getText(driver, textPatientProfile), "Patient Profile", "Patient Profile slide");
		
		swipeLeft();
		verifyText(getText(driver, textParentListView), "Patient List View", "Patient List View slide");
		
		swipeLeft();
        verifyText(getText(driver, textCalendarSchedule), "Calendar Schedule View", "Calendar Schedule View slide");
	}
	
	
	public void swipeRight() {
		new ActionsUtiils(driver).scroll(ScrollDirection.RIGHT, 0.80);
	}
	
	public void swipeLeft() {
		new ActionsUtiils(driver).scroll(ScrollDirection.LEFT, 0.80);
	}
	
}
