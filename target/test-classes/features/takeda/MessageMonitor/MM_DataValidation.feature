@takeda
@data_validation
Feature: MCE Touchpoints page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page Message Monitor
    When the user is able to read the Message Monitor page sp outputs from the database

  @mm_data_validation
  Scenario: Ensure that Message Monitor page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that brands loaded correctly for each bu,benchmark and indication combination in the Brand Selection
    ##For each combination of filters OR Selected combinations check below data loading
    And Verify that Monthly Average across selected Period Multi Brand - Brand Actions loaded properly
    And Verify that HCP - Positive Reactions to the Brand loaded properly
    And Verify that HCP - Negative Reactions to the Brand loaded properly
    ##To be added for two more charts
