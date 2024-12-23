@takeda
@data_validation
Feature: Triggers and Barriers page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page Triggers and Barriers
    When the user is able to read the Triggers and Barriers page sp outputs from the database

  @tb_data_validation
  Scenario: Ensure that Triggers and Barriers page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that Triggers to Prescribe data loaded correctly in Triggers and Barriers for each brands for all filter combinations
    And Verify that Barriers to Prescribe data loaded correctly in Triggers and Barriers for each brands for all filter combinations