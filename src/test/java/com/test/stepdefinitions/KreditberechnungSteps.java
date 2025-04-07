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

import java.util.ArrayList;
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

    /**
     * Navigiert zur FlexCheck-Rechner-Startseite.
     * Diese Methode öffnet die Hauptseite des FLEXCHECK Rechners im Browser.
     */
    @Given("the user is on the FlexCheck calculator page")
    public void the_user_is_on_the_flex_check_calculator_page() {
        angabenZumObjektPage.navigatingToHomePage();
    }

    //=====================Angaben zum Objekt Page=====================
    /**
     * Wählt den Verwendungszweck für den Kredit aus.
     * Diese Methode selektiert den angegebenen Verwendungszweck oder einen zufälligen Wert, 
     * wenn "randomPurpose" angegeben wurde.
     * 
     * @param purpose Der auszuwählende Verwendungszweck oder "randomPurpose" für einen Zufallswert
     */
    @When("the user selects {string} as usage purpose")
    public void theUserSelectsAsUsagePurpose(String purpose) {
        if (purpose.equalsIgnoreCase("randomPurpose")) {
            purpose = randomPurpose;
        }
        angabenZumObjektPage.selectVerwendungszweck(purpose);
    }

    /**
     * Gibt die Postleitzahl für das Objekt ein.
     * Diese Methode setzt die angegebene Postleitzahl oder einen zufälligen Wert,
     * wenn "randomPostalCode" angegeben wurde.
     * 
     * @param plz Die einzugebende Postleitzahl oder "randomPostalCode" für einen Zufallswert
     */
    @When("the user enters postal code {string}")
    public void theUserEntersPostalCode(String plz) {
        if (plz.equalsIgnoreCase("randomPostalCode")) {
            plz = randomPostalCode;
        }
        angabenZumObjektPage.enterPlz(plz);
    }

    /**
     * Gibt den Kaufpreis für das Objekt ein.
     * Diese Methode setzt den angegebenen Kaufpreis oder einen zufälligen Wert,
     * wenn "randomPurchasePrice" angegeben wurde.
     * 
     * @param price Der einzugebende Kaufpreis oder "randomPurchasePrice" für einen Zufallswert
     */
    @When("the user enters purchase price {string}")
    public void theUserEntersPurchasePrice(String price) {
        if (price.equalsIgnoreCase("randomPurchasePrice")) {
            price = randomPurchasePrice;
        }
        angabenZumObjektPage.enterKaufpreis(price);
    }

    /**
     * Wählt die Objektart aus.
     * Diese Methode selektiert die angegebene Objektart oder einen zufälligen Wert,
     * wenn "randomPropertyType" angegeben wurde.
     * 
     * @param type Die auszuwählende Objektart oder "randomPropertyType" für einen Zufallswert
     */
    @When("the user selects {string} as property type")
    public void theUserSelectsAsPropertyType(String type) {
        if (type.equalsIgnoreCase("randomPropertyType")) {
            type = randomPropertyType;
        }
        angabenZumObjektPage.selectObjektart(type);
    }

    /**
     * Klickt auf den Weiter-Button auf der Seite "Angaben zum Objekt".
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur nächsten Seite.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("the user clicks on Weiter in Angaben zum Objekt page")
    public void theUserClicksOnWeiterInAngabenZumObjektPage() throws InterruptedException {
        angabenZumObjektPage.clickWeiter();
    }

    //==========================Angaben zum FinanzierungswunschPage============================

    /**
     * Gibt den gewünschten Darlehensbetrag ein.
     * Diese Methode setzt den angegebenen Darlehensbetrag oder einen zufälligen Wert,
     * wenn "randomLoanAmount" angegeben wurde.
     * 
     * @param amount Der einzugebende Darlehensbetrag oder "randomLoanAmount" für einen Zufallswert
     */
    @When("the user enters desired loan amount {string}")
    public void theUserEntersDesiredLoanAmount(String amount) {
        if (amount.equalsIgnoreCase("randomLoanAmount")) {
            amount = randomLoanAmount;
        }
        finanzierungPage.enterDarlehenshoehe(amount);
    }

    /**
     * Gibt den gewünschten Tilgungsprozentsatz ein.
     * Diese Methode setzt den angegebenen Tilgungsprozentsatz oder einen zufälligen Wert,
     * wenn "randomRepaymentPercentage" angegeben wurde.
     * 
     * @param percentage Der einzugebende Tilgungsprozentsatz oder "randomRepaymentPercentage" für einen Zufallswert
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("the user enters repayment percentage {string}")
    public void theUserEntersRepaymentPercentage(String percentage) throws InterruptedException {
        if (percentage.equalsIgnoreCase("randomRepaymentPercentage")) {
            percentage = randomRepaymentPercentage;
        }
        finanzierungPage.enterTilgung(percentage);
    }

    /**
     * Wählt die Art der Rückzahlung aus.
     * Diese Methode selektiert die angegebene Rückzahlungsart.
     * 
     * @param type Die auszuwählende Rückzahlungsart
     */
    @When("the user selects repayment type {string}")
    public void theUserSelectsRepaymentType(String type) {
        finanzierungPage.selectRueckzahlungsArt(type);
    }

    /**
     * Gibt das Datum der ersten Auszahlung ein.
     * Diese Methode setzt das Auszahlungsdatum für den Kredit.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("the user enters first payout date")
    public void theUserEntersFirstPayoutDate() throws InterruptedException {
        finanzierungPage.enterAuszahlungsdatum();
    }

    /**
     * Klickt auf den Weiter-Button auf der Seite "Angaben zum Finanzierungswunsch".
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur nächsten Seite.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("the user clicks on Weiter in Angaben zum Finanzierungswunsch page")
    public void theUserClicksOnWeiterInAngabenZumFinanzierungswunschPage() throws InterruptedException {
        finanzierungPage.clickWeiterInAngabenZumFinanzierungswunschPage();
    }

    /**
     * Gibt die gewünschte monatliche Rate ein.
     * Diese Methode setzt die angegebene monatliche Rate oder einen zufälligen Wert,
     * wenn "randomMonthlyPayment" angegeben wurde.
     * 
     * @param amount Die einzugebende monatliche Rate oder "randomMonthlyPayment" für einen Zufallswert
     */
    @When("the user enters monthly payment {string}")
    public void theUserEntersMonthlyPayment(String amount) {
        if (amount.equalsIgnoreCase("randomMonthlyPayment")) {
            amount = randomMonthlyPayment;
        }
        finanzierungPage.enterMonthlyPayment(amount);
    }

    /**
     * Gibt die Gesamtlaufzeit in Jahren ein.
     * Diese Methode setzt die angegebene Laufzeit in Jahren oder einen zufälligen Wert,
     * wenn "randomJahre" angegeben wurde.
     * 
     * @param years Die einzugebende Laufzeit in Jahren oder "randomJahre" für einen Zufallswert
     */
    @When("the user enters total term in years {string}")
    public void theUserEntersTotalTermInYears(String years) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (years.equalsIgnoreCase("randomJahre")) {
            years = randomJahreFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }

        finanzierungPage.enterTotalTermYears(years);
    }

    /**
     * Gibt die zusätzliche Gesamtlaufzeit in Monaten ein.
     * Diese Methode setzt die angegebene Laufzeit in Monaten oder einen zufälligen Wert,
     * wenn "randomMonate" angegeben wurde.
     * 
     * @param months Die einzugebende Laufzeit in Monaten oder "randomMonate" für einen Zufallswert
     */
    @When("the user enters total term in months {string}")
    public void theUserEntersTotalTermInMonths(String months) {
        // Eğer senaryo Outline'dan geliyorsa, doğrudan o değeri kullan
        if (months.equalsIgnoreCase("randomMonate")) {
            months = randomMonateFürGesamtlaufzeit; // TestDataGenerator'dan alınan rastgele değer
        }
        /*
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        List<WebElement> pElements = Driver.getDriver().findElements(By.tagName("p"));

        List<String> texts = new ArrayList<>();
        for (WebElement pElement : pElements) {
            String text = (String) js.executeScript("return arguments[0].innerText;", pElement);
            if (!text.trim().isEmpty()) { // Boş olmayanları al
                texts.add(text);
            }
        }
        logger.info("Tüm <p> etiketlerindeki metinler BEFORE ERROR MESSAGE: " + texts);
        System.out.println("Tüm <p> etiketlerindeki metinler: " + texts);
        */

        finanzierungPage.enterTotalTermMonths(months);

    }

    //==========================OfferSelectionPage=BerechnungsergebnisPage============================

    /**
     * Überprüft, ob die Berechnungsergebnisse angezeigt werden.
     * Diese Methode wartet, bis die Ergebnisse geladen sind und überprüft ihre Sichtbarkeit.
     */
    @Then("the user should see the calculation results")
    public void theUserShouldSeeTheCalculationResults() {
        Assertions.assertTrue(berechnungsergebnisPage.waitForResultsToLoad(), "Calculation results not loaded");
    }

    /**
     * Wählt ein verfügbares Angebot aus.
     * Diese Methode selektiert ein Angebot aus den berechneten Optionen.
     * 
     * @throws Exception Falls bei der Auswahl ein Fehler auftritt
     */
    @When("the user selects an option")
    public void theUserSelectsAnOption() throws Exception {
        offerSelectionPage.selectAnAvailableOption();
    }

    /**
     * Klickt auf den Button "Angebote anfordern".
     * Diese Methode führt den Klick auf den Anfordern-Button aus und navigiert zur nächsten Seite.
     */
    @When("the user clicks on Angebote anfordern")
    public void theUserClicksOnAngeboteAnfordern() {
        offerSelectionPage.clickAngebotAnfordern();
    }

    /**
     * Wählt eine bestimmte Option aus den Angeboten.
     * Diese Methode selektiert die angegebene Option oder eine zufällige,
     * wenn "randomOption" angegeben wurde.
     * 
     * @param optionNumber Die Nummer der auszuwählenden Option oder "randomOption" für einen Zufallswert
     */
    @When("the user selects {string} option")
    public void theUserSelectsOption(String optionNumber) {
        if (optionNumber.equalsIgnoreCase("randomOption")) {
            optionNumber = randomOption;
        }
        offerSelectionPage.selectOption(optionNumber);
    }

    //Entered values check
    /**
     * Klickt auf den "Details anzeigen" Button.
     * Diese Methode öffnet den Modal-Dialog mit Details zu den ausgewählten Optionen.
     */
    @When("the user clicks on Details anzeigen button")
    public void theUserClicksOnDetailsAnzeigenButton() {
        // Click the details button
        offerSelectionPage.clickDetailsButton();
        
        // Print all values from the modal
        offerSelectionPage.printModalValues();
    }

    /**
     * Überprüft, ob die im Rechner eingegebenen Werte für den Zahlungstyp "Tilgung in %" korrekt angezeigt werden.
     * Diese Methode verifiziert, dass die Werte im Modal-Dialog mit den Eingabewerten übereinstimmen.
     */
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
        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal(randomPurchasePrice, randomLoanAmount, randomPurpose), "Modals enthalten nicht, was der Benutzer eingegeben hat!");

    }
    
    /**
     * Überprüft, ob die im Rechner eingegebenen Werte für den Zahlungstyp "Monatliche Rate" korrekt angezeigt werden.
     * Diese Methode verifiziert, dass die Werte im Modal-Dialog mit den Eingabewerten übereinstimmen.
     */
    @Then("the user should see values, as the user entered in the calculator based on Monatliche Rate payment type")
    public void verifyCalculatorValuesForMonthlyRatePaymentType() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForMonatlicheRatePaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomMonthlyPayment), "Modals enthalten nicht, was der Benutzer eingegeben hat!");

    }
    
    /**
     * Überprüft, ob die im Rechner eingegebenen Werte für den Zahlungstyp "Gesamtlaufzeit" korrekt angezeigt werden.
     * Diese Methode verifiziert, dass die Werte im Modal-Dialog mit den Eingabewerten übereinstimmen.
     */
    @Then("the user should see values, as the user entered in the calculator based on Gesamtlaufzeit payment type")
    public void verifyCalculatorValuesForGesamtlaufzeitPaymentType() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForGesamtbetragPaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomJahreFürGesamtlaufzeit, randomMonateFürGesamtlaufzeit), "Modals enthalten nicht, was der Benutzer eingegeben hat!");
    }

    //================DokumentHochladenPage=========

    /**
     * Lädt eine Ausweisdatei zur Identifikation hoch.
     * Diese Methode führt den Upload eines Test-Dokuments per Drag-and-Drop durch.
     */
    @When("the user uploads id")
    public void theUserUploadsId() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";
        dokumentHochladenPage.dragAndDropFile2(filePath);
    }

    /**
     * Überprüft, ob die Datei erfolgreich hochgeladen wurde.
     * Diese Methode verifiziert, dass die hochgeladene Datei im System angezeigt wird.
     */
    @Then("the user checks, whether the file was uploaded")
    public void theUserChecksWhetherTheFileWasUploaded() {
        dokumentHochladenPage.verifyFileUpload();
    }

    /**
     * Klickt auf den Weiter-Button auf der Dokumentenupload-Seite.
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur nächsten Seite.
     */
    @And("the user clicks on Weiter in DokumentHochladung page")
    public void theUserClicksOnWeiterInDokumentHochladungPage() {
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();
    }

    //File Upload Negative Test
    /**
     * Versucht, eine Datei auf inkorrekte Weise hochzuladen.
     * Diese Methode führt einen negativen Testfall für den Dokumenten-Upload durch.
     */
    @When("the user uploads a file not correctly")
    public void theUserUploadsAFileNotCorrectly() {
        dokumentHochladenPage.uploadIdForNegativeTest();
    }

