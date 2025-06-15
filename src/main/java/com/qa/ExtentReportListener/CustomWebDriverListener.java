package com.qa.ExtentReportListener;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import com.midas.qa.base.TestBase;
import com.midas.qa.util.Log;
import com.midas.qa.util.ScreenshotUtil;

public class CustomWebDriverListener implements WebDriverListener {

	@Override
	public void afterGet(WebDriver driver, String url) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "afterGet");
		Log.info("Navigated to URL: " + url);
	}

	@Override
	public void afterClick(WebElement element) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterClick");
		Log.info("Clicked on element: " + element);
	}

	@Override
	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterSendKeys");
		Log.info("Sent keys to element: " + element);
	}

	@Override
	public void afterClear(WebElement element) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterClear");
		Log.info("Cleared element: " + element);
	}

	@Override
	public void afterSubmit(WebElement element) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterSubmit");
		Log.info("Submitted element: " + element);
	}

	@Override
	public void afterFindElement(WebDriver driver, By locator, WebElement result) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "afterFindElement");
		Log.info("Found element using locator: " + locator.toString());
	}

	@Override
	public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(driver, testCaseName, "afterFindElements");
		Log.info("Found elements using locator: " + locator.toString());
	}

	@Override
	public void beforeClick(WebElement element) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "BeforeClick");
	}

	@Override
	public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
		String tc = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), tc, "beforeSendKeys");
		Log.info("About to send keys to: " + element);
	}    

	@Override
	public void beforeClear(WebElement element) {
		String tc = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), tc, "beforeClear");
		Log.info("About to clear: " + element);
	}

	@Override
	public void beforeSubmit(WebElement element) {
		String tc = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), tc, "beforeSubmit");
		Log.info("About to submit: " + element);
	}

	/*	public void beforeAlertDismiss(WebDriver driver) {
	//	Log.info("Before dismissing alert.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "Before dismissing alert");
	}

	public void afterAlertDismiss(WebDriver driver) {
		Log.info("After dismissing alert.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterAlertDismiss");
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		//      Log.info("Navigating to: " + url);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "beforeNavigateTo");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
        Log.info("Navigated to: " + url);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterNavigateTo");
	}

	public void beforeNavigateBack(WebDriver driver) {
		//Log.info("Navigating back.");
	}

	public void afterNavigateBack(WebDriver driver) {
		Log.info("Navigated back.");
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "afterNavigate back");
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
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "After Page Refreshed");
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		Log.info("Finding element: " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		Log.info("Element found: " + by.toString());
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "After locator fined");
	}



	public void afterClickOn(WebElement element, WebDriver driver) {
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "after Click");
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
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "before Switched to window");
	}


	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		Log.info("Switched to window: " + windowName);
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "Switched to window");
	}

	 public void onException(Throwable throwable, WebDriver driver) {
		Log.info("Exception occurred: " + throwable.getMessage());
		String testCaseName = TestBase.testCaseName.get();
		ScreenshotUtil.captureScreenshot(TestBase.getDriver(), testCaseName, "On_Exception");
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
	}          */
}