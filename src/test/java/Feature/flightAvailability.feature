Feature: Validate the availability of the flights

  @API
  Scenario: Verify if user could see the availability of the flight
    When user hits the availability api for checking the flights
    Then user verifies the statuscode