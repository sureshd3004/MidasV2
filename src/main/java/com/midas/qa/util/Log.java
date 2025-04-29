package com.midas.qa.util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.ExtentReportListener.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger logger = LogManager.getLogger(Log.class);

    public static void info(String message) {
        logger.info(message);
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void warn(String message) {
        logger.warn(message);
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.WARNING, message);
        }
    }

    public static void error(String message) {
        logger.error(message);
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }
    public static void performance(String message) {
        logger.trace(message);
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, message);
        }
}
    }