package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;

public class RecordingPage extends iOSActions{
	
	IOSDriver driver;
	
	public RecordingPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public By buttonStartRecording = By.xpath("//XCUIElementTypeOther[@name='Start-recording']");
	public By pauseButton = AppiumBy.accessibilityId("pause-button");
	public By stopButton = AppiumBy.accessibilityId("stop-button");
	public By backIcon = By.xpath("//XCUIElementTypeOther[@name='Record']/XCUIElementTypeOther");
	public By doYouWantPausePopup = By.xpath("//XCUIElementTypeStaticText[@name='Do you want to pause the recording ?']");
	public By doYouWantPusePopupCancel = AppiumBy.accessibilityId("Cancel");
	public By doHYouWandPasuePopupOK = AppiumBy.accessibilityId("OK");
	
	// ______________click start recording button___________
	public boolean clickVerifyStartRecording(String operation) {
		
		boolean flag = false;
		if (operation == "click") {
			click(driver, buttonStartRecording, "Start Recording");
		} else if (operation == "verify") {
			flag = IsElementPresent(driver, buttonStartRecording);
		}
		return flag;
	}
	
	// click on pause button 
	public void performClickPause() {
		click(driver, pauseButton, "Pause buton on recording page");
	}
	
	// ____________click pause stoop button_____________
	public void clickPauseStopButton(String buttonName) {
		if (buttonName == "pause") {
			click(driver, pauseButton, "pause");
		} else if (buttonName == "stop") {
			click(driver, stopButton, "stop");
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
