package com.test.stepdefinitions;

import com.test.pages.*;
import com.test.utilities.ConfigurationReader;
import com.test.utilities.Driver;
import com.test.utilities.TestDataGenerator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
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

public class KreditberechnungSteps_de {
    private static final Logger logger = LoggerFactory.getLogger(KreditberechnungSteps_de.class);
    
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
    

    /**
     * Navigiert zur FlexCheck-Rechner-Startseite.
     * Diese Methode öffnet die Hauptseite des FLEXCHECK Rechners im Browser.
     */
    @Given("der Benutzer ist auf der FlexCheck Rechner Seite")
    public void derBenutzerIstAufDerFlexCheckRechnerSeite() {
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
    @When("der Benutzer {string} als Verwendungszweck auswählt")
    public void derBenutzerWaehltAlsVerwendungszweck(String purpose) {
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
    @When("der Benutzer Postleitzahl {string} eingibt")
    public void derBenutzerGibtPostleitzahlEin(String plz) {
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
    @When("der Benutzer Kaufpreis {string} eingibt")
    public void derBenutzerGibtKaufpreisEin(String price) {
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
    @When("der Benutzer {string} als Immobilientyp auswählt")
    public void derBenutzerWaehltAlsImmobilientyp(String type) {
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
    @When("der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt")
    public void derBenutzerKlicktAufWeiterInDerAngabenZumObjektSeite() throws InterruptedException {
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
    @When("der Benutzer gewünschten Darlehensbetrag {string} eingibt")
    public void derBenutzerGibtGewünschtenDarlehensbetragEin(String amount) {
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
    @When("der Benutzer Tilgungsprozentsatz {string} eingibt")
    public void derBenutzerGibtTilgungsprozentsatzEin(String percentage) throws InterruptedException {
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
    @When("der Benutzer Rückzahlungsart {string} auswählt")
    public void derBenutzerWaehltRueckzahlungsart(String type) {
        finanzierungPage.selectRueckzahlungsArt(type);
    }

    /**
     * Gibt das Datum der ersten Auszahlung ein.
     * Diese Methode setzt das Auszahlungsdatum für den Kredit.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("der Benutzer das erste Auszahlungsdatum eingibt")
    public void derBenutzerGibtDasErsteAuszahlungsdatumEin() throws InterruptedException {
        finanzierungPage.enterAuszahlungsdatum();
    }

    /**
     * Klickt auf den Weiter-Button auf der Seite "Angaben zum Finanzierungswunsch".
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur nächsten Seite.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @When("der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt")
    public void derBenutzerKlicktAufWeiterInDerAngabenZumFinanzierungswunschSeite() throws InterruptedException {
        finanzierungPage.clickWeiterInAngabenZumFinanzierungswunschPage();
    }

    /**
     * Gibt die gewünschte monatliche Rate ein.
     * Diese Methode setzt die angegebene monatliche Rate oder einen zufälligen Wert,
     * wenn "randomMonthlyPayment" angegeben wurde.
     * 
     * @param amount Die einzugebende monatliche Rate oder "randomMonthlyPayment" für einen Zufallswert
     */
    @When("der Benutzer monatliche Zahlung {string} eingibt")
    public void derBenutzerGibtMonatlicheZahlungEin(String amount) {
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
    @When("der Benutzer Gesamtlaufzeit in Jahren {string} eingibt")
    public void derBenutzerGibtGesamtlaufzeitInJahrenEin(String years) {
        if (years.equalsIgnoreCase("randomJahre")) {
            years = randomJahreFürGesamtlaufzeit;
        }
        finanzierungPage.enterTotalTermYears(years);
    }

    /**
     * Gibt die Gesamtlaufzeit in Monaten ein.
     * Diese Methode setzt die angegebene Laufzeit in Monaten oder einen zufälligen Wert,
     * wenn "randomMonate" angegeben wurde.
     * 
     * @param months Die einzugebende Laufzeit in Monaten oder "randomMonate" für einen Zufallswert
     */
    @When("der Benutzer Gesamtlaufzeit in Monaten {string} eingibt")
    public void derBenutzerGibtGesamtlaufzeitInMonatenEin(String months) {
        if (months.equalsIgnoreCase("randomMonate")) {
            months = randomMonateFürGesamtlaufzeit;
        }
        finanzierungPage.enterTotalTermMonths(months);
    }

    //==========================BerechnungsergebnisPage============================

    /**
     * Prüft, ob die Berechnungsergebnisse angezeigt werden.
     * Diese Methode verifiziert, dass die Ergebnisseite erfolgreich geladen wurde.
     */
    @Then("sollte der Benutzer die Berechnungsergebnisse sehen")
    public void sollteDeBenutzerDieBerechnungsergebnisseSehen() {
        Assertions.assertTrue(berechnungsergebnisPage.waitForResultsToLoad(), "Berechnungsergebnisse nicht geladen");
    }

    /**
     * Wählt eine Option aus den Berechnungsergebnissen aus.
     * Diese Methode wählt automatisch eine der angebotenen Optionen.
     * 
     * @throws Exception Falls ein Fehler bei der Auswahl auftritt
     */
    @When("der Benutzer wählt {string} Option")
    public void derBenutzerWähltOption(String optionNumber) {
        if (optionNumber.equalsIgnoreCase("randomOption")) {
            optionNumber = randomOption;
        }
        offerSelectionPage.selectOption(optionNumber);
    }

    /**
     * Klickt auf den Button "Angebote anfordern".
     * Diese Methode führt den Klick auf den Button aus und navigiert zur nächsten Seite.
     */
    @When("der Benutzer klickt auf Angebote anfordern")
    public void derBenutzerKlicktAufAngeboteAnfordern() {
        offerSelectionPage.clickAngebotAnfordern();
    }

    /**
     * Klickt auf den Button "Details anzeigen".
     * Diese Methode öffnet die Detailansicht der ausgewählten Option.
     */
    @When("der Benutzer klickt auf den Button Details anzeigen")
    public void derBenutzerKlicktAufDenButtonDetailsAnzeigen() {
        // Details button klicken
        offerSelectionPage.clickDetailsButton();
        
        // Werte aus dem Modal ausgeben
        offerSelectionPage.printModalValues();
    }

    /**
     * Verifiziert die angezeigten Werte basierend auf der Tilgung in % Zahlungsart.
     * Diese Methode prüft, ob die angezeigten Werte den eingegebenen Werten entsprechen.
     */
    @Then("sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Tilgung in % eingegeben hat")
    public void sollteDeBenutzerDieWerteBasierendAufTilgungInProzentSehen() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForTilgungPaymentTypeInFourthModal(randomPurchasePrice, randomLoanAmount, randomPurpose), "Modals enthälen nicht, was der Benutzer eingegeben hat!");
    }

    /**
     * Verifiziert die angezeigten Werte basierend auf der Monatliche Rate Zahlungsart.
     * Diese Methode prüft, ob die angezeigten Werte den eingegebenen Werten entsprechen.
     */
    @Then("sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Monatliche Rate eingegeben hat")
    public void sollteDeBenutzerDieWerteBasierendAufMonatlicherRateSehen() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForMonatlicheRatePaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomMonthlyPayment), "Modals enthalten nicht, was der Benutzer eingegeben hat!");
    }

    /**
     * Verifiziert die angezeigten Werte basierend auf der Gesamtlaufzeit Zahlungsart.
     * Diese Methode prüft, ob die angezeigten Werte den eingegebenen Werten entsprechen.
     */
    @Then("sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Gesamtlaufzeit eingegeben hat")
    public void sollteDeBenutzerDieWerteBasierendAufGesamtlaufzeitSehen() {

        Assertions.assertTrue(offerSelectionPage.verifyingCalculatorValuesForGesamtbetragPaymentType(randomPurchasePrice, randomLoanAmount, randomPurpose, randomJahreFürGesamtlaufzeit, randomMonateFürGesamtlaufzeit), "Modals enthalten nicht, was der Benutzer eingegeben hat!");
    }

    //==========================DokumentHochladenPage============================

    /**
     * Lädt eine ID-Datei hoch.
     * Diese Methode lädt ein Dokument als Identitätsnachweis hoch.
     */
    @When("der Benutzer lädt seine ID hoch")
    public void derBenutzerLaedtSeineIDHoch() {
        // Use an actual file path that exists in the test resources
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";
        dokumentHochladenPage.dragAndDropFile2(filePath);
    }

    /**
     * Prüft, ob die Datei erfolgreich hochgeladen wurde.
     * Diese Methode verifiziert, dass der Upload-Vorgang erfolgreich abgeschlossen wurde.
     */
    @Then("prüft der Benutzer, ob die Datei hochgeladen wurde")
    public void prueftDeBenutzerObDieDateiHochgeladenWurde() {
        // Check if file count > 0 to verify upload
        Assertions.assertTrue(dokumentHochladenPage.verifyFileUpload(), 
                "Document was not uploaded successfully");
    }

    /**
     * Klickt auf den Weiter-Button auf der Seite "DokumentHochladung".
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur nächsten Seite.
     */
    @And("der Benutzer klickt auf Weiter in der DokumentHochladung Seite")
    public void derBenutzerKlicktAufWeiterInDerDokumentHochladungSeite() {
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();
    }

    /**
     * Lädt eine Datei nicht korrekt hoch.
     * Diese Methode simuliert einen fehlerhaften Upload-Vorgang für Testzwecke.
     */
    @When("der Benutzer lädt eine Datei nicht korrekt hoch")
    public void derBenutzerLaedtEineDateiNichtKorrektHoch() {

        dokumentHochladenPage.uploadIdForNegativeTest();
    }

    //==========================FinalPage============================

    /**
     * Schreibt eine Nachricht im Freitextfeld.
     * Diese Methode gibt einen Beispieltext in das Nachrichtenfeld ein.
     */
    @When("der Benutzer schreibt eine Nachricht")
    public void derBenutzerSchreibtEineNachricht() {

        finalPage.writeMessage();
    }

    /**
     * Klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern".
     * Diese Methode führt den Klick auf den Button aus und schließt den Bestellvorgang ab.
     */
    @When("der Benutzer klickt auf den Button {string} in der Final Seite")
    public void derBenutzerKlicktAufDenButtonInDerFinalSeite(String buttonText) {

        finalPage.clickSubmitButton();
        finalPage.isSuccessMessageDisplayedForFileUpload();
    }

    /**
     * Prüft, ob die Erfolgsmeldung angezeigt wird.
     * Diese Methode verifiziert, dass der Bestellvorgang erfolgreich abgeschlossen wurde.
     * 
     * @throws Exception Falls ein Fehler bei der Überprüfung auftritt
     */
    @Then("sollte der Benutzer die Erfolgsmeldung sehen")
    public void sollteDerBenutzerDieErfolgsmeldungSehen() throws Exception {

        Assertions.assertTrue(finalPage.isSuccessMessageDisplayed(), "Erfolgsmeldung nicht angezeigt");
    }

    /**
     * Prüft, ob die Fehlermeldung für den Datei-Upload angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung für einen fehlerhaften Upload erscheint.
     */
    @Then("sollte der Benutzer die Fehlermeldung für den Datei-Upload sehen nach dem klicken auf Angebotsanforderung")
    public void sollteDerBenutzerDieFehlermeldungFürDenDateiUploadSehenNachDemKlickenAufAngebotsanforderung() {

        finalPage.clickSubmitButton();
        Assertions.assertTrue(finalPage.notSuccessMessageDisplayedForFileUpload(), "Fehlermeldung nicht angezeigt");
    }

    /**
     * Prüft, ob die Erfolgsmeldung angezeigt wird.
     * Diese Methode verifiziert, dass der Bestellvorgang erfolgreich abgeschlossen wurde.
     * 
     * @throws Exception Falls ein Fehler bei der Überprüfung auftritt
     */
    @And("der Benutzer sollte die Erfolgsmeldung sehen")
    public void derBenutzerSollteDieErfolgsmeldungSehen() {
        Assertions.assertTrue(finalPage.isSuccessMessageDisplayed(), "Erfolgsmeldung nicht angezeigt");
    }

    //==========================Data Persistence with Navigation============================

    /**
     * Prüft, ob die hochgeladene Datei angezeigt wird.
     * Diese Methode verifiziert, dass die zuvor hochgeladene Datei nach der Navigation sichtbar ist.
     */
    @Then("sollte der Benutzer die hochgeladene Datei sehen")
    public void sollteDerBenutzerDieHochgeladeneDateiSehen() {
        Assertions.assertTrue(dokumentHochladenPage.verifyFileUpload(),
                "Uploaded file not visible");
    }

    /**
     * Klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren.
     * Diese Methode führt den Klick auf den Weiter-Button aus und navigiert zur letzten Seite.
     */
    @And("der Benutzer klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren")
    public void derBenutzerKlicktAufDenWeiterButtonUmZurLetztenSeiteZurückzukehren() {

        dokumentHochladenPage.verifyFileUpload();
        dokumentHochladenPage.clickingWeiterButtonInDokumentHochladungPage();
    }

    /**
     * Prüft, ob der zuvor eingegebene Text im Nachrichtenfeld angezeigt wird.
     * Diese Methode verifiziert, dass der eingegebene Text nach der Navigation noch vorhanden ist.
     */
    @Then("sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat")
    public void sollteDerBenutzerDenTextSehenDenErZuvorImNachrichtenfeldGeschriebenHat() {

        Assertions.assertTrue(finalPage.getEnteredMessageInFinalPage(), "Nachricht nicht angezeigt");
    }

    /**
     * Klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren.
     * Diese Methode führt den Klick auf den Zurück-Button aus und navigiert zur Upload-Seite.
     */
    @When("der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren")
    public void derBenutzerKlicktAufDenZurückButtonUmZurUploadSeiteZurückzukehren() {

        finalPage.clickBackButtonOnFinalPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur "Wählen Sie Ihre gewünschte Kondition" Seite zurückzukehren.
     * Diese Methode führt den Klick auf den Zurück-Button aus und navigiert zur Konditionsauswahl-Seite.
     */
    @When("der Benutzer klickt auf den Zurück-Button, um zur {string} Seite zurückzukehren")
    public void derBenutzerKlicktAufDenZurückButtonUmZurKonditionsauswahlSeiteZurückzukehren(String pageName) {
        dokumentHochladenPage.clickZurueckButtonInDokumentHochladungPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur Angaben zum Finanzierungswunsch Seite zurückzukehren.
     * Diese Methode führt den Klick auf den Zurück-Button aus und navigiert zur Finanzierungswunsch-Seite.
     */
    @When("der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Finanzierungswunsch Seite zurückzukehren")
    public void derBenutzerKlicktAufDenZurückButtonUmZurFinanzierungswunschSeiteZurückzukehren() {

        offerSelectionPage.clickZurueckButtonInOfferSelectionPage();
    }

    /**
     * Klickt auf den Zurück-Button, um zur Angaben zum Objekt Seite zurückzukehren.
     * Diese Methode führt den Klick auf den Zurück-Button aus und navigiert zur Objekt-Seite.
     */
    @When("der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Objekt Seite zurückzukehren")
    public void derBenutzerKlicktAufDenZurückButtonUmZurObjektSeiteZurückzukehren() {
        finanzierungPage.clickBackButtonInAngabenZumFinanzierungswunschPage();
    }

    /**
     * Prüft, ob die zuvor ausgewählte Option angezeigt wird.
     * Diese Methode verifiziert, dass die ausgewählte Option nach der Navigation noch markiert ist.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @Then("sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat")
    public void sollteDerBenutzerSehenWasErAufDerOptionsauswahlseiteAusgewähltHat() {
        Assertions.assertTrue(offerSelectionPage.verifySelectedOption(randomOption), "Die Option ist nicht ausgewählt!");
    }

    /**
     * Prüft, ob die gleichen Werte für Tilgung in % als Zahlungsart angezeigt werden.
     * Diese Methode verifiziert, dass die eingegebenen Werte nach der Navigation noch vorhanden sind.
     */
    @Then("sollte der Benutzer die gleichen Werte für Tilgung in % als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat")
    public void sollteDerBenutzerDieGleichenWerteFürTilgungInProzentAlsZahlungsartSehen() {
        
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
     * Prüft, ob die gleichen Werte für das Objekt angezeigt werden.
     * Diese Methode verifiziert, dass die eingegebenen Werte nach der Navigation noch vorhanden sind.
     */
    @Then("sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat")
    public void sollteDerBenutzerDieGleichenWerteSehen() {
        logger.info("Verifying all values persistence in Objekt page");

        String expectedUsagePurpose = randomPurpose;
        String actualUsagePurpose = angabenZumObjektPage.getSelectedVerwendungszweck();
        Assertions.assertTrue(expectedUsagePurpose.equalsIgnoreCase(actualUsagePurpose), "Usage purpose does not match the previously entered value");

        String expectedPostalCode = randomPostalCode;
        String actualPostalCode = angabenZumObjektPage.getPlz();
        Assertions.assertTrue(expectedPostalCode.equalsIgnoreCase(actualPostalCode), "Postal code does not match the previously entered value");

        String expectedPurchasePrice = randomPurchasePrice;
        String actualPurchasePrice = angabenZumObjektPage.getKaufpreis().replace(".", "");
        Assertions.assertTrue(expectedPurchasePrice.equalsIgnoreCase(actualPurchasePrice), "Purchase price does not match the previously entered value");

        String expectedPropertyType = randomPropertyType;
        String actualPropertyType = angabenZumObjektPage.getSelectedObjektart();
        Assertions.assertTrue(expectedPropertyType.equalsIgnoreCase(actualPropertyType), "Property type does not match the previously entered value");
    }

    /**
     * Prüft, ob die gleichen Werte für monatliche Rate als Zahlungsart angezeigt werden.
     * Diese Methode verifiziert, dass die eingegebenen Werte nach der Navigation noch vorhanden sind.
     */
    @Then("sollte der Benutzer die gleichen Werte für monatliche Rate als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat")
    public void sollteDerBenutzerDieGleichenWerteFürMonatlicheRateAlsZahlungsartSehen() {
        
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
     * Prüft, ob die gleichen Werte für Gesamtlaufzeit als Zahlungsart angezeigt werden.
     * Diese Methode verifiziert, dass die eingegebenen Werte nach der Navigation noch vorhanden sind.
     */
    @Then("sollte der Benutzer die gleichen Werte für Gesamtlaufzeit als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat")
    public void sollteDerBenutzerDieGleichenWerteFürGesamtlaufzeitAlsZahlungsartSehen() {
        
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
     * Aktualisiert die aktuelle Seite.
     * Diese Methode lädt die aktuelle Seite neu, um die Datenpersistenz zu testen.
     */
    @And("der Benutzer die Seite aktualisiert")
    public void derBenutzerDieSeiteAktualisiert() {
        Driver.getDriver().navigate().refresh();
    }




    //======================Fehler Meldungen=========================
    /**
     * Prüft, ob die Fehlermeldung für Postleitzahl und Kaufpreis angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung für ungültige Eingaben erscheint.
     */
    @Then("sollte der Benutzer die Fehlermeldung für Postleitzahl und Kaufpreis sehen")
    public void sollteDerBenutzerDieFehlermeldungFürPostleitzahlUndKaufpreisSehen() {

        Assertions.assertTrue(angabenZumObjektPage.getErrorMessageForPostalCode(), "Fehlermeldung für Postleitzahl nicht angezeigt");
        Assertions.assertTrue(angabenZumObjektPage.getErrorMessageForPurchasePrice(), "Fehlermeldung für Kaufpreis nicht angezeigt");
    }

    /**
     * Prüft, ob die Fehlermeldung für Darlehensbetrag und Tilgungsprozentsatz angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung für ungültige Eingaben erscheint.
     */
    @Then("sollte der Benutzer die Fehlermeldung für Darlehensbetrag und Tilgungsprozentsatz sehen")
    public void sollteDerBenutzerDieFehlermeldungFürDarlehensbetragUndTilgungsprozentsatzSehen() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountAndRepaymentPercentage(), "Fehlermeldung für Darlehensbetrag und Tilgungsprozentsatz nicht angezeigt");
    }

    /**
     * Prüft, ob die Fehlermeldung für einen zu großen Darlehensbetrag angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung erscheint, wenn der Betrag > 1.000.000 ist.
     */
    @Then("sollte der Benutzer die Fehlermeldung für Darlehensbetrag sehen, da der Darlehensbetrag größer als 1000000 ist")
    public void sollteDerBenutzerDieFehlermeldungFürDarlehensbetragSehenDaDerDarlehensbetragGrößerAls1000000Ist() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountGreaterThan1000000(), "Fehlermeldung für Darlehensbetrag nicht angezeigt");
    }

    /**
     * Prüft, ob die Berechnungsergebnisse nicht angezeigt werden, wenn ungültige Daten eingegeben wurden.
     * Diese Methode verifiziert, dass bei fehlerhaften Eingaben keine Ergebnisse erscheinen.
     */
    @Then("sollte der Benutzer nicht die Berechnungsergebnisse sehen, um zur nächsten Seite fortzufahren")
    public void sollteDerBenutzerNichtDieBerechnungsergebnisseSehenUmZurNächstenSeiteFortzufahren() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForLoanAmountGreaterThanPurchasePrice(), "Fehler Meldung für Darlehensbetrag größer als Kaufpreis nicht angezeigt");
    }

    /**
     * Prüft, ob die Fehlermeldung für monatliche Zahlung angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung für ungültige Eingaben erscheint.
     */
    @Then("sollte der Benutzer die Fehlermeldung für monatliche Zahlung sehen")
    public void sollteDerBenutzerDieFehlermeldungFürMonatlicheZahlungSehen() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForMonthlyPayment(), "Fehlermeldung für monatliche Zahlung nicht angezeigt");
    }

    /**
     * Prüft, ob die Fehlermeldung für Gesamtlaufzeit angezeigt wird.
     * Diese Methode verifiziert, dass eine Fehlermeldung für ungültige Eingaben erscheint.
     */
    @Then("sollte der Benutzer die Fehlermeldung für Gesamtlaufzeit in Jahren und Gesamtlaufzeit in Monaten sehen")
    public void sollteDerBenutzerDieFehlermeldungFürGesamtlaufzeitInJahrenUndGesamtlaufzeitInMonatenSehen() {
        Assertions.assertTrue(finanzierungPage.getErrorMessageForTotalTermInYearsAndTotalTermInMonths(), "Fehlermeldung für Gesamtlaufzeit in Jahren und Gesamtlaufzeit in Monaten nicht angezeigt");
    }

    /**
     * Prüft, ob die relevanten Felder auf der Angaben zum Objekt Seite angezeigt werden.
     * Diese Methode verifiziert, dass alle erforderlichen Eingabefelder vorhanden sind.
     */
    @Then("sollte der Benutzer entsprechende Felder in der Angaben zum Objekt Seite sehen")
    public void sollteDerBenutzerEntsprechendeFelderInDerAngabenZumObjektSeiteSehen() {
        Assertions.assertTrue(angabenZumObjektPage.isDisplayedAllRequiredFields(), "Fehler Meldung für verwandte Felder für Angaben zum Objekt Seite nicht angezeigt");
    }

    /**
     * Prüft, ob die relevanten Felder auf der Angaben zum Finanzierungswunsch Seite angezeigt werden.
     * Diese Methode verifiziert, dass alle erforderlichen Eingabefelder vorhanden sind.
     */
    @Then("sollte der Benutzer entsprechende Felder in der Angaben zum Finanzierungswunsch Seite sehen")
    public void sollteDerBenutzerEntsprechendeFelderInDerAngabenZumFinanzierungswunschSeiteSehen() {

        Assertions.assertTrue(finanzierungPage.isDisplayedAllRequiredFields(), "Fehler Meldung für verwandte Felder für Angaben zum Finanzierungswunsch Seite nicht angezeigt");
    }

    /**
     * Prüft, ob die relevanten Felder auf der Upload Seite angezeigt werden.
     * Diese Methode verifiziert, dass alle erforderlichen Eingabefelder vorhanden sind.
     */
    @Then("sollte der Benutzer die entsprechenden Felder in der Upload Seite sehen")
    public void sollteDerBenutzerDieEntsprechendenFelderInDerUploadSeiteSehen() {
        Assertions.assertTrue(dokumentHochladenPage.isDisplayedAllRequiredFields(), "Fehler Meldung für verwandte Felder für Upload Seite nicht angezeigt");
    }

    /**
     * Prüft, ob die relevanten Felder auf der Final Seite angezeigt werden.
     * Diese Methode verifiziert, dass alle erforderlichen Eingabefelder vorhanden sind.
     */
    @Then("sollte der Benutzer die entsprechenden Felder in der Final Seite sehen")
    public void sollteDerBenutzerDieEntsprechendenFelderInDerFinalSeiteSehen() {
        Assertions.assertTrue(finalPage.isDisplayedAllRequiredFields(), "Fehler Meldung für verwandte Felder für Final Seite nicht angezeigt");
    }

    /**
     * Prüft, ob die ausgewählte Option markiert ist.
     * Diese Methode verifiziert, dass die zuvor ausgewählte Option korrekt markiert wurde.
     * 
     * @throws InterruptedException Falls die Thread-Ausführung unterbrochen wird
     */
    @Then("sollte der Benutzer überprüfen, dass die Option ausgewählt ist")
    public void sollteDerBenutzerÜberprüfenDassDieOptionAusgewähltIst() throws InterruptedException {
        Assertions.assertTrue(offerSelectionPage.verifySelectedOption(randomOption), "Option is not selected");
    }
} 