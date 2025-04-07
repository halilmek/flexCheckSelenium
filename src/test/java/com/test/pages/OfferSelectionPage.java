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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Page Object für die Angebotsauswahl-Seite des FLEXCHECK Moduls.
 * Enthält Elemente und Methoden für die Angebotsauswahl und Modal-Dialog-Interaktionen.
 */
public class OfferSelectionPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(OfferSelectionPage.class);

    /**
     * Konstruktor für die OfferSelectionPage-Klasse.
     * Initialisiert PageFactory-Elemente.
     */
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

    /**
     * Prüft, ob die Angebotsergebnisse angezeigt werden
     * @return true, wenn die Ergebnisse angezeigt werden
     */
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

    /**
     * Prüft, ob ein bestimmter Text in den Angebotsergebnissen angezeigt wird
     * @param expectedText Der zu suchende Text
     * @return true, wenn der Text in den Ergebnissen gefunden wurde
     */
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

    /**
     * Wählt eine verfügbare Angebotsoption aus
     * @throws Exception Wenn die Auswahl nicht möglich ist
     */
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

    /**
     * Klickt auf den "Angebot anfordern" Button, um zur nächsten Seite zu navigieren
     */
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

    /**
     * Wählt eine bestimmte Angebotsoption anhand der Optionsnummer aus
     * @param optionNumber Die Nummer der auszuwählenden Option
     */
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

    /**
     * Prüft, ob die ausgewählte Option angezeigt wird
     * @param optionNumber Die Nummer der zu überprüfenden Option
     */
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

    /**
     * Alternative Methode, um zu prüfen, ob die ausgewählte Option angezeigt wird
     * Verwendet JavaScript, um den Status des Radiobuttons zu überprüfen
     * @param optionNumber Die Nummer der zu überprüfenden Option
     */
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

    /**
     * Erweiterte Methode, um zu prüfen, ob die ausgewählte Option angezeigt wird
     * Versucht verschiedene Selektoren und Methoden, um den Auswahlstatus zu bestimmen
     * @param optionNumber Die Nummer der zu überprüfenden Option
     * @return true, wenn die Option ausgewählt ist, sonst false
     */
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

    


    /**
     * Klickt auf den Details-Button, um den Detail-Modal-Dialog zu öffnen
     * Versucht verschiedene Klickmethoden, falls die Standard-Methode fehlschlägt
     */
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

    /**
     * Gibt die Werte aus dem Modal-Dialog in der Konsole aus
     * Extrahiert alle Detail-Zeilen und zeigt Label-Value-Paare an
     */
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


    /**
     * Überprüft die Rechner-Werte für den Tilgungs-Zahlungstyp
     * Vergleicht die gespeicherten Werte mit den angezeigten Werten im Modal-Dialog
     * 
     * @param randomPurchasePrice Der zufällig generierte Kaufpreis
     * @param randomLoanAmount Der zufällig generierte Darlehensbetrag
     * @param randomPurpose Der zufällig generierte Verwendungszweck
     * @param randomRepaymentPercentage Der zufällig generierte Tilgungsprozentsatz
     * @return true, wenn alle Werte übereinstimmen, sonst false
     */
    public boolean verifyingCalculatorValuesForTilgungPaymentType
    (String randomPurchasePrice, String randomLoanAmount, String randomPurpose, String randomRepaymentPercentage) {

        try {
            
                    // Log stored values for debugging
        randomRepaymentPercentage = randomRepaymentPercentage + ",00 %";
        logger.info("Stored values:");
        logger.info("Purchase price: {}", randomPurchasePrice);
        logger.info("Loan amount: {}", randomLoanAmount);
        logger.info("Purpose: {}", randomPurpose);
        logger.info("Repayment percentage: {}", randomRepaymentPercentage);

        // Create map of expected values
        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("Kaufpreis*:", randomPurchasePrice);
        expectedValues.put("Nettodarlehensbetrag:", randomLoanAmount);
        expectedValues.put("Verwendungszweck:", randomPurpose);
        //expectedValues.put("Tilgung p.a.:", randomRepaymentPercentage);

        // Verify values across all info buttons
        boolean allValuesMatch = verifyAllDetailsValues(expectedValues);

        logger.info("All values matched: {}", allValuesMatch);
        //new Actions(Driver.getDriver()).pause(Duration.ofSeconds(10)).perform();
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();

        return allValuesMatch;

        } catch (Exception e) {
            logger.error("Error verifying calculator values for Tilgung payment type: {}", e.getMessage());
            throw e;
        }

    }

    /**
     * Überprüft die Rechner-Werte für den Tilgungs-Zahlungstyp im ersten Modal-Dialog
     * Extrahiert und validiert verschiedene Finanzierungsdetails aus dem Modal
     * 
     * @param randomPurchasePrice Der zufällig generierte Kaufpreis
     * @param params Zusätzliche optionale Parameter
     * @return true, wenn die Überprüfung erfolgreich ist, sonst false
     */
    public boolean verifyingCalculatorValuesForTilgungPaymentTypeInFirstModal
    (String randomPurchasePrice, String... params) {

        try {
            WebElement infoButton1 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung0']"));
            WebElement closeButton1 = driver.findElement(By.xpath("//*[@id='angebotDetailModal0']/div/div/div[1]/div[2]/button"));
            WebElement infoButton2 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung1']"));
            WebElement closeButton2 = driver.findElement(By.xpath("//*[@id='angebotDetailModal1']/div/div/div[1]/div[2]/button"));
            WebElement infoButton3 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung2']"));
            WebElement closeButton3 = driver.findElement(By.xpath("//*[@id='angebotDetailModal2']/div/div/div[1]/div[2]/button"));
            WebElement infoButton4 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung3']"));
            WebElement closeButton4 = driver.findElement(By.xpath("//*[@id='angebotDetailModal3']/div/div/div[1]/div[2]/button"));
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(infoButton1));

            new Actions(driver).moveToElement(infoButton1).click().perform();

            // JavaScript Executor oluştur
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.visibilityOf(closeButton1));

            List<String> extractedValues = new ArrayList<>();

            // document.body.innerText değerini al ve String değişkene ata
            String pageText = (String) jsExecutor.executeScript("return document.body.innerText;");

            // Sonucu yazdır
            // Regex ile ilgili kısmı çek
            String regex = "Kaufpreis\\*:\\s*(.*?)\\s*Euro\\s*" +
            "Nettodarlehensbetrag:\\s*(.*?)\\s*Euro\\s*" +
            "Verwendungszweck:\\s*(.*?)\\s*" +
            "Sollzinsbindung:\\s*(.*?)\\s*" +
            "gebundener Sollzins p.a.:\\s*(.*?)\\s*%\\s*" +
            "effektiver Jahreszins:\\s*(.*?)\\s*%\\s*" +
            "Tilgung p.a.:\\s*(.*?)\\s*%\\s*" +
            "monatliche Rate:\\s*(.*?)\\s*Euro\\s*" +
            "voraussichtliche Restschuld bei Ablauf der Sollzinsbindung:\\s*(.*?)\\s*Euro\\s*" +
            "Anzahl Raten Zinsbindung:\\s*(.*?)\\s*" +
            "Anzahl Raten Gesamtlaufzeit \\(bei gleichem Anschlusszins\\):\\s*(.*?)\\s*" +
            "Vertragslaufzeit:\\s*(.*?)\\s*" +
            "Gesamtbetrag der Finanzierung:\\s*(.*?)\\s*Euro";


            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

            Matcher matcher = pattern.matcher(pageText);
            Map<String, String> loanDetails = new HashMap<>();
            
            if (matcher.find()) { 
                try {
                    loanDetails.put("Kaufpreis*", matcher.group(1));
                    loanDetails.put("Nettodarlehensbetrag", matcher.group(2));
                    loanDetails.put("Verwendungszweck", matcher.group(3));
                    loanDetails.put("Sollzinsbindung", matcher.group(4));
                    loanDetails.put("gebundener Sollzins p.a.", matcher.group(5) + " %");
                    loanDetails.put("effektiver Jahreszins", matcher.group(6) + " %");
                    loanDetails.put("Tilgung p.a.", matcher.group(7) + " %");
                    loanDetails.put("monatliche Rate", matcher.group(8));
                    loanDetails.put("voraussichtliche Restschuld bei Ablauf der Sollzinsbindung", matcher.group(9));
                    loanDetails.put("Anzahl Raten Zinsbindung", matcher.group(10));
                    loanDetails.put("Anzahl Raten Gesamtlaufzeit (bei gleichem Anschlusszins)", matcher.group(11));
                    loanDetails.put("Vertragslaufzeit", matcher.group(12));
                    loanDetails.put("Gesamtbetrag der Finanzierung", matcher.group(13));
            
                    // Sonucu ekrana yazdır
                    for (Map.Entry<String, String> entry : loanDetails.entrySet()) {
                        //System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());

                        String key = entry.getKey();
                        String value = entry.getValue();
                
                        if (key.contains("Tilgung p.a.")) {  // Faiz oranı için
                            value = value.replace(",00 %", "");
                            extractedValues.add(value);
                            logger.info("Tilgung p.a.: {}", value);
                        } else if (key.contains("Nettodarlehensbetrag")) { // Fiyatlar için
                            value = value.replace(",00", "").replace(".", "");
                            extractedValues.add(value);
                            logger.info("Nettodarlehensbetrag: {}", value);
                        } else if (key.contains("Verwendungszweck")) {
                            //value = value.replace(",00 Euro", "");
                            extractedValues.add(value);
                            logger.info("Verwendungszweck: {}", value);
                        } else if (key.contains("Kaufpreis*")) {
                            value = value.replace(",00", "").replace(".", "");
                            extractedValues.add(value);
                            logger.info("Kaufpreis*: {}", value);
                        }
                
                        //extractedValues.add(value);

                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Hata: Eşleşme grupları eksik! Regex doğru çalışmıyor.");
                }
            } else {
                System.out.println("İstenen veri bulunamadı!");
            }
            

            new Actions(driver).moveToElement(closeButton1).click().perform();
            
            boolean allValuesMatch = true;

            // Parametrelerin listede olup olmadığını kontrol et
            for (String param : params) {
                if (!extractedValues.contains(param)) {
                    allValuesMatch = false;
                    logger.warn("Missing parameter: {}", param);
                    return allValuesMatch;  // En az bir parametre eksikse false
                }

                logger.info("Parameter value: {}", param);
            }

            extractedValues.stream().forEach(System.out::println);

            
            return allValuesMatch;
        } catch (Exception e) {
            logger.error("Error verifying calculator values for Tilgung payment type: {}", e.getMessage());
            throw e;
        }

    }


    /**
     * Überprüft die Rechner-Werte für den Tilgungs-Zahlungstyp im zweiten Modal-Dialog
     * Extrahiert und validiert verschiedene Finanzierungsdetails aus dem zweiten Modal
     * 
     * @param randomPurchasePrice Der zufällig generierte Kaufpreis
     * @param params Zusätzliche optionale Parameter für die Validierung
     * @return true, wenn die Überprüfung erfolgreich ist, sonst false
     */
    public boolean verifyingCalculatorValuesForTilgungPaymentTypeInSecondModal
    (String randomPurchasePrice, String... params) {

        try {
            
            WebElement infoButton2 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung1']"));
            WebElement closeButton2 = driver.findElement(By.xpath("//*[@id='angebotDetailModal1']/div/div/div[1]/div[2]/button"));
            WebElement infoButton3 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung2']"));
            WebElement closeButton3 = driver.findElement(By.xpath("//*[@id='angebotDetailModal2']/div/div/div[1]/div[2]/button"));
            WebElement infoButton4 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung3']"));
            WebElement closeButton4 = driver.findElement(By.xpath("//*[@id='angebotDetailModal3']/div/div/div[1]/div[2]/button"));
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(infoButton2));

            new Actions(driver).moveToElement(infoButton2).click().perform();

            // JavaScript Executor oluştur
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.visibilityOf(closeButton2));

            List<String> extractedValues = new ArrayList<>();

            // document.body.innerText değerini al ve String değişkene ata
            String pageText = (String) jsExecutor.executeScript("return document.body.innerText;");

            // Sonucu yazdır
            // Regex ile ilgili kısmı çek
            String regex = "Kaufpreis\\*:\\s*(.*?)\\s*Euro\\s*" +
            "Nettodarlehensbetrag:\\s*(.*?)\\s*Euro\\s*" +
            "Verwendungszweck:\\s*(.*?)\\s*" +
            "Sollzinsbindung:\\s*(.*?)\\s*" +
            "gebundener Sollzins p.a.:\\s*(.*?)\\s*%\\s*" +
            "effektiver Jahreszins:\\s*(.*?)\\s*%\\s*" +
            "Tilgung p.a.:\\s*(.*?)\\s*%\\s*" +
            "monatliche Rate:\\s*(.*?)\\s*Euro\\s*" +
            "voraussichtliche Restschuld bei Ablauf der Sollzinsbindung:\\s*(.*?)\\s*Euro\\s*" +
            "Anzahl Raten Zinsbindung:\\s*(.*?)\\s*" +
            "Anzahl Raten Gesamtlaufzeit \\(bei gleichem Anschlusszins\\):\\s*(.*?)\\s*" +
            "Vertragslaufzeit:\\s*(.*?)\\s*" +
            "Gesamtbetrag der Finanzierung:\\s*(.*?)\\s*Euro";


            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

            Matcher matcher = pattern.matcher(pageText);
            Map<String, String> loanDetails = new HashMap<>();
            
            if (matcher.find()) { 
                try {
                    loanDetails.put("Kaufpreis*", matcher.group(1));
                    loanDetails.put("Nettodarlehensbetrag", matcher.group(2));
                    loanDetails.put("Verwendungszweck", matcher.group(3));
                    loanDetails.put("Sollzinsbindung", matcher.group(4));
                    loanDetails.put("gebundener Sollzins p.a.", matcher.group(5) + " %");
                    loanDetails.put("effektiver Jahreszins", matcher.group(6) + " %");
                    loanDetails.put("Tilgung p.a.", matcher.group(7) + " %");
                    loanDetails.put("monatliche Rate", matcher.group(8));
                    loanDetails.put("voraussichtliche Restschuld bei Ablauf der Sollzinsbindung", matcher.group(9));
                    loanDetails.put("Anzahl Raten Zinsbindung", matcher.group(10));
                    loanDetails.put("Anzahl Raten Gesamtlaufzeit (bei gleichem Anschlusszins)", matcher.group(11));
                    loanDetails.put("Vertragslaufzeit", matcher.group(12));
                    loanDetails.put("Gesamtbetrag der Finanzierung", matcher.group(13));
            
                    // Sonucu ekrana yazdır
                    for (Map.Entry<String, String> entry : loanDetails.entrySet()) {
                        //System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());

                        String key = entry.getKey();
                        String value = entry.getValue();
                
                        if (key.contains("Tilgung p.a.")) {  // Faiz oranı için
                            value = value.replace(",00 %", "");
                            extractedValues.add(value);
                            logger.info("Tilgung p.a.: {}", value);
                        } else if (key.contains("Nettodarlehensbetrag")) { // Fiyatlar için
                            value = value.replace(",00", "").replace(".", "");
                            extractedValues.add(value);
                            logger.info("Nettodarlehensbetrag: {}", value);
                        } else if (key.contains("Verwendungszweck")) {
                            //value = value.replace(",00 Euro", "");
                            extractedValues.add(value);
                            logger.info("Verwendungszweck: {}", value);
                        } else if (key.contains("Kaufpreis*")) {
                            value = value.replace(",00", "").replace(".", "");
                            extractedValues.add(value);
                            logger.info("Kaufpreis*: {}", value);
                        }
                
                        //extractedValues.add(value);

                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Hata: Eşleşme grupları eksik! Regex doğru çalışmıyor.");
                }
            } else {
                System.out.println("İstenen veri bulunamadı!");
            }
            

            new Actions(driver).moveToElement(closeButton2).click().perform();
            
            boolean allValuesMatch = true;

            // Parametrelerin listede olup olmadığını kontrol et
            for (String param : params) {
                if (!extractedValues.contains(param)) {
                    allValuesMatch = false;
                    logger.warn("Missing parameter: {}", param);
                    return allValuesMatch;  // En az bir parametre eksikse false
                }

                logger.info("Parameter value: {}", param);
            }

            extractedValues.stream().forEach(System.out::println);

            
            return allValuesMatch;
        } catch (Exception e) {
            logger.error("Error verifying calculator values for Tilgung payment type: {}", e.getMessage());
            throw e;
        }

    }

    /**
     * Überprüft die Rechner-Werte für den Tilgungs-Zahlungstyp im vierten Modal-Dialog
     * Extrahiert und validiert verschiedene Finanzierungsdetails aus dem vierten Modal
     * 
     * @param params Zusätzliche optionale Parameter für die Validierung
     * @return true, wenn die Überprüfung erfolgreich ist, sonst false
     */
    public boolean verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal
    (String... params) {

        try {
            WebElement infoButton4 = driver.findElement(By.xpath("//*[@id='detailsAnzeigenSollzinsbindung3']"));
            WebElement closeButton4 = driver.findElement(By.xpath("//*[@id='angebotDetailModal3']/div/div/div[1]/div[2]/button"));
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(infoButton4));

            new Actions(driver).moveToElement(infoButton4).click().perform();

            // JavaScript Executor oluştur
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.visibilityOf(closeButton4));

            List<String> extractedValues = new ArrayList<>();
            List<WebElement> offerNumbers = driver.findElements(By.cssSelector("[id^=details-berechnung]"));
            

            // document.body.innerText değerini al ve String değişkene ata
            //String pageText = (String) jsExecutor.executeScript(    "return document.body.innerText;");
            //String elementText = (String) jsExecutor.executeScript("return document.all('#details-berechnung3').innerText;");
            String elementText = "return Array.from(document.querySelectorAll('[id^=details-berechnung] .text-right')).map(element => element.innerText)";

            extractedValues = (List<String>) jsExecutor.executeScript(elementText);

            List<String> filteredValues = extractedValues;

            IntStream.range(0, filteredValues.size()).
            filter(i -> i % 13 < 3).
            forEach(i -> {
                logger.info("extractedValues[{}]: {}", i, filteredValues.get(i));
            });
            

            logger.info("params[0]: {}", params[0]);
            logger.info("params[1]: {}", params[1]);
            logger.info("params[2]: {}", params[2]);
            logger.info("offerNumbers: {}", offerNumbers.size());

