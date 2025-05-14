package com.qa.ExtentReportListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.midas.qa.base.TestBase;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager extends TestBase{


	public static ExtentReports getInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport_" + timestamp + ".html";

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			// Set system info
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Test Environment", prop.getProperty("url"));
			extent.setSystemInfo("Selenium Version", "4.29.0");
			extent.setSystemInfo("Divice Type", prop.getProperty("device.mode"));
			extent.setSystemInfo("Browser", prop.getProperty("browser"));

			// Configure report appearance
			sparkReporter.config().setReportName("Automation Test Report");
			sparkReporter.config().setDocumentTitle("Extent Reports - Selenium");
			sparkReporter.config().setTheme(Theme.DARK);
		}
		return extent;
	}

	public static synchronized void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}

	public static synchronized ExtentTest getTest() {
		return test.get();
	}

}
