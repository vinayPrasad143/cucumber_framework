@takeda
@ui_validation
Feature: Message Monitor page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page Message Monitor

  @mm_ui_validation
  Scenario: Ensure that UI elements in Message Monitor page are loaded as expected
    ##Filter panels and its positions
    Then the user should see the filter panels loaded in the specified order
      | Panel         | Position |
      | BUSINESS UNIT | 1        |
      | BENCHMARK     | 2        |
      | INDICATION    | 3        |
      | COUNTRY       | 4        |
      | SEGMENT       | 5        |
      | PERIOD        | 6        |
    And the user should see the information icon and latest db update message Last update: Sep 2021
    When the user clicks on the information icon panel
    Then the user should see the Message Monitor information pops up with details below
      | Section name             | Section content                                                                             |
      | Monthly Average Messages | This shows the messages from the brand and the reactions from doctors/patients to the brand |
      | Brand Trends             | This shows each brand’s share of voice and sentiment of doctors/patients reaction           |
      | Top Message Trends       | This shows the top brand-related themes between periods.                                    |
    ##To be added remaining