package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;


public class ManageSubscriptionPage extends iOSActions {

	IOSDriver driver;

	public ManageSubscriptionPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Locator
	public By headerTextSubscriptionPage = By.xpath("(//XCUIElementTypeOther[@name='Manage your Subscription'])[4]");
	public By textSelectPlansHeadingBy = By.xpath("//XCUIElementTypeStaticText[@name='Select Subscription Tier']");
	public By freeTrial = AppiumBy.accessibilityId("30-Day Free Trial No credit card needed");
	public By paidSubcription = By.xpath("//XCUIElementTypeOther[@name='$699/Month']");
	public By addPaymentMethodButton = AppiumBy.accessibilityId("payment-add-or-edit-button");
	public By continueButton = By.xpath("(//XCUIElementTypeOther[@name='subscription-continue-button'])[2]");
	public By continueButtonThroughSettingPage = By.xpath("(//XCUIElementTypeOther[@name='Continue'])[2]");
	public By editIconPaymentMethod = By.xpath("//XCUIElementTypeOther[@name='payment-edit-icon']");

	//________verify whether Manage Subscription page exists or not_________ 
	public void validateManageSubscriptionPage()
	{
		if(IsElementPresent(driver, headerTextSubscriptionPage) && IsElementPresent(driver, textSelectPlansHeadingBy))
		{
			ExtentManager.logInfoDetails("<b>User is now on Manage Subscription page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Either Manage Subscription page not exists or opened or verifying header is not available or as expected. please check");
			Assert.fail();
		}
	}
		
	// To select the plan
	public void selectPlan(String plan) {
		switch (plan) {
		case "free":
			click(driver, freeTrial, "Free Trial on Manage Your Subscription page ");
			break;
		case "paid":
			click(driver, paidSubcription, "Paid Subscription on Manage Your Subscription page");
			break;
		default:
			System.out.println("Please enter valid value");
		}
	}
	
	// to perform click on 'Add Payment Method'
	public void clickAddPaymentButton() {
		click(driver, addPaymentMethodButton, "Add Payment Method");
	}
	
	// to perform click on 'Edit Payment Method'
	public void clickEditPaymentIcon() {
		click(driver, editIconPaymentMethod, "Edit Payment Method");
	}
	
	// To verify that "Continue button is disable"
	public boolean verifyContinueButtonDisable(By continueBtn, By ele) {
		boolean flag = true;
		
		click(driver, continueBtn, "Continue");
		
		flag = IsElementPresent(driver, ele); // if element is not present then flag will false and by default it is true
		
		return flag;
	}
	
	// To click on 'Continue' button 
	public void clickContinueButtonSubscriptionPage() {
		click(driver, continueButton, "Continue on subscription page");
	}
	
	// To click on 'Continue' Button through setting page
	public void clickOnContinueThroughSettingPage() {
		click(driver, continueButtonThroughSettingPage, "Continue on subscription page");
	}
}

