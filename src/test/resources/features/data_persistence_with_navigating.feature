Feature: FlexCheck Calculator Functionality
  As a potential customer
  I want to use the financing calculator and check the details
  So that I can see the details, as i entered in the calculator

    Background:
        Given the user is on the FlexCheck calculator page

    #@wip
    Scenario: Verify data persistence when navigating back through dialogs with Tilgung in % as payment type
      When the user selects "randomPurpose" as usage purpose
      And the user enters postal code "randomPostalCode"
      And the user enters purchase price "randomPurchasePrice"
      And the user selects "randomPropertyType" as property type
      And the user clicks on Weiter in Angaben zum Objekt page
      And the user enters desired loan amount "randomLoanAmount"
      And the user selects repayment type "Tilgung in %"
      And the user enters repayment percentage "randomRepaymentPercentage"
      And the user enters first payout date
      And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
      And the user should see the calculation results
      And the user clicks on back button to return to previous page
      Then the loan amount should still be "randomLoanAmount"
      And the repayment percentage should still be "randomRepaymentPercentage"
      And the first payout date should be preserved
      And the user clicks on back button to return to first page
      Then the postal code should still be "randomPostalCode"
      And the purchase price should still be "randomPurchasePrice"
      And the property type should still be "randomPropertyType"