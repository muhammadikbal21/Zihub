Feature: Encounter List with Login Precondition
  Background:
    Given user is on login page
    When input username "superAdmin"
    And input password "admin"
    And click login button
    Then user is navigated to the home page

  Scenario: User views the list of encounter
    Given user click resources menu
    And user is on encounter sub menu
    Then user sees the list of encounter