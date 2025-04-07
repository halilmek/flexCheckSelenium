package com.test.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.utilities.Driver;
import com.test.utilities.Utils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page-Klasse für die Abschlussseite.
 * Enthält Elemente und Methoden zur Interaktion mit der finalen Anfrageseite,
 * auf der eine Nachricht eingegeben werden kann und die Angebotsanfrage abgeschlossen wird.
 */
public class FinalPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FinalPage.class);

    /**
     * Konstruktor für die FinalPage
     */
    public FinalPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /**
     * Message Text Area
     */
    @FindBy(xpath = "//*[@id='informationen']")
    private WebElement messageTextArea;

    /**
     * Submit Button
     */
    @FindBy(xpath = "//*[@id='sendeAnfrage']")
    private WebElement submitButton;

    /**
     * Success Message
     */
    @FindBy(xpath = "//*[@id='datenUebermittlung']/div/div/div[1]/div[1]")
    private WebElement successMessage;

    /**
     * Zurück Button
     */
    @FindBy(xpath = "//*[@id='sendeAnfrage']")
    private WebElement zurückButtoElement;

    /**
     * Schreibt eine Nachricht in das Textfeld
     */
    public void writeMessage() {
        String message = "This is a test message for the financing request.";
        waitForElementToBeClickable(messageTextArea);
        highlightElement(messageTextArea);
        //takeScreenshot("before_writing_message");
        messageTextArea.clear();
        messageTextArea.sendKeys(message);
        //takeScreenshot("after_writing_message");
        logger.info("Wrote message: {}", message);
    }

    /**
     * Klickt auf den Submit Button
     */
    public void clickSubmitButton() {
        waitForElementToBeClickable(submitButton);
        highlightElement(submitButton);
        //takeScreenshot("before_clicking_submit");
        submitButton.click();
        //takeScreenshot("after_clicking_submit");
        logger.info("Clicked submit button");
    }

    /**
     * Macht einen Screenshot
     * @param name
     */
    public void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = "test-output/screenshots/" + name + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(destination));
            System.out.println("Screenshot saved: " + destination);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Prüft ob die Erfolgsmeldung angezeigt wird
     * @return true wenn die Erfolgsmeldung angezeigt wird, false sonst
     */
    public boolean isSuccessMessageDisplayed() {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        System.out.println("\nOverall Verification: " + (successMessage.isDisplayed() ? "✅ PASSED" : "❌ FAILED"));

        return successMessage.isDisplayed();
    }

    /**
     * Prüft ob die Erfolgsmeldung angezeigt wird für das Hochladen von Dokumenten
     * @return true wenn die Erfolgsmeldung angezeigt wird, false sonst
     */
    public boolean isSuccessMessageDisplayedForFileUpload() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(driver -> {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String pageText = (String) js.executeScript(
                    "return Array.from(document.querySelectorAll('*')).map(el => el.textContent).join(' ')"
                );
                
                if (pageText != null) {
                    // Başarı mesajlarını kontrol et
                    boolean uploadSuccess = pageText.contains("Die Datei \"test-document.pdf\" wurden erfolgreich hochgeladen");
                    boolean thankYouSuccess = pageText.contains("Vielen Dank für Ihre Anfrage");
                    
                    System.out.println("\nPage Content Check:");
                    System.out.println("✓ Upload Message Found: " + uploadSuccess);
                    System.out.println("✓ Thank You Message Found: " + thankYouSuccess);
                    
                    return uploadSuccess || thankYouSuccess;
                }
                return false;
            });
            System.out.println("✅ Success: Required messages found on the page");
        } catch (Exception e) {
            System.out.println("❌ Warning: Success messages not found: " + e.getMessage());
        }
        return false;
    }

    /**
     * Prüft ob die unerfolgreiche Nachricht angezeigt wird für das Hochladen von Dokumenten
     * @return true wenn die unerfolgreiche Nachricht angezeigt wird, false sonst
     */
    public boolean notSuccessMessageDisplayedForFileUpload() {
        JavascriptExecutor js1 = (JavascriptExecutor) Driver.getDriver();
      
        boolean messageFound = false;
        int maxRetries = 10; // 10 kez deneyecek (toplam 5 saniye)
        int retry = 0;
 
 
        while (!messageFound && retry < maxRetries) {
            String bodyText = (String) js1.executeScript("return document.body.innerText;");
            if (bodyText.contains("konnte nicht hochgeladen werden")) {
                System.out.println("Fehlermeldung gefunden!");
                System.out.println(bodyText);
                messageFound = true;
            } else {
                System.out.println("Fehlermeldung nicht gefunden...");
            }
           
            try {
                Thread.sleep(1000); // 500ms bekle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry++;
        } 
        return messageFound;
    }

    /**
     * Klickt auf den Zurück Button
     */
    public void clickBackButtonOnFinalPage() {
        try {
            logger.info("Clicking back button to return to upload page");

            // Wait for the page to be fully loaded
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            
            // Try to find the back button using multiple strategies
            WebElement backButton = null;
            
            // Strategy 1: Try to find by ID
            try {
                backButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("zurück-button")));
                logger.info("Found back button by ID");
            } catch (Exception e) {
                logger.debug("Back button not found by ID: {}", e.getMessage());
            }
            
            // Strategy 2: Try to find by class and text
            if (backButton == null) {
                try {
                    List<WebElement> buttons = driver.findElements(By.cssSelector("button.btn, button.btn--nav-previous, button.btn--nav-prev"));
                    for (WebElement button : buttons) {
                        String text = button.getText().toLowerCase().trim();
                        String classes = button.getAttribute("class").toLowerCase();
                        if ((text.contains("zurück") || text.contains("back")) && 
                            !classes.contains("next") && !classes.contains("forward")) {
                            backButton = button;
                            logger.info("Found back button by class and text: {}", text);
                            break;
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Back button not found by class and text: {}", e.getMessage());
                }
            }
            
            // Strategy 3: Try to find by role and text
            if (backButton == null) {
                try {
                    List<WebElement> buttons = driver.findElements(By.cssSelector("[role='button'], button"));
                    for (WebElement button : buttons) {
                        String text = button.getText().toLowerCase().trim();
                        if (text.contains("zurück") || text.contains("back")) {
                            backButton = button;
                            logger.info("Found back button by role and text: {}", text);
                            break;
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Back button not found by role and text: {}", e.getMessage());
                }
            }
            
            // Strategy 4: Try to find by position (first button in navigation)
            if (backButton == null) {
                try {
                    List<WebElement> navigationButtons = driver.findElements(
                        By.cssSelector(".navigation-buttons button, .button-container button, .nav-container button")
                    );
                    if (!navigationButtons.isEmpty()) {
                        backButton = navigationButtons.get(0); // First button is usually back
                        logger.info("Found potential back button by position");
                    }
                } catch (Exception e) {
                    logger.debug("Back button not found by position: {}", e.getMessage());
                }
            }
            
            if (backButton != null) {
                // Take screenshot before clicking
                Utils.takeScreenshot(driver, "before-back-click");
                
                // Scroll the button into view
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                    backButton
                );
                Thread.sleep(1000);
                
                // Try multiple click methods
                boolean clicked = false;
                
                // Try 1: JavaScript click (most reliable)
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);
                    clicked = true;
                    logger.info("Clicked back button using JavaScript");
                } catch (Exception e) {
                    logger.debug("JavaScript click failed: {}", e.getMessage());
                }
                
                // Try 2: Standard click
                if (!clicked) {
                    try {
                        backButton.click();
                        clicked = true;
                        logger.info("Clicked back button using standard click");
                    } catch (Exception e) {
                        logger.debug("Standard click failed: {}", e.getMessage());
                    }
                }
                
                // Try 3: Actions click
                if (!clicked) {
                    try {
                        new Actions(driver)
                            .moveToElement(backButton)
                            .click()
                            .perform();
                        clicked = true;
                        logger.info("Clicked back button using Actions");
                    } catch (Exception e) {
                        logger.debug("Actions click failed: {}", e.getMessage());
                    }
                }
                
                if (clicked) {
                    // Take screenshot after clicking
                    Utils.takeScreenshot(driver, "after-back-click");
                    
                    // Wait for navigation
                    try {
                        wait.until(ExpectedConditions.stalenessOf(backButton));
                        logger.info("Successfully navigated back - element is stale");
                    } catch (Exception e) {
                        logger.info("Element did not become stale, checking if we moved to previous page");
                        // Check if we're on a new page by looking for elements from the previous page
                        try {
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".document-upload")));
                            logger.info("Successfully navigated to document upload page");
                        } catch (Exception e2) {
                            logger.debug("Could not verify navigation to document upload page: {}", e2.getMessage());
                        }
                    }
                } else {
                    throw new RuntimeException("Failed to click back button with any method");
                }
            } else {
                // Take screenshot for debugging
                Utils.takeScreenshot(driver, "back-button-error");
                
                // Log the page source for debugging
                logger.debug("Page source: {}", driver.getPageSource());
                
                throw new RuntimeException("Could not find back button on the page");
            }
        } catch (Exception e) {
            Utils.takeScreenshot(driver, "back-button-error");
            logger.error("Failed to click back button: {}", e.getMessage());
            throw new RuntimeException("Failed to click back button: " + e.getMessage());
        }
    }

    /**
     * Prüft ob die Nachricht im Textfeld angezeigt wird
     * @return true wenn die Nachricht im Textfeld angezeigt wird, false sonst
     */
    public boolean getEnteredMessageInFinalPage() {
        try {
            logger.info("Verifying message text persistence");
            // Wait for the message text area to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("informationen")));
            
            // Get the text area value using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "return document.getElementById('informationen').value;";
            String textAreaValue = (String) js.executeScript(script);
            
            // Expected message
            String expectedMessage = "This is a test message for the financing request.";
            
            // Log the comparison
            logger.info("Expected message: {}", expectedMessage);
            logger.info("Actual message: {}", textAreaValue);
            
            // Take screenshot for verification
            Utils.takeScreenshot(driver, "message-verification");
            
            // Return true if messages match
            boolean result = expectedMessage.equals(textAreaValue);
            logger.info("Message verification result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Failed to verify message in final page: {}", e.getMessage());
            Utils.takeScreenshot(driver, "message-verification-error");
            return false;
        }
    }

    /**
     * Prüft ob alle erforderlichen Felder angezeigt werden
     * @return true wenn alle erforderlichen Felder angezeigt werden, false sonst
     */
    public boolean isDisplayedAllRequiredFields() {

        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(messageTextArea));
            
            boolean isMessageTextAreaVisible = messageTextArea.isDisplayed();
            logger.info("Message text area is visible: " + isMessageTextAreaVisible);
            
            return isMessageTextAreaVisible;
            
        } catch (Exception e) {
            return false;
        }
    }
}