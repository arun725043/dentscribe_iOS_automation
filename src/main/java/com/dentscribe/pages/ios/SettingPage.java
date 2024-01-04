package com.dentscribe.pages.ios;

import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.ActionsUtiils;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;


public class SettingPage extends iOSActions {

	IOSDriver driver;

	public SettingPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public By manageSubscriptionButton = By.xpath("//XCUIElementTypeOther[@name='cancel-suscription-button']");	
	public By cancelButton = AppiumBy.accessibilityId("cancel-suscription-button");
	public By pushNotificationSwitch = AppiumBy.accessibilityId("push-notification-switch");
	public By smsNotificationSwitch = AppiumBy.accessibilityId("sms-notification-switch");
	public By emailNotificationSwitch = AppiumBy.accessibilityId("email-notification-switch");
	public By textPushNotificatiionUpdated = AppiumBy.accessibilityId("Push notification updated!");
	public By textSmsNotificationUpdated = AppiumBy.accessibilityId("Sms notification updated!");
	public By textEmailNotificationUpdated = AppiumBy.accessibilityId("Email notification updated!");
	public By accountInfoLabel = AppiumBy.accessibilityId("Account Info");
	public By inputPhoneNumber = AppiumBy.iOSClassChain("**/XCUIElementTypeTextField[`name == 'phone-input'`][1]");
	public By saveButton = AppiumBy.accessibilityId("account-info-save-button");
	public By textUserDetailUpdated = AppiumBy.accessibilityId("User details updated!");
	public By inputZipCode = By.xpath("//XCUIElementTypeTextField[@name='zipcode-input']");
	public By zipCodeLabel = By.xpath("//XCUIElementTypeStaticText[@name='Zip Code']");
	public By textSetting = By.xpath("//XCUIElementTypeStaticText[@name='Settings']");
	public By textHeadingGeneralSettingsBy = By.xpath("//XCUIElementTypeStaticText[@name='General Settings']");
    public By backIconButton = By.xpath("//XCUIElementTypeOther[@name='Settings']/XCUIElementTypeOther[1]");
    public By phoneNumberLabel = By.xpath("//XCUIElementTypeStaticText[@name='Phone Number']");
    public By inputCity = By.xpath("//XCUIElementTypeTextField[@name='buildings-input']");
    public By buttonContinue = AppiumBy.accessibilityId("Continue");
    public By textPracticeInfoUpdated = AppiumBy.accessibilityId("Practice information updated!");
    public By inputCurentPassword = By.xpath("//XCUIElementTypeTextField[@name='current-password-input']");
    public By inputNewPassword = By.xpath("//XCUIElementTypeTextField[@name='new-password-input']");
    public By reasonDropdown = By.xpath("//XCUIElementTypeTextField[@name='reason-select-input']");
    public By reasonTooExpensive = AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`name == 'It’s too expensive'`][2]");
    public By inputAddDetais = AppiumBy.accessibilityId("add-description-input");
    public By submitButtonReason = By.xpath("(//XCUIElementTypeOther[@name='cancel-subscription-submit-button'])[2]");
    public By addDetailsLabel = By.xpath("//XCUIElementTypeStaticText[@name='Add Details*']");
    public By subscriptionPlan = By.xpath("//XCUIElementTypeStaticText[contains(@name, 'Selected')]");
    public By allFieldsAreMandatoryMessage = AppiumBy.accessibilityId("All fields are mandatory");
    public By closeButtonONCanacaleSubPopup = AppiumBy.accessibilityId("subscription-close-button");
    public By buttonLogOut = AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`name == 'Log Out'`]");
    public By buttonFeedback = AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Feedback'`]");
    public By helpIconButtonTop = By.xpath("//XCUIElementTypeOther[@name='Settings']/XCUIElementTypeOther[2]");
	public By helpOptionUnderGeneralSettings = AppiumBy.accessibilityId("Help-right-arrow");
    
    // Verify whether Settings page exists or not
    public void validateSettingsPage()
    {
    	if(IsElementPresent(driver, textSetting) && IsElementPresent(driver, textHeadingGeneralSettingsBy))
    	{
    		ExtentManager.logInfoDetails("<b>User is now on Settings page as expected");
    	}
    	else {
			ExtentManager.logFailureDetails("Either settings page not exists or not opened or verifying element not found. please check");
			Assert.fail();
		}
    }
    
    // To selected plan exists or not on settings page
 	public void verifyBuyPlanOnSettingsPage(String plan) {
 		switch (plan) {
 		case "free":
 			verifyText(getText(driver, subscriptionPlan), "30-Day Free Trial Selected ", "30-Days Free Trial Selected");
 		    verifyText(getAttribute_ios(manageSubscriptionButton, "label"), "Manage Subscription", "Manage Subscription button");
 			break;
 		case "paid":
 			verifyText(getText(driver, subscriptionPlan), "$699/Month Selected ", "$699/Month Selected ");
 			verifyText(getAttribute_ios(manageSubscriptionButton, "label"), "Cancel", "Cancel button");
 			break;
 		default:
 			ExtentManager.logFailureDetails("Plan name could be only free or paid.");
 			Assert.fail();
 		}
 	}
    
	// To click on Logout
	public void clickOnLogout() {
		click(driver, buttonLogOut, "Logout on Setting page");
	}
	
	// To click on Feedback
	public void clickOnFeedbackOption() {
		click(driver, buttonFeedback, "Feedback on Setting page");
	}
	
	// To click on 'Manage Subscription'
	public void clickManageSubscriptionButton() {
		click(driver, manageSubscriptionButton, "Manage Subscription on setting page");
	}

	// To click on Help Icon present in Nav Options
	public void performClickVerifyHelpIconOnTop() {
		click(driver, helpIconButtonTop, "Help Icon on Navbar on setting page");
	}
	
	// to click on help option on general settings 
	public void performClickOnHelpOtion() {
		scrollUpTillElementVisible(driver, helpOptionUnderGeneralSettings);
		click(driver, helpOptionUnderGeneralSettings, "Help Option User General Settings");
	}
	
	// To verify the notifications Push, SMS, and email
	public void verifyNotifications(String notificationType) 
	{
		switch (notificationType) {
		case "push":
			if(getText(driver, pushNotificationSwitch).equals("1"))
				driver.findElement(pushNotificationSwitch).click();
			
			click(driver, pushNotificationSwitch, "Push Notification on setting page");
			verifyText(getAttribute_ios(textPushNotificatiionUpdated, "name"), "Push notification updated!","Push notification updated on setting page");
			
			if(getText(driver, pushNotificationSwitch).equals("1"))
				ExtentManager.logInfoDetails("Push Notification is on as expected");
			else
				ExtentManager.logFailureDetails("Push Notificatiion is off is not expected");
			break;
		case "sms":
			if(getText(driver, smsNotificationSwitch).equals("1"))
				driver.findElement(smsNotificationSwitch).click();
			
			click(driver, smsNotificationSwitch, "Sms Notification on setting page");
			verifyText(getAttribute_ios(textSmsNotificationUpdated, "name"), "Sms notification updated!","Sms notification updated on setting page");
			
			if(getText(driver, smsNotificationSwitch).equals("1"))
				ExtentManager.logInfoDetails("Sms Notification is on as expected");
			else
				ExtentManager.logFailureDetails("Sms Notificatiion is off is not expected");
			break;
		case "email":
			if(getText(driver, emailNotificationSwitch).equals("1"))
				driver.findElement(emailNotificationSwitch).click();
			
			click(driver, emailNotificationSwitch, "Email Notification on setting page");
			verifyText(getAttribute_ios(textEmailNotificationUpdated, "name"), "Email notification updated!","Email notification updated on setting page");
			
			if(getText(driver, emailNotificationSwitch).equals("1"))
				ExtentManager.logInfoDetails("Email Notification is on as expected");
			else
				ExtentManager.logFailureDetails("Email Notificatiion is off is not expected");
			break;
		default:
			ExtentManager.logFailureDetails("Notification type could be onlu pus/sms/email");
			break;
		}
	}
	
	// To update the phone number under account info on setting page
	 public String updatePhoneNumber() {
		 performScroll(driver);
		 clear_ios(inputPhoneNumber);
		 String phoneNumber = "9" + GenerateRandomNumber(9);
		 sendKeys(driver, inputPhoneNumber,phoneNumber , "Phone Number");
		 new ActionsUtiils(driver).scroll(ScrollDirection.DOWN, 0.10);
		 click(driver, saveButton, "Save Button on setting page");
		 verifyText(getAttribute_ios(textUserDetailUpdated, "name"), "User details updated!", "User details updated! on setting page");
		 return phoneNumber;
	 }
	 
	 // To update the zip code in practice in info section on setting page
	 public void updateZipCode() {
		 performScroll(driver);
		 
	 }
	 
	 // To perform the back Icon button 
	 public void performClickBackIcon() {
		 click(driver, backIconButton, "Back Icon Button on setting page");
	 }

	 // To verify the phone number
	public void verifyPhoneNumber(String phoneNum) {
		performScroll(driver);
		
		verifyText(getText(driver, inputPhoneNumber), phoneNum, phoneNum);
		
	}

	// To update the city
	public String updateCity() {
		performScroll(driver);
		performScroll(driver);
		clear_ios(inputCity);
		String city = "Testing"+GenerateRandomNumber(4);
		sendKeys(driver, inputCity, city, "City input field");
		hideKeyboard();
        performScroll(driver);
		click(driver, buttonContinue, "Contine button on setting page");
		verifyText(getAttribute_ios(textPracticeInfoUpdated, "name"), "Practice information updated!", "Practice information updated!");
		return city;
	}

	// To update the city
	public void verifyUpdatedCity(String city) {
		for(int i = 1; i<=3; i++) {
			performScroll(driver);
		}
		verifyText(getAttribute_ios(inputCity, "value"), city, city);
	}

	// To update the password 
	public void updatePassword(String currentPassword, String newPassword) {
	
		new ActionsUtiils(driver).scroll(ScrollDirection.DOWN, 0.45);
		
		sendKeys(driver, inputCurentPassword, currentPassword, "Current Password");
		hideKeyboard();
		sendKeys(driver, inputNewPassword, newPassword, "New Password");
		hideKeyboard();
		click(driver, saveButton, "Save button on setting page");
		
		// To update the password in ChangePassword file
		writeData("ChangePassword", "currentPassword", newPassword);
		writeData("ChangePassword", "oldPassword", currentPassword);
	}
	
	// To perform cancel the subscription
	public void performCancelSubscription() {
		click(driver, cancelButton, "Cancel butotn on Setting page");
		click(driver, reasonDropdown, "Reason Dropdown");
		click(driver, reasonTooExpensive, "It’s too expensive Reason");
		sendKeys(driver, inputAddDetais, "Cancellation Testing through Automation", "Add Details ");
		click(driver, addDetailsLabel, "Add Details Label");
		click(driver, submitButtonReason, "Submit Button");
	}
	
	// to verify the all field are mandatory text
	public void verifyMandatoryFields() {
		// To verify both reason dropdown and details input fields
		click(driver, cancelButton, "Cancel button on Setting page");
		click(driver, submitButtonReason, "Submit Button");
		
		verifyText(driver.findElement(allFieldsAreMandatoryMessage).getAttribute("name"), "All fields are mandatory", "All fields are mandatory");
		
		// To validate details pop up
		click(driver, reasonDropdown, "Reason Dropdown");
		click(driver, reasonTooExpensive, "It’s too expensive Reason");
		verifyText(driver.findElement(allFieldsAreMandatoryMessage).getAttribute("name"), "All fields are mandatory", "All fields are mandatory");
		
		
		sendKeys(driver, inputAddDetais, "Testing", "Add Detais ");
		click(driver, addDetailsLabel, "Add Details Label");
	
	}
	
	// to perform click on close icon button 
	public void performClickOnCloseIconButton() {
		click(driver, closeButtonONCanacaleSubPopup,"Close Icon Button");
	}
}
