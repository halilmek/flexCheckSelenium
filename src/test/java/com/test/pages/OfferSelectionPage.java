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

import java.util.List;

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

} 