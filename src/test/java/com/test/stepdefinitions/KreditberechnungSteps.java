package com.test.stepdefinitions;

import com.test.pages.*;
import com.test.utilities.ConfigurationReader;
import com.test.utilities.Driver;
import com.test.utilities.TestDataGenerator;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class KreditberechnungSteps {
    private static final Logger logger = LoggerFactory.getLogger(KreditberechnungSteps.class);
    
    private final AngabenZumObjektPage angabenZumObjektPage = new AngabenZumObjektPage();
    private final AngabenZumFinanzierungswunschPage finanzierungPage = new AngabenZumFinanzierungswunschPage();
    private final BerechnungsergebnisPage berechnungsergebnisPage = new BerechnungsergebnisPage();
    private final OfferSelectionPage offerSelectionPage = new OfferSelectionPage();
    private final DokumentHochladenPage dokumentHochladenPage = new DokumentHochladenPage();
    private final FinalPage finalPage = new FinalPage();

    TestDataGenerator testDataGenerator = new TestDataGenerator();
    String postalCode = testDataGenerator.generatePostalCode();
    String purchasePrice = testDataGenerator.generatePurchasePrice();
    String loanAmount = testDataGenerator.generateLoanAmount(purchasePrice);
    String repaymentPercentage = testDataGenerator.generateRepaymentPercentage();
    String monthlyPayment = testDataGenerator.generateMonthlyPayment();
    @Given("the user is on the FlexCheck calculator page")
    public void the_user_is_on_the_flex_check_calculator_page() {
        angabenZumObjektPage.navigatingToHomePage();
    }

    //=====================Angaben zum Objekt Page=====================
    @When("the user selects {string} as usage purpose")
    public void theUserSelectsAsUsagePurpose(String purpose) {
        angabenZumObjektPage.selectVerwendungszweck(purpose);
    }

    @When("the user enters postal code {string}")
    public void theUserEntersPostalCode(String plz) {
        plz = postalCode;
        angabenZumObjektPage.enterPlz(plz);
    }

    @When("the user enters purchase price {string}")
    public void theUserEntersPurchasePrice(String price) {
        price = purchasePrice;
        angabenZumObjektPage.enterKaufpreis(price);
    }

    @When("the user selects {string} as property type")
    public void theUserSelectsAsPropertyType(String type) {
        angabenZumObjektPage.selectObjektart(type);
    }

    @When("the user clicks on Weiter in Angaben zum Objekt page")
    public void theUserClicksOnWeiterInAngabenZumObjektPage() throws InterruptedException {
        angabenZumObjektPage.clickWeiter();
    }

    //==========================Angaben zum FinanzierungswunschPage============================

    @When("the user enters desired loan amount {string}")
    public void theUserEntersDesiredLoanAmount(String amount) {
        amount = loanAmount;
        finanzierungPage.enterDarlehenshoehe(amount);
    }

    @When("the user enters repayment percentage {string}")
    public void theUserEntersRepaymentPercentage(String percentage) throws InterruptedException {
        percentage = repaymentPercentage;
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
        amount = monthlyPayment;
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
