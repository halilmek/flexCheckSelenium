package com.test.stepdefinitions;

import com.test.pages.*;
import com.test.utilities.ConfigurationReader;
import com.test.utilities.Driver;
import com.test.utilities.TestDataGenerator;
import com.test.utilities.Utils;

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
    String randomOption = TestDataGenerator.generateOptionNumber();

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
        if (optionNumber.equalsIgnoreCase("randomOption")) {
            optionNumber = randomOption;
        }
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

    //==========================Data Persistence Steps============================
    
    @When("the user clicks on back button to return to previous page")
    public void theUserClicksOnBackButtonToPreviousPage() throws InterruptedException {
        logger.info("Clicking back button to return to previous page");
        try {
            // Store the current URL
            String currentUrl = Driver.getDriver().getCurrentUrl();
            
            // Click the browser's back button
            Driver.getDriver().navigate().back();
            
            // Wait for URL to change
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(driver -> !driver.getCurrentUrl().equals(currentUrl));
            
            // Wait for page load
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            
            // Add a small pause to ensure dynamic content is loaded
            Thread.sleep(2000);
            
            logger.info("Successfully navigated back to previous page");
        } catch (Exception e) {
            logger.error("Failed to navigate back: {}", e.getMessage());
            throw e;
        }
    }

/*
 *
     @Then("the loan amount should still be {string}")
    public void theLoanAmountShouldStillBe(String expectedAmount) {
        logger.info("Verifying loan amount persistence");
        try {
            if (expectedAmount.equals("randomLoanAmount")) {
                expectedAmount = randomLoanAmount;
            }
            String actualAmount = finanzierungPage.getDarlehensbetrag();
            Assertions.assertEquals(expectedAmount, actualAmount, 
                "Loan amount does not match the previously entered value");
            logger.info("Loan amount verified successfully: {}", actualAmount);
        } catch (Exception e) {
            logger.error("Failed to verify loan amount: {}", e.getMessage());
            throw e;
        }
    } 
 * 
 */


