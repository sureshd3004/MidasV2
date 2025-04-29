package com.midas.qa.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.testng.IAnnotationTransformer;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.midas.qa.base.TestBase;
import com.qa.ExtentReportListener.ExtentManager;

public class ExtentReportListener extends TestBase implements ITestListener, IAnnotationTransformer {

	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(com.midas.qa.util.RetryAnalyzer.class);
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();

		// If test has parameters, append them
		Object[] parameters = result.getParameters();
		if (parameters.length > 0) {
			testName += " | Test Data: " + Arrays.toString(parameters);
		}
		// Create a new test instance in Extent Reports
		ExtentTest extentTest = ExtentManager.getInstance().createTest(testName);
		// Set the test instance in ThreadLocal
		ExtentManager.setTest(extentTest);	       
		// Log the test start event
		ExtentManager.getTest().log(Status.INFO, "Test Started: " + testName);
	}
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test Passed ");
	}

	public void onTestFailure(ITestResult result) {
		// Capture Screenshot
		String screenshotPath = ScreenshotUtil.captureScreenshot(getDriver(), result.getName());

		// Log failure in Extent Reports
		if (TestBase.test.get() != null) {
			TestBase.test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
			TestBase.test.get().addScreenCaptureFromPath(screenshotPath);
		}
	}

	public void onTestSkipped(ITestResult result) {
		String screenshotPath = ScreenshotUtil.captureScreenshot(getDriver(), result.getName());
		TestBase.test.get().addScreenCaptureFromPath(screenshotPath);
		test.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
	}

	public void onFinish(ITestContext context) {

		extent.flush();
	}

	public void onStart(ISuite suite) {
		// Set suite name in ExtentManager
		ExtentManager.getInstance().setSystemInfo("Suite Name", suite.getName());

	}
	// WebDriverEventListener  methods


	/*   public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        test.get().log(Status.INFO, "Locating element: " + by.toString());
    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        test.get().log(Status.PASS, "Successfully found element: " + by.toString());
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        test.get().log(Status.INFO, "Attempting to click on: " + element.toString());
    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        test.get().log(Status.PASS, "Clicked on: " + element.toString());
    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        test.get().log(Status.INFO, "Navigating to URL: " + url);
    }

    public void afterNavigateTo(String url, WebDriver driver) {
        test.get().log(Status.PASS, "Successfully navigated to: " + url);
    }

    public void beforeNavigateBack(WebDriver driver) {
        test.get().log(Status.INFO, "Navigating back...");
    }

    public void afterNavigateBack(WebDriver driver) {
        test.get().log(Status.PASS, "Successfully navigated back.");
    }

    public void beforeNavigateForward(WebDriver driver) {
        test.get().log(Status.INFO, "Navigating forward...");
    }

    public void afterNavigateForward(WebDriver driver) {
        test.get().log(Status.PASS, "Successfully navigated forward.");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        test.get().log(Status.INFO, "Entering text into: " + element.toString() + " | Text: " + String.valueOf(keysToSend));
    }

    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        test.get().log(Status.PASS, "Text entered into: " + element.toString() + " | Text: " + String.valueOf(keysToSend));
    }

    public void onException(Throwable throwable, WebDriver driver) {
        test.get().log(Status.FAIL, "Exception Occurred: " + throwable.getMessage());
    }

    // ✅ NEW: Logging for alert handling
    public void beforeAlertAccept(WebDriver driver) {
        test.get().log(Status.INFO, "Attempting to accept alert.");
    }

    public void afterAlertAccept(WebDriver driver) {
        test.get().log(Status.PASS, "Alert accepted successfully.");
    }

    public void beforeAlertDismiss(WebDriver driver) {
        test.get().log(Status.INFO, "Attempting to dismiss alert.");
    }

    public void afterAlertDismiss(WebDriver driver) {
        test.get().log(Status.PASS, "Alert dismissed successfully.");
    }

    // ✅ NEW: Logging for page refresh
    public void beforeNavigateRefresh(WebDriver driver) {
        test.get().log(Status.INFO, "Refreshing the page.");
    }

    public void afterNavigateRefresh(WebDriver driver) {
        test.get().log(Status.PASS, "Page refreshed successfully.");
    }

    // ✅ NEW: Logging for window switching
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        test.get().log(Status.INFO, "Switching to window: " + windowName);
    }

    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        test.get().log(Status.PASS, "Switched to window: " + windowName);
    }

    // ✅ NEW: Logging for JavaScript execution
    public void beforeScript(String script, WebDriver driver) {
        test.get().log(Status.INFO, "Executing JavaScript: " + script);
    }

    public void afterScript(String script, WebDriver driver) {
        test.get().log(Status.PASS, "Executed JavaScript successfully: " + script);
    }  */

}
