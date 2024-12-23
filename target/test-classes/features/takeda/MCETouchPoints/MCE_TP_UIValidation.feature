@takeda
@ui_validation
Feature: MCE Touchpoints page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page MCE Touchpoints

  @mcetp_ui_validation
  Scenario: Ensure that UI elements in MCE Touchpoints page are loaded as expected
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
    Then the user should see the MCE Touchpoints information pops up with details below
      | Section name           | Section content                                                                                                                                                                                                                                                   |
      | Touchpoint             | Each touchpoint is scored by AI based on its coverage (reach) and positive engagement (impact) among doctors. Touchpoint with higher scores represent greater levels of effectiveness in securing coverage (reach) and positive engagement (impact) among doctors |
      | Brand Touchpoint Score | Within each touchpoint, brands are scored and ranked against competitors in securing coverage (reach) and positive engagement (impact) among doctors                                                                                                              |
    ##To be added remaining