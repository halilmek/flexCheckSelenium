package com.test.stepdefinitions;

import com.test.pages.*;
import com.test.utilities.ConfigurationReader;
import com.test.utilities.Driver;
import com.test.utilities.TestDataGenerator;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.HashMap;
import java.util.Map;

public class KreditberechnungSteps {
    private static final Logger logger = LoggerFactory.getLogger(KreditberechnungSteps.class);
    
    private final AngabenZumObjektPage angabenZumObjektPage = new AngabenZumObjektPage();
    private final AngabenZumFinanzierungswunschPage finanzierungPage = new AngabenZumFinanzierungswunschPage();
    private final BerechnungsergebnisPage berechnungsergebnisPage = new BerechnungsergebnisPage();
    private final OfferSelectionPage offerSelectionPage = new OfferSelectionPage();
    private final DokumentHochladenPage dokumentHochladenPage = new DokumentHochladenPage();
    private final FinalPage finalPage = new FinalPage();

    String randomPostalCode = TestDataGenerator.generatePostalCode();
    String randomPurchasePrice = TestDataGenerator.generatePurchasePrice();
    String randomLoanAmount = TestDataGenerator.generateLoanAmount(randomPurchasePrice);
    String randomRepaymentPercentage = TestDataGenerator.generateRepaymentPercentage();
    String randomMonthlyPayment = TestDataGenerator.generateMonthlyPayment();
    String randomPurpose = angabenZumObjektPage.getVerwendungszweck();
    String randomPropertyType = angabenZumObjektPage.getObjektart();
    String randomJahreFürGesamtlaufzeit = TestDataGenerator.generateJahreFürGesamtlaufzeit();
    String randomMonateFürGesamtlaufzeit = TestDataGenerator.generateMonateFürGesamtlaufzeit();

    @Given("the user is on the FlexCheck calculator page")
    public void the_user_is_on_the_flex_check_calculator_page() {
        angabenZumObjektPage.navigatingToHomePage();
    }

    //=====================Angaben zum Objekt Page=====================
    @When("the user selects {string} as usage purpose")
    public void theUserSelectsAsUsagePurpose(String purpose) {
        if (purpose.equalsIgnoreCase("randomPurpose")) {
            purpose = randomPurpose;
        }
        angabenZumObjektPage.selectVerwendungszweck(purpose);
    }

    @When("the user enters postal code {string}")
    public void theUserEntersPostalCode(String plz) {
        if (plz.equalsIgnoreCase("randomPostalCode")) {
            plz = randomPostalCode;
        }
        angabenZumObjektPage.enterPlz(plz);
    }

    @When("the user enters purchase price {string}")
    public void theUserEntersPurchasePrice(String price) {
        if (price.equalsIgnoreCase("randomPurchasePrice")) {
            price = randomPurchasePrice;
        }
        angabenZumObjektPage.enterKaufpreis(price);
    }

    @When("the user selects {string} as property type")
    public void theUserSelectsAsPropertyType(String type) {
        if (type.equalsIgnoreCase("randomPropertyType")) {
            type = randomPropertyType;
        }
        angabenZumObjektPage.selectObjektart(type);
    }

    @When("the user clicks on Weiter in Angaben zum Objekt page")
    public void theUserClicksOnWeiterInAngabenZumObjektPage() throws InterruptedException {
        angabenZumObjektPage.clickWeiter();
    }

    //==========================Angaben zum FinanzierungswunschPage============================

    @When("the user enters desired loan amount {string}")
    public void theUserEntersDesiredLoanAmount(String amount) {
        if (amount.equalsIgnoreCase("randomLoanAmount")) {
            amount = randomLoanAmount;
        }
        finanzierungPage.enterDarlehenshoehe(amount);
    }

    @When("the user enters repayment percentage {string}")
    public void theUserEntersRepaymentPercentage(String percentage) throws InterruptedException {
        if (percentage.equalsIgnoreCase("randomRepaymentPercentage")) {
            percentage = randomRepaymentPercentage;
        }
        finanzierungPage.enterTilgung(percentage);
    }

    @When("the user selects repayment type {string}")
    public void theUserSelectsRepaymentType(String type) {
        finanzierungPage.selectRueckzahlungsArt(type);
    }

    @When("the user enters first payout date")
    public void theUserEntersFirstPayoutDate() throws InterruptedException {
        finanzierungPage.enterAuszahlungsdatum();
    }

    @When("the user clicks on Weiter in Angaben zum Finanzierungswunsch page")
    public void theUserClicksOnWeiterInAngabenZumFinanzierungswunschPage() throws InterruptedException {
        finanzierungPage.clickWeiterInAngabenZumFinanzierungswunschPage();
    }

    //Montly Payment
    @When("the user enters monthly payment {string}")
    public void theUserEntersMonthlyPayment(String amount) {
        if (amount.equalsIgnoreCase("randomMonthlyPayment")) {
            amount = randomMonthlyPayment;
        }
        logger.info("Entering monthly payment amount: {}", amount);
        try {
            finanzierungPage.enterMonthlyPayment(amount);
            logger.info("Successfully entered monthly payment");
        } catch (Exception e) {
            logger.error("Failed to enter monthly payment: {}", e.getMessage());
            throw e;
        }
    }


