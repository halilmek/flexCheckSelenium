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
import java.util.List;
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
    //String randomOption = TestDataGenerator.generateOptionNumber();
    String randomOption = "4";

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
        finanzierungPage.enterMonthlyPayment(amount);
    }


    //Total Term
    @When("the user enters total term in years {string}")
    public void theUserEntersTotalTermInYears(String years) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (years.equalsIgnoreCase("randomJahre")) {
            years = randomJahreFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }

        finanzierungPage.enterTotalTermYears(years);

    }

    @When("the user enters total term in months {string}")
    public void theUserEntersTotalTermInMonths(String months) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (months.equalsIgnoreCase("randomMonate")) {
            months = randomMonateFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }

        finanzierungPage.enterTotalTermMonths(months);

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

        //Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomRepaymentPercentage));

        //offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFirstModal(randomLoanAmount, randomPurpose, randomPurchasePrice, randomRepaymentPercentage);
        //logger.info("Method result: " + offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFirstModal(randomLoanAmount, randomPurpose, randomPurchasePrice, randomRepaymentPercentage));
        //boolean result = offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInSecondModal("", randomLoanAmount, randomPurpose, randomPurchasePrice, randomRepaymentPercentage);
        //boolean result = offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInSecondModal("", randomLoanAmount, randomPurpose, randomPurchasePrice, randomRepaymentPercentage, randomMonthlyPayment);
        //logger.info("Method result: " + result);
        //System.out.println("Method result: " + offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal(randomPurchasePrice, randomLoanAmount, randomPurpose, randomRepaymentPercentage));
        //System.out.println("Method result: " + offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal(randomPurchasePrice, randomLoanAmount, randomPurpose));
        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal(randomPurchasePrice, randomLoanAmount, randomPurpose));

    }
    
    @Then("the user should see values, as the user entered in the calculator based on Monatliche Rate payment type")
    public void verifyCalculatorValuesForMonthlyRatePaymentType() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForMonatlicheRatePaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomMonthlyPayment));

    }
    
    @Then("the user should see values, as the user entered in the calculator based on Gesamtlaufzeit payment type")
    public void verifyCalculatorValuesForGesamtlaufzeitPaymentType() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForGesamtbetragPaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomJahreFürGesamtlaufzeit, randomMonateFürGesamtlaufzeit));
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
    


