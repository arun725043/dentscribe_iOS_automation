package org.dentscribe.utils;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import com.dentscribe.common.CommonMethods;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

public class iOSActions extends CommonMethods
{
	
	IOSDriver driver;
	
	public iOSActions(IOSDriver driver)
	{
	
		this.driver = driver;
	}
	
	public void longPressAction(WebElement ele)
	{
		Map <String,Object>params = new HashMap<>();
		params.put("element", ((RemoteWebElement)ele).getId());
		params.put("duration", 5);
			
		driver.executeScript("mobile:touchAndHold", params);
	}

	public void scrollToEndAction()
	{
		boolean canScrollMore;
		do
		{
		 canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
			    "left", 100, "top", 100, "width", 200, "height", 200,
			    "direction", "down",
			    "percent", 3.0
			    
			));
		}while(canScrollMore);
	}
	
	public void scrollToWebElement(WebElement ele)
	{
		
		Map<String,Object> params = new HashMap<>();
		params.put("direction", "down");		
		params.put("element", ((RemoteWebElement)ele).getId());
		
		driver.executeScript("mobile:scroll", params);
	}
	
	
	
	public void swipeAction(WebElement ele,String direction)
	{
		Map<String,Object> params1 = new HashMap<String,Object> ();
		params1.put("direction", direction);
		params1.put("element", ((RemoteWebElement)ele).getId());
		driver.executeScript("mobile:swipe", params1);
	}
	
	
	public static void performScroll(IOSDriver driver) {
	    Dimension size = driver.manage().window().getSize();
	    int startX = size.getWidth() / 2;
	    int endX = size.getWidth() / 2;
	    int startY = size.getHeight() / 2;
	    int endY = (int) (size.getHeight() * 0.25);

	    performScrollUsingSequence(startX, startY, endX, endY, driver);
	  }

	  public static void performScrollUsingSequence(int startX, int startY, int endX, int endY, IOSDriver driver) {
	    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "first-finger");
	    Sequence sequence = new Sequence(finger, 0)
	      .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
	      .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
	      .addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, endY))
	      .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

	    ((AppiumDriver)(driver)).perform(Collections.singletonList(sequence));
	    
	  }
	
	  public void hideKeyboard() {
		  driver.hideKeyboard("Return");
	  }
	 
}
