Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator and upload a file not correctly
  So that I can see the error message for file upload

    Background:
        Given the user is on the FlexCheck calculator page
    #@wip
    Scenario: Checking upload success message in negative test
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
        Then the user should see the calculation results
        And the user selects "randomOption" option
        And the user clicks on Angebote anfordern
        And the user uploads a file not correctly
        And the user clicks on Weiter in DokumentHochladung page
        And the user writes a message
        And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
        Then the user should see the error message for file upload
        And the user should see the success message