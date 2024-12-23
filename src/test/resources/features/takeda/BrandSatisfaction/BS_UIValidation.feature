@takeda
@ui_validation
Feature: Brand Satisfaction page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page Brand Satisfaction

  @atu_ui_validation
  Scenario: Ensure that UI elements in Brand Satisfaction page are loaded as expected
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
    Then the user should see the Brand Satisfaction information pops up with details below
      | Section name     | Section content                                                                                                   |
      | Positivity Index | This measures overall positivity towards a brand and the likelihood of advocating the use of brand towards others |
      | Net Satisfaction | This measure the size of segment who are satisfied/happy with a launched brand                                    |
    ##To be added remaining