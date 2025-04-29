package com.qa.ExtentReportListener;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.Log;
import com.midas.qa.util.ScreenshotUtil;

public class CustomWebDriverListener implements WebDriverListener {
	
	public void beforeAlertAccept(WebDriver driver) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "beforeAlertAccept");
	//	Log.info("Before accepting alert.");
	}
	
	public void afterAlertAccept(WebDriver driver) {
		Log.info("After accepting alert.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "After accepting alert");
	}
	
	public void beforeAlertDismiss(WebDriver driver) {
	//	Log.info("Before dismissing alert.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "Before dismissing alert");
	}
	
	public void afterAlertDismiss(WebDriver driver) {
		Log.info("After dismissing alert.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "afterAlertDismiss");
	}
	
	public void beforeNavigateTo(String url, WebDriver driver) {
		//      Log.info("Navigating to: " + url);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "beforeNavigateTo");
		
	}
	
	public void afterNavigateTo(String url, WebDriver driver) {
        Log.info("Navigated to: " + url);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "afterNavigateTo");
	}
	
	public void beforeNavigateBack(WebDriver driver) {
		//Log.info("Navigating back.");
	}
	
	public void afterNavigateBack(WebDriver driver) {
		Log.info("Navigated back.");
	}
	
	public void beforeNavigateForward(WebDriver driver) {
	//	Log.info("Navigating forward.");
	}	
	public void afterNavigateForward(WebDriver driver) {
		Log.info("Navigated forward.");
	}	
	public void beforeNavigateRefresh(WebDriver driver) {
		Log.info("Refreshing page.");
	}

	public void afterNavigateRefresh(WebDriver driver) {
		Log.info("Page refreshed.");
	}

	
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		Log.info("Finding element: " + by.toString());
	}

	
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		Log.info("Element found: " + by.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		Log.info("Clicking on element: " + element.toString());
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "before Click");
	}

	
	public void afterClickOn(WebElement element, WebDriver driver) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "after Click");
		Log.info("Clicked on element: " + element.toString());
	}
	
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		Log.info("Before changing value of element: " + element.toString());
	}

	
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		Log.info("After changing value of element: " + element.toString());
	}

	
	public void beforeScript(String script, WebDriver driver) {
		Log.info("Executing script: " + script);
	}

	
	public void afterScript(String script, WebDriver driver) {
		Log.info("Executed script: " + script);
	}

	
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		Log.info("Switching to window: " + windowName);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "before Switched to window");
	}

	
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		Log.info("Switched to window: " + windowName);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "Switched to window");
	}

	
	public void onException(Throwable throwable, WebDriver driver) {
		Log.info("Exception occurred: " + throwable.getMessage());
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "On_Exception");
	}

	
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		Log.info("Taking screenshot.");
	}

	
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		Log.info("Screenshot captured.");
	}

	
	public void beforeGetText(WebElement element, WebDriver driver) {
		Log.info("Getting text from element: " + element.toString());
	}

	
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		Log.info("Text retrieved: " + text);
	}
}