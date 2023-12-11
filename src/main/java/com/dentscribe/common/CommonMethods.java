package com.dentscribe.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.testng.Assert;
import org.dentscribe.utils.iOSActions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.ActionsUtiils.ScrollDirection;
import com.github.javafaker.Faker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

//This class is used for common methods
public class CommonMethods {
	public AppiumDriverLocalService service;
	static Random randomGenerator;
	static CommonMethods utils;
	static Faker faker = new Faker();
	static int MAX = 26;

	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {

		if (contains(System.getProperty("os.name"), "Windows")) {
			System.out.println("If");
			service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).withIPAddress(ipAddress).usingPort(port).build();
		} else {
			System.out.println("Else");
			service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/bin/appium")).withIPAddress(ipAddress).usingPort(port).build();
		}
		service.start();
		return service;
	}

	// To return element present
	public boolean IsElementPresent(AppiumDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(by);
			return true; // Success!
		} catch (Exception ignored) {
			return false;
		}
	}
	
	// To return element present
	public boolean IsElementPresent(AppiumDriver driver, By by, String expectedElement) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		try {
			driver.findElement(by);
			ExtentManager.logInfoDetails("Expected element found :- <b>" + expectedElement);
			return true; // Success!
		} catch (Exception ignored) {
			ExtentManager.logFailureDetails("Expected element not found :- <b>" + expectedElement);
			ignored.getMessage();
			return false;
		}
	}
	
	public String getAttribute_ios(By locator) {
		return iOSBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("name");
	}
	
	public String getAttribute_ios(By locator, String attr) {
		String actualText = iOSBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attr);
		ExtentManager.logInfoDetails("Actual text found - <b>" + actualText);
		return actualText;
	}

	public void waitForElementToAppear(AppiumDriver driver, By element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeContains((element), "text", "Cart"));
	}

	public void click(AppiumDriver driver, By element) {
		
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(element)).click();
		 
	}

	public void click_ios(AppiumDriver driver, By element) {
		iOSBase.wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element))).click();
	}
	
	// creating this override methods: Ios and Adroid click gernericMethod
	public void click(AppiumDriver driver, By element, String eleName) {
		
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(driver.findElement(element))).click();
		ExtentManager.logInfoDetails("Clicked on <b> "+eleName+" </b>");
		 
	}
	
	// To perform clear operation
	public static void clear_ios(By element) {
		iOSBase.wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
	}
	
	//creating this override method for get text and worked for both Android and IOS
	public String getText(AppiumDriver driver, By locator) {
		String actualText = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		ExtentManager.logInfoDetails("Actual text found - <b>" + actualText);
		return actualText;
		
//		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}
	
	// return text of ios elements
	public String getText_ios(By locator) {
		return iOSBase.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}
	
	// creating this override methods: Ios and Android sendKeys gernericMethod
	public static void sendKeys(AppiumDriver driver, By element, String value, String inputField) {
		
		WebElement inputEle = driver.findElement(element);
		new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(inputEle));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		inputEle.click();
		inputEle.clear();
		inputEle.sendKeys(value);
		
		ExtentManager.logInfoDetails("Entered value into " + inputField + " input field : " + value);
	}

	public void explicitWait(AppiumDriver driver, By locator, int d) {
		new WebDriverWait(driver, Duration.ofSeconds(d)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void back(AppiumDriver driver) { 
		driver.navigate().back();
	}

	public boolean equals(String actual, String expected) {
		boolean flag = false;
		if (actual.equals(expected)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	public boolean contains(String actual, String expected) {
		boolean flag = false;
		if (actual.contains(expected)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	static String value;

	public static String readData(String file, String key) {
		try {
			FileInputStream in = new FileInputStream(CommonVariables.configPath + file);
			Properties properties = new Properties();
			properties.load(in);
			in.close();

			FileReader reader = new FileReader(CommonVariables.configPath + file);
			properties.load(reader);
			value = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Configuration file not readable");
		}
		return value;
	}

	public static void writeData(String file, String key, String value) {
		try {
			FileInputStream in = new FileInputStream(CommonVariables.configPath + file);
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(CommonVariables.configPath + file);
			props.setProperty(key, value);
			props.store(out, "properties");
			out.close();
		} catch (Exception e) {
			System.out.println("Configuration file not readable");
		}
	}

	// To create random integer
	public static int getRandomNumber() {
		int randomInt = 0;
		randomInt = randomGenerator.nextInt(1000);
		return randomInt;
	}

	public static String GenerateRandomNumber(int length) {
		Random random = new Random();

		// Generate 10 random digits and concatenate them
		StringBuilder randomNumber = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int digit = random.nextInt(10); // Generate a random digit (0-9)
			randomNumber.append(digit);
		}

		// Convert the StringBuilder to a String
		String no = randomNumber.toString();
		return no;
	}

	// To create random firstName
	public static String generateRandomFirstName() {
		String firstName = faker.name().firstName();
		return firstName;
	}

	// To create random middleName
	public static String genrateRandomMiddleName() {
		String middleName = faker.name().nameWithMiddle();
		return middleName;
	}

	// To create random lastName
	public static String genrateRandomLastName() {
		String lastName = faker.name().lastName();
		return lastName;
	}

	// To create random city
	public static String genrateRandomCity() {
		String city = faker.address().city();
		return city;
	}

	// To create random state
	public static String genrateRandomState() {
		String state = faker.address().state();
		return state;
	}

	// function to generate a random string of length n
	public static String getAlphaNumericString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	// To create random phone number
	public static String genrateRandomPhoneNumber() {
		String number = faker.phoneNumber().cellPhone();
		return number;
	}

	public static String selectTodaysDate(String format) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(date);
		System.out.println(strDate);
		return strDate;
	}
	
	// To accept the settings after login by clicking the "Allow" button
	public void clickAllow(IOSDriver driver) {
		if(IsElementPresent(driver, CommonLocators.allowButton_ios)) {
			click_ios(driver, CommonLocators.allowButton_ios);
		}
	}
	
	// To verify the text 
	public void verifyText(String actual, String expected, String elemName) {
		
		if(actual.equals(expected)) {
			ExtentManager.logInfoDetails("<b>" + elemName + " : is presented as expected");
		}
		else {
			ExtentManager.logFailureDetails("<b>" + elemName + " : is not presented");
			Assert.fail();
		}
		
	}
	
	//to hide numeric keyboard
	public void hideNumericKeys(By ele, AppiumDriver driver) {
		driver.findElement(ele).click();
	}
	
	// Perform Wait till Loader disappear
	public void waitUntilLoaderDisappear(AppiumDriver driver) {
		new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.invisibilityOfElementLocated(CommonLocators.patientInfoLoader));
	}
	
	// To scroll  down till the element is visible  (button)
	public void scrollDownTillElementVisible(IOSDriver driver, By element ) {
		int i = 1;
		while (i <20) {

			try {
				new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.visibilityOf(driver.findElement(element)));
				break;
			} catch (TimeoutException e) {
				iOSActions.performScroll(driver);
				i++;
			}
		}
	}
	
	// To scroll UP till the element is visible 
	public void scrollUpTillElementVisible(IOSDriver driver, By element ) {
		
		ActionsUtiils gestures = new ActionsUtiils(driver);
		
		int i = 1;
		
		while (i < 20) {

			try {
				new WebDriverWait(driver, Duration.ofMillis(100)).until(ExpectedConditions.visibilityOfElementLocated(element));
				break;
			} catch (Exception e) {
				gestures.scroll(ScrollDirection.UP, 0.30);
				i++;
			}
	
		}
	}
	
	
}
