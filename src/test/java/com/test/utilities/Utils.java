package com.test.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File targetDir = new File("target/screenshots");
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            File target = new File(targetDir, fileName + ".png");
            FileUtils.copyFile(source, target);
            logger.info("Screenshot saved: " + fileName);
        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage());
        }
    }
} 