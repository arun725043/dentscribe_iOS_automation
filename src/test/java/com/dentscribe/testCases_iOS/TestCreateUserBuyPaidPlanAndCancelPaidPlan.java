package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertFalse;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

import Api.GenerateOTP;

public class TestCreateUserBuyPaidPlanAndCancelPaidPlan extends iOSBase 
{
	String emailId = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";

	@Test(priority = 1)
	public void verifySignupNewUser() throws InterruptedException 
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
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifySignupNewUser" })
	public void verifyCreatePracticeInfo() throws InterruptedException 
	{
		// _________________fill Practice form and navigate to Sikka webview________________________
		practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
		practiceInfoPage.clickContinueButtonPracticeInfo();
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
		sikkaPage.fillExistingSikkaCredentials(readData("Config", "existingSikkaUser"), readData("Config", "existingSikkaPwd"));
		explicitWait(driver, loginPage.noteLoginPage, 120);
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifySikkaWebViewRegistration" })
	public void verifySpuInstallPopupAndRefreshData()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {		
		// ________________login application and verify SPU install popup____________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "spu popup");
		
		// ________________By pass the manual sikka refresh steps______________
		GenerateOTP.updateOfficeId(emailId, readData("testData", "dentrix"));
		ExtentManager.logInfoDetails("Sikka refresh done");
	}

	@Test(priority = 5, dependsOnMethods = { "verifySpuInstallPopupAndRefreshData" })
	public void verifyEulaAgreementAndAcceptIt()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "sms page");
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify EULA Agreement page and accept agreement
		eulaPage.validateEulaAgreementPage();
		eulaPage.performScroll_ClickContinueButton();
	}

	@Test(priority = 6, dependsOnMethods = { "verifyEulaAgreementAndAcceptIt" })
	public void verifyCanUserBuyPaidPlan()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

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

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.validateManageSubscriptionPage();
	}
	
	@Test(priority = 10, dependsOnMethods = { "verifyCanUserUpdatePaymentMethod" })
	public void verifyCanUserBuyPlanAgain() throws IOException, InterruptedException 
	{		
		//select the subscription plan
		manageSubscriptionPage.selectPlan("paid");

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickOnContinueThroughSettingPage();

		// To Verify that buy plan is visible on screen
	    verifyText(getText(driver, settingPage.subscriptionPlan), "$699/Month Selected ", "$699/Month Selected ");
	}
}