Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator and check the details
  So that I can see the details, as i entered in the calculator

    Background:
        Given the user is on the FlexCheck calculator page

    #@wip
    Scenario: Checking the details in modals in offer selection page based on Tilgung in % payment type
        When the user selects "randomPurpose" as usage purpose
        And the user enters postal code "randomPostalCode"
        And the user enters purchase price "randomPurchasePrice"
        And the user selects "randomPropertyType" as property type
        And the user clicks on Weiter in Angaben zum Objekt page
        And the user enters desired loan amount "randomLoanAmount"
        And the user enters repayment percentage "randomRepaymentPercentage"
        And the user selects repayment type "Tilgung in %"
        And the user enters first payout date
        And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
        And the user should see the calculation results
        And the user clicks on Details anzeigen button
        Then the user should see values, as the user entered in the calculator based on Tilgung in % payment type
        And the user selects an option
        #And the user clicks on Angebote anfordern
        #And the user uploads id
        #And the user clicks on Weiter in DokumentHochladung page
        #And the user writes a message
        #And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
        #And the user should see the success message
        #Then the user should see values, as the user entered in the calculator as an api response

    #@wip
    Scenario: Checking the details in modals in offer selection page based on Monatliche Rate payment type
        When the user selects "randomPurpose" as usage purpose
        And the user enters postal code "randomPostalCode"
        And the user enters purchase price "randomPurchasePrice"
        And the user selects "randomPropertyType" as property type
        And the user clicks on Weiter in Angaben zum Objekt page
        And the user enters desired loan amount "randomLoanAmount"
        And the user selects repayment type "monatliche Rate"
        And the user enters monthly payment "randomMonthlyPayment"
        And the user enters first payout date
        And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
        And the user should see the calculation results
        And the user clicks on Details anzeigen button
        Then the user should see values, as the user entered in the calculator based on Monatliche Rate payment type
        And the user selects an option


    #@wip
    Scenario: Checking the details in modals in offer selection page based on Gesamtlaufzeit payment type
        When the user selects "randomPurpose" as usage purpose
        And the user enters postal code "randomPostalCode"
        And the user enters purchase price "randomPurchasePrice"
        And the user selects "randomPropertyType" as property type
        And the user clicks on Weiter in Angaben zum Objekt page
        And the user enters desired loan amount "randomLoanAmount"
        And the user selects repayment type "Gesamtlaufzeit"
        And the user enters total term in years "randomJahre"
        And the user enters total term in months "randomMonate"
        And the user enters first payout date
        And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
        And the user should see the calculation results
        And the user clicks on Details anzeigen button
        Then the user should see values, as the user entered in the calculator based on Gesamtlaufzeit payment type
        And the user selects an option