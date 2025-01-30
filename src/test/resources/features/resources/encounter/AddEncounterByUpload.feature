Feature: Add Encounter By Upload CSV
  Background:
    Given user is on login page
    When input username "superAdmin"
    And input password "admin"
    And click login button
    Then user is navigated to the home page

    Given user click resources menu
    And user is on encounter sub menu
    Then user sees the list of encounter

  @TestHere
  Scenario: User success add encounter by upload
    Given user click add data button
    And user click import csv tab
    And user click upload file
    And user see pop up success upload and saved file
    And user get auto reload page
    Then user will see new encounter in the table
