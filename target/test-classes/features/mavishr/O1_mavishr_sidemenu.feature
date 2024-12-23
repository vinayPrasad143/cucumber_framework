@ciplahr
@sidemenu
Feature: CIPLA HR Side Menu Validation

  Scenario: Verify that the Side menu contents are loaded as per the configuration
    Given the user is logged into the Application Under Test via Graphene Access Point
    When the user should see the deep dive page title as Drivers of Employer Rating
    Then the page names should show in shortended format in the right order
    When click on the hamburger menu icon
    Then the page names should show in expanded format in the right order
    And the browser title, header logo and footer information should be loaded properly
    When click on hamburger close button
    Then the page names should show in shortended format in the right order