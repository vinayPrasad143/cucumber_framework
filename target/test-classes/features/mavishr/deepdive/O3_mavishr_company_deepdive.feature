@ciplahr
@deepdive
Feature: CIPLA HR DeepDive page validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point

  @deepdive_header
  Scenario: Verify that the Page Title and Latest Data update information
    When the user navigates to Mavis HR Deep Dive Page
    ##Header Title and Latest data update message
    Then the user should see the deep dive page title as Company Deep Dive
    And the latest data uploaded for deep dive should match with latest data uploaded in the database

  @deepdive_filters
  Scenario: Verify the values in filters are populated from masters successfully
    When the user navigates to Mavis HR Deep Dive Page
    Then the options in deep dive page filter Industry should be populated from Category master
    And the options in deep dive page filter Organization should be populated from Brand master
    And the options in deep dive page filter Country should be populated from Country master
    And the options in deep dive page filter Period should be populated from Period master
 ##Note: @deepdive_filters scenario need to run for all combination scenarios
  @deepdive_positive_themes_all_combination
  Scenario: Verify the Positive themes for each filter combinations
    When the user navigates to Mavis HR Deep Dive Page
    Then the Positive Themes for each filter combinations loaded properly

  @deepdive_negative_themes_all_combination
  Scenario: Verify the Negative themes for each filter combinations
    When the user navigates to Mavis HR Deep Dive Page
    Then the Negative Themes for each filter combinations loaded properly

  ##Below scenarios can be run independently to check for some specific combinations
  @deepdive_positive_themes_selected_combination
  Scenario: Verify the Positive themes for the selected combinations
    When the user navigates to Mavis HR Deep Dive Page
    Then the Positive Themes for specific combinations loaded properly
      | Industry        | Organization | Country | Period  |
      | Pharmaceuticals | Cipla        | India   | Q4 2021 |
      | Pharmaceuticals | Abbott       | India   | Q3 2021 |
      | Pharmaceuticals | Abbott       | India   | Q2 2021 |
      | Pharmaceuticals | Alkem        | India   | Q4 2021 |
      | Pharmaceuticals | Glenmark     | India   | Q4 2021 |
      | Pharmaceuticals | Lupin        | India   | Q4 2021 |

  @deepdive_negative_themes_selected_combination
  Scenario: Verify the Negative themes for the selected combinations
    When the user navigates to Mavis HR Deep Dive Page
    Then the Negative Themes for specific combinations loaded properly
      | Industry        | Organization | Country | Period  |
      | Pharmaceuticals | Cipla        | India   | Q4 2021 |
      | Pharmaceuticals | Abbott       | India   | Q3 2021 |
      | Pharmaceuticals | Abbott       | India   | Q2 2021 |
      | Pharmaceuticals | Alkem        | India   | Q4 2021 |
      | Pharmaceuticals | Glenmark     | India   | Q4 2021 |
      | Pharmaceuticals | Lupin        | India   | Q4 2021 |