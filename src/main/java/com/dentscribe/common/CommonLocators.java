package com.dentscribe.common;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumBy;

public class CommonLocators {
	
	// Common Locators for IOS
	
	public static By passoword_ios = By.xpath("//XCUIElementTypeSecureTextField[@name='password-input']");
	public static By confirmPassowrd_ios = By.xpath("//XCUIElementTypeSecureTextField[@name='confirmpassword-input']");
	public static By returnButtonIosKeys = By.xpath("//XCUIElementTypeButton[@name='Return']");
	public static By textWelcome_ios = By.xpath("//XCUIElementTypeStaticText[@name='Welcome to Dentscribe!']");
	public static By txtUsername_ios = By.xpath("//XCUIElementTypeTextField[@name='username-input']");
	public static By msgUsernameIsRequired_ios = By.xpath("//XCUIElementTypeStaticText[@name='Username is required.']");
	public static By msgPasswordIsRequired_ios = By.xpath("//XCUIElementTypeStaticText[@name='Password is required.']");
	public static By continueButtonBy = By.xpath("//XCUIElementTypeOther[@name='Continue']");
	public static By allowButton_ios = AppiumBy.accessibilityId("Allow");
	public static By patientInfoLoader = AppiumBy.accessibilityId("loader-view");
}
