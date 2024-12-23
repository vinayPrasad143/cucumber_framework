@takeda
@data_validation
Feature: MCE Touchpoints page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page MCE Touchpoints
    When the user is able to read the MCE Touchpoints page sp outputs from the database

  @mcetp_data_validation
  Scenario: Ensure that MCE Touchpoints page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that brands loaded correctly for each bu,benchmark and indication combination in the Brand Selection
    And Verify that the Touchpoints are loaded properly under Multi Channel Engagement Touchpoints
    And Verify that the Brand Touchpoint Score for each Touchpoint is loaded properly
    And Verify that the Sub Ratings for each Touchpoint is loaded properly