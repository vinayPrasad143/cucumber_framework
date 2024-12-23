@takeda
@header_footer
@sanity
Feature: Takeda Application Header and Footer Validation
  @US-25663,@25667
  Scenario: Verify that the Header and Footer of the application loaded successfully
    Given the user is logged into the Application Under Test via Graphene Access Point
    Then the user should see the header loaded with below components
      | Header Element        |
      | ATU                   |
      | MCE Touchpoints       |
      | Triggers & Barriers   |
      | Brand Satisfaction    |
      | Message Monitor       |
      | Perception Insights   |
      | Perception Trendlines |
    And the user should see the onboarding message icon and logout button on header
    And the application footer loaded successfully