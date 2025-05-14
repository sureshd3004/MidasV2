package com.midas.qa.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.ExtentReportListener.CustomWebDriverListener;
import com.qa.ExtentReportListener.ExtentManager;
import com.midas.qa.util.FileUtil;
import com.midas.qa.util.Log;
import com.midas.qa.util.TestUtil;

@Parameters({"role"})
@Listeners({com.midas.qa.util.ExtentReportListener.class})
public class TestBase {

	// ThreadLocal for thread-safe WebDriver and related objects
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static Properties prop;
	public static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
	public static ThreadLocal<JavascriptExecutor> js = new ThreadLocal<>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static ThreadLocal<String> testCaseName = new ThreadLocal<>();

	public static ExtentReports extent;
	static String filePath = System.getProperty("user.dir") + "/src/main/resources/config.properties"; 

	public TestBase() {
		try {
			if (prop == null) { // Load properties only once
				prop = new Properties();
				FileInputStream ip = new FileInputStream(filePath);
				prop.load(ip);
				ip.close();
			}

			// Fetch role from properties
			String role = prop.getProperty("role");

			if (role != null) {
				String sheetName = "login";
				// Assuming this method returns: String[] -> {username, password}
				Object[][] credentials = FileUtil.getTestDataBasedColoumn(sheetName, role);
                                         FileUtil.writeDataToProperties("username", credentials[0][0].toString());
                                         FileUtil.writeDataToProperties("password", credentials[0][1].toString());
	/*			if (credentials != null) {
					String username = credentials[0][0].toString();
					String password = credentials[0][1].toString();

					// Set the new credentials
					prop.setProperty("username", username);
					prop.setProperty("password", password);

					// Save back to properties file if credentials are updated
					saveProperties();
				} else {
					throw new RuntimeException("Username/Password not found for role: " + role);
				} */
			}            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Helper method to save the properties back to the file
	protected static void saveProperties() {
		try {
			// Use resources folder
			FileOutputStream out = new FileOutputStream(filePath);
			prop.store(out, "Updated credentials based on role");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		String browserName = prop.getProperty("browser");
		String deviceMode  = prop.getProperty("device.mode", "desktop");
		WebDriver localDriver;

		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			if (deviceMode.equalsIgnoreCase("mobile")) {
				Map<String, String> mobileEmulation = new HashMap<>();
				mobileEmulation.put("deviceName", prop.getProperty("mobile.deviceName", "Pixel 5"));
				options.setExperimentalOption("mobileEmulation", mobileEmulation);
				options.addArguments("user-agent=Mozilla/5.0 (Linux; Android 11; Pixel 5)");
			}
			localDriver = new ChromeDriver(options);
		}/* else if (browserName.equalsIgnoreCase("chrome") ) {
            ChromeOptions options = new ChromeOptions();
            localDriver = new ChromeDriver(options);
        }            */
		else if (browserName.equalsIgnoreCase("firefox")) {
			localDriver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			if (deviceMode.equalsIgnoreCase("mobile")) {
				Map<String, String> mobileEmulation = new HashMap<>();
				mobileEmulation.put("deviceName", prop.getProperty("mobile.deviceName", "Pixel 5"));
				options.setExperimentalOption("mobileEmulation", mobileEmulation);
				options.addArguments("user-agent=Mozilla/5.0 (Linux; Android 11; Pixel 5)");
				//	options.addArguments("--headless");
			}
			localDriver = new EdgeDriver();
		} else {
			throw new RuntimeException("Unsupported browser: " + browserName);
		}

		CustomWebDriverListener listener = new CustomWebDriverListener();
		WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(localDriver);
		driver.set(decoratedDriver);


		if (deviceMode.equalsIgnoreCase("mobile")) {
			driver.get().manage().window().setSize(new Dimension(414, 896));
		}else {
			driver.get().manage().window().maximize();
		}
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(7));
		driver.get().get(prop.getProperty("url"));

		wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(8)));
		js.set((JavascriptExecutor) driver.get());
	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		extent = ExtentManager.getInstance();
	}

	@AfterSuite
	public void afterSuite() {
		
		extent.flush();

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		TestBase.testCaseName.set(method.getName());
		Log.info("Test starts: " + method.getName());
	}

	@AfterMethod
	public void tearDown() {
		Log.info("Test Ends: " + TestBase.testCaseName.get());
		ExtentTest testLogger = test.get();

		// Add a titled PERFORMANCE section
		testLogger.info(MarkupHelper.createLabel("📈 PERFORMANCE METRICS", ExtentColor.BLUE));

		TestUtil.logPerformanceMetrics(getDriver(), test.get());
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	// Helper method to get the current thread's WebDriver
	public static WebDriver getDriver() {
		return driver.get();
	}
}
