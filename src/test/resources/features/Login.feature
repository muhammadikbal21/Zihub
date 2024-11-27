Feature: Login
  Scenario: Successful login with valid credentials
    Given user is on login page
    When input username "superAdmin"
    And input password "admin"
    And click login button
    Then user is navigated to the home page