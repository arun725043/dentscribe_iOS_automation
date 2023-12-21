package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import io.appium.java_client.ios.IOSDriver;

public class EulaAgreementPage extends iOSActions{
	
	IOSDriver driver;
	
	public EulaAgreementPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public By continueButton = By.xpath("//XCUIElementTypeOther[@name='eula-screen-continue-button']");
	public By headerTextEulaAgreementPage = By.xpath("//XCUIElementTypeOther[@name='End-User License Agreement (EULA)']");

	// Verify whether EULA Agreement page exists or not
    public void validateEulaAgreementPage()
    {
    	if(IsElementPresent(driver, headerTextEulaAgreementPage))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on EULA Agreement page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either EULA Agreement page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
	// To accept the 'EULA'
	public void performScroll_ClickContinueButton() {
		
	    for(int i = 1; i<=2; i++) {
	       performScroll(driver);
	    }
		click(driver, continueButton, "Continue");
	}
}
