package com.midas.qa.base;

import java.io.FileInputStream;
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
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qa.ExtentReportListener.CustomWebDriverListener;
import com.qa.ExtentReportListener.ExtentManager;
import com.midas.qa.util.Log;
import com.midas.qa.util.TestUtil;

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
  //  static String role = "requester"; // you can update dynamically later if needed

    public TestBase() {
        try {
            if (prop == null) { // Load properties only once
                prop = new Properties();
                FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/java/com/midas/qa/config/config.properties");
                prop.load(ip);
            }
     /*       if (driver.get() != null) {
                PageFactory.initElements(driver.get(), this);
            }         */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialization() {
        String browserName = prop.getProperty("browser");
        String deviceMode = prop.getProperty("device.mode", "desktop");
        WebDriver localDriver;

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (deviceMode.equalsIgnoreCase("mobile")) {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", prop.getProperty("mobile.deviceName", "Pixel 5"));
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
                options.addArguments("user-agent=Mozilla/5.0 (Linux; Android 11; Pixel 5)");
            }
            localDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("chrome") ) {
            ChromeOptions options = new ChromeOptions();
            localDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            localDriver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
        	   EdgeOptions options = new EdgeOptions();
               if (deviceMode.equalsIgnoreCase("mobile")) {
                   Map<String, String> mobileEmulation = new HashMap<>();
                   mobileEmulation.put("deviceName", prop.getProperty("mobile.deviceName", "Pixel 5"));
                   options.setExperimentalOption("mobileEmulation", mobileEmulation);
                   options.addArguments("user-agent=Mozilla/5.0 (Linux; Android 11; Pixel 5)");
               }
            localDriver = new EdgeDriver();
        } else {
            throw new RuntimeException("Unsupported browser: " + browserName);
        }

        // Wrap driver with EventFiringDecorator
        CustomWebDriverListener listener = new CustomWebDriverListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(localDriver);
        driver.set(decoratedDriver);

       
        if (deviceMode.equalsIgnoreCase("mobile")) {
            driver.get().manage().window().setSize(new Dimension(310, 740));
        }else {
        	 driver.get().manage().window().maximize();
		}
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get().get(prop.getProperty("url"));

        wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(15)));
        js.set((JavascriptExecutor) driver.get());
    }

    @BeforeSuite
    public void beforeSuite() throws IOException {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        TestBase.testCaseName.set(method.getName());
        Log.info("Test starts: " + method.getName());
    }

    @AfterMethod
    public void tearDown() {
        Log.info("Test Ends: " + TestBase.testCaseName.get());
        TestUtil.logPerformanceMetrics(getDriver(), test.get());
        getDriver().quit();
        driver.remove();
    }

    // Helper method to get the current thread's WebDriver
    public static WebDriver getDriver() {
        return driver.get();
    }
}
