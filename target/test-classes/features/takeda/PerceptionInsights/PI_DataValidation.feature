@takeda
@data_validation
Feature: Perception Insights page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page Perception Insights
    When the user is able to read the Perception Insights page sp outputs from the database

  @pi_data_validation
  Scenario: Ensure that Message Monitor page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that brands loaded correctly for each bu,benchmark and indication combination in the Brand Selection

    ##To be added for two more charts
