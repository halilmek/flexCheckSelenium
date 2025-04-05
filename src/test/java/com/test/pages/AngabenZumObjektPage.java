package com.test.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BooleanSupplier;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.utilities.ConfigurationReader;
import com.test.utilities.Driver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Page-Klasse für die "Angaben zum Objekt"-Seite.
 * Enthält Elemente und Methoden zur Interaktion mit der Objektinformationsseite im FlexCheck-Rechner.
 */
public class AngabenZumObjektPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(AngabenZumObjektPage.class);

    @FindBy(css = "select[name='verwendungszweck']")
    private WebElement verwendungszweckDropdown;

    @FindBy(css = "input[name='plz']")
    private WebElement plzInput;

    @FindBy(css = "input[name='kaufpreis']")
    private WebElement kaufpreisInput;

    @FindBy(css = "select[name='objektart']")
    private WebElement objektartDropdown;

    @FindBy(css = "button.btn--nav-next")
    private WebElement weiterButton;

    /**
     * Konstruktor für die AngabenZumObjektPage.
     * Initialisiert PageFactory-Elemente.
     */
    public AngabenZumObjektPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /**
     * Navigiert zur FlexCheck-Rechner-Startseite.
     * Verwendet die URL aus der Konfigurationsdatei.
     */
    public void navigatingToHomePage() {
        driver.get(ConfigurationReader.getProperty("url"));
        logger.info("Navigated to FlexCheck calculator page");
    }
    
    /**
     * Gibt einen zufälligen Verwendungszweck zurück.
     * 
     * @return Ein zufällig ausgewählter Verwendungszweck als String
     */
    public String getVerwendungszweck() {

        // Tekrar içermeyen elemanları saklayan Set
        Set<String> uniqueSet = new LinkedHashSet<String>();

        // Eleman ekleme
        uniqueSet.add("Kauf und Modernisierung");
        uniqueSet.add("Kauf");
        uniqueSet.add("Kauf Neubau");
        uniqueSet.add("Kauf und Erweiterung");
        uniqueSet.add("Modernisierung");
        uniqueSet.add("Neubau");
        uniqueSet.add("Umschuldung");
        uniqueSet.add("sonstige Verwendung");

        // Set'i List'e çevir (rastgele seçim için)
        List<String> uniqueList = new ArrayList<String>(uniqueSet);

        // Rastgele eleman seç
        Random random = new Random();
        String randomElement = uniqueList.get(random.nextInt(uniqueList.size()));
        return randomElement;   
    }

    /**
     * Gibt eine zufällige Objektart zurück.
     * 
     * @return Eine zufällig ausgewählte Objektart als String
     */
    public String getObjektart() {

        // Tekrar içermeyen elemanları saklayan Set
        Set<String> uniqueSet = new LinkedHashSet<String>();

        // Eleman ekleme
        uniqueSet.add("Einfamilienhaus");
        uniqueSet.add("Reihenendhaus");
        uniqueSet.add("Reihenmittelhaus");
        uniqueSet.add("Doppelhaushälfte");
        uniqueSet.add("Zweifamilienhaus");
        uniqueSet.add("Baugrundstück");
        uniqueSet.add("Eigentumswohnung");

        // Set'i List'e çevir (rastgele seçim için)
        List<String> uniqueList = new ArrayList<String>(uniqueSet);

        // Rastgele eleman seç
        Random random = new Random();
        String randomElement = uniqueList.get(random.nextInt(uniqueList.size()));
        return randomElement;   
    }

    /**
     * Wählt den angegebenen Verwendungszweck aus der Dropdown-Liste aus.
     * 
     * @param purpose Der auszuwählende Verwendungszweck als String
     */
    public void selectVerwendungszweck(String purpose) {
        try {
            waitForElementToBeClickable(verwendungszweckDropdown);
            highlightElement(verwendungszweckDropdown);
            Select select = new Select(verwendungszweckDropdown);
            select.selectByVisibleText(purpose);
            logger.info("Selected usage purpose: {}", purpose);
        } catch (Exception e) {
            logger.error("Failed to select usage purpose: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gibt die Postleitzahl in das entsprechende Feld ein.
     * 
     * @param plz Die einzugebende Postleitzahl als String
     */
    public void enterPlz(String plz) {
        waitForElementToBeClickable(plzInput);
        highlightElement(plzInput);
        plzInput.clear();
        plzInput.sendKeys(plz);
        logger.info("Entered postal code: {}", plz);
    }

    /**
     * Gibt den Kaufpreis in das entsprechende Feld ein.
     * 
     * @param price Der einzugebende Kaufpreis als String
     */
    public void enterKaufpreis(String price) {
        waitForElementToBeClickable(kaufpreisInput);
        highlightElement(kaufpreisInput);
        kaufpreisInput.clear();
        kaufpreisInput.sendKeys(price);
        logger.info("Entered purchase price: {}", price);
    }

    /**
     * Wählt die angegebene Objektart aus der Dropdown-Liste aus.
     * 
     * @param type Die auszuwählende Objektart als String
     */
    public void selectObjektart(String type) {
        waitForElementToBeClickable(objektartDropdown);
        highlightElement(objektartDropdown);
        Select select = new Select(objektartDropdown);
        select.selectByVisibleText(type);
        logger.info("Selected property type: {}", type);
    }

    /**
     * Klickt auf den Weiter-Button, um zur nächsten Seite zu navigieren.
     * 
     * @throws InterruptedException Wenn der Thread während des Wartens unterbrochen wird
     */
    public void clickWeiter() throws InterruptedException {
        waitForElementToBeClickable(weiterButton);
        highlightElement(weiterButton);
        weiterButton.click();
        logger.info("Clicked Weiter button on object details page");
        Thread.sleep(2000);
    }

    /**
     * Gibt den aktuell ausgewählten Verwendungszweck zurück.
     * 
     * @return Der ausgewählte Verwendungszweck als String
     */
    public String getSelectedVerwendungszweck() {
        Select select = new Select(verwendungszweckDropdown);
        logger.info("Selected usage purpose: " + select.getFirstSelectedOption().getText());
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Gibt die eingegebene Postleitzahl zurück.
     * Verwendet JavaScript, um den Wert direkt aus dem Element zu extrahieren.
     * 
     * @return Die eingegebene Postleitzahl als String
     */
    public String getEnteredPLZ() {

        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", plzInput);
            logger.info("Value from property: " + valueFromProperty);
            return valueFromProperty;
        } catch (Exception e) {
            logger.error("Failed to verify values for postal code in Objekt page: {}", e.getMessage());
            throw e;
        }   
    }

    /**
     * Gibt den eingegebenen Kaufpreis zurück.
     * Verwendet JavaScript, um den Wert direkt aus dem Element zu extrahieren.
     * Entfernt Tausender-Punkte aus dem Kaufpreis.
     * 
     * @return Der eingegebene Kaufpreis als String ohne Tausender-Punkte
     */
    public String getEnteredKaufpreis() {
        
        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", kaufpreisInput);
            logger.info("Value from property: " + valueFromProperty);
            String expectedAmount = valueFromProperty.replace(".", "");
            return expectedAmount;
        } catch (Exception e) {
            logger.error("Failed to verify values for purchase price in Objekt page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gibt die aktuell ausgewählte Objektart zurück.
     * 
     * @return Die ausgewählte Objektart als String
     */
    public String getSelectedObjektart() {
        Select select = new Select(objektartDropdown);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Gibt die eingegebene Postleitzahl zurück.
     * Verwendet eine Webdriver-spezifische Methode, um den Wert zu erhalten.
     * 
     * @return Die eingegebene Postleitzahl als String
     */
    public String getPlz() {
        return plzInput.getAttribute("value");
    }

    /**
     * Gibt den eingegebenen Kaufpreis zurück.
     * 
     * @return Der eingegebene Kaufpreis als String
     */
    public String getKaufpreis() {
        return kaufpreisInput.getAttribute("value");
    }

    /**
     * Prüft, ob eine Fehlermeldung für Postleitzahl und Kaufpreis angezeigt wird.
     * 
     * @return true, wenn die Fehlermeldung angezeigt wird, sonst false
     */
    public boolean getErrorMessageForPostalCodeAndPurchasePrice() {
        try {
            // 45s'ye kadar bekle
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
            
            // Hata mesajını içeren elementi bul
            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'error-message') and contains(text(), 'Bitte geben Sie eine gültige Postleitzahl ein.')]")));
            
            // Eğer element bulunduysa true döndür
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            logger.error("Error message for postal code and purchase price is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Prüft, ob alle erforderlichen Felder auf der Angaben zum Objekt Seite angezeigt werden.
     * 
     * @return true, wenn alle erforderlichen Felder angezeigt werden, sonst false
     */
    public boolean isDisplayedAllRequiredFields() {
        try {
            // Check if all required fields are displayed
            waitForElementToBeVisible(verwendungszweckDropdown);
            waitForElementToBeVisible(plzInput);
            waitForElementToBeVisible(kaufpreisInput);
            waitForElementToBeVisible(objektartDropdown);
            waitForElementToBeVisible(weiterButton);

            // Wait for page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> (((JavascriptExecutor) webDriver).executeScript("return document.readyState")).equals("complete"));
            logger.info("Page loaded: " + js.executeScript("return document.readyState"));
            
            // If all are displayed, return true
            return verwendungszweckDropdown.isDisplayed() && 
                   plzInput.isDisplayed() && 
                   kaufpreisInput.isDisplayed() && 
                   objektartDropdown.isDisplayed() && 
                   weiterButton.isDisplayed();
        } catch (Exception e) {
            logger.error("Not all required fields are displayed: {}", e.getMessage());
            return false;
        }

    }
} 