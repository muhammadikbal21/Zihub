Feature: Encounter List with Date Filter
  Background:
    Given user is on login page
    When input username
    And input password
    And click login button
    Then user is navigated to the home page

    Given user click resources menu
    And user is on encounter sub menu
    Then user sees the list of encounter

  Scenario: User views the list of encounter with date filter
    Given user click from date
    And user select from date
    And user click to date
    And user select to date
    And user click search
    Then user sees the list of encounter with date filter