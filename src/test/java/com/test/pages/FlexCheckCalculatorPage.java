package com.test.pages;

import com.test.utilities.BrowserUtil;
import com.test.utilities.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object für die FLEXCHECK Rechner-Seite.
 * Behandelt alle Eingabefelder und Aktionen auf der Rechner-Seite.
 */
public class FlexCheckCalculatorPage extends BasePage {

    /**
     * Verwendungszweck
     */
    @FindBy(id = "verwendungszweck")
    private WebElement usagePurposeSelect;

    /**
     * Postleitzahl
     */
    @FindBy(id = "plz")
    private WebElement postalCodeInput;

    /**
     * Kaufpreis
     */
    @FindBy(id = "kaufpreis")
    private WebElement purchasePriceInput;

    /**
     * Objektart
     */
    @FindBy(id = "objektart")
    private WebElement propertyTypeSelect;

    /**
     * Darlehensbetrag
     */
    @FindBy(id = "darlehensbetrag")
    private WebElement loanAmountInput;

    /**
     * Tilgungsprozentsatz
     */
    @FindBy(id = "tilgung")
    private WebElement repaymentPercentageInput;

    /**
     * Tilgungsart
     */
    @FindBy(id = "tilgungsart")
    private WebElement repaymentTypeSelect;

    /**
     * Auszahlungstermin
     */
    @FindBy(id = "auszahlungstermin")
    private WebElement payoutDateInput;

    /**
     * Berechnen Button
     */
    @FindBy(id = "berechnen")
    private WebElement calculateButton;

    /**
     * Ergebniscontainer
     */
    @FindBy(css = ".result-container")
    private WebElement resultContainer;

    /**
     * Fehlermeldung
     */
    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    /**
     * Konstruktor für die FlexCheckCalculatorPage
     */
    public FlexCheckCalculatorPage() {
        super();
    }

    /**
     * Navigiert zur Rechner-Seite
     */
    public void navigateToCalculator() {
        driver.get(ConfigurationReader.getProperty("url"));
        BrowserUtil.waitFor(2);
    }

    /**
     * Wählt den Verwendungszweck aus
     * @param purpose Der auszuwählende Verwendungszweck
     */
    public void selectUsagePurpose(String purpose) {
        BrowserUtil.waitForVisibility(usagePurposeSelect, 10);
        Select select = new Select(usagePurposeSelect);
        select.selectByVisibleText(purpose);
    }

    /**
     * Gibt die Postleitzahl ein
     * @param postalCode Die einzugebende Postleitzahl
     */
    public void enterPostalCode(String postalCode) {
        BrowserUtil.waitForVisibility(postalCodeInput, 10);
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }

    /**
     * Gibt den Kaufpreis ein
     * @param price Der einzugebende Kaufpreis
     */
    public void enterPurchasePrice(String price) {
        BrowserUtil.waitForVisibility(purchasePriceInput, 10);
        purchasePriceInput.clear();
        purchasePriceInput.sendKeys(price);
    }

    /**
     * Holt den aktuellen Kaufpreis
     * @return Der aktuelle Kaufpreis als String
     */
    public String getPurchasePrice() {
        BrowserUtil.waitForVisibility(purchasePriceInput, 10);
        return purchasePriceInput.getAttribute("value");
    }

    /**
     * Wählt die Objektart aus
     * @param propertyType Die auszuwählende Objektart
     */
    public void selectPropertyType(String propertyType) {
        BrowserUtil.waitForVisibility(propertyTypeSelect, 10);
        Select select = new Select(propertyTypeSelect);
        select.selectByVisibleText(propertyType);
    }

    /**
     * Gibt den Darlehensbetrag ein
     * @param amount Der einzugebende Darlehensbetrag
     */
    public void enterLoanAmount(String amount) {
        BrowserUtil.waitForVisibility(loanAmountInput, 10);
        loanAmountInput.clear();
        loanAmountInput.sendKeys(amount);
    }

    /**
     * Gibt den Tilgungsprozentsatz ein
     * @param percentage Der einzugebende Prozentsatz
     */
    public void enterRepaymentPercentage(String percentage) {
        BrowserUtil.waitForVisibility(repaymentPercentageInput, 10);
        repaymentPercentageInput.clear();
        repaymentPercentageInput.sendKeys(percentage);
    }

    /**
     * Gibt das Auszahlungsdatum ein
     */
    public void enterPayoutDate() {
        BrowserUtil.waitForVisibility(payoutDateInput, 10);
        String date = java.time.LocalDate.now().plusMonths(1).format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        payoutDateInput.clear();
        payoutDateInput.sendKeys(date);
    }

    /**
     * Klickt auf den Berechnen-Button
     */
    public void clickCalculate() {
        BrowserUtil.waitForClickability(calculateButton, 10);
        calculateButton.click();
    }

    /**
     * Prüft ob die Ergebnisse angezeigt werden
     * @return true wenn die Ergebnisse sichtbar sind
     */
    public boolean isResultDisplayed() {
        return BrowserUtil.waitForVisibility(resultContainer, 10).isDisplayed();
    }

    public boolean isResultValueDisplayed(String resultType) {
        try {
            WebElement resultElement = driver.findElement(org.openqa.selenium.By.xpath("//div[contains(text(),'" + resultType + "')]"));
            return resultElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Prüft ob Fehlermeldungen angezeigt werden
     * @return true wenn Fehlermeldungen sichtbar sind
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return BrowserUtil.waitForVisibility(errorMessage, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Leert alle Eingabefelder
     */
    public void clearAllFields() {
        postalCodeInput.clear();
        purchasePriceInput.clear();
        loanAmountInput.clear();
        repaymentPercentageInput.clear();
        payoutDateInput.clear();
    }

    /**
     * Navigiert zurück
     */
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigiert vorwärts
     */
    public void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Wählt die Art der Rückzahlung aus
     * @param type Die Art der Rückzahlung
     */
    public void selectRepaymentType(String type) {
        BrowserUtil.waitForVisibility(repaymentTypeSelect, 10);
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(repaymentTypeSelect);
        select.selectByVisibleText(type);
    }
} 