Feature: Start and monitor EV charging session

  Scenario: Access driver page
    Given I am in the home page
    When I click on "Driver"
    And I click on Driver "3"
    Then I should see the "Manuel Silva" page

  Scenario: Start charging session
    Given I am on the Driver "Manuel Silva" page
    When I click on "my-bookings" button
    And I click on "Start Charging" in the "start-charging-button-2"
    Then I should see "Stop Charging" in the "stop-charging-button-2"