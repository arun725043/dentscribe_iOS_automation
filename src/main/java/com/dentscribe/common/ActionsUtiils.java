package com.dentscribe.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import java.io.File;
import java.time.Duration;

public class ActionsUtiils {
	static double SCROLL_RATIO = 0.5;
	static Duration SCROLL_DUR = Duration.ofMillis(500);

	 AppiumDriver driver;

	public ActionsUtiils(AppiumDriver driver) {
		this.driver = driver;
	}

	public enum ScrollDirection {
		UP, DOWN, LEFT, RIGHT
	}

	public void scroll(ScrollDirection dir, double scrollRatio) {

		if (scrollRatio < 0 || scrollRatio > 1) {
			throw new Error("Scroll distance must be between 0 and 1");
		}
		Dimension size = driver.manage().window().getSize();
		Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
		int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
		int top = midPoint.y - (int) (midPoint.y * scrollRatio);
		int left = midPoint.x - (int) (midPoint.x * scrollRatio);
		int right = midPoint.x + (int) (midPoint.x * scrollRatio);

		if (dir == ScrollDirection.UP) {
			swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
		} else if (dir == ScrollDirection.DOWN) {
			swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
		} else if (dir == ScrollDirection.LEFT) {
			swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
		} else {
			swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
		}
	}

	protected void swipe(Point start, Point end, Duration duration) {

		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		Sequence swipe = new Sequence(input, 0);
		swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
		swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
		swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		((AppiumDriver) driver).perform(ImmutableList.of(swipe));
	}

	
	public void pullToRefres(ScrollDirection dir, double scrollRatio) {
		if (scrollRatio < 0 || scrollRatio > 1) {
			throw new Error("Scroll distance must be between 0 and 1");
		}
		Dimension size = driver.manage().window().getSize();
		Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
		int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
		int top = midPoint.y - (int) (midPoint.y * scrollRatio);
		int left = midPoint.x - (int) (midPoint.x * scrollRatio);
		int right = midPoint.x + (int) (midPoint.x * scrollRatio);

		if (dir == ScrollDirection.UP) {
			swipe_pullRefres(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
		} else if (dir == ScrollDirection.DOWN) {
			swipe_pullRefres(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
		} else if (dir == ScrollDirection.LEFT) {
			swipe_pullRefres(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
		} else {
			swipe_pullRefres(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
		}
	}
	
	protected void swipe_pullRefres(Point start, Point end, Duration duration) {

		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		Sequence swipe = new Sequence(input, 0);
		swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
		swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y*2));
		swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		((AppiumDriver) driver).perform(ImmutableList.of(swipe));
	}
	
	public void longPress(WebElement el) {
		Point location = el.getLocation();
		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		Sequence swipe = new Sequence(input, 0);
		swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), location.x, location.y));
		swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		swipe.addAction(
				input.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), location.x, location.y));
		swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		((AppiumDriver) driver).perform(ImmutableList.of(swipe));
	}

	public void longPress_gesturePlugin(WebElement el) {
		((JavascriptExecutor) driver).executeScript("gesture: longPress",
				ImmutableMap.of("elementId", ((RemoteWebElement) el).getId(), "pressure", 0.5, "duration", 800));
	}

	public void swipe_gesturePlugin(WebElement el, String direction) {
		((JavascriptExecutor) driver).executeScript("gesture: swipe", ImmutableMap.of("elementId",
				((RemoteWebElement) el).getId(), "percentage", 50, "direction", direction));
	}

	private Point getCenter(WebElement el, int y) {
		Point location = el.getLocation();
		Dimension size = el.getSize();
		return new Point(location.x + size.getWidth() / 2, (location.y + size.getHeight() / 2)+y);
	}

	public void dragNDrop(WebElement source, WebElement target) {
		Point pSourcce = getCenter(source, 25);   // adding some more y co-ordinates 
		Point pTarget = getCenter(target, 0);
		System.out.println(source.getLocation());
		System.out.println(target.getLocation());
		
		swipe(pSourcce, pTarget, SCROLL_DUR);
	}

	public void dragNDrop_gesture(WebElement source, WebElement target) {
		((JavascriptExecutor) driver).executeScript("gesture: dragAndDrop", ImmutableMap.of("sourceId",
				((RemoteWebElement) source).getId(), "destinationId", ((RemoteWebElement) target).getId()));
	}

	public void Drawing(WebElement drawPanel) {

		Point location = drawPanel.getLocation();
		Dimension size = drawPanel.getSize();

		Point pSource = new Point(location.x + size.getWidth() / 2, location.y + 10);
		Point pDest = new Point(location.x + size.getWidth() / 2, location.y + size.getHeight() - 10);

		// The same way, try to identify the Points for horigental drawing
		swipe(pSource, pDest, SCROLL_DUR);
	}

	public void captureScreenShotOf(WebElement el, String fileName) {
		File newImg = el.getScreenshotAs(OutputType.FILE);
		try { 
			FileUtils.copyFile(newImg, new File("./screenshot/" + fileName + ".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}