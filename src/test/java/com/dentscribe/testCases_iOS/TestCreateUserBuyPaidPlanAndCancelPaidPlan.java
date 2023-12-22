package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertFalse;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;

public class TestCreateUserBuyPaidPlanAndCancelPaidPlan extends iOSBase 
{
	String emailId = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";
	String pass = "Pass@" + CommonMethods.GenerateRandomNumber(5);

	@Test(priority = 1)
	public void verifySignupNewUser() throws InterruptedException 
	{
		// ____________________Application launched____________________
		loginPage.verifyIsApplicationLaunched();
		CommonVariables.appVersion = getText(driver, CommonLocators.versionApplicationBy);
		
		// ____________________click on Sign Up tab and verify it____________________
		click(driver, signUpPage.signupButton, "Sign Up");
		signUpPage.validateSignUpPage();
		
		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(generateRandomFirstName(), genrateRandomLastName(), "9", readData("testData", "mobile"), emailId, "title" + GenerateRandomNumber(3), 
				String.valueOf(GenerateRandomNumber(6)), pass, pass, "Dentrix"); // spelling need to be correct to this method
		signUpPage.verifyConfirmationPopup();

		//___________Click on Confirmation popup button______________
		signUpPage.clickConfirmationPopupButton("continue");
		
		//___________Enter the OTP_______
		
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifySignupNewUser" })
	public void verifyCreatePracticeInfo() throws InterruptedException 
	{
		// _________________fill Practice form and navigate to Sikka webview________________________
		practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"), "9", readData("testData", "mobile"));
		practiceInfoPage.clickContinueButtonPracticeInfo();
		explicitWait(driver, sikkaPage.registerButton, 120);
		sikkaPage.validateSikkaWebViewPage();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyCreatePracticeInfo" })
	public void verifySikkaWebViewRegistration() throws InterruptedException
	{
		//_______________Click on 'Yes' Radio Button and accept the Terms of Services__________________________
		sikkaPage.waitLoadingDone();
		sikkaPage.click_registerButton();
		sikkaPage.clickYes();
		sikkaPage.acceptAgreement();
		
		// __________________Fill the confirmation page______________________________
		sikkaPage.fillExistingSikkaCredentials(readData("UserDetails", "existingSikkaUser"), readData("UserDetails", "existingSikkaPwd"));
		explicitWait(driver, loginPage.noteLoginPage, 120);
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifySikkaWebViewRegistration" })
	public void verifySpuInstallPopupAndRefreshData() throws IOException, InterruptedException {		
		// ________________login application and verify SPU install popup____________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "spu popup");
		
		// ________________By pass the manual sikka refresh steps______________
		GenerateOTP.updateOfficeId(emailId, readData("testData", "dentrix"));
		ExtentManager.logInfoDetails("Sikka refresh done");
	}

	@Test(priority = 5, dependsOnMethods = { "verifySpuInstallPopupAndRefreshData" })
	public void verifyEulaAgreementPageAndAcceptAgreement() throws IOException, InterruptedException {
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "sms page");
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify EULA Agreement page and accept agreement
		eulaPage.validateEulaAgreementPage();
		eulaPage.performScroll_ClickContinueButton();
	}

	@Test(priority = 6, dependsOnMethods = { "verifyEulaAgreementPageAndAcceptAgreement" })
	public void verifyCanUserBuyPaidPlan() throws InterruptedException {

		// To verify 'Manage Your Subscription' screen appear
		manageSubscriptionPage.validateManageSubscriptionPage();

		// select the subscription plan
		manageSubscriptionPage.selectPlan("paid");

		// Verify that continue button is disable
		assertFalse(manageSubscriptionPage.verifyContinueButtonDisable(manageSubscriptionPage.continueButton, tourPages.textCalendarSchedule));
		ExtentManager.logInfoDetails("Continue button is disable as expected");

		// click on 'Add Payment Method' and go to payment method page
		manageSubscriptionPage.clickAddPaymentButton();
		addPaymentMethodPage.validateAddPaymentMethodPage();

		// To fill the payment details
		String[] details = { "Testing", readData("testData", "cardNo"), readData("testData", "expiry"), readData("testData", "cvc"), readData("testData", "zipcode") };
		addPaymentMethodPage.fillCardDetails(details);

		// To fill the billing details
		addPaymentMethodPage.fillBillingDetails("testing");

		// To click 'Continue' button
		addPaymentMethodPage.clickContinueButtonPaymentMethodPage();

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		Thread.sleep(5000);
		tourPages.validateTourPageCalendarScheduleView();
		
		//_____________________To skip the tour page and go to settings page_______________________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		// go to settings page and verify
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
				
		// To Verify that buy plan is visible on screen
	    verifyText(getText(driver, settingPage.subscriptionPlan), "$699/Month Selected ", "$699/Month Selected ");
	}

	@Test(priority = 7, dependsOnMethods = { "verifyCanUserBuyPaidPlan" })
	public void verifyCancelSubscriptionPopup()
	{
		// To save username and password for other test case
		writeData("UserDetails", "newuser", emailId);
		writeData("UserDetails", "newpassword", CommonVariables.actualPass);
				
		// to verify the all field are mandatory text
		settingPage.verifyMandatoryFields();
		
		// to click on close button
		settingPage.performClickOnCloseIconButton();
	}
	
	@Test(priority = 8, dependsOnMethods = { "verifyCancelSubscriptionPopup" })
	
	public void verifyCanUserCancelSubcription() throws IOException, InterruptedException 
	{	
		// to cancel the subscription
		settingPage.performCancelSubscription();
		
		// to verify that login page appear as expected 
		loginPage.validateLoginPage();
	}
	
	@Test(priority = 9, dependsOnMethods = { "verifyCanUserCancelSubcription" })
	public void verifyCanUserUpdatePaymentMethod() throws IOException, InterruptedException {
		
		// ______________________Login into Application again_________________________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "sms page");
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		tourPages.validateTourPageCalendarScheduleView();
		
		// To skip the tour page and go to calendar view page
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

		// To click on setting icon and go to settings page
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();

		// To click on 'Manage SubsCription' button
		settingPage.clickManageSubscriptionButton();
		manageSubscriptionPage.validateManageSubscriptionPage();
			
		//click edit payment icon and go to payment method page
		manageSubscriptionPage.clickEditPaymentIcon();
		addPaymentMethodPage.validateAddPaymentMethodPage();

		// To fill the payment details and verify
		String[] details = { "Testing", readData("testData", "cardNo"), readData("testData", "expiry"),readData("testData", "cvc"), readData("testData", "zipcode") };
		addPaymentMethodPage.fillCardDetails(details);

		// To fill the billing details
		addPaymentMethodPage.fillBillingDetails("testing");
		
		// To click 'Continue' button
		addPaymentMethodPage.clickContinueButtonPaymentMethodPage();
		explicitWait(driver, manageSubscriptionPage.headerTextSubscriptionPage, 60);

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.validateManageSubscriptionPage();
	}
	
	@Test(priority = 10, dependsOnMethods = { "verifyCanUserUpdatePaymentMethod" })
	public void verifyCanUserBuyPaidPlanAgainAfterCancelOnce() throws IOException, InterruptedException 
	{		
		//select the subscription plan
		manageSubscriptionPage.selectPlan("paid");

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickOnContinueThroughSettingPage();

		// To Verify that buy plan is visible on screen
	    verifyText(getText(driver, settingPage.subscriptionPlan), "$699/Month Selected ", "$699/Month Selected ");
	}
}
