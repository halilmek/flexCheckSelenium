Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator and check the details
  So that I can see the details, as i entered in the calculator

    Background:
        Given the user is on the FlexCheck calculator page

@wip
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
        And the user should see the calculation results
        And the user clicks on Details anzeigen button
        Then the user should see values, as the user entered in the calculator
        And the user selects an option
        #And the user clicks on Angebote anfordern
        #And the user uploads id
        #And the user clicks on Weiter in DokumentHochladung page
        #And the user writes a message
        #And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
        #And the user should see the success message
        #Then the user should see values, as the user entered in the calculator as an api response