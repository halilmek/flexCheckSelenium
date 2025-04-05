package com.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.utilities.BrowserUtil;
import com.test.utilities.Driver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

/**
 * Page class representing the "Angaben zum Finanzierungswunsch" (Financing Details) page
 */
public class AngabenZumFinanzierungswunschPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AngabenZumFinanzierungswunschPage.class);

    public AngabenZumFinanzierungswunschPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    BrowserUtil browserUtil = new BrowserUtil();

    @FindBy(id = "darlehenswunsch")
    private WebElement darlehenswunschInput;

    @FindBy(id = "tilgung")
    private WebElement tilgungInput;

    @FindBy(id = "berechnennach")
    private WebElement rueckzahlungsArtDropdown;

    @FindBy(id = "auszahlungsdatum")
    private WebElement auszahlungsdatumInput;

    @FindBy(xpath = "//*[@id='rate']") 
    private WebElement monthlyPaymentInput;

    @FindBy(xpath = "//h3[span[contains(text(), 'Angaben zum Finanzierungswunsch')]]/ancestor::section//button") 
    private WebElement weiterButtonInAngabenZumFinanzierungswunschPage;


    @FindBy(xpath = "//*[@id='laufzeitjahre']")
    private WebElement totalTermYearsInput;
    
    @FindBy(xpath = "//*[@id='laufzeitmonate']")
    private WebElement totalTermMonthsInput;


    /**
     * Enters the loan amount into the loan amount input field
     * @param amount The amount to enter into the loan amount input field
     */
    public void enterDarlehenshoehe(String amount) {
        waitForElementToBeClickable(darlehenswunschInput);
        highlightElement(darlehenswunschInput);
        //takeScreenshot("before_entering_loan_amount");
        darlehenswunschInput.clear();
        darlehenswunschInput.sendKeys(amount);
        //takeScreenshot("after_entering_loan_amount");
        logger.info("Entered loan amount: {}", amount);
    }

    /**
     * Enters the repayment percentage into the repayment percentage input field
     * @param percentage The percentage to enter into the repayment percentage input field
     */
    public void enterTilgung(String percentage) throws InterruptedException {
        new Actions(driver).click(tilgungInput)
            .keyDown(Keys.COMMAND)
            .sendKeys("a")
            .keyUp(Keys.COMMAND)
            .sendKeys(percentage)
            .perform();
        //takeScreenshot("after_entering_repayment");
        logger.info("Entered repayment percentage: {}", percentage);
    }

    /**
     * Selects the repayment type from the repayment type dropdown
     * @param type The type to select from the repayment type dropdown
     */
    public void selectRueckzahlungsArt(String type) {
        wait.until(ExpectedConditions.elementToBeClickable(rueckzahlungsArtDropdown));
        highlightElement(rueckzahlungsArtDropdown);
        //takeScreenshot("rueckzahlungsArt-before");
        Select select = new Select(rueckzahlungsArtDropdown);
        select.selectByVisibleText(type);
        //takeScreenshot("rueckzahlungsArt-after");
        logger.info("Successfully selected repayment type: " + type);
    }

    /**
     * Enters the first payout date into the first payout date input field
     * @throws InterruptedException if an error occurs while entering the first payout date
     */
    public void enterAuszahlungsdatum() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(auszahlungsdatumInput));
        highlightElement(auszahlungsdatumInput);
        //takeScreenshot("auszahlungsdatum-before");
        auszahlungsdatumInput.clear();

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = tomorrow.format(formatter);
        logger.info("Entered first payout date: {}", formattedDate);

        auszahlungsdatumInput.sendKeys(formattedDate,Keys.ENTER);

        Thread.sleep(2000);    
        //takeScreenshot("auszahlungsdatum-after");
        logger.info("Successfully entered payout date: " + formattedDate);
    }

    /**
     * Clicks the "Weiter" button in the Angaben zum Finanzierungswunsch page
     * @throws InterruptedException if an error occurs while clicking the button
     */
    public void clickWeiterInAngabenZumFinanzierungswunschPage() throws InterruptedException {
        logger.info("Attempting to click Weiter button in Angaben zum Finanzierungswunsch page");
        try {
            // Wait for page load
            Thread.sleep(2000);
            
            // Try to find the button using the section header approach
            WebElement weiterButton = driver.findElement(By.xpath("//h3[contains(., 'Angaben zum Finanzierungswunsch')]/following::button[contains(@class, 'btn--nav-next')]"));
            
            // Wait for button to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(weiterButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", weiterButton);
            Thread.sleep(1000);
            
            // Highlight for debugging
            highlightElement(weiterButton);
            //takeScreenshot("before-clicking-weiter");
            
            // Try different click methods
            try {
                weiterButton.click();
                logger.info("Weiter button clicked in Angaben zum Finanzierungswunsch page and in try block");
            } catch (Exception e) {
                try {
                    new Actions(driver).moveToElement(weiterButton).click().perform();
                    logger.info("Weiter button clicked in Angaben zum Finanzierungswunsch page and in try block in catch block");
                } catch (Exception e2) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", weiterButton);
                    logger.info("Weiter button clicked in Angaben zum Finanzierungswunsch page and in catch block in catch block");
                }
            }
            
            logger.info("Successfully clicked Weiter button");
            Thread.sleep(1000);
            
        } catch (Exception e) {
            logger.error("Failed to click Weiter button: {}", e.getMessage());
            //takeScreenshot("weiter-button-error");
            throw e;
        }
    }


    /**
     * Clicks the back button in the Angaben zum Finanzierungswunsch page
     */
    public void clickBackButtonInAngabenZumFinanzierungswunschPage() {

        logger.info("Clicking back button to return to Objekt page");
        try {
            // Find and click the back button
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn--nav-previous, button[class*='nav-previous'], button[class*='zurück']")
            ));
            backButton.click();
            
            // Wait for Objekt page to load
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='postalCode']")));
            logger.info("Successfully navigated back to Objekt page");
        } catch (Exception e) {
            logger.error("Failed to navigate back to Objekt page: {}", e.getMessage());
            throw e;
        }
    }   


    /**
     * Enters the monthly payment amount into the monthly payment input field
     * @param amount The amount to enter into the monthly payment input field
     */
    public void enterMonthlyPayment(String amount) {

        try {
            logger.info("Entering monthly payment amount: {}", amount);
            wait.until(ExpectedConditions.elementToBeClickable(monthlyPaymentInput));
            highlightElement(monthlyPaymentInput);

            //takeScreenshot("before-entering-monthly-payment");

            monthlyPaymentInput.clear();
            monthlyPaymentInput.sendKeys(amount);

            //takeScreenshot("after-entering-monthly-payment");

            logger.info("Successfully entered monthly payment");
        } catch (Exception e) {
            logger.error("Failed to enter monthly payment: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Enters the total term years into the total term years input field
     * @param years The years to enter into the total term years input field
     */
    public void enterTotalTermYears(String years) {

        try {
            
            wait.until(ExpectedConditions.elementToBeClickable(totalTermYearsInput));
            logger.info("Entered total term years: {}", years);
            highlightElement(totalTermYearsInput);

            //takeScreenshot("before-entering-term-years");

            totalTermYearsInput.clear();
            totalTermYearsInput.sendKeys(years);

            //takeScreenshot("after-entering-term-years");
            logger.info("Successfully entered total term in years");

        } catch (Exception e) {
            logger.error("Failed to enter total term years: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Enters the total term months into the total term months input field
     * @param months The months to enter into the total term months input field
     */ 
    public void enterTotalTermMonths(String months) {

        try {
            
            logger.info("Entered total term months: {}", months);
            wait.until(ExpectedConditions.elementToBeClickable(totalTermMonthsInput));
            highlightElement(totalTermMonthsInput);
            //takeScreenshot("before-entering-term-months");
    
            totalTermMonthsInput.clear();
            totalTermMonthsInput.sendKeys(months);
            //takeScreenshot("after-entering-term-months");
    
            logger.info("Successfully entered total term in months");
        } catch (Exception e) {
            logger.error("Failed to enter total term months: {}", e.getMessage());
            throw e;
        }
        
    }



    /**
     * Gets the current value of the loan amount field
     * @return String containing the loan amount
     */
    public String getDarlehensbetrag(String randomLoanAmount) {

        try {
            
            logger.info("Verifying all values persistence in Finanzierung page");


            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            // Input'un value property'sini almak için (en olası durum):
            WebElement darlehensWunschInput = Driver.getDriver().findElement(By.id("darlehenswunsch"));
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", darlehensWunschInput);
            System.out.println("Value from property: " + valueFromProperty);
            logger.info("Value from property: " + valueFromProperty);
            logger.info("randomLoanAmount: " + randomLoanAmount);
            String actualAmount = valueFromProperty.replace(".", "");
            return actualAmount;
        } catch (Exception e) {
            logger.error("Failed to verify values for loan amount in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the current value of the repayment percentage field
     * @return String containing the repayment percentage
     */
    public String getTilgung(String randomRepaymentPercentage) {
        
        try {
            
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            // Input'un value property'sini almak için (en olası durum):
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", tilgungInput);
            System.out.println("Value from property: " + valueFromProperty);
            logger.info("Value from property: " + valueFromProperty);
            logger.info("randomRepaymentPercentage: " + randomRepaymentPercentage);
            String actualAmount = valueFromProperty.replace(",00", "");
            return actualAmount;
        } catch (Exception e) {
            logger.error("Failed to verify values for repayment percentage in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the expected first payout date (tomorrow's date)
     * @return String containing the expected date in dd.MM.yyyy format
     */
    public String getExpectedAuszahlungsdatum() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String expectedDate = tomorrow.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        logger.info("Expected payout date: " + expectedDate);
        return expectedDate;
    }

    public String getRepaymentType() {

        try {
            Select select = new Select(rueckzahlungsArtDropdown);
            String textOfRepaymentType = select.getFirstSelectedOption().getText();
            logger.info("Repayment type: " + textOfRepaymentType);
            return textOfRepaymentType;
        } catch (Exception e) {
            logger.error("Failed to verify repayment type in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }
    /**
     * Gets the current value of the first payout date field
     * @return String containing the payout date
     */
    public String getAuszahlungsdatum() {

        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", auszahlungsdatumInput);
            logger.info("Payout date: " + valueFromProperty);
            return valueFromProperty;
        } catch (Exception e) {
            logger.error("Failed to verify payout date in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the current value of the monthly payment field
     * @return String containing the monthly payment
     */
    public String getMonatlicheRate() {
        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", monthlyPaymentInput);
            logger.info("Monthly payment: " + valueFromProperty);
            String actualAmount = valueFromProperty.replace(".", "");
            logger.info("Actual amount: " + actualAmount);
            return actualAmount;
        } catch (Exception e) {
            logger.error("Failed to verify monthly payment in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the current value of the total term years field
     * @return String containing the total term years
     */
    public String getGesamtlaufzeitJahre() {
        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", totalTermYearsInput);
            logger.info("Total term years: " + valueFromProperty);
            return valueFromProperty;
        } catch (Exception e) {
            logger.error("Failed to verify total term years in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the current value of the total term months field
     * @return String containing the total term months
     */
    public String getGesamtlaufzeitMonate() {
        try {
            String valueFromProperty = (String) js.executeScript("return arguments[0].value;", totalTermMonthsInput);
            logger.info("Total term months: " + valueFromProperty);
            return valueFromProperty;
        } catch (Exception e) {
            logger.error("Failed to verify total term months in Finanzierungswunsch page: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Refreshes the current page
     */
    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    /**
     * Gets the error message for the loan amount and repayment percentage
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForLoanAmountAndRepaymentPercentage() {

        try {

            String expectedErrorMessageDarlehenswunsch = "Der Mindestwert beträgt 25.000,00.";
            String expectedErrorMessageTilgung = "Der Mindestwert beträgt 1,00.";
    
            WebElement errorMessageElement = driver.findElement(By.xpath("//*[@id='finanzierung-form']/section/div[2]/div[1]/div[1]/div/p"));
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
    
            String errorMessageDarlehenswunsch = errorMessageElement.getText();
            logger.info("Error message for loan amount: " + errorMessageDarlehenswunsch);
    
            WebElement errorMessageElement2 = driver.findElement(By.xpath("//*[@id='finanzierung-form']/section/div[2]/div[1]/div[2]/div/p"));
            WebDriverWait wait2 = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait2.until(ExpectedConditions.visibilityOf(errorMessageElement2));
    
            String errorMessageTilgung = errorMessageElement2.getText();
            logger.info("Error message for repayment percentage: " + errorMessageTilgung);
            boolean isErrorMessageDarlehenswunschCorrect = errorMessageDarlehenswunsch.contains(expectedErrorMessageDarlehenswunsch);
            boolean isErrorMessageTilgungCorrect = errorMessageTilgung.contains(expectedErrorMessageTilgung);

            return isErrorMessageDarlehenswunschCorrect && isErrorMessageTilgungCorrect;
        } catch (Exception e) {
            return false;
        } 
    }

    /**
     * Gets the error message for the loan amount greater than 1.000.000
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForLoanAmountGreaterThan1000000() {

        try {

            String expectedErrorMessageDarlehenswunsch = "Der Höchstwert beträgt 1.000.000,00.";
    
            WebElement errorMessageElement = Driver.getDriver().findElement(By.xpath("//*[@id='finanzierung-form']/section/div[2]/div[1]/div[1]/div/p"));
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
    
            String errorMessageDarlehenswunsch    = errorMessageElement.getText();
            logger.info("Error message for loan amount: " + errorMessageDarlehenswunsch);
    
            boolean isErrorMessageDarlehenswunschCorrect = errorMessageDarlehenswunsch.contains(expectedErrorMessageDarlehenswunsch);

            return isErrorMessageDarlehenswunschCorrect;
        } catch (Exception e) {
            return false;
        } 
    }

    /**
     * Gets the error message for the loan amount greater than the purchase price
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForLoanAmountGreaterThanPurchasePrice() {

        try {

            String expectedErrorMessageDarlehenswunsch = "Vielen Dank für Ihr Interesse an unseren Baufinanzierungsprodukten. Aufgrund Ihrer Angaben können wir Ihnen leider keine Ergebnisse anzeigen. Bitte überprüfen Sie Ihre Angaben oder wenden Sie sich direkt an unser Beraterteam 0800/2385-544.";
    
            WebElement errorMessageElement = Driver.getDriver().findElement(By.xpath("//*[@id='kategorie4']/div/div/p"));
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
    
            String errorMessageDarlehenswunsch = errorMessageElement.getText();
            logger.info("Error message for loan amount: " + errorMessageDarlehenswunsch);
    
            boolean isErrorMessageDarlehenswunschCorrect = errorMessageDarlehenswunsch.contains(expectedErrorMessageDarlehenswunsch);

            return isErrorMessageDarlehenswunschCorrect;
        } catch (Exception e) {
            return false;
        } 
    }

    /**
     * Gets the error message for the monthly payment
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForMonthlyPayment() {

        try {

            String expectedErrorMessageMonthlyPayment = "nope!";
    //No element located!
            WebElement errorMessageElement = Driver.getDriver().findElement(By.xpath("//*[@id='kategorie4']/div/div/p"));
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
    
            String errorMessageMonthlyPayment = errorMessageElement.getText();
            logger.info("Error message for monthly payment: " + errorMessageMonthlyPayment);
    
            boolean isErrorMessageMonthlyPaymentCorrect = errorMessageMonthlyPayment.contains(expectedErrorMessageMonthlyPayment);

            return isErrorMessageMonthlyPaymentCorrect;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the error message for the total term in years and total term in months
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForTotalTermInYearsAndTotalTermInMonths() {


        try {

            String expectedErrorMessage = "Bitte geben Sie eine ganze Zahl ein.";

            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            List<WebElement> pElements = Driver.getDriver().findElements(By.tagName("p"));

            List<String> texts = new ArrayList<>();
            for (WebElement pElement : pElements) {
                String text = (String) js.executeScript("return arguments[0].innerText;", pElement);
                if (!text.trim().isEmpty()) { // Boş olmayanları al
                    texts.add(text);
                    logger.info("Text: " + text);
                }
            }
            

            return texts.contains(expectedErrorMessage);

        } catch (Exception e) {
            logger.error("Error message verification failed for total term in years and total term in months: {}", e.getMessage());
            throw e;
        }
 
    }

    /**
     * Gets the error message for the total term in years and total term in months
     * @return boolean value indicating whether the error message is correct
     */
    public boolean getErrorMessageForTotalTermInYearsAndTotalTermInMonths2() {


        try {

            String expectedErrorMessage = "Bitte geben Sie eine ganze Zahl ein.";
            List<WebElement> targetElements = Driver.getDriver().findElements(By.xpath("//p[contains(text(), '" + expectedErrorMessage + "')]"));
            
            logger.info("Hata mesajını içeren element XPath ile bulundu: " + targetElements);

            WebElement errorMessageElement = targetElements.get(0);
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
            String errorMessageTotalTermInYears = errorMessageElement.getText();
            logger.info("Error message for total term in years: " + errorMessageTotalTermInYears);
    
            WebElement errorMessageElement2 = targetElements.get(1);
            WebDriverWait wait2 = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait2.until(ExpectedConditions.visibilityOf(errorMessageElement2));
            String errorMessageTotalTermInMonths = errorMessageElement2.getText();
            logger.info("Error message for total term in months: " + errorMessageTotalTermInMonths);

            return errorMessageTotalTermInYears.contains(expectedErrorMessage) && errorMessageTotalTermInMonths.contains(expectedErrorMessage);

        } catch (Exception e) {
            logger.error("Error message verification failed for total term in years and total term in months: {}", e.getMessage());
            throw e;
        }
 
    }

    /**
     * Checks if all required fields are displayed in the Angaben zum Finanzierungswunsch page
     * @return boolean value indicating whether all required fields are displayed
     */
    public boolean isDisplayedAllRequiredFields() {

        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(darlehenswunschInput));
            wait.until(ExpectedConditions.visibilityOf(tilgungInput));
            wait.until(ExpectedConditions.visibilityOf(rueckzahlungsArtDropdown));
            wait.until(ExpectedConditions.visibilityOf(auszahlungsdatumInput));

            

            boolean isDarlehenswunschInputDisplayed = darlehenswunschInput.isDisplayed();
            logger.info("Darlehenswunsch input is displayed: " + isDarlehenswunschInputDisplayed);
            boolean isTilgungInputDisplayed = tilgungInput.isDisplayed();
            logger.info("Tilgung input is displayed: " + isTilgungInputDisplayed);
            boolean isRueckzahlungsArtDropdownDisplayed = rueckzahlungsArtDropdown.isDisplayed();
            logger.info("RueckzahlungsArt dropdown is displayed: " + isRueckzahlungsArtDropdownDisplayed);
            boolean isAuszahlungsdatumInputDisplayed = auszahlungsdatumInput.isDisplayed();
            logger.info("Auszahlungsdatum input is displayed: " + isAuszahlungsdatumInputDisplayed);
            
            return isDarlehenswunschInputDisplayed && isTilgungInputDisplayed && isRueckzahlungsArtDropdownDisplayed && isAuszahlungsdatumInputDisplayed;
        } catch (Exception e) {
            return false;
        }
    }



} 