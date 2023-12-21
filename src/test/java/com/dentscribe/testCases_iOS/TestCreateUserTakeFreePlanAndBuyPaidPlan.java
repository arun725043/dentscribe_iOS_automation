package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertFalse;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;

public class TestCreateUserTakeFreePlanAndBuyPaidPlan extends iOSBase 
{
	String emailId = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";

	@Test(priority = 1)
	public void createNewUserAndGoToManageSubscriptionPage() throws InterruptedException, IOException 
	{
		// ____________________Application launched____________________
		loginPage.verifyIsApplicationLaunched();
		
		// ____________________click on Sign Up page and verify it____________________
		click(driver, signUpPage.signupButton, "Sign Up");
		signUpPage.validateSignUpPage();
		
		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), emailId, "Dentrix"); // spelling need to be correct to this method
		signUpPage.verifyConfirmationPopup();

		//___________Click on Confirmation popup button______________
		signUpPage.clickConfirmationPopupButton("continue");
		
		//___________Enter the OTP_______
		
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		// verify whether practice info page opened
		practiceInfoPage.validatePracticeInfoPage();

		// _________________fill Practice form and navigate to Sikka webview________________________
		practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
		practiceInfoPage.clickContinueButtonPracticeInfo();
		sikkaPage.validateSikkaWebViewPage();
	
		//_______________Click on 'Yes' Radio Button and accept the Terms of Services__________________________
		sikkaPage.waitLoadingDone();
		sikkaPage.click_registerButton();
		sikkaPage.clickYes();
		sikkaPage.acceptAgreement();
		
		// __________________Fill the confirmation page______________________________
		sikkaPage.fillExistingSikkaCredentials(readData("UserDetails", "existingSikkaUser"), readData("UserDetails", "existingSikkaPwd"));
		explicitWait(driver, loginPage.noteLoginPage, 120);
		
		// ________________login application and verify SPU install popup____________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "spu popup");
		
		// ________________By pass the manual sikka refresh steps______________
		GenerateOTP.updateOfficeId(emailId, readData("testData", "dentrix"));
		ExtentManager.logInfoDetails("Sikka refresh done");
	
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "sms page");
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify EULA Agreement page and accept agreement
		eulaPage.validateEulaAgreementPage();
		eulaPage.performScroll_ClickContinueButton();
		
		// To verify 'Manage Your Subscription' screen appear
		manageSubscriptionPage.validateManageSubscriptionPage();
	}
	
	@Test(priority = 2, dependsOnMethods = { "createNewUserAndGoToManageSubscriptionPage" })
	public void verifyIsAddPaymentMethodExists() throws InterruptedException 
	{
		// To verify 'Manage Your Subscription' screen appear
		manageSubscriptionPage.validateManageSubscriptionPage();
		
		// go to Add Payment Method
		manageSubscriptionPage.clickAddPaymentButton();
		addPaymentMethodPage.validateAddPaymentMethodPage();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyIsAddPaymentMethodExists" })
	public void verifyAddPaymentMethodMandatoryFields() throws InterruptedException 
	{
		// To verify 'Add Payment Method' screen appear
		addPaymentMethodPage.validateAddPaymentMethodPage();
				
		// To verify the 'card holder name is required'
		addPaymentMethodPage.verifyCardHolderNameIsRequired();
		
		// To verify the 'Card details not complete' alert
		addPaymentMethodPage.verifyCarddetailsnotcompletedAlert();
		
		// To verify the invalid Card 
		addPaymentMethodPage.verifyInvalidCard();
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyAddPaymentMethodMandatoryFields" })
	public void verifyBackIconAddPaymentMethodPage() throws InterruptedException 
	{
		// click back icon and verify manage subscription page
		addPaymentMethodPage.clickBackIconPaymentMethodPage();
		manageSubscriptionPage.validateManageSubscriptionPage();
	}

	@Test(priority = 5, dependsOnMethods = { "createNewUserAndGoToManageSubscriptionPage" })
	public void verifyCanUserTake30DaysFreeTrialPlan() throws InterruptedException, IOException 
	{
		// To verify 'Manage Your Subscription' screen appear
		manageSubscriptionPage.validateManageSubscriptionPage();

		// Verify that continue button is disable
		assertFalse(manageSubscriptionPage.verifyContinueButtonDisable(manageSubscriptionPage.continueButton, tourPages.textCalendarSchedule));
		ExtentManager.logInfoDetails("Continue button is disable as expected");
		
		// select the subscription plan
		scrollUpTillElementVisible(driver, manageSubscriptionPage.freeTrial);
		manageSubscriptionPage.selectPlan("free");

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickContinueButtonSubscriptionPage();
		Thread.sleep(5000);
		tourPages.validateTourPageCalendarScheduleView();
		
		// skip tour pages and go to calendar view
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		// go to settings page and verify free trials
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
		
		// To Verify that free plan is visible on screen
	    settingPage.verifyBuyPlanOnSettingsPage("free");
	}

	@Test(priority = 6, dependsOnMethods = { "verifyCanUserTake30DaysFreeTrialPlan" })
	public void verifyCanUserBuyPaidPlanAfterFreeTrialPlan() throws IOException, InterruptedException 
	{
		// To click on 'Manage SubsCription' button
		settingPage.clickManageSubscriptionButton();
		manageSubscriptionPage.validateManageSubscriptionPage();
			
		//click add payment button and go to payment method page
		manageSubscriptionPage.clickAddPaymentButton();
		addPaymentMethodPage.validateAddPaymentMethodPage();

		// To fill the payment details and verify
		String[] details = { "Testing", readData("testData", "cardNo"), readData("testData", "expiry"),readData("testData", "cvc"), readData("testData", "zipcode") };
		addPaymentMethodPage.fillCardDetails(details);

		// To fill the billing details
		addPaymentMethodPage.fillBillingDetails("testing");

		// To click 'Continue' button
		addPaymentMethodPage.clickContinueButtonPaymentMethodPage();

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.validateManageSubscriptionPage();
		
		//select the subscription plan
		manageSubscriptionPage.selectPlan("paid");

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickOnContinueThroughSettingPage();

		// To Verify that buy plan is visible on screen
		settingPage.verifyBuyPlanOnSettingsPage("paid");
	}
}
