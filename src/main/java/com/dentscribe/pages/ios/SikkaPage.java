package com.dentscribe.pages.ios;

import java.time.Duration;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class SikkaPage extends iOSActions{

	IOSDriver driver;
	ActionsUtiils gestures;
	
	public SikkaPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// Locators
	public By proceedButton = By.xpath("//XCUIElementTypeLink[@name='Proceed']");   
	public By registerButton = By.xpath("//XCUIElementTypeStaticText[@name='Register']");   
	public By nextButton = AppiumBy.accessibilityId("Next");
	public By yesRadioButton = By.xpath("(//XCUIElementTypeStaticText[@name=''])[1]");
	public By agreeButton = AppiumBy.accessibilityId("Agree");
	public By userName = AppiumBy.accessibilityId("Username");
	public By password = AppiumBy.accessibilityId("Password");
	public By roundSpinningLoading = By.xpath("//XCUIElementTypeImage[@name='processing...']");
	public By plsWaittxt = By.xpath("//XCUIElementTypeStaticText[@name='please wait...']");
	public By plsWaitLoader = By.xpath("//XCUIElementTypeOther[@name='Sikka Software Software Marketplace']/XCUIElementTypeOther[2]/XCUIElementTypeOther[5]/XCUIElementTypeImage");
	public By signatureRequired = By.xpath("//XCUIElementTypeStaticText[@name='Signature Required']");
	public By mouseIcon = By.xpath("//XCUIElementTypeStaticText[@name='Please use your mouse to Sign']");
	
	// ________verify whether Sikka WebView opened or not_________
	public void validateSikkaWebViewPage()
	{
		if (IsElementPresent(driver, registerButton) || IsElementPresent(driver, proceedButton))
		{
			ExtentManager.logInfoDetails("<b>User is now on Sikka Webview Page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either expected sikka webview page not opened or verfying elements register/proceed button not found. Please check");
			Assert.fail();
		}
	}
	
	public void click_registerButton() {
		click(driver, proceedButton, "Proceed on sikka page");
		click(driver, registerButton, "Register on sikka page");
		iOSBase.wait.until(ExpectedConditions.invisibilityOfElementLocated(plsWaittxt));
		System.out.println("Loading Done");
	}

	// To click on yes radio button 
	public void clickYes() {
		for(int i=1; i<=3; i++)
			performScroll(driver);
		click(driver, nextButton, "Next");
		gestures = new ActionsUtiils(driver);
		gestures.pullToRefres(ScrollDirection.UP, 0.10);
		click(driver, yesRadioButton, "Yes");
	}
	
	// Perform wait till the loading is done on sikka page
	public void waitLoadingDone() {
		iOSBase.wait.until(ExpectedConditions.invisibilityOfElementLocated(roundSpinningLoading));
		System.out.println("Loading Done");
	}
	
	// To accept the agreement
	public void acceptAgreement() {
		int i = 1;
		
		while(true && i<=60) {
			
			try {
				
				new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.elementToBeClickable(agreeButton));
				break; 
			} catch (Exception e) {
				gestures.scroll(ScrollDirection.DOWN, 0.50);
				i++;
			}
		}
	
		click(driver, agreeButton, "Agree");
	}
	
	// To fill the order details
	public void fillExistingSikkaCredentials(String sikkaUsername, String sikkaPassword) {
		sendKeys(driver, userName, sikkaUsername, "User Name on sikka page");
		sendKeys(driver, password, sikkaPassword, "Password on sikka page");
		 click(driver, AppiumBy.accessibilityId("Done"), "Done Button on keyboard");
		 gestures.dragNDrop(driver.findElement(signatureRequired), driver.findElement(mouseIcon));
		 performScroll(driver);
		 click(driver, nextButton, "Next on order page on sikka");
		 iOSBase.wait.until(ExpectedConditions.invisibilityOfElementLocated(plsWaittxt));
	}
}