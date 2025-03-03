Feature: Filter Encounter List with each Status
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
  Scenario: User view list of encounter with each Status Filter
    Given user click status filter
    And user select status and search
    Then user sees the list of encounter with status filter