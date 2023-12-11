package com.dentscribe.testCases;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.time.Month;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.ExtentReport.Screenshot;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.pages.ios.CalendarPage;
import com.dentscribe.pages.ios.PatientSearchPage;

import Api.GenerateOTP;

public class TestAppointmentStatuses extends iOSBase {
	String emailId = "kapoor.arun+" + CommonMethods.GenerateRandomNumber(3) + "@thinksys.com";
	String patientName = null;

	@Test(priority = 0)
	public void signupSmokeTest() throws InterruptedException {
		try {
			// Application launched
			verifyText(getText(driver, CommonLocators.textWelcome_ios), "Welcome to Dentscribe!", "Welcome to Dentscribe!");
			ExtentManager.logInfoDetails("Application launched successfully");
			
			// click on Sign Up page
			click(driver, signUpPage.signupButton, "Sign Up");
			
			// ____________________Fill signup form and verify confirmation popup button_________________
			System.out.println(emailId);
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), emailId, "Eaglesoft"); // spelling need to be correct to this method
			signUpPage.verifyConfirmationPopup();

			//___________Click on Confirmation popup button______________
			signUpPage.clickConfirmationPopupButton("continue");
			
			//___________Enter the OTP_______
			GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
			click(driver, signUpPage.smsVerificationContinueButton, "Continue on sms verification screen");
			