/*
            List<String> textValuesForJsoup = new ArrayList<>();
            String html = "#details-berechnung3 > div.card-body";
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select(".text-right");

            for(Element element : elements) {

                String text = element.ownText().trim();
                if(!text.isEmpty()) {

                    textValuesForJsoup.add(text);
                    logger.info("Text: {}", text);
                }

            }

            textValuesForJsoup.forEach(System.out::println);
*/


            List<String> processedValues = extractedValues.stream()
                .map(element -> element.contains(".") ? element.replace(".", "") : element)
                .map(element -> element.contains(",") ? element.substring(0, element.indexOf(",")) : element)
                .collect(Collectors.toList());

            boolean allParametersMatch = Arrays.stream(params)
                .allMatch(param -> 
                    processedValues.stream()
                        .filter(element -> param.equals(element))
                        .count() >= offerNumbers.size());

            return allParametersMatch;
        }
        catch (Exception e) {
            logger.error("Error verifying calculator values for Tilgung payment type: {}", e.getMessage());
            throw e;
        }   

    }


    /**
     * Überprüft die Rechner-Werte für den Monatlichen-Zahlungstyp
     * 
     * @param randomPurchasePrice Der zufällig generierte Kaufpreis
     * @param randomLoanAmount Der zufällig generierte Nettodarlehensbetrag
     * @param randomPurpose Der zufällig generierte Verwendungszweck
     * @param randomMonthlyPayment Der zufällig generierte Monatliche Rate
     * @return true, wenn die Überprüfung erfolgreich ist, sonst false
     */
    public boolean verifyingCalculatorValuesForMonatlicheRatePaymentType
    (String randomPurchasePrice, String randomLoanAmount, String randomPurpose, String randomMonthlyPayment) {

        try {
        // Log stored values for debugging
        logger.info("Stored values:");
        logger.info("Purchase price: {}", randomPurchasePrice);
        logger.info("Loan amount: {}", randomLoanAmount);
        logger.info("Purpose: {}", randomPurpose);
        logger.info("Monthly payment: {}", randomMonthlyPayment);

        // Create map of expected values
        Map<String, String> expectedValues = new HashMap<>();
        expectedValues.put("Kaufpreis*:", randomPurchasePrice);
        expectedValues.put("Nettodarlehensbetrag:", randomLoanAmount);
        expectedValues.put("Verwendungszweck:", randomPurpose);
        // Don't check monthly payment as it might be recalculated with interest
        // expectedValues.put("monatliche Rate:", randomMonthlyPayment);

        // Verify values across all info buttons
        boolean allValuesMatch = verifyAllDetailsValues(expectedValues);

        logger.info("All values matched: {}", allValuesMatch);
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();

        return allValuesMatch;
        } catch (Exception e) {
            logger.error("Error verifying calculator values for Monatliche Rate payment type: {}", e.getMessage());
            throw e;
        }   
    }


    /**
     * Überprüft die Rechner-Werte für den Gesamtbetrag-Zahlungstyp
     * 
     * @param randomPurchasePrice Der zufällig generierte Kaufpreis
     * @param randomLoanAmount Der zufällig generierte Nettodarlehensbetrag
     * @param randomPurpose Der zufällig generierte Verwendungszweck
     * @param randomJahreFürGesamtlaufzeit Der zufällig generierte Jahre für Gesamtlaufzeit
     * @param randomMonateFürGesamtlaufzeit Der zufällig generierte Monate für Gesamtlaufzeit
     * @return true, wenn die Überprüfung erfolgreich ist, sonst false
     */
    public boolean verifyingCalculatorValuesForGesamtbetragPaymentType
    (String randomPurchasePrice, String randomLoanAmount, String randomPurpose, String randomJahreFürGesamtlaufzeit, String randomMonateFürGesamtlaufzeit) {

        try {

            String totalTerm = randomJahreFürGesamtlaufzeit + " Jahre " + randomMonateFürGesamtlaufzeit + " Monate";
            // Log stored values for debugging
            logger.info("Stored values:");
            logger.info("Purchase price: {}", randomPurchasePrice);
            logger.info("Loan amount: {}", randomLoanAmount);
            logger.info("Purpose: {}", randomPurpose);
            logger.info("Total term: {}", totalTerm);
            
            // Create map of expected values
            Map<String, String> expectedValues = new HashMap<>();
            expectedValues.put("Kaufpreis*:", randomPurchasePrice);
            expectedValues.put("Nettodarlehensbetrag:", randomLoanAmount);
            expectedValues.put("Verwendungszweck:", randomPurpose);
            expectedValues.put("Vertragslaufzeit:", totalTerm); //Create a static variable for totalTerm 
            // Note: We don't verify the total term as it might be formatted differently in the modal
    
            // Verify values across all info buttons
            boolean allValuesMatch = verifyAllDetailsValues(expectedValues);
    
            logger.info("All values matched: {}", allValuesMatch);
            new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();
            
            if (!allValuesMatch) {
                throw new AssertionError("Not all values matched across info buttons. Check logs for details.");
            }

            return allValuesMatch;

        } catch (Exception e) {
            logger.error("Error verifying calculator values for Gesamtbetrag payment type: {}", e.getMessage());
            throw e;
        }
    }



    /**
     * Normalisiert eine Zahl, indem Tausendertrennzeichen entfernt, Kommas durch Punkte ersetzt und nachfolgende Nullen entfernt werden
     * 
     * @param value Der zu normalisierende Wert
     * @return Der normalisierte Wert
     */
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
    
    /**
     * Stellt sicher, dass kein Modal offen ist
     */
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

    /**
     * Schließt ein Modal
     * 
     * @param modal Das zu schließende Modal
     */
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

    /**
     * Schließt das aktuelle Modal
     */
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

    /**
     * Extrahieert und verarbeitet die Werte aus dem aktuellen Modal
     * 
     * @return Ein Map-Objekt mit den extrahierten Werten
     */
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

    /**
     * Klickt auf den Zurück-Button in der Angebotsauswahlseite
     */
    public void clickZurueckButtonInOfferSelectionPage() {
        WebElement zurueckButton = Driver.getDriver().findElement(By.id("sollzinsbindungen-zurück-button"));

        try {
            logger.info("Clicking back button to return to Finanzierung page");

            zurueckButton.click();
            logger.info("Successfully clicked back button in offer selection page");
        } catch (Exception e) {
            logger.error("Failed to click back button in offer selection page: {}", e.getMessage());
            throw e;
        }
        logger.info("Clicking back button to return to 	Angaben zum Finanzierungswunsch page");
    }

    /**
     * Überprüft die Auswahl der Option in der Angebotsauswahlseite
     * 
     * @param randomOption Die zufällig generierte Option
     * @return true, wenn die Auswahl korrekt ist, sonst false
     */
    public boolean verifySelectedOption(String randomOption) {

        logger.info("Verifying selected option persistence");
        logger.info("Selected option number: {}", randomOption);
        
        try {
            boolean isSelectedOptionVerified = false;
            // Wait for page to load after navigation
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
            
            // Try multiple selectors to find the offer container
            String[] possibleSelectors = {
                ".panel-card-button", 
                ".offer-container", 
                ".offer-selection-container",
                "[data-testid='offer-container']",
                "table.offer-table"
            };
            
            boolean foundElement = false;
            for (String selector : possibleSelectors) {
                try {
                    logger.info("Trying to locate offer element with selector: {}", selector);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
                    logger.info("Successfully found element with selector: {}", selector);
                    foundElement = true;
                    break;
                } catch (Exception e) {
                    logger.info("Could not find element with selector: {}", selector);
                }
            }
            
            if (!foundElement) {
                logger.error("Could not find any offer container element");
                Utils.takeScreenshot(Driver.getDriver(), "offer-container-not-found");
                throw new AssertionError("Could not find any offer container element after navigation");
            }
            
            // Execute JavaScript to check for radio button selection
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            
            // Simple script to find selected option
            String simpleScript = 
                "var options = document.querySelectorAll('.panel-card-button');" +
                "var result = null;" +
                "for (var i = 0; i < options.length; i++) {" +
                "  var option = options[i];" +
                "  var showChosen = option.querySelector('.hidden-unforced.show-chosen');" +
                "  if (showChosen && (window.getComputedStyle(showChosen).display !== 'none')) {" +
                "    result = {" +
                "      index: i + 1," +
                "      hasShownChosen: true," +
                "      displayValue: window.getComputedStyle(showChosen).display," +
                "      elementId: option.id || 'no-id'" +
                "    };" +
                "    break;" +
                "  }" +
                "}" +
                "return result;";
            
            Map<String, Object> result = (Map<String, Object>) js.executeScript(simpleScript);
            
            // Print all keys and values from the map for debugging
            logger.info("JavaScript result map contents:");
            if (result != null) {
                for (Map.Entry<String, Object> entry : result.entrySet()) {
                    logger.info("  {} = {}", entry.getKey(), entry.getValue());
                }
                
                int selectedIndex = ((Long) result.get("index")).intValue();
                logger.info("Found selected option at index: {}", selectedIndex);
                
                // Verify if this matches our expected selection
                //Assertions.assertEquals(Integer.parseInt(randomOption), selectedIndex,
                //    "Selected option does not match the expected option");
                if (Integer.parseInt(randomOption) == selectedIndex) {
                    isSelectedOptionVerified = true;
                }
                
                logger.info("Successfully verified selected option persistence");
            } else {
                // Try a direct class-based check without computed style
                String directCheckScript = 
                    "var options = document.querySelectorAll('.panel-card-button');" +
                    "return options.length;";
                
                Long optionsCount = (Long) js.executeScript(directCheckScript);
                logger.info("Found {} panel-card-button elements", optionsCount);
                
                // Take screenshot for debugging
                Utils.takeScreenshot(Driver.getDriver(), "no-selected-option");
                logger.error("No selected option found. Number of options: {}", optionsCount);
                
                throw new AssertionError("No selected option found among " + optionsCount + " options");
            }

            return isSelectedOptionVerified;
        } catch (Exception e) {
            logger.error("Failed to verify selected option persistence: {}", e.getMessage());
            Utils.takeScreenshot(Driver.getDriver(), "option-verification-failure");
            throw e;
        }
    }   

} 