package com.test.pages;

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
        return select.getFirstSelectedOption().getText();
    }

    public String getEnteredPLZ() {
        return plzInput.getAttribute("value");
    }

    public String getEnteredKaufpreis() {
        return kaufpreisInput.getAttribute("value");
    }

    public String getSelectedObjektart() {
        Select select = new Select(objektartDropdown);
        return select.getFirstSelectedOption().getText();
    }






} 