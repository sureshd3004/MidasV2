package com.midas.qa.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import java.util.Random;
import java.util.Set;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.midas.qa.base.TestBase;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeMultipart;
import com.aventstack.extentreports.ExtentTest;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 15;
	public static String currentwindow;
	
	public static void logPerformanceMetrics(WebDriver driver, ExtentTest logger) {
	    try {
	        // Capture key navigation timings
	        long navigationStart = (Long) js.get().executeScript("return window.performance.timing.navigationStart;");
	        long responseStart   = (Long) js.get().executeScript("return window.performance.timing.responseStart;");
	        long domContentLoaded = (Long) js.get().executeScript("return window.performance.timing.domContentLoadedEventEnd;");
	        long domInteractive   = (Long) js.get().executeScript("return window.performance.timing.domInteractive;");
	        long domComplete      = (Long) js.get().executeScript("return window.performance.timing.domComplete;");

	        // Individual metric calculations
	        long domContentLoadedTime = domContentLoaded - navigationStart;     // Total DOM Load
	        long scriptExecutionTime  = domComplete - domInteractive;           // Proxy for script execution
	        long layoutExecutionTime  = domInteractive - responseStart;         // Proxy for layout

	        long frontendTime = domComplete - responseStart;                    // Frontend = DOMComplete - ResponseStart
	        long backendTime  = responseStart - navigationStart;                // Backend = ResponseStart - NavigationStart

	        // Log thresholds and status
	        if (domContentLoadedTime > 2000) {
	            logger.warning("⚠ DomContentLoaded exceeds threshold: " + domContentLoadedTime + " ms");
	        } else {
	            logger.info("✅ DomContentLoaded OK: " + domContentLoadedTime + " ms");
	        }

	        if (scriptExecutionTime > 500) {
	            logger.warning("⚠ Script Execution exceeds threshold: " + scriptExecutionTime + " ms");
	        } else {
	            logger.info("✅ Script Execution is acceptable: " + scriptExecutionTime + " ms");
	        }

	        if (layoutExecutionTime > 300) {
	            logger.warning("⚠ Layout Duration exceeds threshold: " + layoutExecutionTime + " ms");
	        } else {
	            logger.info("✅ Layout Duration is acceptable: " + layoutExecutionTime + " ms");
	        }

	        if (frontendTime > 2500) {
	            logger.warning("⚠ Frontend Time exceeds threshold: " + frontendTime + " ms");
	        } else {
	            logger.info("✅ Frontend Time is acceptable: " + frontendTime + " ms");
	        }
	        if (backendTime > 2000) {
	            logger.warning("⚠ Backend Time exceeds threshold: " + backendTime + " ms");
	        } else {
	            logger.info("✅ Backend Time is acceptable: " + backendTime + " ms");
	        }
	   
	    } catch (Exception e) {
	        logger.warning("❌ Error capturing page performance metrics: " + e.getMessage());
	    }
	}


	 public static boolean isTokenExpired(String jwtToken) {
	        try {
	            String[] parts = jwtToken.split("\\.");
	            if (parts.length < 2) return true;

	            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
	            JSONObject json = new JSONObject(payload);

	            long exp = json.getLong("exp");
	            long now = System.currentTimeMillis() / 1000;

	            return now > exp; // true = expired
	        } catch (Exception e) {
	            e.printStackTrace();
	            return true; // Treat as expired if parsing fails
	        }
	    }

	  public static void switchToMainWindow() {
	        if (currentwindow != null) {
	            driver.get().switchTo().window(currentwindow);
	        } else {
	            throw new IllegalStateException("Main window handle not stored. Call storeMainWindowHandle() first.");
	        }
	    }
	  
	 public static WebDriver switchNewWindow() {
		 currentwindow = getDriver().getWindowHandle();
		Set<String> windows = getDriver().getWindowHandles();
		for (String windowHandle : windows) {
			if (!windowHandle.equals(currentwindow)) {
				getDriver().switchTo().window(windowHandle); // Switch to new window
				break;
			}	}
		return getDriver();
	}

		private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		    StringBuilder result = new StringBuilder();
		    int count = mimeMultipart.getCount();
		    for (int i = 0; i < count; i++) {
		        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
		        if (bodyPart.isMimeType("text/plain")) {
		            result.append(bodyPart.getContent());
		        } else if (bodyPart.isMimeType("text/html")) {
		            String html = (String) bodyPart.getContent();
		            result.append(org.jsoup.Jsoup.parse(html).text()); // use Jsoup to convert HTML to plain text
		        } else if (bodyPart.getContent() instanceof MimeMultipart) {
		            result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
		        }
		    }
		    return result.toString();
		}


	public static void WaitAndSwitchframe(WebElement element) {
		//	wait.get() = new WebDriverWait(getDriver(),Duration.ofSeconds(25));
		wait.get().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	public static void WaitAndSwitchframe(int n) {
		wait.get().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(n));
	}

	public static void waitAndClickElement(WebElement Element) {
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		wait.get().until(ExpectedConditions.elementToBeClickable(Element));
		Element.click();
	}

	public static void waitforElementToLoad(WebElement element) {
		wait.get().until(ExpectedConditions.visibilityOf(element));
	}

	public static String waitAndGetAttribute(WebElement element,String attribute) {
		return	wait.get().until(ExpectedConditions.visibilityOf(element)).getDomAttribute(attribute);
	}

	public static void waitAndClickStaleElement(WebElement Element) {
		wait.get().until(ExpectedConditions.stalenessOf(Element));
		js.get().executeScript("arguments[0].click();", Element);
	}

	public static String waitAndGetText(WebElement Element) {
	//	wait.get().until(ExpectedConditions.stalenessOf(Element));
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		return Element.getText();
	}

	public static String waitAndGetInnerText(WebElement Element) {
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		return Element.getDomAttribute("innerText");
	}

	public static void waitToLoadAllElements(List<WebElement> Elements) {		
		wait.get().until(ExpectedConditions.visibilityOfAllElements(Elements));
	}

	public static void waitForAllStaleElement(List<WebElement> Elements) {
		wait.get().until(ExpectedConditions.visibilityOfAllElements(Elements));		
	}

	public static WebDriver switchAndWaitNewWindowTitle() {
		String currentwindow = getDriver().getWindowHandle();
		Set<String> windows = getDriver().getWindowHandles();
		for (String windowHandle : windows) {
			if (!windowHandle.equals(currentwindow)) {
				getDriver().switchTo().window(windowHandle); // Switch to new window
				break;
			}	}
		return getDriver();

	}
	public static void switchtoDefaultContent() {
		getDriver().switchTo().defaultContent();
	}
	public static void waitAndSendkeys(WebElement Element, String testData) {
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].value='"+testData+"'; arguments[0].dispatchEvent(new Event('input'));", Element);	
	}
	public static void scrollAndClick(WebElement element) {
		js.get().executeScript("arguments[0].scrollIntoView(true);", element);
		js.get().executeScript("arguments[0].click();", element);
	}
	public static void scrolltoElement(WebElement element) {
		js.get().executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void waitForAllElementVisible(WebElement element) {
		wait.get().until(ExpectedConditions.visibilityOf(element));
	}

	public static String getRandomNumericString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10)); // Appends a random digit (0-9)
		}return sb.toString();		    
	}

	public static void waitStale() {
		wait.get().until(ExpectedConditions.stalenessOf(getDriver().findElement(By.tagName("body"))));
	}

	public static void openNewTab(String url) {
		getDriver().switchTo().newWindow(WindowType.TAB);		
		getDriver().navigate().to(url);
	}

	public static void waitAndClickElementByVisualText(String input) {
		WebElement element = wait.get().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//*[text()='"+input+"']"))));
		wait.get().until(ExpectedConditions.elementToBeClickable(element));
		element.click();	
		
	}}

