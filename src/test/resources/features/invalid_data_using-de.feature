# language: de
Funktionalität: FlexCheck Rechner Funktionalität
  Als potenzieller Kunde
  Darf ich nur ungültige Daten eingeben

  Grundlage:
    Gegeben sei der Benutzer ist auf der FlexCheck Rechner Seite

  #@wip
  Szenario: Eingabe ungültiger Daten zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Tilgung in % als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "invalidPostalCode  " eingibt
    Und der Benutzer Kaufpreis "invalidPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Dann sollte der Benutzer die Fehlermeldung für Postleitzahl und Kaufpreis sehen
    # Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    # Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    # Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    # Und der Benutzer das erste Auszahlungsdatum eingibt
    # Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    # Dann sollte der Benutzer die Berechnungsergebnisse sehen
    # Und der Benutzer wählt "randomOption" Option
    # Und der Benutzer klickt auf Angebote anfordern
    # Und der Benutzer lädt seine ID hoch
    # Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    # Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    # Und der Benutzer schreibt eine Nachricht
    # Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    # Dann sollte der Benutzer die Erfolgsmeldung sehen

    #@wip
  Szenario: Eingabe ungültiger Daten zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Tilgung in % als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "invalidLoanAmount" eingibt
    Und der Benutzer Tilgungsprozentsatz "invalidRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Fehlermeldung für Darlehensbetrag und Tilgungsprozentsatz sehen

    #@wip
  Szenario: Eingabe ungültiger Daten zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Tilgung in % als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "1000001" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Fehlermeldung für Darlehensbetrag sehen, da der Darlehensbetrag größer als 1000000 ist


    #@wip
  Szenario: Eingabe eines größeren Darlehensbetrags als Kaufpreis zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Tilgung in % als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "999999" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer nicht die Berechnungsergebnisse sehen, um zur nächsten Seite fortzufahren


    #@wip
  Szenario: Eingabe ungültiger Daten zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Monatliche Rate als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Rückzahlungsart "monatliche Rate" auswählt
    Und der Benutzer monatliche Zahlung "invalidMonthlyPayment" eingibt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Fehlermeldung für monatliche Zahlung sehen


    #@wip
  Szenario: Eingabe ungültiger Daten zum Ausfüllen des Formulars in der Angaben zum Objekt Seite mit Gesamtlaufzeit als Zahlungsart
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Rückzahlungsart "Gesamtlaufzeit" auswählt
    Und der Benutzer Gesamtlaufzeit in Jahren "invalidTotalTermInYears" eingibt
    Und der Benutzer Gesamtlaufzeit in Monaten "invalidTotalTermInMonths" eingibt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Fehlermeldung für Gesamtlaufzeit in Jahren und Gesamtlaufzeit in Monaten sehen 