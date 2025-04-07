package com.test.utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hilfsfunktionen für Selenium Tests.
 * Diese Klasse stellt allgemeine Hilfsmethoden für die Testautomatisierung bereit.
 */
public class UtilsDe {

    /**
     * Erstellt einen Screenshot vom aktuellen Browserzustand.
     * 
     * @param driver Der WebDriver, mit dem der Screenshot erstellt wird
     * @param fileName Der Name der Datei, unter dem der Screenshot gespeichert wird
     * @return boolean True, wenn der Screenshot erfolgreich erstellt wurde, andernfalls False
     */
    public static boolean takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Ensure target directory exists
            Path screenshotDir = Paths.get("target/screenshots");
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Take screenshot
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path targetFile = Paths.get("target/screenshots/" + fileName + ".png");
            Files.copy(scrFile.toPath(), targetFile);
            
            System.out.println("Screenshot saved: " + targetFile);
            return true;
        } catch (IOException e) {
            System.err.println("Error taking screenshot: " + e.getMessage());
            return false;
        }
    }
} 