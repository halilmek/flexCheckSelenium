package com.test.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BrowserUtil {
    

    public BrowserUtil() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element WebElement to wait for
     * @param timeToWaitInSec Time to wait in seconds
     * @return WebElement
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element to be clickable
     *
     * @param element WebElement to wait for
     * @param timeToWaitInSec Time to wait in seconds
     * @return WebElement
     */
    public static WebElement waitForClickability(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Clicks on an element using JavaScript
     *
     * @param element WebElement to click
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element WebElement to scroll to
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Performs double click action on an element
     *
     * @param element WebElement to double click
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Hovers over an element
     *
     * @param element WebElement to hover over
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * Waits for provided element to be stale
     *
     * @param element WebElement to wait for
     * @param timeout Time to wait in seconds
     */
    public static void waitForStaleElement(WebElement element, int timeout) {
        int y = 0;
        while (y <= timeout) {
            try {
                element.isDisplayed();
                break;
            } catch (Exception e) {
                y++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Switches to new window by the exact title
     *
     * @param targetTitle Title of the window to switch to
     */
    public static void switchToWindow(String targetTitle) {
        String mainHandle = Driver.getDriver().getWindowHandle();
        Set<String> allHandles = Driver.getDriver().getWindowHandles();
        
        for (String handle : allHandles) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        
        Driver.getDriver().switchTo().window(mainHandle);
    }

    /**
     * Takes screenshot of the current page
     *
     * @return Screenshot as byte array
     */
    public static byte[] takeScreenshot() {
        return ((org.openqa.selenium.TakesScreenshot) Driver.getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
    }

    /**
     * Executes JavaScript code
     *
     * @param script JavaScript code to execute
     * @return Result of JavaScript execution
     */
    public static Object executeJSscript(String script) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        return js.executeScript(script);
    }

    /**
     * Wait for a specific amount of time
     *
     * @param seconds Time to wait in seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // TAB ile elementi bul ve SPACE tuşuna bas
    public void findAndPressSpaceByTab(By targetLocator) throws InterruptedException {
        Actions actions = new Actions(Driver.getDriver());
        int maxTries = 20;

        for (int i = 0; i < maxTries; i++) {
            WebElement focusedElement = Driver.getDriver().switchTo().activeElement();

            if (focusedElement.equals(Driver.getDriver().findElement(targetLocator))) {
                Thread.sleep(2000);
                actions.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
                Thread.sleep(4000);
                //new Actions(Driver.getDriver()).sendKeys(Keys.TAB).perform();
                System.out.println("Element bulundu ve SPACE tuşuna basıldı.");
                return;
            }
            actions.sendKeys(Keys.TAB).perform();
        }
        System.out.println("Element bulunamadı.");
    }

    public void clickAngebotAnfordernButton() {
        List<WebElement> buttons = Driver.getDriver().findElements(By.tagName("button"));

        for (WebElement button : buttons) {
            if (button.getText().trim().equalsIgnoreCase("weiter")) {
                new Actions(Driver.getDriver()).sendKeys(Keys.SPACE).perform();
                System.out.println("‘Angebot anfordern’ butonuna tıklandı.");
                return;
            }
        }
        System.out.println("‘Angebot anfordern’ butonu bulunamadı!");
    }
} 