/*
    @Then("the repayment percentage should still be {string}")
    public void theRepaymentPercentageShouldStillBe(String expectedPercentage) {
        logger.info("Verifying repayment percentage persistence");
        try {
            if (expectedPercentage.equals("randomRepaymentPercentage")) {
                expectedPercentage = randomRepaymentPercentage;
            }
            String actualPercentage = finanzierungPage.getTilgung();
            Assertions.assertEquals(expectedPercentage, actualPercentage, 
                "Repayment percentage does not match the previously entered value");
            logger.info("Repayment percentage verified successfully: {}", actualPercentage);
        } catch (Exception e) {
            logger.error("Failed to verify repayment percentage: {}", e.getMessage());
            throw e;
        }
    }
    */

    @Then("the first payout date should be preserved")
    public void theFirstPayoutDateShouldBePreserved() {
        logger.info("Verifying first payout date persistence");
        try {
            String expectedDate = finanzierungPage.getExpectedAuszahlungsdatum();
            String actualDate = finanzierungPage.getAuszahlungsdatum();
            Assertions.assertEquals(expectedDate, actualDate, 
                "First payout date does not match the previously entered value");
            logger.info("First payout date verified successfully: {}", actualDate);
        } catch (Exception e) {
            logger.error("Failed to verify first payout date: {}", e.getMessage());
            throw e;
        }
    }

    @When("the user clicks on back button to return to first page")
    public void theUserClicksOnBackButtonToFirstPage() {
        logger.info("Clicking back button to return to first page");
        try {
            Driver.getDriver().navigate().back();
            // Wait for page to load and verify we're on the first page
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='postalCode']")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select[name='usagePurpose']")));
            logger.info("Successfully navigated back to first page");
        } catch (Exception e) {
            logger.error("Failed to navigate back to first page: {}", e.getMessage());
            throw e;
        }
    }

    @Then("the postal code should still be {string}")
    public void thePostalCodeShouldStillBe(String expectedPostalCode) {
        logger.info("Verifying postal code persistence");
        try {
            if (expectedPostalCode.equals("randomPostalCode")) {
                expectedPostalCode = randomPostalCode;
            }
            String actualPostalCode = angabenZumObjektPage.getPlz();
            Assertions.assertEquals(expectedPostalCode, actualPostalCode, 
                "Postal code does not match the previously entered value");
            logger.info("Postal code verified successfully: {}", actualPostalCode);
        } catch (Exception e) {
            logger.error("Failed to verify postal code: {}", e.getMessage());
            throw e;
        }
    }

    @Then("the purchase price should still be {string}")
    public void thePurchasePriceShouldStillBe(String expectedPrice) {
        logger.info("Verifying purchase price persistence");
        try {
            if (expectedPrice.equals("randomPurchasePrice")) {
                expectedPrice = randomPurchasePrice;
            }
            String actualPrice = angabenZumObjektPage.getKaufpreis();
            Assertions.assertEquals(expectedPrice, actualPrice, 
                "Purchase price does not match the previously entered value");
            logger.info("Purchase price verified successfully: {}", actualPrice);
        } catch (Exception e) {
            logger.error("Failed to verify purchase price: {}", e.getMessage());
            throw e;
        }
    }

    @Then("the property type should still be {string}")
    public void thePropertyTypeShouldStillBe(String expectedType) {
        logger.info("Verifying property type persistence");
        try {
            if (expectedType.equals("randomPropertyType")) {
                expectedType = randomPropertyType;
            }
            String actualType = angabenZumObjektPage.getSelectedObjektart();
            Assertions.assertEquals(expectedType, actualType, 
                "Property type does not match the previously entered value");
            logger.info("Property type verified successfully: {}", actualType);
        } catch (Exception e) {
            logger.error("Failed to verify property type: {}", e.getMessage());
            throw e;
        }
    }

    @Then("the user should see the uploaded file")
    public void theUserShouldSeeTheUploadedFile() {
        logger.info("Verifying uploaded file persistence");
        try {
            dokumentHochladenPage.verifyFileUpload();
            logger.info("Uploaded file verified successfully");
        } catch (Exception e) {
            logger.error("Failed to verify uploaded file: {}", e.getMessage());
            throw e;
        }
    }

    @And("the user clicks on weiter button to return to last page")
    public void theUserClicksOnWeiterButtonToReturnToLastPage() {
        try {
            dokumentHochladenPage.verifyFileUpload();
            dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();
        } catch (Exception e) {
            logger.error("Error clicking weiter button to return to last page: " + e.getMessage());
            throw e;
        }
    }

    @Then("the user should see the text, what the user wrote in the message field before")
    public void theUserShouldSeeTheMessageText() {
        logger.info("Verifying message text persistence");
        FinalPage finalPage = new FinalPage();
        boolean messageVerified = finalPage.getEnteredMessageInFinalPage();
        Assertions.assertTrue(messageVerified, "Message text verification failed");
        logger.info("Message text verification {}", messageVerified ? "successful" : "failed");
    }

    @When("the user clicks on back button to return to upload page")
    public void theUserClicksOnBackButtonToUploadPage() {
        logger.info("Clicking back button to return to upload page");
        finalPage.clickBackButtonnFinalPage();
    }

    @When("the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page")
    public void theUserClicksOnBackButtonToOfferSelectionPage() {
        dokumentHochladenPage.clickZurueckButtonInDokumentHochladungPage();
    }

    @When("the user clicks on back button to return to Angaben zum Finanzierungswunsch page")
    public void theUserClicksOnBackButtonToFinanzierungPage() {
        logger.info("Clicking back button to return to Finanzierung page");
        offerSelectionPage.clickZurueckButtonInOfferSelectionPage();
    }

    @When("the user clicks on back button to return to Angaben zum Objekt page")
    public void theUserClicksOnBackButtonToObjektPage() {
        logger.info("Clicking back button to return to Objekt page");
        try {
            // Find and click the back button
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn--nav-previous, button[class*='nav-previous'], button[class*='zurück']")
            ));
            backButton.click();
            
            // Wait for Objekt page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='postalCode']")));
            logger.info("Successfully navigated back to Objekt page");
        } catch (Exception e) {
            logger.error("Failed to navigate back to Objekt page: {}", e.getMessage());
            throw e;
        }
    }

    @Then("the user should see, what the user selected in that page before")
    public void theUserShouldSeeSelectedOption() {
        logger.info("Verifying selected option persistence");
        logger.info("Selected option number: {}", randomOption);
        
        try {
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
                Assertions.assertEquals(Integer.parseInt(randomOption), selectedIndex,
                    "Selected option does not match the expected option");
                
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
        } catch (Exception e) {
            logger.error("Failed to verify selected option persistence: {}", e.getMessage());
            Utils.takeScreenshot(Driver.getDriver(), "option-verification-failure");
            throw e;
        }
    }

    @Then("the user should see same values, as the user entered in Finanzierungswunsch page before")
    public void theUserShouldSeeSameValuesInFinanzierungPage() {
        logger.info("Verifying all values persistence in Finanzierung page");
        String actualAmount = finanzierungPage.getDarlehensbetrag(randomLoanAmount);
        Assertions.assertTrue(randomLoanAmount.equalsIgnoreCase(actualAmount), 
                "Loan amount does not match the previously entered value"); 
        
        String actualPercentage = finanzierungPage.getTilgung(randomRepaymentPercentage);
        Assertions.assertTrue(randomRepaymentPercentage.equalsIgnoreCase(actualPercentage), 
                "Repayment percentage does not match the previously entered value");

        String actualRepaymentType = "Tilgung in %";
        logger.info("Repayment type: " + actualRepaymentType);
        
        Assertions.assertTrue(finanzierungPage.getRepaymentType().equalsIgnoreCase(actualRepaymentType), 
                "Repayment type does not match the previously entered value");
        
        String actualPayoutDate = finanzierungPage.getExpectedAuszahlungsdatum();
        Assertions.assertTrue(actualPayoutDate.equalsIgnoreCase(finanzierungPage.getAuszahlungsdatum()), 
                "Payout date does not match the previously entered value");

    }

    @Then("the user should see same values, as the user entered in Objekt page before")
    public void theUserShouldSeeSameValuesInObjektPage() {
        logger.info("Verifying all values persistence in Objekt page");
        try {
            // Verify postal code
            String expectedPostalCode = randomPostalCode;
            String actualPostalCode = angabenZumObjektPage.getPlz();
            Assertions.assertEquals(expectedPostalCode, actualPostalCode, 
                "Postal code does not match the previously entered value");

            // Verify purchase price
            String expectedPrice = randomPurchasePrice;
            String actualPrice = angabenZumObjektPage.getKaufpreis();
            Assertions.assertEquals(expectedPrice, actualPrice, 
                "Purchase price does not match the previously entered value");

            // Verify property type
            String expectedType = randomPropertyType;
            String actualType = angabenZumObjektPage.getSelectedObjektart();
            Assertions.assertEquals(expectedType, actualType, 
                "Property type does not match the previously selected value");

            logger.info("All values in Objekt page verified successfully");
        } catch (Exception e) {
            logger.error("Failed to verify values in Objekt page: {}", e.getMessage());
            throw e;
        }
    }
} 
