# language: de
Funktionalität: FlexCheck Rechner Funktionalität
  Als potenzieller Kunde
  Möchte ich den Finanzierungsrechner verwenden
  Um meine Finanzierungsoptionen zu prüfen

  Grundlage:
    Gegeben sei der Benutzer ist auf der FlexCheck Rechner Seite

  #@wip
  Szenariogrundriss: Finanzierung mit verschiedenen Verwendungszwecken berechnen
    Wenn der Benutzer "<usage_purpose>" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "randomOption" Option
    Und der Benutzer klickt auf Angebote anfordern
    Und der Benutzer lädt seine ID hoch
    Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Und der Benutzer schreibt eine Nachricht
    Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    Dann sollte der Benutzer die Erfolgsmeldung sehen

    Beispiele:
      | usage_purpose           |
      | Kauf und Modernisierung|
      | Kauf                   |
      | Kauf Neubau           |
      | Kauf und Erweiterung  |
      | Modernisierung        |
      | Neubau                |
      | Umschuldung           |
      | sonstige Verwendung   |

#@wip
Szenariogrundriss: Finanzierung mit verschiedenen Immobilientypen berechnen
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "<property_type>" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "randomOption" Option
    Und der Benutzer klickt auf Angebote anfordern
    Und der Benutzer lädt seine ID hoch
    Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Und der Benutzer schreibt eine Nachricht
    Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    Dann sollte der Benutzer die Erfolgsmeldung sehen

    Beispiele:
      | property_type        |
      | Einfamilienhaus     |
      | Reihenendhaus       |
      | Reihenmittelhaus    |
      | Doppelhaushälfte    |
      | Zweifamilienhaus    |
      | Baugrundstück       |
      | Eigentumswohnung    |


  #@wip
  Szenario: Finanzierung mit monatlicher Zahlungsart berechnen
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Rückzahlungsart "monatliche Rate" auswählt
    Und der Benutzer monatliche Zahlung "randomMonthlyPayment" eingibt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "randomOption" Option
    Und der Benutzer klickt auf Angebote anfordern
    Und der Benutzer lädt seine ID hoch
    Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Und der Benutzer schreibt eine Nachricht
    Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    Dann sollte der Benutzer die Erfolgsmeldung sehen


  #@wip
  Szenariogrundriss: Finanzierung mit Gesamtlaufzeit Zahlungsart berechnen
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Rückzahlungsart "Gesamtlaufzeit" auswählt
    Und der Benutzer Gesamtlaufzeit in Jahren "<total_term_in_years>" eingibt
    Und der Benutzer Gesamtlaufzeit in Monaten "<total_term_in_months>" eingibt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "randomOption" Option
    Und der Benutzer klickt auf Angebote anfordern
    Und der Benutzer lädt seine ID hoch
    Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Und der Benutzer schreibt eine Nachricht
    Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    Dann sollte der Benutzer die Erfolgsmeldung sehen

    Beispiele:
      | total_term_in_years | total_term_in_months |
      | 25                  | 0                   |
      | 0                  | 240                   |




  #@wip
  Szenariogrundriss: Alle Optionen nacheinander auswählen
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "<an>" Option
    Und der Benutzer klickt auf Angebote anfordern
    Und der Benutzer lädt seine ID hoch
    Dann prüft der Benutzer, ob die Datei hochgeladen wurde
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Und der Benutzer schreibt eine Nachricht
    Und der Benutzer klickt auf den Button "jetzt kostenlos und unverbindlich Ihr Angebot anfordern" in der Final Seite
    Dann sollte der Benutzer die Erfolgsmeldung sehen

    Beispiele:
      | an |
      | 1  |
      | 2  |
      | 3  |
      | 4  |



  #@wip
  Szenario: Überprüfen, ob Seitenelemente wie erwartet geladen wurden
    Dann sollte der Benutzer entsprechende Felder in der Angaben zum Objekt Seite sehen
    Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
    Und der Benutzer Postleitzahl "randomPostalCode" eingibt
    Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
    Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
    Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
    Dann sollte der Benutzer entsprechende Felder in der Angaben zum Finanzierungswunsch Seite sehen
    Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
    Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
    Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
    Und der Benutzer das erste Auszahlungsdatum eingibt
    Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
    Dann sollte der Benutzer die Berechnungsergebnisse sehen
    Und der Benutzer wählt "randomOption" Option
    Und der Benutzer klickt auf Angebote anfordern
    Dann sollte der Benutzer die entsprechenden Felder in der Upload Seite sehen
    Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
    Dann sollte der Benutzer die entsprechenden Felder in der Final Seite sehen 