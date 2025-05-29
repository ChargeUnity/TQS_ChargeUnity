Feature: Search for chargers

  Scenario: Display available charging stations
    Given the user has opened the app,
    When they navigate to the search stations feature,
    And they enter their current location or a destination,
    Then a list of available charging stations in that area should be displayed.