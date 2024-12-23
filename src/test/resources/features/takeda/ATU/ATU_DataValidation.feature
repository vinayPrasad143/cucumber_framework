@takeda
@data_validation
Feature: ATU page Data Validation

  Background:
    Given the user is logged into the Application Under Test via Graphene Access Point
    And the user navigates to takeda page ATU
    When the user is able to read the ATU page sp outputs from the database

  @atu_data_validation
  Scenario: Ensure that ATU page populated the data from database as expected
    Then Verify that countries loaded correctly for each bu,benchmark combination
    And Verify that brands loaded correctly for each bu,benchmark and indication combination
    And Verify that Unaided Mentions data loaded correctly in ATU for each brands for all filter combinations
    And Verify that Trial/Prescribed data loaded correctly in ATU for each brands for all filter combinations
    And Verify that Usage data loaded correctly in ATU for each brands for all filter combinations