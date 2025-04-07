# language: de
Funktionalität: FlexCheck Rechner Funktionalität
  Als potenzieller Kunde
  Möchte ich den Finanzierungsrechner verwenden und die Details prüfen
  Um die Details zu sehen, die ich im Rechner eingegeben habe

    Grundlage:
        Gegeben sei der Benutzer ist auf der FlexCheck Rechner Seite

    #@wip
    Szenario: Überprüfen der Datenpersistenz beim Zurücknavigieren durch Dialoge mit Tilgung in % als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
      Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Und der Benutzer klickt auf den Zurück-Button, um zur "Wählen Sie Ihre gewünschte Kondition" Seite zurückzukehren
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Finanzierungswunsch Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte für Tilgung in % als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Objekt Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat


    #@wip #Failed=Es gibt keinen Zurück-Button auf der monatliche Rate Seite
    Szenario: Überprüfen der Datenpersistenz beim Zurücknavigieren durch Dialoge mit monatliche Rate als Zahlungsart
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
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Und der Benutzer klickt auf den Zurück-Button, um zur "Wählen Sie Ihre gewünschte Kondition" Seite zurückzukehren
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Finanzierungswunsch Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte für monatliche Rate als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Objekt Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat


    #@wip #Failed=Es gibt keinen Zurück-Button auf der Gesamtlaufzeit Seite
    Szenario: Überprüfen der Datenpersistenz beim Zurücknavigieren durch Dialoge mit Gesamtlaufzeit als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "Gesamtlaufzeit" auswählt
      Und der Benutzer Gesamtlaufzeit in Jahren "randomJahre" eingibt
      Und der Benutzer Gesamtlaufzeit in Monaten "randomMonate" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf den Weiter-Button, um zur letzten Seite zurückzukehren
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Upload-Seite zurückzukehren
      Und der Benutzer klickt auf den Zurück-Button, um zur "Wählen Sie Ihre gewünschte Kondition" Seite zurückzukehren
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Finanzierungswunsch Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte für Gesamtlaufzeit als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer klickt auf den Zurück-Button, um zur Angaben zum Objekt Seite zurückzukehren
      Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat


    #@wip #Failed => Wenn die Seite aktualisiert wird, bleiben die Daten nicht erhalten und der Benutzer wird zur ersten Seite zurückgeleitet
    Szenario: Überprüfen der Datenpersistenz durch Aktualisieren der Seite mit Tilgung in % als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      #Und der Benutzer die Seite aktualisiert
      #Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "Tilgung in %" auswählt
      Und der Benutzer Tilgungsprozentsatz "randomRepaymentPercentage" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die gleichen Werte für Tilgung in % als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat


    #@wip #Failed => Wenn die Seite aktualisiert wird, bleiben die Daten nicht erhalten und der Benutzer wird zur ersten Seite zurückgeleitet
    Szenario: Überprüfen der Datenpersistenz durch Aktualisieren der Seite mit monatliche Rate als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      #Und der Benutzer die Seite aktualisiert
      #Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "monatliche Rate" auswählt
      Und der Benutzer monatliche Zahlung "randomMonthlyPayment" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die gleichen Werte für monatliche Rate als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat


    #@wip #Failed => Wenn die Seite aktualisiert wird, bleiben die Daten nicht erhalten und der Benutzer wird zur ersten Seite zurückgeleitet
    Szenario: Überprüfen der Datenpersistenz durch Aktualisieren der Seite mit Gesamtlaufzeit als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      #Und der Benutzer die Seite aktualisiert
      #Dann sollte der Benutzer die gleichen Werte sehen, die er zuvor auf der Objekt-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "Gesamtlaufzeit" auswählt
      Und der Benutzer Gesamtlaufzeit in Jahren "randomJahre" eingibt
      Und der Benutzer Gesamtlaufzeit in Monaten "randomMonate" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die gleichen Werte für Gesamtlaufzeit als Zahlungsart sehen, die er zuvor auf der Finanzierungswunsch-Seite eingegeben hat
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer sehen, was er auf der Optionsauswahlseite ausgewählt hat
      Und der Benutzer klickt auf Angebote anfordern
      Und der Benutzer lädt seine ID hoch
      #Und der Benutzer prüft, ob die Datei hochgeladen wurde
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer die hochgeladene Datei sehen
      Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
      Und der Benutzer schreibt eine Nachricht
      Und der Benutzer die Seite aktualisiert
      Dann sollte der Benutzer den Text sehen, den er zuvor im Nachrichtenfeld geschrieben hat


    #@wip #TODO: Überprüfen anderer Anzeigewerte für ausgewählte Option auf der Angebotsauswahlseite
    Szenario: Überprüfen der Datenpersistenz beim Zurücknavigieren durch Dialoge mit Gesamtlaufzeit als Zahlungsart
      Wenn der Benutzer "randomPurpose" als Verwendungszweck auswählt
      Und der Benutzer Postleitzahl "randomPostalCode" eingibt
      Und der Benutzer Kaufpreis "randomPurchasePrice" eingibt
      Und der Benutzer "randomPropertyType" als Immobilientyp auswählt
      Und der Benutzer auf Weiter in der Angaben zum Objekt Seite klickt
      Und der Benutzer gewünschten Darlehensbetrag "randomLoanAmount" eingibt
      Und der Benutzer Rückzahlungsart "Gesamtlaufzeit" auswählt
      Und der Benutzer Gesamtlaufzeit in Jahren "randomJahre" eingibt
      Und der Benutzer Gesamtlaufzeit in Monaten "randomMonate" eingibt
      Und der Benutzer das erste Auszahlungsdatum eingibt
      Und der Benutzer auf Weiter in der Angaben zum Finanzierungswunsch Seite klickt
      Und der Benutzer sollte die Berechnungsergebnisse sehen
      Und der Benutzer wählt "randomOption" Option
      Dann sollte der Benutzer überprüfen, dass die Option ausgewählt ist 