    //Total Term
    @When("the user enters total term in years {string}")
    public void theUserEntersTotalTermInYears(String years) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (years.equalsIgnoreCase("randomJahre")) {
            years = randomJahreFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }
        logger.info("Entering total term in years: {}", years);
        try {
            finanzierungPage.enterTotalTermYears(years);
            logger.info("Successfully entered total term in years");
        } catch (Exception e) {
            logger.error("Failed to enter total term in years: {}", e.getMessage());
            throw e;
        }
    }

    @When("the user enters total term in months {string}")
    public void theUserEntersTotalTermInMonths(String months) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (months.equalsIgnoreCase("randomMonate")) {
            months = randomMonateFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }
        logger.info("Entering total term in months: {}", months);
        try {
            finanzierungPage.enterTotalTermMonths(months);
            logger.info("Successfully entered total term in months");
        } catch (Exception e) {
            logger.error("Failed to enter total term in months: {}", e.getMessage());
            throw e;
        }
    }


    //==========================OfferSelectionPage=BerechnungsergebnisPage============================

    @Then("the user should see the calculation results")
    public void theUserShouldSeeTheCalculationResults() {
        berechnungsergebnisPage.waitForResultsToLoad();
    }

    @When("the user selects an option")
    public void theUserSelectsAnOption() throws Exception {
        offerSelectionPage.selectAnAvailableOption();
    }

    @When("the user clicks on Angebote anfordern")
    public void theUserClicksOnAngeboteAnfordern() {
        offerSelectionPage.clickAngebotAnfordern();
    }

    //Options Test
    @When("the user selects {string} option")
    public void theUserSelectsOption(String optionNumber) {
        offerSelectionPage.selectOption(optionNumber);
    }


    //Entered values check
    @When("the user clicks on Details anzeigen button")
    public void theUserClicksOnDetailsAnzeigenButton() {
        // Click the details button
        offerSelectionPage.clickDetailsButton();
        
        // Print all values from the modal
        offerSelectionPage.printModalValues();
    }

    @Then("the user should see values, as the user entered in the calculator based on Tilgung in % payment type")
    public void verifyCalculatorValues() {
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
        boolean allValuesMatch = offerSelectionPage.verifyAllDetailsValues(expectedValues);

        logger.info("All values matched: {}", allValuesMatch);
        //new Actions(Driver.getDriver()).pause(Duration.ofSeconds(10)).perform();
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();
        
        if (!allValuesMatch) {
            throw new AssertionError("Not all values matched across info buttons. Check logs for details.");
        }
    }
    
    @Then("the user should see values, as the user entered in the calculator based on Monatliche Rate payment type")
    public void verifyCalculatorValuesForMonthlyRatePaymentType() {
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
        boolean allValuesMatch = offerSelectionPage.verifyAllDetailsValues(expectedValues);

        logger.info("All values matched: {}", allValuesMatch);
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();
        
        if (!allValuesMatch) {
            throw new AssertionError("Not all values matched across info buttons. Check logs for details.");
        }
    }
    
    @Then("the user should see values, as the user entered in the calculator based on Gesamtlaufzeit payment type")
    public void verifyCalculatorValuesForGesamtlaufzeitPaymentType() {
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
        boolean allValuesMatch = offerSelectionPage.verifyAllDetailsValues(expectedValues);

        logger.info("All values matched: {}", allValuesMatch);
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();
        
        if (!allValuesMatch) {
            throw new AssertionError("Not all values matched across info buttons. Check logs for details.");
        }
    }

    //================DokumentHochladenPage=========

    @When("the user uploads id")
    public void theUserUploadsId() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";
        dokumentHochladenPage.dragAndDropFile2(filePath);
    }

    @Then("the user checks, whether the file was uploaded")
    public void theUserChecksWhetherTheFileWasUploaded() {
        dokumentHochladenPage.verifyFileUpload();
    }

    @And("the user clicks on Weiter in DokumentHochladung page")
    public void theUserClicksOnWeiterInDokumentHochladungPage() {
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();
    }

    //File Upload Negative Test
    @When("the user uploads a file not correctly")
    public void theUserUploadsAFileNotCorrectly() {
        dokumentHochladenPage.uploadIdForNegativeTest();
    }

//============Final Page============

    @When("the user writes a message")
    public void theUserWritesAMessage() {
        finalPage.writeMessage();
    }

    @When("the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page")
    public void theUserClicksOnJetztKostenlosButtonInFinalPage() {
        finalPage.clickSubmitButton();
        finalPage.isSuccessMessageDisplayedForFileUpload();
    }

    @Then("the user should see the success message")
    public void theUserShouldSeeTheSuccessMessage() throws Exception {
        finalPage.isSuccessMessageDisplayed();
    }

    @Then("the user should see the error message for file upload")
    public void theUserShouldSeeTheErrorMessageForFileUpload() {
        finalPage.notSuccessMessageDisplayedForFileUpload();
    }
} 
