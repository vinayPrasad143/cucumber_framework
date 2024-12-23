@takeda
@ui_validation
Feature: Perception Insights page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page Perception Insights

  @mm_ui_validation
  Scenario: Ensure that UI elements in Perception Insights page are loaded as expected
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
    Then the user should see the Perception Insights information pops up with details below
      | Section name             | Section content                                                                             |
      | Composite Brand Index (CBI) | CBI shows an overall brand score based on drivers |

    ##To be added remaining