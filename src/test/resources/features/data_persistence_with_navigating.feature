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
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user clicks on back button to return to upload page
      Then the user should see the uploaded file
      And the user clicks on weiter button to return to last page
      Then the user should see the text, what the user wrote in the message field before
      And the user clicks on back button to return to upload page
      And the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page
      Then the user should see, what the user selected in option selection page
      And the user clicks on back button to return to Angaben zum Finanzierungswunsch page
      Then the user should see same values for Tilgung in % as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on back button to return to Angaben zum Objekt page
      Then the user should see same values, as the user entered in Objekt page before


    #@wip #Failed=There is no zurück button in monatliche Rate page
    Scenario: Verify data persistence when navigating back through dialogs with monatliche Rate as payment type
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
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user clicks on back button to return to upload page
      Then the user should see the uploaded file
      And the user clicks on weiter button to return to last page
      Then the user should see the text, what the user wrote in the message field before
      And the user clicks on back button to return to upload page
      And the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page
      Then the user should see, what the user selected in option selection page
      And the user clicks on back button to return to Angaben zum Finanzierungswunsch page
      Then the user should see same values for monatliche Rate as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on back button to return to Angaben zum Objekt page
      Then the user should see same values, as the user entered in Objekt page before


    #@wip #Failed=There is no zurück button in Gesamtlaufzeit page
    Scenario: Verify data persistence when navigating back through dialogs with Gesamtlaufzeit as payment type
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
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user clicks on back button to return to upload page
      Then the user should see the uploaded file
      And the user clicks on weiter button to return to last page
      Then the user should see the text, what the user wrote in the message field before
      And the user clicks on back button to return to upload page
      And the user clicks on back button to return to Wahlen Sie Ihre gewünschte Kondition page
      Then the user should see, what the user selected in option selection page
      And the user clicks on back button to return to Angaben zum Finanzierungswunsch page
      Then the user should see same values for Gesamtlaufzeit as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on back button to return to Angaben zum Objekt page
      Then the user should see same values, as the user entered in Objekt page before


    #@wip #Failed => When the page is refreshed, the data is not persisted and the user is redirected to the first page
    Scenario: Verify data persistence by refreshing the page with Tilgung in % as payment type
      When the user selects "randomPurpose" as usage purpose
      And the user enters postal code "randomPostalCode"
      And the user enters purchase price "randomPurchasePrice"
      And the user selects "randomPropertyType" as property type
      #And the user refreshes the page
      #Then the user should see same values, as the user entered in Objekt page before
      And the user clicks on Weiter in Angaben zum Objekt page
      And the user enters desired loan amount "randomLoanAmount"
      And the user selects repayment type "Tilgung in %"
      And the user enters repayment percentage "randomRepaymentPercentage"
      And the user enters first payout date
      And the user refreshes the page
      Then the user should see same values for Tilgung in % as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user refreshes the page
      Then the user should see, what the user selected in option selection page
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user refreshes the page
      Then the user should see the uploaded file
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user refreshes the page
      Then the user should see the text, what the user wrote in the message field before


    #@wip #Failed => When the page is refreshed, the data is not persisted and the user is redirected to the first page
    Scenario: Verify data persistence by refreshing the page with monatliche Rate as payment type
      When the user selects "randomPurpose" as usage purpose
      And the user enters postal code "randomPostalCode"
      And the user enters purchase price "randomPurchasePrice"
      And the user selects "randomPropertyType" as property type
      #And the user refreshes the page
      #Then the user should see same values, as the user entered in Objekt page before
      And the user clicks on Weiter in Angaben zum Objekt page
      And the user enters desired loan amount "randomLoanAmount"
      And the user selects repayment type "monatliche Rate"
      And the user enters monthly payment "randomMonthlyPayment"
      And the user enters first payout date
      And the user refreshes the page
      Then the user should see same values for monatliche Rate as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user refreshes the page
      Then the user should see, what the user selected in option selection page
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user refreshes the page
      Then the user should see the uploaded file
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user refreshes the page
      Then the user should see the text, what the user wrote in the message field before


    #@wip #Failed => When the page is refreshed, the data is not persisted and the user is redirected to the first page
    Scenario: Verify data persistence by refreshing the page with Gesamtlaufzeit as payment type
      When the user selects "randomPurpose" as usage purpose
      And the user enters postal code "randomPostalCode"
      And the user enters purchase price "randomPurchasePrice"
      And the user selects "randomPropertyType" as property type
      #And the user refreshes the page
      #Then the user should see same values, as the user entered in Objekt page before
      And the user clicks on Weiter in Angaben zum Objekt page
      And the user enters desired loan amount "randomLoanAmount"
      And the user selects repayment type "Gesamtlaufzeit"
      And the user enters total term in years "randomJahre"
      And the user enters total term in months "randomMonate"
      And the user enters first payout date
      And the user refreshes the page
      Then the user should see same values for Gesamtlaufzeit as payment type, as the user entered in Finanzierungswunsch page before
      And the user clicks on Weiter in Angaben zum Finanzierungswunsch page
      #And the user should see the calculation results
      And the user selects "randomOption" option
      And the user refreshes the page
      Then the user should see, what the user selected in option selection page
      And the user clicks on Angebote anfordern
      And the user uploads id
      And the user checks, whether the file was uploaded
      And the user refreshes the page
      Then the user should see the uploaded file
      And the user clicks on Weiter in DokumentHochladung page
      And the user writes a message
      And the user refreshes the page
      Then the user should see the text, what the user wrote in the message field before


    #@wip #TODO: Checking other display values for selected option in offer selection page
    Scenario: Verify data persistence when navigating back through dialogs with Gesamtlaufzeit as payment type
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
      #And the user should see the calculation results
      And the user selects "randomOption" option
      Then the user should verify the option is selected