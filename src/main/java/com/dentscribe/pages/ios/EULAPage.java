package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;

public class EULAPage extends iOSActions{
	
	IOSDriver driver;
	
	public EULAPage(IOSDriver driver) {
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
		
		//new ActionsUtiils(driver).scroll(ScrollDirection.DOWN, 0.80);
		//scrollToActiveTheAgreeButton(2);
		click(driver, continueButton, "Continue");
	}
	
	public void scrollToActiveTheAgreeButton(int n)  {
        // Java
        for(int i=0;i<n;i++)
        {
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("left", 200, "top", 1000,
                    "width", 800, "height", 900, "direction", "up", "percent", 0.75));
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
