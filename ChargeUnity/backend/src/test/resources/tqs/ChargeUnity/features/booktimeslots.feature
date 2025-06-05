Feature: Book time slots

  Scenario: Book a time slot in a charging station
    Given the user has selected a charging station,
    When they select a time slot,
    And they submit the reservation, 
    Then the reservation should be confirmed and shown in the user’s bookings panel.
  
  Scenario: Faild to book a time slot because of overlaping
    Given the user already has a reservation at the same time,
    When they try to book another slot,
    Then the system should prevent duplicate bookings.

