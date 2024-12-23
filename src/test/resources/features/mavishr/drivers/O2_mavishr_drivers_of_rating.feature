@ciplahr
@driversofrating
Feature: CIPLA HR Drivers Of Rating page validation

  Scenario: Verify that the Page Title and Latest Data update information
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to Mavis HR Drivers of Rating Page
    ##Header Title and Latest data update message
    Then the user should see the drivers of rating page title as Drivers of Employer Rating
    And the latest data uploaded for drivers of rating should match with latest data uploaded in the database

  Scenario: Verify the values in filters are populated from masters successfully
    And the options in drivers of rating page filter Industry should be populated from Category master
    And the options in drivers of rating page filter Country should be populated from Country master
    And the options in drivers of rating page filter Period should be populated from Period master
    ##Above scenario need to run for the below scenarios
  Scenario: Verify the Employee score and Drivers score under summary view for each filter combinations
    Then under Summary View the Employee score and Positive Drivers score for each filter combinations loaded properly
    And under Summary View the Employee score and Negative Drivers score for each filter combinations loaded properly