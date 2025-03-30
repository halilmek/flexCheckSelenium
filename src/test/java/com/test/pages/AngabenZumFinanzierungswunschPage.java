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



    public void enterDarlehenshoehe(String amount) {
        waitForElementToBeClickable(darlehenswunschInput);
        highlightElement(darlehenswunschInput);
        //takeScreenshot("before_entering_loan_amount");
        darlehenswunschInput.clear();
        darlehenswunschInput.sendKeys(amount);
        //takeScreenshot("after_entering_loan_amount");
        logger.info("Entered loan amount: {}", amount);
    }

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

    public void selectRueckzahlungsArt(String type) {
        wait.until(ExpectedConditions.elementToBeClickable(rueckzahlungsArtDropdown));
        highlightElement(rueckzahlungsArtDropdown);
        //takeScreenshot("rueckzahlungsArt-before");
        Select select = new Select(rueckzahlungsArtDropdown);
        select.selectByVisibleText(type);
        //takeScreenshot("rueckzahlungsArt-after");
        logger.info("Successfully selected repayment type: " + type);
    }

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

    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }



} 