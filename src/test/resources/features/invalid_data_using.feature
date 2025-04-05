Feature: FlexCheck Calculator Functionality
  As a potential customer
  I am only allowed to enter invalid data

  Background:
    Given the user is on the FlexCheck calculator page

  #@wip
  Scenario: Entering invalid data to fullfill the form in Angaben zum Objekt page with Tilgung in % as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "invalidPostalCode  "
    And the user enters purchase price "invalidPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    Then the user should see the error message for postal code and purchase price
    # And the user enters desired loan amount "randomLoanAmount"
    # And the user enters repayment percentage "randomRepaymentPercentage"
    # And the user selects repayment type "Tilgung in %"
    # And the user enters first payout date
    # And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    # Then the user should see the calculation results
    # And the user selects "randomOption" option
    # And the user clicks on Angebote anfordern
    # And the user uploads id
    # Then the user checks, whether the file was uploaded
    # And the user clicks on Weiter in DokumentHochladung page
    # And the user writes a message
    # And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    # Then the user should see the success message

    #@wip
  Scenario: Entering invalid data to fullfill the form in Angaben zum Objekt page with Tilgung in % as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "invalidLoanAmount"
    And the user enters repayment percentage "invalidRepaymentPercentage"
    And the user selects repayment type "Tilgung in %"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the error message for loan amount and repayment percentage

    #@wip
  Scenario: Entering invalid data to fullfill the form in Angaben zum Objekt page with Tilgung in % as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "1000001"
    And the user enters repayment percentage "randomRepaymentPercentage"
    And the user selects repayment type "Tilgung in %"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the error message for loan amount, as the loan amount is greater than 1000000


    #@wip
  Scenario: Entering bigger loan value than purchase price to fullfill the form in Angaben zum Objekt page with Tilgung in % as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "999999"
    And the user enters repayment percentage "randomRepaymentPercentage"
    And the user selects repayment type "Tilgung in %"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should not see the calculation results to proceed to next page


    #@wip
  Scenario: Entering invalid data to fullfill the form in Angaben zum Objekt page with Monatliche Rate as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "randomLoanAmount"
    And the user selects repayment type "monatliche Rate"
    And the user enters monthly payment "invalidMonthlyPayment"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the error message for monthly payment


    #@wip
  Scenario: Entering invalid data to fullfill the form in Angaben zum Objekt page with Gesamtlaufzeit as payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "randomLoanAmount"
    And the user selects repayment type "Gesamtlaufzeit"
    And the user enters total term in years "invalidTotalTermInYears"
    And the user enters total term in months "invalidTotalTermInMonths"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the error message for total term in years and total term in months