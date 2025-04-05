package com.test.pages;

import com.test.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * Abstrakte Basisklasse für alle Page-Objekte.
 * Stellt gemeinsame Funktionalitäten für alle Seiten bereit und initialisiert
 * WebDriver, WebDriverWait und JavascriptExecutor.
 */
public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver = Driver.getDriver();
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    /**
     * Konstruktor für die BasePage.
     * Initialisiert PageFactory, WebDriverWait und JavascriptExecutor.
     */
    public BasePage() {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    /**
     * Nimmt einen Screenshot der aktuellen Seite auf.
     * 
     * @param name Name des Screenshots, der als Dateiname verwendet wird
     */
    protected void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("target/screenshots/" + name + ".png"));
            System.out.println("Screenshot saved: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Protokolliert den HTML-Quellcode der aktuellen Seite.
     * Hilfreich für das Debugging von Elementproblemen.
     */
    protected void logPageSource() {
        System.out.println("Current page source:");
        System.out.println(driver.getPageSource());
    }

    /**
     * Wartet, bis die Seite vollständig geladen ist.
     * Überprüft den document.readyState auf "complete".
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Wartet, bis ein Element anklickbar ist.
     * 
     * @param element Das WebElement, auf dessen Klickbarkeit gewartet werden soll
     */
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wartet, bis ein Element sichtbar ist.
     * 
     * @param element Das WebElement, auf dessen Sichtbarkeit gewartet werden soll
     */
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Hebt ein Element durch rote Umrandung hervor.
     * Nützlich für visuelle Debugging-Zwecke.
     * 
     * @param element Das WebElement, das hervorgehoben werden soll
     */
    protected void highlightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red'", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Thread interrupted while highlighting element", e);
        }
        js.executeScript("arguments[0].style.border=''", element);
    }
} 