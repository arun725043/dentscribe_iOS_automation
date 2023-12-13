package com.dentscribe.pages.ios;

import java.time.Duration;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class PatientSearchPage extends iOSActions {

	public By inputSearch = By.xpath("//XCUIElementTypeTextField[@name='search-input']");
	public By iconSettingSearchPage = By.xpath("(//XCUIElementTypeOther[@name='setting-icon-button'])[1]/XCUIElementTypeOther[2]");
	public By listOfPatient = By.xpath("");
	public By textWelcome = AppiumBy.accessibilityId("Welcome, Android Dev User!");
	public By patient = By.xpath("//XCUIElementTypeOther[@name='patient-info-card-click']");
	public By rightArrowIconButton = By.xpath("(//XCUIElementTypeOther[@name='patient-info-card-click'])[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeImage");
	public By noteInputSearch = By.xpath("//XCUIElementTypeStaticText[@name='Format:- Date (yyyy-mm-dd), Ph No. (1234567890)']");
	public By textNoMatchFound = By.xpath("//XCUIElementTypeStaticText[@name='No Matches found']");
	
	IOSDriver driver;

	public PatientSearchPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Verify whether Patient Search page exists or not
    public void validatePatientSearchPage()
    {
    	fetchingPatientLoader();
    	if(IsElementPresent(driver, inputSearch) || IsElementPresent(driver, noteInputSearch))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Patient Search page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either Patient Search page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
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
