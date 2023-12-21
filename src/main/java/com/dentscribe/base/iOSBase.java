package com.dentscribe.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import com.dentscribe.common.CommonMethods;
import com.dentscribe.pages.ios.ForgotPasswordPage;
import com.dentscribe.pages.ios.AddPaymentMethodPage;
import com.dentscribe.pages.ios.CalendarPage;
import com.dentscribe.pages.ios.EulaAgreementPage;
import com.dentscribe.pages.ios.FeedbackPage;
import com.dentscribe.pages.ios.HelpPage;
import com.dentscribe.pages.ios.LoginPage;
import com.dentscribe.pages.ios.ManageSubscriptionPage;
import com.dentscribe.pages.ios.PatientProfilePage;
import com.dentscribe.pages.ios.PatientSearchPage;
import com.dentscribe.pages.ios.PracticeInfoPage;
import com.dentscribe.pages.ios.RecordingPage;
import com.dentscribe.pages.ios.SettingPage;
import com.dentscribe.pages.ios.SignUpPage;
import com.dentscribe.pages.ios.SikkaPage;
import com.dentscribe.pages.ios.SoapReportPage;
import com.dentscribe.pages.ios.TourPages;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class iOSBase extends CommonMethods {

	// driver and page class object
	public IOSDriver driver;
	public static WebDriverWait wait;
	public SignUpPage  signUpPage;
	public LoginPage loginPage;
	public TourPages tourPages;
	public PatientSearchPage patientSearchPage;
	public CalendarPage calendarPage;
	public SettingPage settingPage;
	public PracticeInfoPage practiceInfoPage;
	public SikkaPage sikkaPage;
	public ManageSubscriptionPage manageSubscriptionPage;
	public AddPaymentMethodPage addPaymentMethodPage;
	public EulaAgreementPage eulaPage;
	public SoapReportPage soapReportPage;
	public RecordingPage recordingPage;
	public HelpPage helpPage;
	public ForgotPasswordPage forgotPasswordPage;
	public PatientProfilePage patientProfilePage;
	public FeedbackPage feedbackPage;
	
	// To start the server automatically
	//@BeforeTest
	public void startServer() {
		service = startAppiumServer(readData("Config", "ipAddress"), Integer.parseInt(readData("Config", "port")));
		System.out.println("Start");
	}

	@SuppressWarnings("deprecation")
	@BeforeClass
	public void configureAppium() throws MalformedURLException {

		XCUITestOptions options = new XCUITestOptions();

		options.setDeviceName(readData("Config", "IOSDeviceName")); // emulator
		options.setApp("//Users//mcl-0048/Desktop//DentScribe.app");
		options.setPlatformVersion(readData("Config", "IOSPlatformVersion"));
		options.setNewCommandTimeout(Duration.ofMinutes(90));

		driver = new IOSDriver(new URL("http://127.0.0.1:4723"), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		initializeObjects();
	}

	//@AfterClass
	public void tearDown() {
		driver.quit();

	}

    // @AfterTest
	public void stopService() {
		service.stop();
	}

	public void initializeObjects() {
		signUpPage = new SignUpPage(driver);
		loginPage = new LoginPage(driver);
		tourPages = new TourPages(driver);
		patientSearchPage = new PatientSearchPage(driver);
		calendarPage = new CalendarPage(driver);
		settingPage = new SettingPage(driver);
		practiceInfoPage = new PracticeInfoPage(driver);
		sikkaPage = new SikkaPage(driver);
		manageSubscriptionPage = new ManageSubscriptionPage(driver);
		addPaymentMethodPage = new AddPaymentMethodPage(driver);
		eulaPage = new EulaAgreementPage(driver);
		soapReportPage = new SoapReportPage(driver);
		recordingPage = new RecordingPage(driver);
		helpPage = new HelpPage(driver);
		forgotPasswordPage = new ForgotPasswordPage(driver);
		patientProfilePage = new PatientProfilePage(driver);
		feedbackPage = new FeedbackPage(driver);
	}

}
