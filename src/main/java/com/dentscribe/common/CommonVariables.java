package com.dentscribe.common;

public class CommonVariables 
{
	public static String directoryPath = System.getProperty("user.dir");
	public static String configPath = directoryPath + "//properties//";
	public static String reportsPath = System.getProperty("user.dir")+ "//reports//";
	
	public static String appVersion = null;
	public static String email = null;
	public static String actualPass = null;
	
	public static  String startButton = "Start-button";
	public static String reviewButton = "Review-button";
	
	//SOAP Report common variables 
	public static String firstName = null;
	public static String title = null;
	public static String license = null;
	
	//note message
	public static String noteMessageLoginPageString = "Sign in with your username and password or use biometrics/multifactor authentication for enhanced security.";
	public static String noteMessageSignupPageString = "Set up your new account. You can also add payment information next if you choose to subscribe to our service. ";
	
	//Popup messages
	public static String messageNonSupportPmsPopup = "Sorry! At this time this practice management software is not supported. We will contact you once your practice management software is supported. ";
	
	//error messages
	public static String errorMsgWithoutRegisterPractice = "Currently the practice is not authorized. Please contact support.";
	
	//success messages
	
	
	//FAQs list questions
	public static String question1 = "Can I edit my SOAP report?";
	public static String question2 = "Can I integrate my patient management system with Dentscribe?";
	public static String question3 = "How do I cancel or pause my subscription?";
	public static String question4 = "How do I record patient notes?";
}
