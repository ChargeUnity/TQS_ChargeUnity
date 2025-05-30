Feature: Register charging station

    Scenario: Register a new charging station
        Given I am on the station page
        When I navigate to the register station feature
        And I enter valid details and submit
        Then the new station is added to the system

    Scenario: Register a duplicate station
        Given that a station with the same name already exists
        When I try to register a duplicate
        Then the system shows an error preventing duplication