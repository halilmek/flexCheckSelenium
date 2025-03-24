Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator
  So that I can check my financing options

    Background:
        Given the user is on the FlexCheck calculator page
    @negative
    Scenario: Calculate financing with different usage purposes
        When the user selects "Kauf" as usage purpose
        And the user enters postal code "string"
        And the user enters purchase price "string"
        And the user selects "Einfamilienhaus" as property type
        And the user clicks on Weiter in Angaben zum Objekt page
        And the user enters desired loan amount "string"
        And the user enters repayment percentage "string"
        And the user selects repayment type "Tilgung in %"
        And the user enters first payout date
        And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
        Then the user should see the calculation results
        And the user selects an option
        And the user clicks on Angebote anfordern
        And the user uploads a file not correctly
        And the user clicks on Weiter in DokumentHochladung page
        And the user writes a message
        And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
        Then the user should see the error message for file upload
        And the user should see the success message