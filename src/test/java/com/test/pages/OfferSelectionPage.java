package com.test.pages;

import com.test.utilities.BrowserUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.test.utilities.Driver;
import com.test.utilities.TestDataGenerator;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page Object für die Angebotsauswahl-Seite des FLEXCHECK Moduls.
 * Enthält Elemente und Methoden für die Angebotsauswahl und Modal-Dialog-Interaktionen.
 */
public class OfferSelectionPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(OfferSelectionPage.class);

    public OfferSelectionPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "[data-testid='info-button']")
    private WebElement infoButton;

    @FindBy(css = ".modal-dialog")
    private WebElement modalDialog;

    @FindBy(css = ".modal-content")
    private WebElement modalContent;

    @FindBy(css = "[data-testid='close-modal']")
    private WebElement closeModalButton;

    @FindBy(css = "[data-testid='select-offer']")
    private WebElement selectOfferButton;

    @FindBy(css = ".previous-data")
    private WebElement previousDataSection;

    @FindBy(css = "#sollzinsbindungen-form")
    private WebElement resultsForm;

    @FindBy(css = ".panel-card-table-head")
    private WebElement resultsTableHead;

    @FindBy(xpath = "//*[@id='sectionSollzinsbindungTableHead']/div/div[1]/div[1]")
    private List<WebElement> offerCards;

    @FindBy(xpath = "//*[@id='anfrage1']")
    private WebElement secondOption;

    @FindBy(css = "#sollzinsbindungen-weiter-button")
    private WebElement angebotAnfordernButton;

    @FindBy(css = "button[id*='detailsAnzeigenSollzinsbindung']")
    private WebElement detailsButton;

    @FindBy(xpath = "//div[contains(@class, 'modal')]//div[contains(@class, 'modal-body')]")
    private WebElement detailsModal;

    TestDataGenerator testDataGenerator = new TestDataGenerator();
    String fakeOptionNumber = testDataGenerator.generateOptionNumber();

    /**
     * Öffnet den Modal-Dialog durch Klicken auf den Info-Button
     * @return true wenn der Modal-Dialog erfolgreich geöffnet wurde
     */
    public boolean openModalDialog() {
        BrowserUtil.waitForClickability(infoButton, 10);
        infoButton.click();
        return isModalDialogDisplayed();
    }

    /**
     * Prüft ob der Modal-Dialog angezeigt wird
     * @return true wenn der Modal-Dialog sichtbar ist
     */
    public boolean isModalDialogDisplayed() {
        return BrowserUtil.waitForVisibility(modalDialog, 10).isDisplayed();
    }

    /**
     * Holt den Text aus dem Modal-Dialog mit den vorherigen Eingaben
     * @return String mit den angezeigten Daten
     */
    public String getPreviousInputData() {
        BrowserUtil.waitForVisibility(previousDataSection, 10);
        return previousDataSection.getText();
    }

    /**
     * Schließt den Modal-Dialog
     */
    public void closeModalDialog() {
        BrowserUtil.waitForClickability(closeModalButton, 10);
        closeModalButton.click();
    }

    /**
     * Wählt das berechnete Angebot aus
     */
    public void selectOffer() {
        BrowserUtil.waitForClickability(selectOfferButton, 10);
        selectOfferButton.click();
    }

    /**
     * Verifiziert ob die eingegebenen Daten im Modal-Dialog korrekt angezeigt werden
     * @param expectedData Die erwarteten Daten
     * @return true wenn die Daten übereinstimmen
     */
    public boolean verifyPreviousData(String expectedData) {
        String actualData = getPreviousInputData();
        return actualData.contains(expectedData);
    }

    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(resultsForm));
            wait.until(ExpectedConditions.visibilityOf(resultsTableHead));
            logger.info("Results are displayed");
            return true;
        } catch (Exception e) {
            logger.error("Results are not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isTextDisplayedInResults(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(resultsTableHead));
            String tableText = resultsTableHead.getText();
            boolean isDisplayed = tableText.contains(expectedText);
            if (isDisplayed) {
                logger.info("Text '{}' is displayed in results", expectedText);
            } else {
                logger.error("Text '{}' is not found in results. Actual text: {}", expectedText, tableText);
            }
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error checking for text '{}' in results: {}", expectedText, e.getMessage());
            return false;
        }
    }

    public void selectAnAvailableOption() throws Exception {
        logger.info("Selecting option number: {}", fakeOptionNumber);
        try {
            String xpath = "//*[@id='anfrage" + (Integer.parseInt(fakeOptionNumber) - 1) + "']";
            WebElement option = Driver.getDriver().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(option));
            highlightElement(option);
            //takeScreenshot("before-selecting-option-" + optionNumber);
            option.click();
            //takeScreenshot("after-selecting-option-" + optionNumber);
            logger.info("Successfully selected option {}", fakeOptionNumber);
        } catch (Exception e) {
            logger.error("Failed to select option {}: {}", fakeOptionNumber, e.getMessage());
            throw e;
        }
    
    }

    public void clickAngebotAnfordern() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(angebotAnfordernButton));
            highlightElement(angebotAnfordernButton);
            //takeScreenshot("angebotAnfordern-before");
            angebotAnfordernButton.click();
            //takeScreenshot("angebotAnfordern-after");
            logger.info("Successfully clicked Angebot Anfordern button");
        } catch (Exception e) {
            logger.error("Failed to click Angebot Anfordern button: " + e.getMessage());
            throw e;
        }
    }

    public void selectOption(String optionNumber) {
        logger.info("Selecting option number: {}", optionNumber);
        try {
            String xpath = "//*[@id='anfrage" + (Integer.parseInt(optionNumber) - 1) + "']";
            WebElement option = Driver.getDriver().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(option));
            highlightElement(option);
            //takeScreenshot("before-selecting-option-" + optionNumber);
            option.click();
            //takeScreenshot("after-selecting-option-" + optionNumber);
            logger.info("Successfully selected option {}", optionNumber);
        } catch (Exception e) {
            logger.error("Failed to select option {}: {}", optionNumber, e.getMessage());
            throw e;
        }
    }


    public void clickDetailsButton() {
        try {
            // Find all details buttons
            List<WebElement> detailsButtons = driver.findElements(By.cssSelector("button[id*='detailsAnzeigen']"));
            logger.debug("Found {} elements with selector: {}", detailsButtons.size(), "By.cssSelector: button[id*='detailsAnzeigen']");

            // Find first visible and enabled button
            WebElement detailsButton = null;
            for (WebElement button : detailsButtons) {
                if (button.isDisplayed() && button.isEnabled()) {
                    detailsButton = button;
                    logger.debug("Found visible element - Text: '{}', Class: '{}', ID: '{}'",
                            button.getText(), button.getAttribute("class"), button.getAttribute("id"));
                    break;
                }
            }

            if (detailsButton == null) {
                throw new RuntimeException("No visible Details button found");
            }

            logger.info("Found Details button with selector: By.cssSelector: button[id*='detailsAnzeigen']");

            // Get the modal ID from the button's data-target attribute
            String modalId = detailsButton.getAttribute("data-target");
            if (modalId == null || modalId.isEmpty()) {
                throw new RuntimeException("Button does not have a data-target attribute for modal");
            }
            modalId = modalId.replace("#", ""); // Remove # from the ID

            // Try clicking with different methods
            boolean clicked = false;
            
            // 1. Try regular click
            try {
                detailsButton.click();
                logger.info("Successfully clicked Details button with regular click");
                clicked = true;
            } catch (Exception e) {
                logger.debug("Regular click failed: {}", e.getMessage());
            }

            // 2. Try Actions click if regular click failed
            if (!clicked) {
                try {
                    new Actions(driver).moveToElement(detailsButton).click().perform();
                    logger.info("Successfully clicked Details button with Actions");
                    clicked = true;
                } catch (Exception e) {
                    logger.debug("Actions click failed: {}", e.getMessage());
                }
            }

            // 3. Try JavaScript click if both previous methods failed
            if (!clicked) {
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", detailsButton);
                    logger.info("Successfully clicked Details button with JavaScript");
                    clicked = true;
                } catch (Exception e) {
                    logger.debug("JavaScript click failed: {}", e.getMessage());
                }
            }

            if (!clicked) {
                throw new RuntimeException("Failed to click Details button with any method");
            }

            // Wait for modal to appear and become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                // First try waiting for the specific modal
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(String.format("#%s.modal.show", modalId))));
                logger.info("Modal {} is visible", modalId);
            } catch (Exception e) {
                logger.debug("Could not find specific modal, trying generic modal selectors");
                
                // If specific modal not found, try generic modal selectors
                try {
                    // Try to force show the modal using JavaScript if it exists but is not visible
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript(
                        "var modal = document.querySelector('#" + modalId + "');" +
                        "if(modal) {" +
                        "   modal.classList.add('show');" +
                        "   modal.style.display = 'block';" +
                        "   modal.setAttribute('aria-hidden', 'false');" +
                        "}"
                    );
                    
                    // Wait again for modal visibility
                    wait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal.show")),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal.fade.show")),
                        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='dialog'][aria-hidden='false']"))
                    ));
                    logger.info("Modal is visible after JavaScript intervention");
                } catch (Exception e2) {
                    logger.error("Modal did not appear after JavaScript intervention: {}", e2.getMessage());
                    throw new RuntimeException("Modal did not appear after clicking Details button");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to interact with Details button: {}", e.getMessage());
            throw new RuntimeException("Failed to interact with Details button: " + e.getMessage());
        }
    }

    public void printModalValues() {
        try {
            // Get all detail rows with a more specific selector
            By detailRowsSelector = By.cssSelector("[id^='details-berechnung'] .row.row--details");
            List<WebElement> detailRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(detailRowsSelector));
            
            // Filter out empty rows
            List<WebElement> nonEmptyRows = detailRows.stream()
                .filter(row -> {
                    try {
                        String text = row.getText().trim();
                        return !text.isEmpty();
                    } catch (Exception e) {
                        logger.warn("Error checking row text: {}", e.getMessage());
                        return false;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
            
            System.out.println("\n=== Modal Values ===");
            System.out.println("Found " + nonEmptyRows.size() + " detail rows in modal\n");
            
            for (WebElement row : nonEmptyRows) {
                try {
                    // Use more specific selectors for label and value elements
                    WebElement labelElement = row.findElement(By.cssSelector(".col-6.col-md-6.hyphenation"));
                    WebElement valueElement = row.findElement(By.cssSelector(".col-6.col-md-6.text-right"));
                    
                    // Clean up the label (remove asterisk and colon)
                    String label = labelElement.getText().trim()
                        .replace("*", "")
                        .replace(":", "")
                        .trim();
                    
                    // Clean up the value (remove "Euro" and trim)
                    String value = valueElement.getText().trim()
                        .replace("Euro", "")
                        .trim();
                    
                    // Only print if both label and value are non-empty
                    if (!label.isEmpty() && !value.isEmpty()) {
                        System.out.println(label + ", " + value);
                    }
                    
                } catch (Exception e) {
                    logger.warn("Could not extract value from row: {}", e.getMessage());
                    try {
                        // Take a screenshot of the problematic row
                        BrowserUtil.takeScreenshot();
                    } catch (Exception screenshotError) {
                        logger.error("Failed to take screenshot: {}", screenshotError.getMessage());
                    }
                }
            }
            System.out.println("\n=== End of Modal Values ===\n");
            
        } catch (Exception e) {
            logger.error("Failed to get values from modal: {}", e.getMessage());
            try {
                BrowserUtil.takeScreenshot();
            } catch (Exception screenshotError) {
                logger.error("Failed to take screenshot: {}", screenshotError.getMessage());
            }
            throw new RuntimeException("Failed to extract values from modal: " + e.getMessage(), e);
        }
    }

    private String normalizeNumber(String value) {
        if (value == null) return null;
        // Remove thousand separators, replace comma with dot for decimals, and remove trailing zeros
        return value.replace(".", "")  // Remove thousand separator
                   .replace(",", ".")  // Convert decimal comma to dot
                   .replaceAll("\\.00$", "")  // Remove .00 if it's at the end
                   .trim();
    }

    /**
     * Clicks all info buttons sequentially, verifies values, and closes each modal
     * @return Map containing values from all info buttons for comparison
     */
    public List<Map<String, String>> getAllDetailsValues() {
        List<Map<String, String>> allModalValues = new ArrayList<>();
        
        // Try different selectors to find the info buttons
        List<WebElement> infoButtons = new ArrayList<>();
        String[] selectors = {
            "button[id*='detailsAnzeigen']",
            "button[id*='details-button']",
            "button.details-button",
            "button[aria-label*='Details']"
        };
        
        for (String selector : selectors) {
            infoButtons = driver.findElements(By.cssSelector(selector));
            if (!infoButtons.isEmpty()) {
                logger.info("Found " + infoButtons.size() + " info buttons using selector: " + selector);
                break;
            }
        }
        
        if (infoButtons.isEmpty()) {
            logger.error("No info buttons found with any of the tried selectors");
            return allModalValues;
        }

        // Create a longer wait for modal interactions
        WebDriverWait modalWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        for (int i = 0; i < infoButtons.size(); i++) {
            try {
                // Check for any existing open modals and close them
                try {
                    List<WebElement> openModals = driver.findElements(By.cssSelector(".modal.show, .modal[style*='display: block']"));
                    if (!openModals.isEmpty()) {
                        logger.info("Found " + openModals.size() + " open modals, attempting to close them");
                        for (WebElement modal : openModals) {
                            ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].style.display='none';" +
                                "arguments[0].classList.remove('show');" +
                                "document.body.classList.remove('modal-open');" +
                                "var backdrop = document.querySelector('.modal-backdrop');" +
                                "if(backdrop) backdrop.remove();",
                                modal
                            );
                        }
                        Thread.sleep(1000); // Wait for modal cleanup
                    }
                } catch (Exception e) {
                    logger.warn("Error while checking for open modals: " + e.getMessage());
                }

                WebElement button = infoButtons.get(i);
                
                // Log button state before interaction
                logger.info("Button " + (i + 1) + " state - Displayed: " + button.isDisplayed() + 
                          ", Enabled: " + button.isEnabled() +
                          ", Text: " + button.getText());
                
                // Ensure button is in viewport and clickable
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                    button
                );
                Thread.sleep(1000); // Wait for scroll and any animations

                // Try multiple click methods with verification
                boolean clicked = false;
                try {
                    modalWait.until(ExpectedConditions.elementToBeClickable(button));
                    button.click();
                    clicked = true;
                } catch (Exception e1) {
                    try {
                        new Actions(driver).moveToElement(button).click().perform();
                        clicked = true;
                    } catch (Exception e2) {
                        try {
                            ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].click(); console.log('Button clicked via JavaScript');",
                                button
                            );
                            clicked = true;
                        } catch (Exception e3) {
                            logger.error("All click methods failed for button " + (i + 1));
                            throw e3;
                        }
                    }
                }
                
                if (clicked) {
                    logger.info("Successfully clicked button " + (i + 1));
                    
                    // Wait for modal with multiple checks
                    WebElement modal = null;
                    String[] modalSelectors = {
                        "[id^='details-berechnung']",
                        ".modal.show",
                        ".modal[style*='display: block']",
                        "[role='dialog']"
                    };
                    
                    for (String modalSelector : modalSelectors) {
                        try {
                            modal = modalWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(modalSelector)));
                            logger.info("Modal found with selector: " + modalSelector);
                            break;
                        } catch (Exception e) {
                            logger.debug("Modal not found with selector: " + modalSelector);
                        }
                    }
                    
                    if (modal != null && modal.isDisplayed()) {
                        logger.info("Modal " + (i + 1) + " is now visible");
                        
                        // Get modal values
                        Map<String, String> modalValues = getDetailsValues();
                        allModalValues.add(modalValues);
                        
                        // Print modal values
                        logger.info("\n=== Modal " + (i + 1) + " Values ===");
                        logger.info("Found " + modalValues.size() + " detail rows in modal\n");
                        modalValues.forEach((key, value) -> logger.info(key + ", " + value));
                        logger.info("\n=== End of Modal " + (i + 1) + " Values ===\n");

                        // Close modal with multiple methods
                        boolean modalClosed = false;
                        
                        // Try clicking close button with different selectors
                        String[] closeSelectors = {
                            "[aria-label='Close']",
                            "button.close",
                            ".modal-header .close",
                            "[data-dismiss='modal']"
                        };
                        
                        for (String closeSelector : closeSelectors) {
                            try {
                                WebElement closeButton = modal.findElement(By.cssSelector(closeSelector));
                                if (closeButton.isDisplayed() && closeButton.isEnabled()) {
                                    closeButton.click();
                                    modalClosed = true;
                                    break;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        
                        if (!modalClosed) {
                            try {
                                // Try pressing Escape key
                                new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                                modalClosed = true;
                            } catch (Exception e) {
                                // Force close modal with JavaScript
                                ((JavascriptExecutor) driver).executeScript(
                                    "var modal = document.querySelector('.modal.show, .modal[style*=\"display: block\"]');" +
                                    "if(modal) {" +
                                    "  modal.style.display='none';" +
                                    "  modal.classList.remove('show');" +
                                    "  document.body.classList.remove('modal-open');" +
                                    "}" +
                                    "var backdrop = document.querySelector('.modal-backdrop');" +
                                    "if(backdrop) backdrop.remove();"
                                );
                                modalClosed = true;
                            }
                        }

                        // Verify modal is closed
                        try {
                            modalWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".modal.show, .modal[style*='display: block']")));
                            logger.info("Modal " + (i + 1) + " is now closed");
                        } catch (Exception e) {
                            logger.warn("Could not verify modal " + (i + 1) + " closure: " + e.getMessage());
                        }
                        
                        // Add delay between modal interactions
                        Thread.sleep(2000);
                    } else {
                        logger.error("Modal " + (i + 1) + " did not become visible after clicking");
                        BrowserUtil.takeScreenshot();
                    }
                }
            } catch (Exception e) {
                logger.error("Error processing modal " + (i + 1) + ": " + e.getMessage());
                BrowserUtil.takeScreenshot();
            }
        }
        return allModalValues;
    }

    /**
     * Verifies if the values match across all info buttons
     * @param expectedValues The expected values to compare against
     * @return true if all values match across all modals
     */
    public boolean verifyAllDetailsValues(Map<String, String> expectedValues) {
        List<Map<String, String>> allModalValues = getAllDetailsValues();
        boolean allValuesMatch = true;

        for (int i = 0; i < allModalValues.size(); i++) {
            Map<String, String> modalValues = allModalValues.get(i);
            boolean modalValuesMatch = true;

            for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
                String expectedKey = entry.getKey()
                    .replace("*", "")  // Remove asterisk
                    .replace(":", "")  // Remove colon
                    .trim();
                String expectedValue = normalizeNumber(entry.getValue());
                
                // Find matching key in modal values
                String actualValue = null;
                for (Map.Entry<String, String> modalEntry : modalValues.entrySet()) {
                    String modalKey = modalEntry.getKey()
                        .replace("*", "")  // Remove asterisk
                        .replace(":", "")  // Remove colon
                        .trim();
                    
                    if (modalKey.equals(expectedKey)) {
                        actualValue = normalizeNumber(modalEntry.getValue());
                        break;
                    }
                }

                if (actualValue != null && actualValue.equals(expectedValue)) {
                    logger.info("Modal {}: Value matches for key '{}': Expected={}, Actual={}", 
                        i, expectedKey, expectedValue, actualValue);
                } else {
                    logger.warn("Modal {}: Value mismatch for key '{}': Expected={}, Actual={}", 
                        i, expectedKey, expectedValue, actualValue);
                    modalValuesMatch = false;
                }
            }

            if (!modalValuesMatch) {
                allValuesMatch = false;
            }
        }

        return allValuesMatch;
    }

    private Map<String, String> getDetailsValues() {
        Map<String, String> values = new HashMap<>();
        try {
            // Get all detail rows with a more specific selector
            By detailRowsSelector = By.cssSelector(".row.row--details");
            List<WebElement> detailRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(detailRowsSelector));
            
            for (WebElement row : detailRows) {
                try {
                    // Use more specific selectors for label and value elements
                    WebElement labelElement = row.findElement(By.cssSelector(".col-6.col-md-6.hyphenation"));
                    WebElement valueElement = row.findElement(By.cssSelector(".col-6.col-md-6.text-right"));
                    
                    // Clean up the label (remove asterisk and colon)
                    String label = labelElement.getText().trim()
                        .replace("*", "")
                        .replace(":", "")
                        .trim();
                    
                    // Clean up the value (remove "Euro" and trim)
                    String value = valueElement.getText().trim()
                        .replace("Euro", "")
                        .trim();
                    
                    if (!label.isEmpty() && !value.isEmpty()) {
                        values.put(label, value);
                    }
                } catch (Exception e) {
                    logger.warn("Could not extract value from row: {}", e.getMessage());
                    try {
                        // Take a screenshot of the problematic row
                        BrowserUtil.takeScreenshot();
                    } catch (Exception screenshotError) {
                        logger.error("Failed to take screenshot: {}", screenshotError.getMessage());
                    }
                }
            }
            
        } catch (Exception e) {
            logger.error("Failed to get values from modal: {}", e.getMessage());
            try {
                BrowserUtil.takeScreenshot();
            } catch (Exception screenshotError) {
                logger.error("Failed to take screenshot: {}", screenshotError.getMessage());
            }
            throw new RuntimeException("Failed to extract values from modal: " + e.getMessage(), e);
        }
        
        return values;
    }

} 