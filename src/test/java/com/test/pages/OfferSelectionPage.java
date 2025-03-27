package com.test.pages;

import com.test.utilities.BrowserUtil;
import com.test.utilities.Utils;
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

    public void isSelectedOptionDisplayed(String optionNumber) {
        
        try {
            String xpath = "//*[@id='anfrage" + (Integer.parseInt(optionNumber) - 1) + "']";
            WebElement option = Driver.getDriver().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(option));
            highlightElement(option);
            new Actions(driver).moveToElement(option).perform();
            logger.info("Selected option verified successfully: {}", option.isSelected());
        } catch (Exception e) {
            logger.error("Failed to verify selected option: {}", e.getMessage());
            throw e;
        }
    }

    public void isSelectedOptionDisplayed2(String optionNumber) {
        
        try {
            
            String xpath = "//*[@id='anfrage" + (Integer.parseInt(optionNumber) - 1) + "']";
            WebElement radioButton = Driver.getDriver().findElement(By.xpath("//*[@id='anfrage" + (Integer.parseInt(optionNumber) - 1) + "']"));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) Driver.getDriver();
            Boolean isChecked = (Boolean) jsExecutor.executeScript("return arguments[0].checked;", radioButton);
            logger.info("JavaScript ile kontrol sonucu (checked): " + isChecked);
            logger.info("selected option: " + xpath);
            //Assert.assertTrue("Seçilen radyo butonu seçili olmalıydı (JS).", isChecked);
        } catch (Exception e) {
            logger.error("Failed to verify selected option: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isSelectedOptionDisplayed3(String optionNumber) {
        try {
            // Wait for any loading indicators to disappear
            BrowserUtil.waitForInvisibilityOfElement(driver, By.cssSelector(".loading-indicator"), 10);
            
            // Try multiple selectors for the offer container
            String[] offerSelectors = {
                "table.offer-table",
                ".offer-container",
                "[data-testid='offer-table']",
                ".offer-selection-container"
            };
            
            WebElement offerContainer = null;
            for (String selector : offerSelectors) {
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
                    offerContainer = driver.findElement(By.cssSelector(selector));
                    logger.info("Found offer container using selector: " + selector);
                    break;
                } catch (Exception e) {
                    logger.debug("Selector not found: " + selector);
                }
            }
            
            if (offerContainer == null) {
                logger.error("No offer container found with any of the attempted selectors");
                Utils.takeScreenshot(driver, "no-offer-container");
                return false;
            }
            
            // Wait for the page to be fully loaded
            BrowserUtil.waitForPageToLoad(10);
            
            // Get all radio buttons and log their attributes
            List<WebElement> radioButtons = driver.findElements(By.cssSelector("input[type='radio']"));
            logger.info("Found " + radioButtons.size() + " radio buttons");
            
            for (WebElement radio : radioButtons) {
                String id = radio.getAttribute("id");
                String name = radio.getAttribute("name");
                String value = radio.getAttribute("value");
                boolean isSelected = radio.isSelected();
                logger.info(String.format("Radio button - ID: %s, Name: %s, Value: %s, Selected: %s", 
                    id, name, value, isSelected));
            }
            
            // Try to find the specific radio button for the given option
            String optionXPath = String.format("//input[@type='radio'][@value='%s']", optionNumber);
            WebElement targetRadio = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(optionXPath)));
            
            // Check if the radio button is selected using multiple methods
            boolean isSelectedByMethod = targetRadio.isSelected();
            boolean isSelectedByJS = (boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].checked;", targetRadio);
            boolean isSelectedByAttr = "true".equals(targetRadio.getAttribute("aria-checked"));
            
            logger.info(String.format("Option %s selection status - isSelected(): %s, JS checked: %s, aria-checked: %s",
                optionNumber, isSelectedByMethod, isSelectedByJS, isSelectedByAttr));
            
            Utils.takeScreenshot(driver, "radio-button-check");
            
            return isSelectedByMethod || isSelectedByJS || isSelectedByAttr;
            
        } catch (Exception e) {
            logger.error("Error checking selected option: " + e.getMessage());
            Utils.takeScreenshot(driver, "option-check-error");
            return false;
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
        
        try {
            // Use JavaScript to directly target all Details buttons by their position
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Get the number of buttons/rows in the offer table 
            List<WebElement> offerRows = driver.findElements(By.cssSelector("input[id^='anfrage']"));
            int numOffers = offerRows.size();
            logger.info("Found {} offer rows on the page", numOffers);
            
            // Create a longer wait for modal interactions
            WebDriverWait modalWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // For each offer row, find and click its details button
            for (int i = 0; i < numOffers; i++) {
                try {
                    // Ensure no modal is open
                    ensureNoModalIsOpen();
                    
                    // Find the specific details button for this row
                    String buttonSelector = "button[id='detailsAnzeigenSollzinsbindung" + i + "']";
                    List<WebElement> buttons = driver.findElements(By.cssSelector(buttonSelector));
                    
                    if (buttons.isEmpty()) {
                        logger.warn("No button found with selector: {}", buttonSelector);
                        // Try a more general approach
                        buttons = driver.findElements(By.cssSelector("button[id*='detailsAnzeigen']"));
                        if (buttons.size() > i) {
                            logger.info("Using button {} from {} buttons found with general selector", 
                                i + 1, buttons.size());
                        } else {
                            logger.error("Not enough buttons found: {} buttons, trying to access index {}", 
                                buttons.size(), i);
                            continue;
                        }
                    }
                    
                    WebElement button = buttons.get(i < buttons.size() ? i : 0);
                    
                    // Scroll to ensure button is visible
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
                    Thread.sleep(1000);
                    
                    // Log button details
                    logger.info("Button {} details - ID: {}, Text: {}, Visible: {}, Enabled: {}", 
                        i + 1, 
                        button.getAttribute("id"), 
                        button.getText(), 
                        button.isDisplayed(), 
                        button.isEnabled());
                    
                    // Click the button using JavaScript (most reliable)
                    js.executeScript("arguments[0].click();", button);
                    logger.info("Clicked button {} using JavaScript", i + 1);
                    
                    // Check for visible modal
                    WebElement modal = null;
                    try {
                        modal = modalWait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".modal.show, .modal[style*='display: block']")));
                    } catch (Exception e) {
                        logger.warn("Modal not visible after clicking button {}: {}", i + 1, e.getMessage());
                        
                        // Try clicking again with another method
                        try {
                            new Actions(driver).moveToElement(button).click().perform();
                            logger.info("Tried clicking button {} with Actions", i + 1);
                            modal = modalWait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector(".modal.show, .modal[style*='display: block']")));
                        } catch (Exception e2) {
                            logger.error("Modal still not visible after second click attempt: {}", e2.getMessage());
                            continue;
                        }
                    }
                    
                    if (modal != null && modal.isDisplayed()) {
                        logger.info("Modal visible after clicking button {}", i + 1);
                        logger.info("Modal ID: {}, Class: {}", 
                            modal.getAttribute("id"), 
                            modal.getAttribute("class"));
                        
                        // Wait to ensure content is loaded
                        Thread.sleep(1500);
                        
                        // Get the values
                        Map<String, String> modalValues = getDetailsValues();
                        
                        // Check if we got values
                        if (modalValues.isEmpty()) {
                            logger.warn("No values found in modal {}, retrying after delay", i + 1);
                            Thread.sleep(2000);
                            modalValues = getDetailsValues();
                        }
                        
                        allModalValues.add(modalValues);
                        
                        // Close the modal
                        closeCurrentModal();
                        
                        // Wait between modal interactions
                        Thread.sleep(2000);
                    } else {
                        logger.error("Failed to detect modal after clicking button {}", i + 1);
                    }
                } catch (Exception e) {
                    logger.error("Error processing button/modal {}: {}", i + 1, e.getMessage());
                    // Continue with next button even if this one failed
                    ensureNoModalIsOpen();
                }
            }
        } catch (Exception e) {
            logger.error("Critical error in getAllDetailsValues: {}", e.getMessage());
        }
        
        // Log summary of modals processed
        logger.info("Processed {} modals in total", allModalValues.size());
        for (int i = 0; i < allModalValues.size(); i++) {
            logger.info("Modal {} has {} values", i + 1, allModalValues.get(i).size());
        }
        
        return allModalValues;
    }
    
    private void ensureNoModalIsOpen() {
        try {
            List<WebElement> openModals = driver.findElements(
                By.cssSelector(".modal.show, .modal[style*='display: block']"));
            
            if (!openModals.isEmpty()) {
                logger.info("Found {} open modals, closing them", openModals.size());
                for (WebElement modal : openModals) {
                    closeModal(modal);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.warn("Error checking for open modals: {}", e.getMessage());
        }
    }
    
    private void closeModal(WebElement modal) {
        try {
            // Try clicking close button
            String[] closeSelectors = {
                "[aria-label='Close']",
                "button.close",
                ".modal-header .close",
                "[data-dismiss='modal']"
            };
            
            boolean closed = false;
            for (String selector : closeSelectors) {
                try {
                    List<WebElement> closeButtons = modal.findElements(By.cssSelector(selector));
                    if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
                        closeButtons.get(0).click();
                        closed = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // If close button didn't work, try ESC key
            if (!closed) {
                try {
                    new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                    closed = true;
                } catch (Exception e) {
                    logger.warn("ESC key failed to close modal: {}", e.getMessage());
                }
            }
            
            // If still not closed, force close with JavaScript
            if (!closed) {
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.display='none';" +
                    "arguments[0].classList.remove('show');" +
                    "document.body.classList.remove('modal-open');" +
                    "var backdrop = document.querySelector('.modal-backdrop');" +
                    "if(backdrop) backdrop.parentNode.removeChild(backdrop);",
                    modal
                );
            }
        } catch (Exception e) {
            logger.error("Error closing modal: {}", e.getMessage());
        }
    }
    
    private void closeCurrentModal() {
        try {
            WebElement modal = driver.findElement(By.cssSelector(".modal.show, .modal[style*='display: block']"));
            closeModal(modal);
            
            // Wait for modal to close
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".modal.show, .modal[style*='display: block']")));
            
            logger.info("Modal closed successfully");
        } catch (Exception e) {
            logger.warn("Failed to close modal gracefully: {}", e.getMessage());
            
            // Force close any modal with JavaScript as fallback
            try {
                ((JavascriptExecutor) driver).executeScript(
                    "var modals = document.querySelectorAll('.modal.show, .modal[style*=\"display: block\"]');" +
                    "modals.forEach(function(modal) {" +
                    "  modal.style.display='none';" +
                    "  modal.classList.remove('show');" +
                    "});" +
                    "document.body.classList.remove('modal-open');" +
                    "var backdrops = document.querySelectorAll('.modal-backdrop');" +
                    "backdrops.forEach(function(backdrop) {" +
                    "  backdrop.parentNode.removeChild(backdrop);" +
                    "});"
                );
                logger.info("Forced modal closure with JavaScript");
            } catch (Exception jsError) {
                logger.error("JavaScript error while forcing modal closure: {}", jsError.getMessage());
            }
        }
    }

    /**
     * Verifies if the values match across all info buttons
     * @param expectedValues The expected values to compare against
     * @return true if all values match across all modals
     */
    public boolean verifyAllDetailsValues(Map<String, String> expectedValues) {
        List<Map<String, String>> allModalValues = getAllDetailsValues();
        boolean allValuesMatch = true;

        // Print out expected values for debugging
        System.out.println("\n=== Expected Values ===");
        expectedValues.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println("=== End of Expected Values ===\n");

        // Print out actual values from each modal for debugging
        for (int i = 0; i < allModalValues.size(); i++) {
            System.out.println("\n=== Modal " + (i + 1) + " Actual Values ===");
            allModalValues.get(i).forEach((key, value) -> System.out.println(key + ": " + value));
            System.out.println("=== End of Modal " + (i + 1) + " Actual Values ===\n");
        }

        for (int i = 0; i < allModalValues.size(); i++) {
            Map<String, String> modalValues = allModalValues.get(i);
            boolean modalValuesMatch = true;

            for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
                String expectedKey = entry.getKey()
                    .replace("*", "")  // Remove asterisk
                    .replace(":", "")  // Remove colon
                    .trim();
                String expectedValue = normalizeNumber(entry.getValue());
                
                // Find matching key in modal values - try with exact match first
                String actualValue = null;
                
                // First try exact key match
                if (modalValues.containsKey(expectedKey)) {
                    actualValue = normalizeNumber(modalValues.get(expectedKey));
                } else {
                    // If not found, try case-insensitive matching
                    for (Map.Entry<String, String> modalEntry : modalValues.entrySet()) {
                        String modalKey = modalEntry.getKey()
                            .replace("*", "")  // Remove asterisk
                            .replace(":", "")  // Remove colon
                            .trim();
                        
                        if (modalKey.equalsIgnoreCase(expectedKey)) {
                            actualValue = normalizeNumber(modalEntry.getValue());
                            break;
                        }
                    }
                }

                if (actualValue != null && actualValue.equals(expectedValue)) {
                    logger.info("Modal {}: Value matches for key '{}': Expected={}, Actual={}", 
                        i + 1, expectedKey, expectedValue, actualValue);
                    System.out.println("Modal " + (i + 1) + ": Value matches for key '" + expectedKey + 
                                      "': Expected=" + expectedValue + ", Actual=" + actualValue);
                } else {
                    logger.warn("Modal {}: Value mismatch for key '{}': Expected={}, Actual={}", 
                        i + 1, expectedKey, expectedValue, actualValue != null ? actualValue : "NULL");
                    System.out.println("Modal " + (i + 1) + ": Value mismatch for key '" + expectedKey + 
                                      "': Expected=" + expectedValue + ", Actual=" + (actualValue != null ? actualValue : "NULL"));
                    modalValuesMatch = false;
                }
            }

            if (!modalValuesMatch) {
                allValuesMatch = false;
            }
        }

        System.out.println("\n=== Comparison Result ===");
        System.out.println("All values match: " + allValuesMatch);
        System.out.println("=== End of Comparison Result ===\n");

        return allValuesMatch;
    }

    private Map<String, String> getDetailsValues() {
        Map<String, String> values = new HashMap<>();
        try {
            // Log the page source to see the actual structure
            WebElement modalElement = driver.findElement(By.cssSelector(".modal.show"));
            logger.info("Modal HTML structure: {}", modalElement.getAttribute("outerHTML"));
            
            // Get the modal ID to determine which tab content to check
            String modalId = modalElement.getAttribute("id");
            logger.info("Processing modal with ID: {}", modalId);
            
            // Look for the details-berechnung tab ID based on the modal number
            String detailsTabId = null;
            if (modalId != null && modalId.matches(".*\\d+$")) {
                // Extract number from the end of the modalId
                String modalNumber = modalId.replaceAll(".*?(\\d+)$", "$1");
                detailsTabId = "details-berechnung" + modalNumber;
                logger.info("Looking for tab content with ID: {}", detailsTabId);
            }
            
            // Wait longer for modal content to be fully loaded
            WebDriverWait modalWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // First wait for the modal container to be visible (more general selector)
            modalWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal.show")));
            
            // Wait additional time for dynamic content to fully load
            Thread.sleep(1000);
            
            // Look for tab content specifically if we have a tab ID
            List<WebElement> detailRows = new ArrayList<>();
            if (detailsTabId != null) {
                try {
                    // First try the specific tab content
                    String tabSelector = "#" + detailsTabId + " .row.row--details";
                    List<WebElement> tabRows = driver.findElements(By.cssSelector(tabSelector));
                    if (tabRows != null && !tabRows.isEmpty()) {
                        logger.info("Found {} rows with tab selector: {}", tabRows.size(), tabSelector);
                        detailRows = tabRows;
                    } else {
                        logger.warn("No rows found with tab selector: {}", tabSelector);
                    }
                } catch (Exception e) {
                    logger.warn("Error finding rows with tab selector: {}", e.getMessage());
                }
            }
            
            // If we didn't find rows with specific tab, try general selectors
            if (detailRows.isEmpty()) {
                String[] rowSelectors = {
                    ".modal.show .row.row--details",
                    ".modal.show .row",
                    ".modal-body .row",
                    ".modal-content .row"
                };
                
                for (String selector : rowSelectors) {
                    try {
                        List<WebElement> rows = driver.findElements(By.cssSelector(selector));
                        if (rows != null && !rows.isEmpty()) {
                            logger.info("Found {} rows with selector: {}", rows.size(), selector);
                            detailRows = rows;
                            break;
                        }
                    } catch (Exception e) {
                        logger.debug("No rows found with selector: {}", selector);
                    }
                }
            }
            
            if (detailRows.isEmpty()) {
                logger.error("No detail rows found in modal with any selector");
                // Try JavaScript to print modal contents to console
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "console.log('Modal contents via JS:', document.querySelector('.modal.show').innerHTML);"
                    );
                    
                    // Try forced refresh of modal content
                    ((JavascriptExecutor) driver).executeScript(
                        "var tabContent = document.querySelector('.modal.show .tab-content');" +
                        "if (tabContent) { tabContent.style.display = 'none'; setTimeout(function() { tabContent.style.display = 'block'; }, 200); }"
                    );
                    Thread.sleep(500);
                    
                    // Try once more after the refresh
                    detailRows = driver.findElements(By.cssSelector(".modal.show .row.row--details"));
                    if (!detailRows.isEmpty()) {
                        logger.info("Found {} rows after modal content refresh", detailRows.size());
                    }
                } catch (Exception jsError) {
                    logger.error("JavaScript error: {}", jsError.getMessage());
                }
                
                // Take screenshot for debugging
                BrowserUtil.takeScreenshot();
                
                // If still no rows found, return empty map
                if (detailRows.isEmpty()) {
                    return values;
                }
            }
            
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
                    // Print row HTML for debugging
                    logger.debug("Row HTML: {}", row.getAttribute("outerHTML"));
                    
                    // Try to extract label-value pairs from different column structures
                    List<WebElement> columns = row.findElements(By.cssSelector("*[class*='col']"));
                    
                    if (columns.size() >= 2) {
                        String label = columns.get(0).getText().trim()
                            .replace("*", "")
                            .replace(":", "")
                            .trim();
                        
                        String value = columns.get(1).getText().trim()
                            .replace("Euro", "")
                            .trim();
                        
                        if (!label.isEmpty() && !value.isEmpty()) {
                            values.put(label, value);
                            System.out.println(label + " -> " + value);
                            logger.info("Added to map - Key: '{}', Value: '{}'", label, value);
                        } else {
                            logger.warn("Skipping empty label or value - Label: '{}', Value: '{}'", label, value);
                        }
                    } else {
                        logger.warn("Row doesn't have enough columns: {}", row.getText());
                    }
                } catch (Exception e) {
                    logger.warn("Could not extract value from row: {}", e.getMessage());
                    try {
                        BrowserUtil.takeScreenshot();
                    } catch (Exception screenshotError) {
                        logger.error("Failed to take screenshot: {}", screenshotError.getMessage());
                    }
                }
            }
            
            System.out.println("\n=== End of Modal Values ===\n");
            
            // Print final map contents for verification
            System.out.println("\n=== Final Map Contents ===");
            System.out.println("Map size: " + values.size());
            values.forEach((key, value) -> System.out.println("Key: '" + key + "', Value: '" + value + "'"));
            System.out.println("=== End of Final Map Contents ===\n");
            
            logger.info("Final map size: {}", values.size());
            logger.info("Map contents:");
            values.forEach((key, value) -> logger.info("Key: '{}', Value: '{}'", key, value));
            
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

    /**
     * Gets the currently selected option
     * @return String containing the selected option number
     */
    public String getSelectedOption() {
        WebElement selectedOption = driver.findElement(By.cssSelector(".offer-selection input[type='radio']:checked"));
        return selectedOption.getAttribute("value");
    }

    public void clickZurueckButtonInOfferSelectionPage() {
        WebElement zurueckButton = Driver.getDriver().findElement(By.id("sollzinsbindungen-zurück-button"));

        try {
            zurueckButton.click();
            logger.info("Successfully clicked back button in offer selection page");
        } catch (Exception e) {
            logger.error("Failed to click back button in offer selection page: {}", e.getMessage());
            throw e;
        }
        logger.info("Clicking back button to return to 	Angaben zum Finanzierungswunsch page");
    }

} 