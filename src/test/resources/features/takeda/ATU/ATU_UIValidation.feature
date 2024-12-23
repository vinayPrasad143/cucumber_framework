@takeda
@ui_validation
Feature: ATU page UI Functionality Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user navigates to takeda page ATU

  @atu_ui_validation
  Scenario: Ensure that UI elements in ATU page are loaded as expected
   ##Given the user is logged into the Application Under Test Directly
    ##Filter panels and its positions
    Then the user should see the filter panels loaded in the specified order
      | Panel         | Position |
      | BUSINESS UNIT | 1        |
      | BENCHMARK     | 2        |
      | INDICATION    | 3        |
      | COUNTRY       | 4        |
      | SEGMENT       | 5        |
      | PERIOD        | 6        |
    ##This has to be validate against DB
    And the user should see the information icon and latest db update message Last update: Sep 2021
    When the user clicks on the information icon panel
    Then the user should see the ATU information pops up with details below
      | Section name         | Section content                                                                                                                                                              |
      | Category Performance | Shows the rolling average of doctors’ conversion rate from unaided mention to usage a brand in the selected period                                                           |
      | Category Performance | When a doctor mentions the brand online, he/she is considered under “Unaided mention”                                                                                        |
      | Category Performance | When a doctor has claimed to prescribe a brand in the past 12 months, he/she is considered to have “Trial/Prescribed”                                                        |
      | Category Performance | When a doctor has claimed to prescribe more than once in the past 12 months and at least once in the past 3 months, the doctor is considered under “Usage” as a regular user |
      | ATU Trendline        | This shows the trend of unaided mentions, trial/prescribed to usage between periods                                                                                          |

    ##Category Performance Section Validation
    And the top section loaded with title Category Performance attached with a help icon
    And the Category Performance section shows three tabs as radio button
      |Unaided Mentions|Trial/Prescribed|Usage|
    And the Sort By Highest on the right side with below options
    |Brand Percentage|Brand Rank|
    And the data loaded in descending order for each Category Performance section

    ##ATU Trendline section
    And the bottom section loaded with title ATU Trendline attached with a help icon
    And the ATU Trendline section shows three tabs as radio button with click disabled
      |Unaided Mentions|Trial/Prescribed|Usage|
    And the selected section in Category Performance should be highlighted in ATU Trendline section also

    ##Functionality of Select All and Brand button
    When the user clicks on Select All button in the brand filter section
    Then all the brands loaded should be selected and highlighted

    When the user selects a specific brand name from the brand names displayed
    Then the selected brand should change its status from enable or disable based on the current state
    And the brand value should be highlighted or grayed out from the chart also

    When the user clicks on takeda brand button
    Then takeda brand is highlighted and other brands are greyed out in chart and filter section

    ## Sort by highest dropdown functionality
    When the user select the option Brand Rank from Sort By Highest section
    Then the Category performance section chart should be loaded with rank values
    When the user select the option Brand Percentage from Sort By Highest section
    Then the Category performance section chart should be loaded with percent values