//============Final Page============

    /**
     * Gibt eine Nachricht im Textfeld der finalen Seite ein.
     * Diese Methode setzt einen Text in das Nachrichtenfeld ein.
     */
    @When("the user writes a message")
    public void theUserWritesAMessage() {
        finalPage.writeMessage();
    }

    /**
     * Klickt auf den Angebot-Anfordern-Button auf der finalen Seite.
     * Diese Methode führt den Klick auf den Submit-Button aus und überprüft die Erfolgsanzeige.
     */
    @When("the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page")
    public void theUserClicksOnJetztKostenlosButtonInFinalPage() {
        finalPage.clickSubmitButton();
        finalPage.isSuccessMessageDisplayedForFileUpload();
    }

    /**
     * Überprüft, ob die Erfolgsmeldung nach der Angebotsanforderung angezeigt wird.
     * Diese Methode verifiziert, dass die Anfrage erfolgreich übermittelt wurde.
     * 
     * @throws Exception Falls bei der Überprüfung ein Fehler auftritt
     */
    @Then("the user should see the success message")
    public void theUserShouldSeeTheSuccessMessage() throws Exception {
        Assertions.assertTrue(finalPage.isSuccessMessageDisplayed(), "Erfolgsmeldung nicht angezeigt");
    }

    /**
     * Überprüft, ob die Fehlermeldung für den Datei-Upload angezeigt wird.
     * Diese Methode verifiziert, dass bei fehlerhaftem Upload eine entsprechende Meldung erscheint.
     */
    @Then("the user should see the error message for file upload")
    public void theUserShouldSeeTheErrorMessageForFileUpload() {
        Assertions.assertTrue(finalPage.notSuccessMessageDisplayedForFileUpload(), "Fehlermeldung nicht angezeigt");
    }

    //==========================Data Persistence Steps============================

    /**
     * Überprüft, ob die hochgeladene Datei angezeigt wird.
     * Diese Methode verifiziert, dass die Datei auch nach der Navigation erhalten bleibt.
     */
    @Then("the user should see the uploaded file")
    public void theUserShouldSeeTheUploadedFile() {

        Assertions.assertTrue(dokumentHochladenPage.verifyFileUpload());
    }

    /**
     * Klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren.
     * Diese Methode überprüft den Datei-Upload und navigiert dann zur nächsten Seite.
     */
    @And("the user clicks on weiter button to return to last page")
    public void theUserClicksOnWeiterButtonToReturnToLastPage() {

        dokumentHochladenPage.verifyFileUpload();
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();

    }

    /**
     * Überprüft, ob der zuvor eingegebene Text im Nachrichtenfeld angezeigt wird.
     * Diese Methode verifiziert die Datenpersistenz des Nachrichtentexts zwischen den Seiten.
     */
    @Then("the user should see the text, what the user wrote in the message field before")
    public void theUserShouldSeeTheMessageText() {
        
        Assertions.assertTrue(finalPage.getEnteredMessageInFinalPage(), "Message text verification failed");

    }

    /**
     * Klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren.
     * Diese Methode navigiert von der finalen Seite zurück zur Dokumentenupload-Seite.
     */
    @When("the user clicks on back button to return to upload page")
    public void theUserClicksOnBackButtonToUploadPage() {

        finalPage.clickBackButtonOnFinalPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur Angebotsauswahlseite zurückzukehren.
     * Diese Methode navigiert von der Dokumentenupload-Seite zurück zur Seite "Wählen Sie Ihre gewünschte Kondition".
     */
    @When("the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page")
    public void theUserClicksOnBackButtonToOfferSelectionPage() {
        dokumentHochladenPage.clickZurueckButtonInDokumentHochladungPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur Finanzierungswunsch-Seite zurückzukehren.
     * Diese Methode navigiert von der Angebotsauswahlseite zurück zur Seite "Angaben zum Finanzierungswunsch".
     */
    @When("the user clicks on back button to return to Angaben zum Finanzierungswunsch page")
    public void theUserClicksOnBackButtonToFinanzierungPage() {

        offerSelectionPage.clickZurueckButtonInOfferSelectionPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur Objekt-Seite zurückzukehren.
     * Diese Methode navigiert von der Finanzierungswunsch-Seite zurück zur Seite "Angaben zum Objekt".
     */
    @When("the user clicks on back button to return to Angaben zum Objekt page")
    public void theUserClicksOnBackButtonToObjektPage() {

        finanzierungPage.clickBackButtonInAngabenZumFinanzierungswunschPage();
    }

    /**
     * Überprüft, ob die zuvor ausgewählte Option nach der Navigation angezeigt wird.
     * Diese Methode verifiziert die Datenpersistenz der ausgewählten Option.
     */
    @Then("the user should see, what the user selected in option selection page")
    public void theUserShouldSeeSelectedOption() {

        Assertions.assertTrue(offerSelectionPage.verifySelectedOption(randomOption));
    }

    /**
     * Überprüft, ob die eingegebenen Werte für den Zahlungstyp "Tilgung in %" nach der Navigation angezeigt werden.
     * Diese Methode verifiziert die Datenpersistenz mehrerer Werte auf der Finanzierungswunsch-Seite.
     */
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

    /**
     * Überprüft, ob die eingegebenen Werte auf der Objekt-Seite nach der Navigation angezeigt werden.
     * Diese Methode verifiziert die Datenpersistenz mehrerer Werte auf der Seite "Angaben zum Objekt".
     */
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

    /**
     * Überprüft, ob die eingegebenen Werte für den Zahlungstyp "monatliche Rate" nach der Navigation angezeigt werden.
     * Diese Methode verifiziert die Datenpersistenz mehrerer Werte auf der Finanzierungswunsch-Seite.
     */
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

    /**
     * Überprüft, ob die eingegebenen Werte für den Zahlungstyp "Gesamtlaufzeit" nach der Navigation angezeigt werden.
     * Diese Methode verifiziert die Datenpersistenz mehrerer Werte auf der Finanzierungswunsch-Seite.
     */
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

    /**
     * Aktualisiert die Seite im Browser.
     * Diese Methode lädt die aktuelle Seite neu, um deren Zustand zu aktualisieren.
     */
    @And("the user refreshes the page")
    public void theUserRefreshesThePage() {
        finanzierungPage.refreshPage();
    }



    
    //======================Error Messages=========================
    /**
     * Überprüft, ob die Fehlermeldung für Postleitzahl und Kaufpreis angezeigt wird.
     * Diese Methode verifiziert, dass bei ungültigen Eingaben ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should see the error message for postal code and purchase price")
    public void theUserShouldSeeTheErrorMessageForPostalCodeAndPurchasePrice() {

        Assertions.assertTrue(angabenZumObjektPage.getErrorMessageForPostalCode(), "Error message verification failed");
        Assertions.assertTrue(angabenZumObjektPage.getErrorMessageForPurchasePrice(), "Error message verification failed");
    }

    /**
     * Überprüft, ob die Fehlermeldung für Darlehensbetrag und Tilgungsprozentsatz angezeigt wird.
     * Diese Methode verifiziert, dass bei ungültigen Eingaben ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should see the error message for loan amount and repayment percentage")
    public void theUserShouldSeeTheErrorMessageForLoanAmountAndRepaymentPercentage() {

        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountAndRepaymentPercentage(), "Error message verification failed");
    }

    /**
     * Überprüft, ob die Fehlermeldung für einen Darlehensbetrag über 1.000.000 € angezeigt wird.
     * Diese Methode verifiziert, dass bei zu hohen Darlehensbeträgen ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should see the error message for loan amount, as the loan amount is greater than 1000000")
    public void theUserShouldSeeTheErrorMessageForLoanAmountGreaterThan1000000() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountGreaterThan1000000(), "Error message verification failed");
    }

    /**
     * Überprüft, ob die Berechnungsergebnisse nicht angezeigt werden, wenn der Darlehensbetrag größer als der Kaufpreis ist.
     * Diese Methode verifiziert, dass bei ungültigen Eingabeverhältnissen ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should not see the calculation results to proceed to next page")
    public void theUserShouldNotSeeTheCalculationResultsToProceedToNextPage() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountGreaterThanPurchasePrice(), "Error message verification failed");
    }

    /**
     * Überprüft, ob die Fehlermeldung für die monatliche Rate angezeigt wird.
     * Diese Methode verifiziert, dass bei ungültigen monatlichen Raten ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should see the error message for monthly payment")
    public void theUserShouldSeeTheErrorMessageForMonthlyPayment() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForMonthlyPayment(), "Error message verification failed for monthly payment");
    }

    /**
     * Überprüft, ob die Fehlermeldung für die Gesamtlaufzeit in Jahren und Monaten angezeigt wird.
     * Diese Methode verifiziert, dass bei ungültigen Laufzeitangaben ein entsprechender Fehlerhinweis erscheint.
     */
    @Then("the user should see the error message for total term in years and total term in months")
    public void theUserShouldSeeTheErrorMessageForTotalTermInYearsAndTotalTermInMonths() {
        
        //Assertions.assertTrue(finanzierungPage.getErrorMessageForTotalTermInYearsAndTotalTermInMonths(), "Error message verification failed for total term in years and total term in months");
        Assertions.assertTrue(finanzierungPage.getErrorMessageForTotalTermInYearsAndTotalTermInMonths2(), "Error message verification failed for total term in years and total term in months");
    }

    /**
     * Überprüft, ob alle erforderlichen Felder auf der Seite "Angaben zum Objekt" angezeigt werden.
     * Diese Methode stellt sicher, dass alle notwendigen Eingabefelder für Objektdaten korrekt dargestellt werden.
     */
    @Then("the user should see related fields in Angaben zum Objekt page")
    public void theUserShouldSeeRelatedFieldsForAngabenZumObjektPage() {
        Assertions.assertTrue(angabenZumObjektPage.isDisplayedAllRequiredFields(), "Error message verification failed for related fields for Angaben zum Objekt page");
    }

    /**
     * Überprüft, ob alle erforderlichen Felder auf der Seite "Angaben zum Finanzierungswunsch" angezeigt werden.
     * Diese Methode stellt sicher, dass alle notwendigen Eingabefelder für Finanzierungsdaten korrekt dargestellt werden.
     */
    @Then("the user should see related fields in Angaben zum Finanzierungswunsch page")
    public void theUserShouldSeeRelatedFieldsForAngabenZumFinanzierungswunschPage() {
        Assertions.assertTrue(finanzierungPage.isDisplayedAllRequiredFields(), "Error message verification failed for related fields for Angaben zum Finanzierungswunsch page");
    }

    /**
     * Überprüft, ob alle erforderlichen Felder auf der Upload-Seite angezeigt werden.
     * Diese Methode stellt sicher, dass alle notwendigen Elemente für den Dokumenten-Upload korrekt dargestellt werden.
     */
    @Then("the user should see the related fields in Upload page")
    public void theUserShouldSeeRelatedFieldsForUploadPage() {
        Assertions.assertTrue(dokumentHochladenPage.isDisplayedAllRequiredFields(), "Error message verification failed for related fields for Upload page");
    }

    /**
     * Überprüft, ob alle erforderlichen Felder auf der Abschlussseite angezeigt werden.
     * Diese Methode stellt sicher, dass alle notwendigen Elemente auf der finalen Seite korrekt dargestellt werden.
     */
    @Then("the user should see the related fields in Final page")
    public void theUserShouldSeeRelatedFieldsForFinalPage() {
        Assertions.assertTrue(finalPage.isDisplayedAllRequiredFields(), "Error message verification failed for related fields for Final page");
    }

    //======================in order to get options visuality status through javascript with displayValue=========================

    /**
     * Überprüft, ob die Option korrekt ausgewählt wurde, mittels JavaScript.
     * Diese Methode nutzt JavaScript, um den Auswahlstatus der Optionen zu überprüfen und zu verifizieren.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
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
} 
