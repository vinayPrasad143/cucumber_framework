@takeda
@ui_validation
Feature: Triggers and Barriers page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page Triggers and Barriers

  @tb_ui_validation
  Scenario: Ensure that UI elements in Triggers and Barriers page are loaded as expected
    ##Filter panels and its positions
    Then the user should see the filter panels loaded in the specified order
      | Panel         | Position |
      | BUSINESS UNIT | 1        |
      | BENCHMARK     | 2        |
      | INDICATION    | 3        |
      | COUNTRY       | 4        |
      | SEGMENT       | 5        |
    And the user should see the information icon and latest db update message Last update: Sep 2021
    When the user clicks on the information icon panel
    Then the user should see the Triggers and Barriers information pops up with details below
      | Section name | Section content                                     |
      | Triggers     | Reasons cited by doctors to prescribe your brand.   |
      | Barriers     | Challenges cited by doctors to prescribe your brand |
    ##To be added remaining