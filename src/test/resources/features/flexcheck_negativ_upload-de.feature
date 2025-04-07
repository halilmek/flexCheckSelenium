# language: de
Funktionalität: FlexCheck Rechner Funktionalität
  Als potenzieller Kunde
  Möchte ich den Finanzierungsrechner verwenden und eine Datei nicht korrekt hochladen
  Um die Fehlermeldung für den Datei-Upload zu sehen

    Grundlage:
        Gegeben sei der Benutzer ist auf der FlexCheck Rechner Seite
    #@wip
    Szenario: Überprüfen der Upload-Erfolgsmeldung im negativen Test
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
        Und der Benutzer wählt "randomOption" Option
        Und der Benutzer klickt auf Angebote anfordern
        Und der Benutzer lädt eine Datei nicht korrekt hoch
        Und der Benutzer klickt auf Weiter in der DokumentHochladung Seite
        Und der Benutzer schreibt eine Nachricht
        Dann sollte der Benutzer die Fehlermeldung für den Datei-Upload sehen nach dem klicken auf Angebotsanforderung
        Und der Benutzer sollte die Erfolgsmeldung sehen 