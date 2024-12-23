@takeda
@data_validation
Feature: Brand Satisfaction page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page Brand Satisfaction
    When the user is able to read the Brand Satisfaction page sp outputs from the database

  @bs_data_validation
  Scenario: Ensure that ATU page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that brands loaded correctly for each bu,benchmark and indication combination
    And Verify that Positivity Index data loaded correctly in Brand Satisfaction for each brands for all filter combinations
    And Verify that Net Satisfaction data loaded correctly in Brand Satisfaction for each brands for all filter combinations