			verifyText(getText(driver, CommonLocators.textWelcome_ios), "Welcome to Dentscribe!", "Welcome to Dentscribe!");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1)
	public void practiceInfoSmokeTest() {
		try {
			// _________________fill Practice form and Navigate to sikka page________________________
			practiceInfoPage.fillPracticeInfo(readData("testData", "state"), readData("testData", "country"));
			
			//_______________Click on 'Yes' Radio Button and accept the Terms of Services __________________________
			sikkaPage.waitLoadingDone();
			sikkaPage.click_registerButton();
			sikkaPage.clickYes();
			sikkaPage.acceptAgreement();
			
			// __________________Fill the confirmation page______________________________
//			sikkaPage.fillOrderDetails();
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test(priority = 2)
	public void loginSmokeTest()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

		// ______________verify home page_____________________
		loginPage.verifyHomePageElement();

		// _______________verify mandatory fields_____________
		loginPage.verifyLoginMandatoryField();

		// try to login with invalid credentials
	    loginPage.loginApplication("arunr@gmail.com", "kapoor", "no record error");
		ExtentManager.logInfoDetails("User is not logged in as expected");
		
		// ________________login application and verify SPU install popup____________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "spu popup");
		
		// ________________By pass the manual sikka refresh steps______________
		GenerateOTP.updateOfficeId(emailId, "D46381");
		ExtentManager.logInfoDetails("Sikka refresh done");
					
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(emailId, CommonVariables.actualPass, "sms page");

		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
	}

	@Test(priority = 3)
	public void eulaAgreementSmokeTest()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

		// Verify EULA Agreement page and accept agreement
		assertTrue(IsElementPresent(driver, eulaPage.headerTextEulaAgreementPage));
		ExtentManager.logInfoDetails("EULA agreement page appears as expected");
		eulaPage.performScroll_ClickContinueButton();
	}

	@Test(priority = 4)
	public void manageSubscriptionSmokeTest()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

		// To verify 'Manage Your Subscription' screen appear
		verifyText(getText(driver, manageSubscriptionPage.headerTextSubscriptionPage), "Manage your Subscription","Manage Your Subscription page");

		// select the subscription plan
		manageSubscriptionPage.selectPlan("paid");

		// Verify that continue button is disable
		assertFalse(manageSubscriptionPage.verifyContinueButtonDisable(manageSubscriptionPage.continueButton ,tourPages.textCalendarSchedule));
		ExtentManager.logInfoDetails("Continue button is disable as expected");

		// to click on 'Add Payment Method'
		manageSubscriptionPage.clickAddPaymentButton();

		// Verify the 'Add Payment Method' Screen appears
		verifyText(getText(driver, addPaymentMethodPage.headerAddPaymentPage), "Add Payment Method","Add Payment Method Page");

		// To fill the payment details
		String[] details = { "Testing", readData("testData", "cardNo"), readData("testData", "expiry"),readData("testData", "cvc"), readData("testData", "zipcode") };
		addPaymentMethodPage.fillCardDetails(details);

		// To fill the billing details
		addPaymentMethodPage.fillBillingDetails("testing");

		// To click 'Continue' button
		addPaymentMethodPage.clickContinueButtonPaymentMethodPage();

		// To click on continue button on 'Manage Your Subscription'
		manageSubscriptionPage.clickOnContinueThroughSettingPage();
	}

	@Test(priority = 6)
	public void calendarRecordingSmokeTest()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
		// Verify Calendar page and do recording and open SOAP Report
		waitUntilLoaderDisappear(driver);
		ExtentManager.logInfoDetails("Calendar page appears as  expected");
		int[] date = calendarPage.getDateMonthYear(readData("testData", "calendarTestDate"));
		Month month = Month.of(date[1]);

	    month.toString();
		click(driver, calendarPage.dropdownCalendar, "Calendar Dropdown");
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");

		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day,Month and year is selected successfully");

		click(driver, calendarPage.doneButton, "Done");

		// __________________________________Click patient____________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickPatient();
		patientName = getText(driver, calendarPage.patientNameOnRecording);
		patientName = patientName.trim();
		ExtentManager.logInfoDetails("<b>" + patientName + "</b> Has Been selected");
		System.out.println(patientName);
		assertTrue(recordingPage.clickVerifyStartRecording("verify"));
		ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected");

		// __________________________________start Recording___________________________________________________________
		recordingPage.clickVerifyStartRecording("click");
		waitUntilLoaderDisappear(driver);
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(30000);
		// calendarPage.runAudio(30000);

		// ___________________________Stop Recording_______________________

		recordingPage.clickPauseStopButton("stop");
		Thread.sleep(5000);
		
		// ____________submit soap report__________________
		calendarPage.verifySoapReport(patientName, soapReportPage.textSoapReport);
		
		//_____________Update signature and verified reviewed button________
		click(driver, soapReportPage.editButton, "Edit Button on report page");
		assertTrue(IsElementPresent(driver, soapReportPage.saveButton));
		ExtentManager.logInfoDetails("Save Button appears on report page as expectes");
		click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signatur on report page");
		click(driver, soapReportPage.buttonSubmit, "Submit");
		click(driver, soapReportPage.saveButton, "Save Button on report page");
		click(driver, soapReportPage.soapReportBackButton, "Back Icon on report page");
		
		calendarPage.verifyReviewedButton(patientName);
		ExtentManager.logInfoDetails(patientName+": appointment has been reviewed");
	}

	@Test(priority = 7)
	public void patientSearchSmokeTest()
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {

		// Go to Patient Search page and search same patient

		// search result should be display after searching
		assertTrue(calendarPage.verifySearchLandingPage());
		ExtentManager.logInfoDetails("Patient search page opened successfully");

		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + readData("testData", "patientName") + "</b>");
		searchPage.searchPatient(readData("testData", "patientName"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name='" + readData("testData", "patientName") + "'])[1]")));
		assertTrue(searchPage.verifySearchedPatient(readData("testData", "patientName")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

		// Search By patientMobile and verify
		ExtentManager.logInfoDetails("Searching By mobile : <b> " + readData("testData", "patientPhone") + "</b>");
		searchPage.searchPatient(readData("testData", "patientPhone"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' " + readData("testData", "patientPhone") + "'])[1]")));

		String no = driver.findElements(By.xpath("(//XCUIElementTypeStaticText[@name=' " + readData("testData", "patientPhone") + "'])[1]")).get(0).getText();
		assertEquals(no.trim(), readData("testData", "patientPhone"));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

		// Search By dob and verify
		ExtentManager.logInfoDetails("Searching By DOB : <b> " + readData("testData", "dob") + "</b>");
		searchPage.searchPatient(readData("testData", "dob"));

		assertTrue(searchPage.verifySearchedPatient(readData("testData", "dob")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

		// Search By insurance and verify
		ExtentManager.logInfoDetails("Searching By insurance : <b> " + readData("testData", "insurance") + "</b>");
		searchPage.searchPatient(readData("testData", "insurance"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' " + readData("testData", "insurance") + " '])[1]")));
		assertTrue(IsElementPresent(driver, By.xpath("(//XCUIElementTypeStaticText[@name=' " + readData("testData", "insurance") + " '])[1]")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
		waitUntilLoaderDisappear(driver);
	}
	
	@Test(priority = 8)
	public void patientProfileSmokeTest() throws InterruptedException
	{
		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + patientName + "</b>");
		searchPage.searchPatient(patientName);
		waitUntilLoaderDisappear(driver);
		
		// To click on patient name 
		searchPage.performClickONPatient();
		
		// To verify the 'Patient Profile' text 
		patientProfilePage.verifyPatientProfileText();
		
		// To verify the patient name 
		assertTrue(patientProfilePage.verifyPatientName(patientName));
		ExtentManager.logInfoDetails("Patient Profile Opened as expected for : <b>" + patientName);
		Thread.sleep(5000);
		
		// go back to patient search page
		patientProfilePage.clickPatientProfileBackIcon();
		
		// search result should be display after searching
		assertTrue(IsElementPresent(driver, searchPage.inputSearch));
		System.out.println(getText(driver, searchPage.dateFormat));
		verifyText(getText(driver, searchPage.dateFormat), "Format:- Date (yyyy-mm-dd), Ph No. (1234567890)", "Date and Phone Number format");
		ExtentManager.logInfoDetails("User is on Patient search page again");
	}
	
	@Test(priority = 9)
	public void cancelSubcribtionSmokeTest() throws IOException, InterruptedException {
	
		// go to settings page from patient search page
		click_ios(driver, calendarPage.iconSettingSearchPage);
		
		// to cancel the subscription 
		settingPage.performCancelSubscription();
		
		// to verify that login page appear as expected 
		verifyText(getText(driver, loginPage.userNameLabel), "Username", "Username");
	}
	

	
	@Test(priority = 12)
	public void logoutSmokeTest() 
	{
		// click on setting->Logouts
		settingPage.clickOnLogout();
		wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.txtUsername_ios));

		// Verify login page
		assertEquals(getText_ios(loginPage.userNameLabel), "Username");
		ExtentManager.logInfoDetails("Login page is displayed as expected");
	}

}
