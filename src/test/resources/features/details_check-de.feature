# language: de
Funktionalität: FlexCheck Rechner Funktionalität
  Als potenzieller Kunde
  Möchte ich den Finanzierungsrechner verwenden und die Details prüfen
  Um die Details zu sehen, die ich im Rechner eingegeben habe

    Grundlage:
        Gegeben sei der Benutzer ist auf der FlexCheck Rechner Seite

    #@wip
    Szenario: Überprüfen der Details in Modalfenstern auf der Angebotsauswahl-Seite basierend auf Tilgung in % Zahlungsart
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
        #Und der Benutzer sollte die Berechnungsergebnisse sehen
        #Und der Benutzer klickt auf den Button Details anzeigen
        Dann sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Tilgung in % eingegeben hat
        #Und der Benutzer wählt "randomOption" Option


    #@wip
    Szenario: Überprüfen der Details in Modalfenstern auf der Angebotsauswahl-Seite basierend auf Monatliche Rate Zahlungsart
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
        #Und der Benutzer sollte die Berechnungsergebnisse sehen
        Und der Benutzer klickt auf den Button Details anzeigen
        Dann sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Monatliche Rate eingegeben hat
        Und der Benutzer wählt "randomOption" Option


    #@wip
    Szenario: Überprüfen der Details in Modalfenstern auf der Angebotsauswahl-Seite basierend auf Gesamtlaufzeit Zahlungsart
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
        #Und der Benutzer sollte die Berechnungsergebnisse sehen
        Und der Benutzer klickt auf den Button Details anzeigen
        Dann sollte der Benutzer die Werte sehen, die er im Rechner basierend auf der Zahlungsart Gesamtlaufzeit eingegeben hat
        Und der Benutzer wählt "randomOption" Option 