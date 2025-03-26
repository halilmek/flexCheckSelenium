Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator
  So that I can check my financing options

  Background:
    Given the user is on the FlexCheck calculator page

  #@wip
  Scenario Outline: Calculate financing with different usage purposes
    When the user selects "<usage_purpose>" as usage purpose
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
    And the user selects an option
    And the user clicks on Angebote anfordern
    And the user uploads id
    Then the user checks, whether the file was uploaded
    And the user clicks on Weiter in DokumentHochladung page
    And the user writes a message
    And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    Then the user should see the success message

    Examples:
      | usage_purpose           |
      | Kauf und Modernisierung|
      #| Kauf                   |
      #| Kauf Neubau           |
      #| Kauf und Erweiterung  |
      #| Modernisierung        |
      #| Neubau                |
      #| Umschuldung           |
      #| sonstige Verwendung   |

#@wip
Scenario Outline: Calculate financing with different property types
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "<property_type>" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "randomLoanAmount"
    And the user enters repayment percentage "randomRepaymentPercentage"
    And the user selects repayment type "Tilgung in %"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the calculation results
    And the user selects an option
    And the user clicks on Angebote anfordern
    And the user uploads id
    Then the user checks, whether the file was uploaded
    And the user clicks on Weiter in DokumentHochladung page
    And the user writes a message
    And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    Then the user should see the success message

    Examples:
      | property_type        |
      | Einfamilienhaus     |
      | Reihenendhaus       |
      | Reihenmittelhaus    |
      | Doppelhaushälfte    |
      | Zweifamilienhaus    |
      | Baugrundstück       |
      | Eigentumswohnung    |


  #@wip
  Scenario: Calculate financing with monthly payment type
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
    Then the user should see the calculation results
    And the user selects an option
    And the user clicks on Angebote anfordern
    And the user uploads id
    Then the user checks, whether the file was uploaded
    And the user clicks on Weiter in DokumentHochladung page
    And the user writes a message
    And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    Then the user should see the success message


  #@wip
  Scenario Outline: Calculate financing with total term payment type
    When the user selects "randomPurpose" as usage purpose
    And the user enters postal code "randomPostalCode"
    And the user enters purchase price "randomPurchasePrice"
    And the user selects "randomPropertyType" as property type
    And the user clicks on Weiter in Angaben zum Objekt page
    And the user enters desired loan amount "randomLoanAmount"
    And the user selects repayment type "Gesamtlaufzeit"
    And the user enters total term in years "<total_term_in_years>"
    And the user enters total term in months "<total_term_in_months>"
    And the user enters first payout date
    And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
    Then the user should see the calculation results
    And the user selects an option
    And the user clicks on Angebote anfordern
    And the user uploads id
    Then the user checks, whether the file was uploaded
    And the user clicks on Weiter in DokumentHochladung page
    And the user writes a message
    And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    Then the user should see the success message

    Examples:
      | total_term_in_years | total_term_in_months |
      | 25                  | 0                   |
      | 0                  | 240                   |




  #@wip
  Scenario Outline: Select all options one by one
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
    And the user selects "<an>" option
    And the user clicks on Angebote anfordern
    And the user uploads id
    Then the user checks, whether the file was uploaded
    And the user clicks on Weiter in DokumentHochladung page
    And the user writes a message
    And the user clicks on jetzt kostenlos und unverbindlich Ihr Angebot anfordern button in Final page
    Then the user should see the success message

    Examples:
      | an |
      | 1  |
      | 2  |
      | 3  |
      | 4  |
