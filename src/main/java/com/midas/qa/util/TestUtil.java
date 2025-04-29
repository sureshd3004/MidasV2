package com.midas.qa.util;

import java.util.List;
import java.util.Optional;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v118.performance.Performance;
import org.openqa.selenium.devtools.v118.performance.model.Metric;
import com.aventstack.extentreports.ExtentTest;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.midas.qa.base.TestBase;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.FlagTerm;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 15;
	
	 public static void logPerformanceMetrics(WebDriver driver, ExtentTest logger) {
	        if (!(driver instanceof HasDevTools)) return;

	        DevTools devTools = ((HasDevTools) driver).getDevTools();
	        devTools.createSession();
	        devTools.send(Performance.enable(Optional.empty()));

	        List<Metric> metrics = devTools.send(Performance.getMetrics());

	        for (Metric metric : metrics) {
	            String log = metric.getName() + " = " + metric.getValue();
	            logger.info(log);
	        }
	    }

	public static WebDriver switchNewWindow() {
		String currentwindow = getDriver().getWindowHandle();
		Set<String> windows = getDriver().getWindowHandles();
		for (String windowHandle : windows) {
			if (!windowHandle.equals(currentwindow)) {
				getDriver().switchTo().window(windowHandle); // Switch to new window
				break;
			}	}
		return getDriver();
	}

	public static String fetchLatestResetLink(String userEmail, String appPassword) {
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");

			Session session = Session.getInstance(props);
			Store store = session.getStore();
			store.connect("imap.gmail.com", userEmail, appPassword);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			// Search for unread emails
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				String subject = message.getSubject();

				if (subject != null && subject.contains("Reset Your Password")) {
					String content = message.getContent().toString();

					// Extract the link - update regex as per your email format
					String resetLinkRegex = "https?://\\S+reset\\S+";
					java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(resetLinkRegex).matcher(content);
					if (matcher.find()) {
						return matcher.group(0);
					}
				}
			}

			inbox.close(false);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static long pageFrontendPerformance() {
		long responseStart = (Long) js.get().executeScript("return window.performance.timing.responseStart");
		long domComplete   = (Long) js.get().executeScript("return window.performance.timing.domComplete");
		return  domComplete - responseStart;
	}

	public static long pageBackendPerformance() {
		long navigationStart = (Long) js.get().executeScript("return window.performance.timing.navigationStart");
		long responseStart   = (Long) js.get().executeScript("return window.performance.timing.responseStart");
		return responseStart - navigationStart;
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
		js.get().executeScript("arguments[0].click();", Element);
	}

	public static void waitforElementToLoad(WebElement element) {
		wait.get().until(ExpectedConditions.visibilityOf(element));
	}

	public static String waitAndGetAttribute(WebElement element,String attribute) {
		return	wait.get().until(ExpectedConditions.visibilityOf(element)).getAttribute(attribute);
	}

	public static void waitAndClickStaleElement(WebElement Element) {
		wait.get().until(ExpectedConditions.stalenessOf(Element));
		js.get().executeScript("arguments[0].click();", Element);
	}

	public static String waitAndGetText(WebElement Element) {
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		return Element.getText();
	}

	public static String waitAndGetInnerText(WebElement Element) {
		wait.get().until(ExpectedConditions.visibilityOf(Element));
		return Element.getAttribute("innerText");
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
	}}

