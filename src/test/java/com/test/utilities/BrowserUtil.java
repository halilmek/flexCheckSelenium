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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BrowserUtil {
    private static final Logger logger = LoggerFactory.getLogger(BrowserUtil.class);
    private static final Map<String, String> storedValues = new HashMap<>();
    

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
                System.out.println("'Angebot anfordern' butonuna tıklandı.");
                return;
            }
        }
        System.out.println("'Angebot anfordern' butonu bulunamadı!");
    }

    // Store input and displayed values
    private static Map<String, String> inputValues = new HashMap<>();
    private static Map<String, String> displayedValues = new HashMap<>();

    /**
     * Stores the input value with its key
     * @param key identifier for the value (e.g., "purchasePrice", "loanAmount")
     * @param value the value entered by user
     */
    public static void storeInputValue(String key, String value) {
        inputValues.put(key, cleanValue(value));
        logger.info("Stored input value for {}: {}", key, value);
    }

    /**
     * Stores the displayed value with its key
     * @param key identifier for the value
     * @param value the value displayed in UI
     */
    public static void storeDisplayedValue(String key, String value) {
        displayedValues.put(key, cleanValue(value));
        logger.info("Stored displayed value for {}: {}", key, value);
    }

    /**
     * Compares stored input value with displayed value
     * @param key identifier for the value to compare
     * @return true if values match, false otherwise
     */
    public static boolean compareValues(String key) {
        String input = inputValues.get(key);
        String displayed = displayedValues.get(key);
        
        if (input == null || displayed == null) {
            logger.warn("Missing value for comparison. Key: {}, Input: {}, Displayed: {}", 
                       key, input, displayed);
            return false;
        }

        boolean matches = input.equals(displayed);
        if (!matches) {
            logger.warn("Values don't match for {}. Input: {}, Displayed: {}", 
                       key, input, displayed);
        }
        return matches;
    }

    /**
     * Gets all stored input values
     * @return Map of input values
     */
    public static Map<String, String> getInputValues() {
        return new HashMap<>(inputValues);
    }

    /**
     * Gets all stored displayed values
     * @return Map of displayed values
     */
    public static Map<String, String> getDisplayedValues() {
        return new HashMap<>(displayedValues);
    }

    /**
     * Cleans up stored values
     */
    public static void clearStoredValues() {
        inputValues.clear();
        displayedValues.clear();
        logger.info("Cleared all stored values");
    }

    /**
     * Cleans numeric values by removing currency symbols and formatting
     * @param value the value to clean
     * @return cleaned value
     */
    public static String cleanValue(String value) {
        if (value == null) return null;
        // Remove currency symbol, dots, spaces and other formatting
        // Keep only numbers, decimal point and minus sign
        return value.replaceAll("[^0-9,.-]", "")
                   .replace(",", ".");
    }

    /**
     * Verifies if all required values match between input and display
     * @param keysToVerify list of keys to verify
     * @return true if all values match, false otherwise
     */
    public static boolean verifyAllValues(List<String> keysToVerify) {
        boolean allMatch = true;
        for (String key : keysToVerify) {
            if (!compareValues(key)) {
                allMatch = false;
                logger.error("Mismatch found for key: {}", key);
            }
        }
        return allMatch;
    }

    public static void waitForInvisibilityOfElement(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForPageToLoad(int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeoutSeconds));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.error("Error waiting for page to load: " + e.getMessage());
        }
    }
} 