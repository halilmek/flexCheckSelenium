package com.test.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.LogType;
import java.util.logging.Level;
import org.openqa.selenium.devtools.DevTools;

public class Driver {
    private Driver() {}

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.getProperty("browser");
            
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--ignore-certificate-errors");
                    
                    // Enable performance logging
                    LoggingPreferences logPrefs = new LoggingPreferences();
                    logPrefs.enable(LogType.BROWSER, Level.ALL);
                    options.setCapability("goog:loggingPrefs", logPrefs);
                    
                    // Create ChromeDriver with DevTools support
                    ChromeDriver driver = new ChromeDriver(options);
                    driverPool.set(driver);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "safari":
                    driverPool.set(new SafariDriver());
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }
        }
        return driverPool.get();
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
} 