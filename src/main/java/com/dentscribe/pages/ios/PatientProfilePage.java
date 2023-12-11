package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;

import io.appium.java_client.ios.IOSDriver;

public class PatientProfilePage extends iOSActions{	
	
	// Locator
	public By textPatientProfil = By.xpath("//XCUIElementTypeStaticText[@name='Patient Profile']");
	public By patientName = By.xpath("//XCUIElementTypeStaticText[@name=‘DOB: ‘]//parent::XCUIElementTypeOther//parent::XCUIElementTypeOther//preceding-sibling::XCUIElementTypeOther");
	public By iconBackPatientProfile = By.xpath("//XCUIElementTypeOther[@name='Patient Profile']/XCUIElementTypeOther");
	
	
	IOSDriver driver;

	public PatientProfilePage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	// To verify that user navigated on patient profile page
	public void verifyPatientProfileText() {
		verifyText(getText(driver, textPatientProfil), "Patient Profile", "Patient Profile");
	}
	
	// To verify the patient name
	public boolean verifyPatientName(String name) {
		
		String n = name.split(" ")[0].trim();
		return IsElementPresent(driver,	By.xpath("//XCUIElementTypeStaticText[contains(@name, '"+n+"')]"));
	}
	
	// To verify that user navigated on patient search page on Back icon click
	public void clickPatientProfileBackIcon() {
		click(driver, iconBackPatientProfile, "Click on Back Icon button on patient profile page");
	}
	
}
