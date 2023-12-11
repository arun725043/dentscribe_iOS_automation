package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class PatientSearchPage extends iOSActions {

	public By inputSearch = By.xpath("//XCUIElementTypeTextField[@name='search-input']");
	public By listOfPatient = By.xpath("");
	public By textWelcome = AppiumBy.accessibilityId("Welcome, Android Dev User!");
	public By iconSetting= AppiumBy.accessibilityId("setting-icon-button");
	public By patient = By.xpath("//XCUIElementTypeOther[@name='patient-info-card-click']");
	public By rightArrowIconButton = By.xpath("(//XCUIElementTypeOther[@name='patient-info-card-click'])[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeImage");
	public By dateFormat = By.xpath("//XCUIElementTypeStaticText[@name='Format:- Date (yyyy-mm-dd), Ph No. (1234567890)']");
	public By textNoMatchFound = By.xpath("//XCUIElementTypeStaticText[@name='No Matches found']");
	
	IOSDriver driver;

	public PatientSearchPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// ______________verify user is able to search patient or not______________

	//________searching the patient___________________
	public void searchPatient(String phone) {
		clear_ios(inputSearch);
        waitUntilLoaderDisappear(driver);
        System.out.println("Loading Done");
		sendKeys(driver, inputSearch, phone, "Search Input");
	}

	// ______________verify patient is searched or not_________
	public boolean verifySearchedPatient(String... info) {
		
		return IsElementPresent(driver, By.xpath("(//XCUIElementTypeStaticText[@name='"+ info[0] +"'])[1]"));
	}
	
	// To click on the patient name 
	public void performClickONPatient() {
		hideKeyboard();
		waitUntilLoaderDisappear(driver);
		click(driver, rightArrowIconButton, "Right Arrow Icon Button");
	}
	
	// To verify the 'No Matches found'
	public void verifyNoMatchesFound() {
		sendKeys(driver, inputSearch, "Testing", "Search Input");
		hideKeyboard();
		waitUntilLoaderDisappear(driver);
		verifyText(getText(driver, textNoMatchFound), "No Matches found", "No Matches found. on patient search page");
	}

}