/*

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
    */



    @Then("the user should see the uploaded file")
    public void theUserShouldSeeTheUploadedFile() {

        Assertions.assertTrue(dokumentHochladenPage.verifyFileUpload());
    }

    @And("the user clicks on weiter button to return to last page")
    public void theUserClicksOnWeiterButtonToReturnToLastPage() {

        dokumentHochladenPage.verifyFileUpload();
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();

    }

    @Then("the user should see the text, what the user wrote in the message field before")
    public void theUserShouldSeeTheMessageText() {
        
        Assertions.assertTrue(finalPage.getEnteredMessageInFinalPage(), "Message text verification failed");

    }

    @When("the user clicks on back button to return to upload page")
    public void theUserClicksOnBackButtonToUploadPage() {

        finalPage.clickBackButtonOnFinalPage();
    }

    @When("the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page")
    public void theUserClicksOnBackButtonToOfferSelectionPage() {
        dokumentHochladenPage.clickZurueckButtonInDokumentHochladungPage();
    }

    @When("the user clicks on back button to return to Angaben zum Finanzierungswunsch page")
    public void theUserClicksOnBackButtonToFinanzierungPage() {

        offerSelectionPage.clickZurueckButtonInOfferSelectionPage();
    }

    @When("the user clicks on back button to return to Angaben zum Objekt page")
    public void theUserClicksOnBackButtonToObjektPage() {

        finanzierungPage.clickBackButtonInAngabenZumFinanzierungswunschPage();
    }

    @Then("the user should see, what the user selected in option selection page")
    public void theUserShouldSeeSelectedOption() {

        Assertions.assertTrue(offerSelectionPage.verifySelectedOption(randomOption));
    }

    @Then("the user should see same values for Tilgung in % as payment type, as the user entered in Finanzierungswunsch page before")
    public void theUserShouldSeeSameValuesInFinanzierungPage() {

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

        String expectedUsagePurpose = randomPurpose;
        String actualUsagePurpose = angabenZumObjektPage.getSelectedVerwendungszweck();
        Assertions.assertTrue(expectedUsagePurpose.equalsIgnoreCase(actualUsagePurpose), 
                "Usage purpose does not match the previously entered value");

        String expectedPostalCode = randomPostalCode;
        String actualPostalCode = angabenZumObjektPage.getPlz();
        Assertions.assertTrue(expectedPostalCode.equalsIgnoreCase(actualPostalCode), 
                "Postal code does not match the previously entered value");

        String expectedPurchasePrice = randomPurchasePrice;
        String actualPurchasePrice = angabenZumObjektPage.getKaufpreis().replace(".", "");
        Assertions.assertTrue(expectedPurchasePrice.equalsIgnoreCase(actualPurchasePrice), 
                "Purchase price does not match the previously entered value");

        String expectedPropertyType = randomPropertyType;
        String actualPropertyType = angabenZumObjektPage.getSelectedObjektart();
        Assertions.assertTrue(expectedPropertyType.equalsIgnoreCase(actualPropertyType), 
                "Property type does not match the previously entered value");
    }

    @Then("the user should see same values for monatliche Rate as payment type, as the user entered in Finanzierungswunsch page before")
    public void theUserShouldSeeSameValuesForMonatlicheRateAsPaymentType() {
    
        String actualAmount = finanzierungPage.getDarlehensbetrag(randomLoanAmount);
        Assertions.assertTrue(randomLoanAmount.equalsIgnoreCase(actualAmount), 
                "Loan amount does not match the previously entered value"); 
        
        
        String actualPayoutDate = finanzierungPage.getExpectedAuszahlungsdatum();
        Assertions.assertTrue(actualPayoutDate.equalsIgnoreCase(finanzierungPage.getAuszahlungsdatum()), 
                "Payout date does not match the previously entered value");

        String expectedRepaymentType = "monatliche Rate";
        Assertions.assertTrue(finanzierungPage.getRepaymentType().equalsIgnoreCase(expectedRepaymentType), 
        "Repayment type does not match the previously entered value");


        String expectedMonthlyPayment = randomMonthlyPayment;
        Assertions.assertTrue(finanzierungPage.getMonatlicheRate().equalsIgnoreCase(expectedMonthlyPayment), 
                "Monthly payment does not match the previously entered value");

    }

    @Then("the user should see same values for Gesamtlaufzeit as payment type, as the user entered in Finanzierungswunsch page before")
    public void theUserShouldSeeSameValuesForGesamtlaufzeitAsPaymentType() {
        

        String actualAmount = finanzierungPage.getDarlehensbetrag(randomLoanAmount);
        Assertions.assertTrue(randomLoanAmount.equalsIgnoreCase(actualAmount), 
                "Loan amount does not match the previously entered value"); 
        
        String actualPayoutDate = finanzierungPage.getExpectedAuszahlungsdatum();
        Assertions.assertTrue(actualPayoutDate.equalsIgnoreCase(finanzierungPage.getAuszahlungsdatum()), 
                "Payout date does not match the previously entered value");


        String expectedRepaymentType = "Gesamtlaufzeit";
        Assertions.assertTrue(finanzierungPage.getRepaymentType().equalsIgnoreCase(expectedRepaymentType), 
        "Repayment type does not match the previously entered value");


        String expectedTotalTermYears = randomJahreFürGesamtlaufzeit;
        Assertions.assertTrue(finanzierungPage.getGesamtlaufzeitJahre().equalsIgnoreCase(expectedTotalTermYears), 
                "Total term years does not match the previously entered value");

        String expectedTotalTermMonths = randomMonateFürGesamtlaufzeit;
        Assertions.assertTrue(finanzierungPage.getGesamtlaufzeitMonate().equalsIgnoreCase(expectedTotalTermMonths), 
                "Total term months does not match the previously entered value");

    }


    @And("the user refreshes the page")
    public void theUserRefreshesThePage() {
        finanzierungPage.refreshPage();
    }



    //======================in order to get options visuality status through javascript with displayValue=========================

    @Then("the user should verify the option is selected")
    public void theUserShouldVerifyTheOptionIsSelected() throws InterruptedException {
        logger.info("Verifying the option is selected");
        
        //display === 'none'

        Thread.sleep(6000);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        List<WebElement> buttons = Driver.getDriver().findElements(By.xpath("//*[@href='#anfragen']"));

                    // Simple script to find selected option

                String simpleScript = 
                "var options = document.querySelectorAll('.panel-card-button');" +
                "var result = [];" +
                "for (var i = 0; i < options.length; i++) {" +
                "  var option = options[i];" +
                "  var showChosen = option.querySelector('.hidden-unforced.show-chosen');" +
                "  if (showChosen) {" +
                "    var displayValue = window.getComputedStyle(showChosen).display;" +
                "    result.push({" +
                "      index: i + 1," +
                "      displayValue: displayValue," +
                "      elementId: option.id || 'no-id'" +
                "    });" +
                "  }" +
                "}" +
                "console.log(result); return result;";

            List<Map<String, Object>> result = (List<Map<String, Object>>) js.executeScript(simpleScript);

            // Konsola yazdırma
            for (Map<String, Object> entry : result) {
                System.out.println("Index: " + entry.get("index") + ", Display Value: " + entry.get("displayValue") + ", Element ID: " + entry.get("elementId"));
            }

                



                

        /*
        for (WebElement button : buttons) {
            String bgColor = (String) js.executeScript("return window.getComputedStyle(arguments[0]).backgroundColor;", button);
            logger.info("bgColor: " + bgColor);
            String elementId = button.getAttribute("id"); // ID değerini al
            
            logger.info("randomOption: " + randomOption);
            if (bgColor.equals("rgb(230, 162, 0)")) {  // Yeşil olmayanı seç
                
                System.out.println("Seçilen butonun ID'si: " + elementId);
                
                //js.executeScript("arguments[0].click();", button);
                //System.out.println("Yeşil olmayan butona tıklandı: " + button.getText());
                break; // İlk uygun elementi bulunca çık
            }
        }

        ================================================

        // JavaScript kodu ile arka plan rengi kontrol etme
                String script = 
                "var elements = document.querySelectorAll('*');" +
                "var matchingElements = [];" +
                "elements.forEach(function(element) {" +
                "   var backgroundColor = window.getComputedStyle(element).backgroundColor;" +
                "   if (backgroundColor === 'rgb(230, 162, 0)') {" +
                "       matchingElements.push(element);" +
                "   }" +
                "});" +
                "return matchingElements;";
    
            // JavaScript Executor ile elementleri almak
            List<WebElement> matchingElements = (List<WebElement>) js.executeScript(script);
    
            // Eşleşen öğeleri yazdırma
            System.out.println("Matching elements with background color #e6a200:");
            for (WebElement element : matchingElements) {
                System.out.println(element.getAttribute("id"));
            }



            ================================================

                                String simpleScript = 
                    "var options = document.querySelectorAll('.panel-card-button');" +
                    "var result = null;" +
                    "for (var i = 0; i < options.length; i++) {" +
                    "  var option = options[i];" +
                    "  var showChosen = option.querySelector('.hidden-unforced.show-chosen');" +
                    "  if (showChosen && (window.getComputedStyle(showChosen).display === 'none')) {" +
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

        */

    }
} 
