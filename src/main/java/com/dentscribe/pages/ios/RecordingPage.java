package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class RecordingPage extends iOSActions{
	
	IOSDriver driver;
	
	public RecordingPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public By headerTextRecordingPage = By.xpath("//XCUIElementTypeStaticText[@name='Record']");
	public By textStartRecording = By.xpath("//XCUIElementTypeStaticText[@name='• Recording']");
	public By pauseButton = AppiumBy.accessibilityId("pause-button");
	public By stopButton = AppiumBy.accessibilityId("stop-button");
	public By backIcon = By.xpath("//XCUIElementTypeOther[@name='Record']/XCUIElementTypeOther");
	public By doYouWantPausePopup = By.xpath("//XCUIElementTypeStaticText[@name='Do you want to pause the recording ?']");
	public By doYouWantPusePopupCancel = AppiumBy.accessibilityId("Cancel");
	public By doHYouWandPasuePopupOK = AppiumBy.accessibilityId("OK");
	
	//________verify whether Recording page exists or not_________ 
	public void validateRecordingPage()
	{
		if(IsElementPresent(driver, headerTextRecordingPage) && IsElementPresent(driver, textStartRecording))
		{
			ExtentManager.logInfoDetails("<b>User is now on Recording page as expected and recording is running");
		}
		else {
			ExtentManager.logFailureDetails("Either Recording not exists or opened or verifying note message is not available or as expected. please check");
			Assert.fail();
		}
	}
	
	// ____________click pause stop button_____________
	public void clickPauseStopButton(String buttonName) {
		if (buttonName == "pause") {
			click(driver, pauseButton, "pause button on Recording page");
		} else if (buttonName == "stop") {
			click(driver, stopButton, "stop button on Recording page");
		}
	}
	
	// To perform click on back icon button 
	public void perfromClickBackIconButton() {
		click(driver, backIcon, "Back Icon Button on recording page");
	}
	
	// To click on OK, or Cancel Button on 'Do you wand pause popup'
	public void performclick_OK_Cancel(String button) {
		
		switch(button) {
		case "ok": click(driver, doHYouWandPasuePopupOK, "OK on 'Do you want to pause the recording ?' popup");
		break;
		case "cancel": click(driver, doYouWantPusePopupCancel, "Cancel on 'Do you want to pause the recording ?' popup");
		}
		
	}
	
}
