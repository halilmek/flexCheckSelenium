package com.test.pages;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

/**
 * Page class representing the "Angaben zum Objekt" (Property Details) page
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

    public AngabenZumObjektPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }



    // Methods for interacting with web elements
    public void navigatingToHomePage() {
        driver.get(ConfigurationReader.getProperty("url"));
        logger.info("Navigated to FlexCheck calculator page");
    }
    
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
     * Selects the Verwendungszweck (Usage Purpose)
     * @param purpose the purpose to select
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
     * Enters the PLZ (Postal Code)
     * @param plz the postal code to enter
     */
    public void enterPlz(String plz) {
        waitForElementToBeClickable(plzInput);
        highlightElement(plzInput);
        plzInput.clear();
        plzInput.sendKeys(plz);
        logger.info("Entered postal code: {}", plz);
    }

    /**
     * Enters the Kaufpreis (Purchase Price)
     * @param price the purchase price to enter
     */
    public void enterKaufpreis(String price) {
        waitForElementToBeClickable(kaufpreisInput);
        highlightElement(kaufpreisInput);
        kaufpreisInput.clear();
        kaufpreisInput.sendKeys(price);
        logger.info("Entered purchase price: {}", price);
    }

    /**
     * Selects the Objektart (Property Type)
     * @param type the property type to select
     */
    public void selectObjektart(String type) {
        waitForElementToBeClickable(objektartDropdown);
        highlightElement(objektartDropdown);
        Select select = new Select(objektartDropdown);
        select.selectByVisibleText(type);
        logger.info("Selected property type: {}", type);
    }

    /**
     * Clicks the Weiter (Continue) button
          * @throws InterruptedException 
          */
         public void clickWeiter() throws InterruptedException {
        waitForElementToBeClickable(weiterButton);
        highlightElement(weiterButton);
        weiterButton.click();
        logger.info("Clicked Weiter button on object details page");
        Thread.sleep(2000);
    }

    // Getter methods for verification purposes
    public String getSelectedVerwendungszweck() {
        Select select = new Select(verwendungszweckDropdown);
        logger.info("Selected usage purpose: " + select.getFirstSelectedOption().getText());
        return select.getFirstSelectedOption().getText();
    }

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
     * Gets the currently selected property type
     * @return String containing the selected property type
     */
    public String getSelectedObjektart() {
        WebElement element = Driver.getDriver().findElement(By.id("objektart"));
        Select select = new Select(element);
        String actualPropertyType = select.getFirstSelectedOption().getText();
        logger.info("Selected property type: " + actualPropertyType);
        return actualPropertyType;
    }

    /**
     * Gets the current value of the postal code field
     * @return String containing the postal code
     */
    public String getPlz() {
        WebElement element = Driver.getDriver().findElement(By.id("plz"));
        logger.info("Postal code: " + element.getAttribute("value"));
        return element.getAttribute("value");
    }

    /**
     * Gets the current value of the purchase price field
     * @return String containing the purchase price
     */
    public String   getKaufpreis() {
        WebElement element = Driver.getDriver().findElement(By.id("kaufpreis"));
        logger.info("Purchase price: " + element.getAttribute("value"));
        return element.getAttribute("value");
    }






} 