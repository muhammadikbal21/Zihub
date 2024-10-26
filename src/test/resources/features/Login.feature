Feature: Login
  Scenario: Successful login with valid credentials
    Given user is on login page
    When input username
    And input password
    And click login button
    Then user is navigated to the home page