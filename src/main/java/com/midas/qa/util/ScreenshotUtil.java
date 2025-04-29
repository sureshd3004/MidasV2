package com.midas.qa.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.midas.qa.base.TestBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
	
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/Screenshots/Desktop/" + testName + "_" + timestamp + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
    
    public static String captureScreenshot(WebDriver driver, String testCaseName, String actionName) {
        String deviceMode = TestBase.prop.getProperty("device.mode", "desktop");
        if (!deviceMode.equalsIgnoreCase("mobile")) return null;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String folderPath = System.getProperty("user.dir") + "/screenshots/Mobile/";
        String fileName = testCaseName + "_"+ actionName   + ".png";

        try {
            Files .createDirectories(Paths.get(folderPath));
            String fullPath = folderPath + fileName;
            Files.copy(srcFile.toPath(), Paths.get(fullPath));
            Log.info("📸 Screenshot captured: " + fullPath);
            return fullPath;
        } catch (IOException e) {
         e.printStackTrace();
            return null;
        }
    }
}
